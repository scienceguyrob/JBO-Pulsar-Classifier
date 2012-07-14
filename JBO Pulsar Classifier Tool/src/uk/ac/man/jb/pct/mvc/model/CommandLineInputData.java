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
 * File name: 	CommandLineInputData.java
 * Package: uk.ac.man.jb.pct.mvc.model
 * Created:	June 15, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.mvc.model;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import uk.ac.man.jb.pct.io.Reader;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.util.Common;
import uk.ac.man.jb.pct.util.StringOps;

/**
 * This class is used to process and store the command line
 * input to the application. It is simply a wrapper that tries
 * to simply the process of passing these in.
 * 
 * @author Rob Lyon
 */
public class CommandLineInputData implements I_CommandLineInputData
{
    //*****************************************
    //*****************************************
    //                Variables
    //*****************************************
    //*****************************************

    /**
     * The logging flag.
     */
    boolean logging = true;

    /**
     * The language to be used in the application.
     */
    String language = "";

    /**
     * The country this application is being run in.
     */
    String country = "";

    /**
     * Path to the training set.
     */
    String trainingSetPath = "";

    /**
     * Path to the validation set.
     */
    String validationSetPath = "";

    /**
     * Path to the classification set.
     */
    String classificationSetPath = "";

    /**
     * Path to the file to write classifications to.
     */
    String outputFile = "";

    /**
     * The path to an existing neural network.
     */
    String pathToSavedNetwork = "";

    /**
     * The path to save a neural network to.
     */
    String saveNeuralNetworkToPath = "";

    /**
     * The size of the map.
     */
    int mapWidth = -1;

    /**
     * The desired accuracy of the network to be trained.
     */
    int desiredAccuracy = -1;

    /**
     * The type of classifier to use.
     */
    int classifier = 0;

    /**
     * The number of tests to run against the classifier.
     */
    int tests = -1;

    /**
     * The value of k for a KNN-Classifier.
     */
    int k = 3;

    /**
     * Any extra parameters not expected or pre-defined.
     */
    Vector<String> extraParams = new Vector<String>();

    //*****************************************
    //*****************************************
    //             Constructor
    //*****************************************
    //*****************************************

    /**
     * Default constructor.
     */
    public CommandLineInputData(){}

    //*****************************************
    //*****************************************
    //         Getters & Setters
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#enableLogging()
     */
    @Override
    public void enableLogging() { this.logging = true; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#disableLogging()
     */
    @Override
    public void disableLogging() { this.logging = false; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#isLoggingEnabled()
     */
    @Override
    public boolean isLoggingEnabled() { return this.logging; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setLanguage(java.lang.String)
     */
    @Override
    public void setLanguage(String l) { this.language = l; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getLanguage()
     */
    @Override
    public String getLanguage() { return this.language; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setLocale(java.lang.String)
     */
    @Override
    public void setCountry(String l){ this.country = l; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getLocale()
     */
    @Override
    public String getCountry() { return this.country; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setPathToSavedNetwork(java.lang.String)
     */
    @Override
    public void setPathToSavedNetwork(String p) { this.pathToSavedNetwork = p; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getPathToSavedNetwork()
     */
    @Override
    public String getPathToSavedNetwork() { return this.pathToSavedNetwork; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setNetworkSavePath(java.lang.String)
     */
    @Override
    public void setNetworkSavePath(String p) { this.saveNeuralNetworkToPath = p; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getNetworkSavePath()
     */
    @Override
    public String getNetworkSavePath() { return this.saveNeuralNetworkToPath; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setPathToTrainingFile(java.lang.String)
     */
    @Override
    public void setPathToTrainingFile(String p) { this.trainingSetPath = p; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getPathToTrainingFile()
     */
    @Override
    public String getPathToTrainingFile() { return this.trainingSetPath; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setPathToValidationFile(java.lang.String)
     */
    @Override
    public void setPathToValidationFile(String p) { this.validationSetPath = p; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getPathToValidationFile()
     */
    @Override
    public String getPathToValidationFile() { return this.validationSetPath; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setPathToClassificationFile(java.lang.String)
     */
    @Override
    public void setPathToClassificationFile(String p) { this.classificationSetPath = p; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getPathToClassificationFile()
     */
    @Override
    public String getPathToClassificationFile() { return this.classificationSetPath; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setPathToOutputFile(java.lang.String)
     */
    @Override
    public void setPathToOutputFile(String p) { this.outputFile = p; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getPathToOutputFile()
     */
    @Override
    public String getPathToOutputFile() { return this.outputFile; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setMapSize(int)
     */
    @Override
    public void setMapSize(int s) { this.mapWidth = s; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getMapSize()
     */
    @Override
    public int getMapSize() { return this.mapWidth; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setDesiredNetworkAccuracy(int)
     */
    public void setDesiredNetworkAccuracy(int a) { this.desiredAccuracy = a; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getDesiredNetworkAccuracy()
     */
    public int getDesiredNetworkAccuracy() { return this.desiredAccuracy; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setTests(int)
     */
    public void setTests(int a) { this.tests = a; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getTests()
     */
    public int getTests() { return this.tests; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setClassifier(int)
     */
    public void setClassifier(int i) { this.classifier = i; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getClassifier()
     */
    public int getClassifier() { return this.classifier; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#setK(int)
     */
    public void setK(int i) { this.k = i; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getK()
     */
    public int getK() { return this.k; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getExtraParams()
     */
    public Vector<String> getExtraParams() { return this.extraParams; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#loadSettings(java.lang.String)
     */
    public void loadSettings(String path)
    {
	try
	{
	    // Read the settings file, and store in an array.
	    String[] contents = Reader.getContents(path).split("\r");

	    // For each line from the settings file
	    for(int i = 0 ; i < contents.length ; i++)
	    {
		if( !contents[i].startsWith(" ") && // As long as the line doesn't begin with whitespace
			!contents[i].startsWith("//") && // or a comment
			!contents[i].startsWith("#") && // or a hash symbol
			!contents[i].contains("-settings=")) // or the path to another settings file
		    this.processArgument(contents[i]); // THEN, process the argument.
	    }
	}
	catch(NullPointerException npe){ System.out.println("Error reading settings file.");}
    }

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#getResourceBundle()
     */
    @Override
    public ResourceBundle getResourceBundle()
    {
	// Get locale and country information.
	if (Common.isEmptyString(language)) 
	    language = new String("en");

	if (Common.isEmptyString(country)) 
	    country = new String("UK");

	// The locale for this application
	Locale currentLocale;

	// The bundle containing supported languages
	final ResourceBundle messages;

	try { currentLocale = new Locale(language, country); } 
	catch(Exception e)
	{
	    language = new String("en");
	    country = new String("UK");
	    currentLocale = new Locale(language, country);
	}

	messages = ResourceBundle.getBundle("i18N.MessagesBundle", currentLocale);

	System.out.println(messages.getString("greetings"));
	System.out.println(messages.getString("inquiry"));
	System.out.println(messages.getString("farewell"));

	return messages;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#isValid()
     */
    @Override
    public String getActionPossibleFromInputs()
    {
	// There are four possible automation cases:
	//
	// 1. User wants to train a network automatically
	// 2. User wants to classify data.
	// 3. User wants to tests the classifier
	//
	// Each of these cases requires slightly different inputs, so this method
	// must ascertain if the application has been provided with enough information 
	// to perform these tasks.
	//
	// We will try to derive which action the user wants to take by inferring
	// from the input available the users intention.

	// Case 1: Training the network automatically.
	//
	// Requires: Training set, Validation set, a desired classification accuracy, 
	//           and path to save the constructed network to.
	// Optional: Type of classifier to use

	if(!StringOps.isAStringsEmpty(this.trainingSetPath,this.validationSetPath,this.saveNeuralNetworkToPath) && this.tests == -1)
	{
	    if(this.desiredAccuracy > -1)
		return Constants.AUTOMATED_TRAINING;
	}

	// Case 2: Data classification.
	//
	// Requires: Path to a previously saved neural network, the path to the data to be classified, 
	//           and the path to the file to output results to.
	// Optional: Type of clustering technique to use.

	if(!StringOps.isAStringsEmpty(this.pathToSavedNetwork,this.classificationSetPath,this.outputFile) |
		( !StringOps.isAStringsEmpty(this.pathToSavedNetwork,this.outputFile) && this.extraParams.size() > 0))
	{
	    return Constants.AUTOMATED_CLASSIFICATION;
	}

	// Case 3: Test the accuracy of the neural network.
	//
	// Requires: Training set, Validation set, Type of classifier to use, 
	//           the number of tests to run
	// Optional: Path to output file.
	if(!StringOps.isAStringsEmpty(this.trainingSetPath,this.validationSetPath))
	{
	    if(this.tests > -1 && this.classifier > -1)
		return Constants.AUTOMATED_TESTING;
	}

	// Else no actions possible
	return Constants.NO_ACTION;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData#processArgument(java.lang.String)
     */
    public void processArgument(String arg)
    {
	if (arg.startsWith(Constants.LOGGING_FLAG) || arg.startsWith(Constants.LOGGING_FLAG.toUpperCase()))
	{
	    this.enableLogging();
	}
	else if(arg.startsWith(Constants.LANGUAGE_FLAG) || arg.startsWith(Constants.LANGUAGE_FLAG.toUpperCase()))
	{
	    this.setLanguage(StringOps.trimArgument(arg, Constants.LANGUAGE_FLAG));
	}
	else if(arg.startsWith(Constants.COUNTRY_FLAG) || arg.startsWith(Constants.COUNTRY_FLAG.toUpperCase()))
	{
	    this.setCountry(StringOps.trimArgument(arg, Constants.COUNTRY_FLAG));
	}
	else if(arg.startsWith(Constants.DESIRED_ACCURACY_FLAG) || arg.startsWith(Constants.DESIRED_ACCURACY_FLAG.toUpperCase()))
	{
	    this.setDesiredNetworkAccuracy(Integer.parseInt(StringOps.trimArgument(arg, Constants.DESIRED_ACCURACY_FLAG)));
	}	
	else if(arg.startsWith(Constants.CLASSIFIER_FLAG) || arg.startsWith(Constants.CLASSIFIER_FLAG.toUpperCase()))
	{
	    try
	    {
		this.setClassifier(Integer.parseInt(StringOps.trimArgument(arg, Constants.CLASSIFIER_FLAG)));
	    }
	    catch(NumberFormatException nfe){ this.setClassifier(-1); }
	}
	else if(arg.startsWith(Constants.LOAD_NN_FLAG) || arg.startsWith(Constants.LOAD_NN_FLAG.toUpperCase()))
	{
	    this.setPathToSavedNetwork(StringOps.trimArgument(arg, Constants.LOAD_NN_FLAG));
	}
	else if(arg.startsWith(Constants.SAVE_NN_FLAG) || arg.startsWith(Constants.SAVE_NN_FLAG.toUpperCase()))
	{
	    this.setNetworkSavePath(StringOps.trimArgument(arg, Constants.SAVE_NN_FLAG));
	}
	else if(arg.startsWith(Constants.TRAINING_SET_FLAG) || arg.startsWith(Constants.TRAINING_SET_FLAG.toUpperCase()))
	{
	    this.setPathToTrainingFile(StringOps.trimArgument(arg, Constants.TRAINING_SET_FLAG));
	}
	else if(arg.startsWith(Constants.VALIDATION_SET_FLAG) || arg.startsWith(Constants.VALIDATION_SET_FLAG.toUpperCase()))
	{
	    this.setPathToValidationFile(StringOps.trimArgument(arg, Constants.VALIDATION_SET_FLAG));
	}
	else if(arg.startsWith(Constants.CLASSIFICATION_SET_FLAG) || arg.startsWith(Constants.CLASSIFICATION_SET_FLAG.toUpperCase()))
	{
	    this.setPathToClassificationFile(StringOps.trimArgument(arg, Constants.CLASSIFICATION_SET_FLAG));
	}
	else if(arg.startsWith(Constants.OUTPUT_FILE_FLAG) || arg.startsWith(Constants.OUTPUT_FILE_FLAG.toUpperCase()))
	{
	    this.setPathToOutputFile(StringOps.trimArgument(arg, Constants.OUTPUT_FILE_FLAG));
	}
	else if(arg.startsWith(Constants.SETTINGS_FLAG) || arg.startsWith(Constants.SETTINGS_FLAG.toUpperCase()))
	{
	    this.loadSettings((StringOps.trimArgument(arg, Constants.SETTINGS_FLAG)));
	}
	else if(arg.startsWith(Constants.MAPSIZE_FLAG) || arg.startsWith(Constants.MAPSIZE_FLAG.toUpperCase()))
	{
	    try
	    {
		this.setMapSize(Integer.parseInt(StringOps.trimArgument(arg, Constants.MAPSIZE_FLAG)));
	    }
	    catch(NumberFormatException nfe){ this.setMapSize(-1); }
	}
	else if(arg.startsWith(Constants.K_FLAG) || arg.startsWith(Constants.K_FLAG.toUpperCase()))
	{
	    try
	    {
		this.setK(Integer.parseInt(StringOps.trimArgument(arg, Constants.K_FLAG)));
	    }
	    catch(NumberFormatException nfe){ this.setK(3); }
	}
	else if(arg.startsWith(Constants.TESTS_FLAG) || arg.startsWith(Constants.TESTS_FLAG.toUpperCase()))
	{
	    try
	    {
		this.setTests(Integer.parseInt(StringOps.trimArgument(arg, Constants.TESTS_FLAG)));
	    }
	    catch(NumberFormatException nfe){ this.setTests(-1); }
	}
	else { this.extraParams.add(arg); } // Process any extra parameters not expected later.
    }

    /**
     * Over-ridden toString method.
     */
    @Override
    public String toString()
    {
	return "Command Line Input Data Object\n"+
		"Logging: " +this.logging + "\n"+
		"Language: "+this.language+ "\n"+
		"Country: "+this.country+ "\n"+
		"Training set: "+this.trainingSetPath+ "\n"+
		"Validation set: "+this.validationSetPath+ "\n"+
		"Classification set: "+this.classificationSetPath+ "\n"+
		"Ouput file: "+this.outputFile+ "\n"+
		"Load Network: "+this.pathToSavedNetwork+ "\n"+
		"Save network to: "+this.saveNeuralNetworkToPath+ "\n"+
		"Map Width: "+this.mapWidth+ "\n"+
		"Desired accuracy: "+this.desiredAccuracy+ "\n"+
		"Classifier: "+this.classifier+ "\n"+
		"Tests: "+this.tests+ "\n"+
		"K Vlaue: "+this.k + "\n";
    }
}
