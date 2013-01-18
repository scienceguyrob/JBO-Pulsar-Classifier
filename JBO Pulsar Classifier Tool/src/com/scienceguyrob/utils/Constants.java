/**
 *
 * This file is part of the Stats library application.
 *
 * The Stats library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Stats library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Stats library.  If not, see <http://www.gnu.org/licenses/>.
 *
 * File name: 	Constants.java
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package com.scienceguyrob.utils;

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
     * Command line flag for the path to the file containing training data.
     */
    public static final String TRAINING_DATA_FLAG = "-t=";

    /**
     * Command line flag for the path to the file containing data to be classified.
     */
    public static final String CLASSIFICATION_DATA_FLAG = "-c=";
    
    /**
     * Command line flag for the path to the output file.
     */
    public static final String OUTPUT_FILE_FLAG = "-o=";
    
    /**
     * Command line flag for the verbose logging.
     */
    public static final String LOGGING_FLAG = "-logging";

    //*****************************************
    //*****************************************
    //         Command Line Actions
    //*****************************************
    //*****************************************

    public static final String RUN = "RUN";
    public static final String EXIT = "EXIT";

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
    public static final String NOT_PULSAR = "NotPulsar";
    public static final String RFI = "RFI";
    public static final String UNKNOWN = "Unknown";
    

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
}
