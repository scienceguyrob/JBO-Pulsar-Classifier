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
 * File name: 	AutoClassificationController.java
 * Package: uk.ac.man.jb.pct.mvc.controllers
 * Created:	Jul 1, 2012
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
 * AutoClassificationController
 * 
 * Requires: 
 * 
 * Path to a previously saved neural network 
 * The path to the data to be classified
 * The path to the file to output results to.
 * 
 * Optional: Type of clustering technique to use.
 * 
 * @author Rob Lyon
 */
public class AutoClassificationController implements I_Controller
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
    public AutoClassificationController(I_CommandLineInputData params) { this.input = params; }

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

		// Load Map
		SOMClassifier classifier = (SOMClassifier)SOMClassifier.read(input.getPathToSavedNetwork());

		System.out.println("Details of classifier loaded:");
		System.out.println(classifier.getStats().toString());
		
		// How many data attributes
		//if(map.getAttributes() != input data attrobutes...)

		// Validate against validation training set
		int classifierChoice = input.getClassifier();
		System.out.println("Using classifier: "+classifierChoice);
		
		if()// File contains list of file paths (to files containing a line of data)
		else if()// File contains list of input patterns

		for
		if(classifierChoice < 0)
		    classifier.classify(p, 0);
		else
		    classifier.classify(p, classifierChoice);


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
	// Requires: Path to a previously saved neural network, the path to the data to be classified, 
	//           and the path to the file to output results to.
	// Optional: Type of clustering technique to use.

	if(!Common.fileExist(this.input.getPathToSavedNetwork()))
	{
	    System.out.println("Error: Invalid neural network file");
	    return false;
	}

	if(Common.fileExist(this.input.getPathToClassificationFile()))
	{
	    System.out.println("Error: File containing data for classification invalid");
	    return false;
	}

	if(!Common.isPathValid(this.input.getPathToOutputFile()))
	{
	    System.out.println("Error: The path to output classifications to is invalid");
	    return false;
	}

	return true;
    }
}
