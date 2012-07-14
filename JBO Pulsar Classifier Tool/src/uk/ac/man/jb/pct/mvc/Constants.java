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
 * File name: 	Constants.java
 * Package: uk.ac.man.jb.pct.mvc
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.mvc;

import java.io.File;

/**
 * This class contains constants used throughout the application, used
 * to identify GUI components store file paths and even store the text
 * to be displayed on dialogs.
 * 
 * @author Rob Lyon
 */
public class Constants
{   
    //*****************************************
    //*****************************************
    //         Command Line Flags
    //*****************************************
    //*****************************************

    /**
     * Command line flag For the desired accuracy of the network being trained.
     */
    public static final String DESIRED_ACCURACY_FLAG = "-acc=";

    /**
     * Command line flag For the country choice.
     */
    public static final String COUNTRY_FLAG = "-country=";

    /**
     * Command line flag For the desired classifier to use (if more are added).
     */
    public static final String CLASSIFIER_FLAG = "-classifier=";

    /**
     * Command line flag For the language choice.
     */
    public static final String LANGUAGE_FLAG = "-lang=";

    /**
     * Command line flag For the verbose logging.
     */
    public static final String LOGGING_FLAG = "-logging";

    /**
     * Command line flag For the neural network to load.
     */
    public static final String LOAD_NN_FLAG = "-load=";

    /**
     * Command line flag For the path to save a neural network at.
     */
    public static final String SAVE_NN_FLAG = "-save=";

    /**
     * Command line flag used to pass in the path to the training set.
     */
    public static final String TRAINING_SET_FLAG = "-t=";

    /**
     * Command line flag used to pass in the path to the validation set.
     */
    public static final String VALIDATION_SET_FLAG = "-v=";

    /**
     * Command line flag used to pass in the path to the classification set.
     */
    public static final String CLASSIFICATION_SET_FLAG = "-c=";

    /**
     * Command line flag used to pass in the path to the output file.
     */
    public static final String OUTPUT_FILE_FLAG = "-o=";

    /**
     * Command line flag used to pass in the size of the map.
     */
    public static final String MAPSIZE_FLAG = "-w=";
    
    /**
     * Command line flag used to pass in the value of K for a KNN classifier.
     */
    public static final String K_FLAG = "-k=";

    /**
     * Command line flag used to pass in the number of tests to run.
     */
    public static final String TESTS_FLAG = "-tests=";

    /**
     * Command line flag used to pass in the path to a file where application settings are stored.
     */
    public static final String SETTINGS_FLAG = "-settings=";
    
    /**
     * The command line flags.
     */
    public static final String[] FLAGS = 
	{
	DESIRED_ACCURACY_FLAG,COUNTRY_FLAG,CLASSIFIER_FLAG,
	LANGUAGE_FLAG,LOGGING_FLAG,LOAD_NN_FLAG,SAVE_NN_FLAG,
	TRAINING_SET_FLAG,VALIDATION_SET_FLAG,CLASSIFICATION_SET_FLAG,
	OUTPUT_FILE_FLAG,MAPSIZE_FLAG,TESTS_FLAG,K_FLAG,SETTINGS_FLAG
	};

    //*****************************************
    //*****************************************
    //         Command Line Actions
    //*****************************************
    //*****************************************

    public static final String AUTOMATED_TRAINING = "AUTOMATED TRAINING";
    public static final String SEMI_AUTOMATED_TRAINING = "SEMI AUTOMATED TRAINING";
    public static final String AUTOMATED_CLASSIFICATION = "AUTOMATED CLASSIFICATION";
    public static final String AUTOMATED_TESTING = "AUTOMATED TESTING";
    public static final String NO_ACTION = "NO ACTION";

    //*****************************************
    //*****************************************
    //         File path constants
    //*****************************************
    //*****************************************

    /*
     * Characters array used to validate file name input. 
     */

    public static final char[] ILLEGAL_FILENAME_CHARACTERS = {'^', '`', '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '<', '>', '|', '\"', ':','\\',File.separatorChar };
    public static final char[] ILLEGAL_PATH_CHARACTERS =     {'^', '`', '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '<', '>', '|', '\"', ':','\\' };

    //*****************************************
    //*****************************************
    //         Classification classes
    //*****************************************
    //*****************************************
    
    public static final String PULSAR = "Pulsar";
    public static final String RFI = "RFI";
    

    //*****************************************
    //*****************************************
    //         Image type constants
    //*****************************************
    //*****************************************

    /*
     * Constants used to represent images. 
     */

    public static final int IMAGE_ERROR = -1;
    public static final int IMAGE_MAYBE = 0;
    public static final int IMAGE_OK = 1;

    //*****************************************
    //*****************************************
    //         Menu Constants
    //*****************************************
    //*****************************************

    public static final int MI_EXIT = 0;
    public static final int MI_OPEN = 1;
    public static final int MI_STATS = 2;
    public static final int MI_ABOUT = 3;

    public static final int BROWSE_FOR_TRAINING_SET = 4;
    public static final int BROWSE_FOR_VALIDATION_SET = 5;
    public static final int BROWSE_FOR_CLASSIFICATION_SET = 6;
    public static final int BROWSE_FOR_RESULT_SET = 7;

    public static final int LABEL_TRAINING_PATTERNS = 8;
    public static final int LABEL_VALIDATION_PATTERNS = 9;
    public static final int LABEL_CLASSIFICATION_PATTERNS = 10;
    public static final int LABEL_ACCURACY = 11;
    public static final int LABEL_PRECISION = 12;
    public static final int LABEL_RECALL = 13;
    public static final int LABEL_SPECIFICITY = 14;
    public static final int LABEL_NPV = 15;
    public static final int LABEL_MCC = 16;
    public static final int LABEL_FSCORE = 17;
}
