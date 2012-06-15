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