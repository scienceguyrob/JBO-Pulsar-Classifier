/**
 *
 * This file is part of the JBO Pulsar Classifier Tool application.
 *
 * The JBO Pulsar Classifier Tool is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The JBO Pulsar Classifier Tool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the JBO Pulsar Classifier Tool.  If not, see <http://www.gnu.org/licenses/>.
 *
 * File name: 	AutoTrainerController.java
 * Package: uk.ac.man.jb.pct.mvc.controllers
 * Created:	Jun 15, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.mvc.controllers;

import uk.ac.man.jb.pct.classifiers.som.SOMClassifier;
import uk.ac.man.jb.pct.classifiers.som.SelfOrganizingMap;
import uk.ac.man.jb.pct.data.I_ClassifierStatistics;
import uk.ac.man.jb.pct.data.I_DataSet;
import uk.ac.man.jb.pct.data.PatternFileProcessor;
import uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData;
import uk.ac.man.jb.pct.util.Common;

/**
 * AutoTrainerController.
 * 
 * Requires: Training set, Validation set, a desired classification accuracy,
 * and path to save the constructed network to.
 * 
 * @author Rob Lyon
 */
public class AutoTrainerController implements I_Controller
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The input parameters provided by the user.
     */
    private I_CommandLineInputData input;

    //*****************************************
    //*****************************************
    //             Constructor
    //*****************************************
    //*****************************************

    /**
     * Default constructor.
     */
    public AutoTrainerController(I_CommandLineInputData params){ this.input = params; }

    //*****************************************
    //*****************************************
    //               Getters
    //*****************************************
    //*****************************************
    
    // None

    //*****************************************
    //*****************************************
    //               Setters
    //*****************************************
    //*****************************************

    public void setParameters(I_CommandLineInputData params) { this.input = params; }

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /**
     * Executes the automated trainer.
     */
    public void run()
    {
	if(input != null)
	{
	    if(this.validateParameters())
	    {
		// We Now know the parameters are valid, so we can proceed
		// to automatically train the network.

		// Get Training & Validation Data
		I_DataSet t_data = new PatternFileProcessor().process(this.input.getPathToTrainingFile());
		I_DataSet v_data = new PatternFileProcessor().process(this.input.getPathToValidationFile());


		System.out.println("Training Data Rows:"+t_data.getRows());
		System.out.println("Training Data Cols:"+t_data.getColumns());

		System.out.println("Validation Data Rows:"+v_data.getRows());
		System.out.println("Validation Data Cols:"+v_data.getColumns());

		// Major error if we return at this stage.
		if(t_data == null | v_data == null)
		    return;

		boolean accuracyAttained = false;

		int count = 0;
		
		while(!accuracyAttained && count < 100)
		{
		    // Build Map
		    SelfOrganizingMap map = new SelfOrganizingMap(t_data.getDataAsArrayList());
		    
		    // How many data attributes
		    map.setAttributes(t_data.getColumns());
		    
		    // What map width to use?
		    if(this.input.getMapSize() > 1 && this.input.getMapSize() < 25)
			map.setMapWidth(this.input.getMapSize());
		    else
			map.setMapWidth(10); // Default

		    // Normalise Inputs
		    map.NormalisePatterns();

		    // Build map
		    map.Build();

		    // Train map
		    map.Train(map.maximumErrorRate);

		    // Find Clusters of positive pulsar instances
		    SOMClassifier classifier = new SOMClassifier(map);

		    if(!classifier.locateClusters(t_data))
		    {
			System.out.println("No clusters found");
			break;
		    }

		    // Validate against validation training set
		    int classifierChoice = input.getClassifier();
		    System.out.println("Using classifier: "+classifierChoice);
		    
		    if(classifierChoice < 0)
			classifier.validate(v_data, 0);
		    else
			classifier.validate(v_data, classifierChoice);

		    // Get Statistics
		    I_ClassifierStatistics stats = classifier.getStatistics();

		    // If accuracy sufficient, save map.
		    
		    int accuracy = (int)Math.round( stats.getAccuracy() * 100 );
		    int desiredAccuracy = (int)input.getDesiredNetworkAccuracy();
		    int precision = (int)Math.round( stats.getPrecision() * 100 );
		    
		    if(accuracy >= desiredAccuracy && precision > 50)
		    {
			accuracyAttained = true;
			System.out.println("Accuracy attained: "+accuracy);
			System.out.println("Rounded accuracy: " + desiredAccuracy);
			
			// Save 
			Common.fileDelete(input.getNetworkSavePath());
			
			if(SOMClassifier.write(classifier,input.getNetworkSavePath()))
			    System.out.println("Self Organizing Map state persisted.");
			else
			    System.out.println("Self Organizing Map state could not be persisted!");
		    }
		    else
		    {
			System.out.println("Current Accuracy: "+accuracy);
		    }
		    
		    count++;
		}

	    }
	}
	else
	    System.out.println("Error: Command Line input parameters are null");

    }

    /**
     * This method validates that the variables used by this class
     * are valid.
     * @return true if the variables are valid, else false.
     */
    public boolean validateParameters()
    {
	// First check that the provided parameters are
	// valid, i.e. file paths actually lead to a file etc.
	//
	// This class requires the following variables to be correct
	// before proceeding.
	//
	// Requires: Training set, Validation set, a desired 
	// classification accuracy, and path to save the constructed
	// network to.

	if(!Common.isPathValid(this.input.getPathToTrainingFile()) | 
		!Common.fileExist(this.input.getPathToTrainingFile()))
	{
	    System.out.println("Error: Training Set invalid");
	    return false;
	}

	if(!Common.isPathValid(this.input.getPathToValidationFile()) | 
		!Common.fileExist(this.input.getPathToValidationFile()))
	{
	    System.out.println("Error: Validation Set invalid");
	    return false;
	}

	if(input.getDesiredNetworkAccuracy() < 0 | input.getDesiredNetworkAccuracy() >= 100)
	{
	    System.out.println("Error: Desired Network Accuracy invalid");
	    return false;
	}

	if(!Common.isPathValid(this.input.getNetworkSavePath()))
	{
	    System.out.println("Error: The path to save the trained netork to is invalid");
	    return false;
	}

	return true;
    }
}