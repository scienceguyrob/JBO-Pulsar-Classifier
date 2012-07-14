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
import uk.ac.man.jb.pct.data.I_DataSet;
import uk.ac.man.jb.pct.data.I_InputPattern;
import uk.ac.man.jb.pct.data.OutputResults;
import uk.ac.man.jb.pct.data.PatternFileProcessor;
import uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData;
import uk.ac.man.jb.pct.util.Common;
import uk.ac.man.jb.pct.util.StringOps;

/**
 * AutoClassificationController. This class is used to automatically
 * classify pulsar data, using a SOM classifier constructed using the
 * AutoTrainerController class.
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
	// If the user has supplied some input
	if(input != null)
	{
	    // If the user supplied input is valid
	    if(this.validateParameters())
	    {
		// We Now know the parameters are in principle valid.
		// Some more checks are required, which were not performed before
		// simply for efficiency sake. So we now proceed
		// to automatically classify the data if we can.

		// Load Map
		SOMClassifier classifier = (SOMClassifier)SOMClassifier.read(input.getPathToSavedNetwork());

		// Get the number of attributes that the classifier expects
		int attributes = classifier.getMap().getAttributes();

		System.out.println("Details of classifier loaded:");
		System.out.println(classifier.getStats().toString());
		System.out.println("Number of parameters used by classifier: " + attributes);

		// Get the type of classifier that the user wants to use.
		int classifierChoice = input.getClassifier();
		System.out.println("Using classifier: "+classifierChoice);

		// Now the user may provide a variety of inputs, either
		// 
		// 1. A file containing raw data
		// 2. A file containing paths to OTHER files that contain raw data
		// 3. A directory path, to a directory containing data files.
		// 4. A list of file paths, passed in from the unix terminal.

		PatternFileProcessor p = new PatternFileProcessor(); // Used to read files etc.

		I_DataSet data; // Used to store the data read in.

		OutputResults out = new OutputResults(); // Used to output the results obtained.
		out.setOutputPath(this.input.getPathToOutputFile());
		
		// Clear the output file if it exists.
		if(Common.fileExist(this.input.getPathToOutputFile()))
		    Common.fileDelete(this.input.getPathToOutputFile());

		// Has the user given us a file?
		if(Common.fileExist(this.input.getPathToClassificationFile()))
		{
		    //*********************
		    //      Case 1.
		    //*********************
		    // Is the file a raw data file? If yes, read this file,
		    // and classify the data it contains.
		    if(this.input.getPathToClassificationFile().contains(".phcx")) // Raw data file
		    {
			// Read data file, and create an input pattern instance
			// containing the data ready for classification.
			I_InputPattern pattern = p.processPatternFile(this.input.getPathToClassificationFile());

			if(pattern == null)
			{
			    System.out.println("Input data for classification invalid.");
			    return;
			}

			// If the pattern has the same number of attributes as the 
			// map to be used to classify it.
			if(pattern.getDataLength() != attributes)
			    System.out.println("This map cannot be used to classify this data - parameter mismatch.");
			else
			{
			    // Classify the input pattern
			    String classification = classifier.classify(pattern, input.getClassifier());
			    System.out.println("Pattern: "+ StringOps.getFileNameFromPath(pattern.getName()) + " Classification: "+ classification);
			    out.process(pattern, classification);// Write to file.

			    // Finally makes sure output file has details of the classifier used etc
			    out.writeSummaryStatisitics(classifier.getStatistics(),input.getPathToOutputFile());
			}
		    }
		    //*********************
		    //      Case 2.
		    //*********************
		    // The input is a file containing links to other files.
		    else
		    {
			// Read the file into a data set object
			data = p.processLinkFile(this.input.getPathToClassificationFile());

			if(data == null)
			{
			    System.out.println("Input data for classification invalid.");
			    return;
			}

			// If the pattern has the same number of attributes as the 
			// map to be used to classify it.
			if(data.getColumns() != attributes)
			    System.out.println("This map cannot be used to classify this data - parameter mismatch.");
			else
			{
			    // For each data file linked to
			    for(int i = 0; i < data.getRows(); i ++)
			    {
				// Classify the file
				String classification = classifier.classify(data.getDataRow(i), input.getClassifier());
				System.out.println("Pattern: "+ StringOps.getFileNameFromPath(data.getDataRow(i).getName()) + " Classification: "+ classification);
				out.process(data.getDataRow(i), classification);// Write to file.
			    }

			    // Finally makes sure output file has details of the classifier used etc
			    out.writeSummaryStatisitics(classifier.getStatistics(), input.getPathToOutputFile());
			}
		    }		
		}
		//*********************
		//      Case 3.
		//*********************
		// User has provided a directory path.
		else if(Common.isDirectory(this.input.getPathToClassificationFile()))
		{
		    // Get all the files in the specified directory
		    String[] inputFiles = Common.getFilePaths(this.input.getPathToClassificationFile(),".phcx.gz.dat");

		    // If there are files to be processed
		    if(inputFiles.length > 0)
		    {
			// For each file in the directory
			for(int i = 0; i < inputFiles.length; i++)
			{
			    // Create an input pattern object from the file
			    I_InputPattern pattern = p.processPatternFile(inputFiles[i]);

			    if(pattern == null)
				break;

			    // If the pattern has the same number of attributes as the 
			    // map to be used to classify it.
			    if(pattern.getDataLength() != attributes)
				System.out.println("This map cannot be used to classify this data - parameter mismatch.");
			    else
			    {
				// Classify the pattern
				String classification = classifier.classify(pattern, input.getClassifier());
				System.out.println("Pattern: "+ StringOps.getFileNameFromPath(pattern.getName()) + " Classification: "+ classification);
				out.process(pattern, classification);// Write to file.
			    }
			}

			// Finally makes sure output file has details of the classifier used etc
			out.writeSummaryStatisitics(classifier.getStatistics(), input.getPathToOutputFile());
		    }
		}
		//*********************
		//      Case 4.
		//*********************
		// File paths passed in from the unix terminal
		else
		{
		    // Count unix parameters
		    int unixParams = input.getExtraParams().size();

		    // If there are files to be processed
		    if(this.input.getExtraParams().size() > 0)
		    {
			// For each file in the directory
			for(int i = 0; i < unixParams; i++)
			{
			    if(this.input.getExtraParams().elementAt(i).contains(".phcx")) // Raw data file
			    {
				// Create an input pattern object from the file
				I_InputPattern pattern = p.processPatternFile(this.input.getExtraParams().elementAt(i));

				if(pattern == null)
				    break;

				// If the pattern has the same number of attributes as the 
				// map to be used to classify it.
				if(pattern.getDataLength() != attributes)
				    System.out.println("This map cannot be used to classify this data - parameter mismatch.");
				else
				{
				    // Classify the pattern
				    String classification = classifier.classify(pattern, input.getClassifier());
				    System.out.println("Pattern: "+ StringOps.getFileNameFromPath(pattern.getName()) + " Classification: "+ classification);
				    out.process(pattern, classification); // Write to file.
				}
			    }
			}

			// Finally makes sure output file has details of the classifier used etc
			out.writeSummaryStatisitics(classifier.getStatistics(), input.getPathToOutputFile());
		    }
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
	// Requires: Path to a previously saved neural network, the path to the data to be classified, 
	//           and the path to the file to output results to.
	// Optional: Type of clustering technique to use.

	// Check the path to an existing neural network to use for classification
	// points to a real file.
	if(!Common.fileExist(this.input.getPathToSavedNetwork()))
	{
	    System.out.println("Error: Invalid neural network file");
	    return false;
	}

	// Check the path to the file containing classification data is valid.
	// This file may not be file, but may be one of four possible things...
	//
	// 1. A file containing links to OTHER files that contain classification data. 
	// 2. A file containing classification data.
	// 3. A path to a directory holding files containing classification data
	// 4. No file path, or directory path may be provided. But file paths
	//    may be piped in from a unix terminal. In this case, these paths
	//    will be stored in a different variable, so we must check this too.
	if(!Common.fileExist(this.input.getPathToClassificationFile()) & // No valid file of any kind
		!Common.isPathValid(this.input.getPathToClassificationFile()) & // No valid directory path
		this.input.getExtraParams().size() < 1) // No parameters passed in from unix terminal
	{
	    System.out.println("Error: File\\Directory containing data for classification invalid");
	    return false;
	}

	// Check the path that the user wants to write output data to is valid.
	if(!Common.isPathValid(this.input.getPathToOutputFile()))
	{
	    System.out.println("Error: The path to output classifications to is invalid");
	    return false;
	}

	return true;
    }
}