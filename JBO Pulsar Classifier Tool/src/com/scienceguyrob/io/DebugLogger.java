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
 * File name: 	DebugLogger.java
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package com.scienceguyrob.io;

/**
 *
 * This class is used to provide a wrapper for logging utilities. It is
 * used to toggle verbose logging statements on and off in this application,
 * through the use of a command line argument passed to this class's 
 * constructor. A single copy of this class is initialised in the main method
 * for the application, and this instance is then used throughout the program.
 * 
 * This is used of Java's own logging classes for the sake of simplicity.
 * 
 * @author Rob Lyon
 *
 */
public class DebugLogger
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The boolean flag used to toggle logging on and off. If true 
     * verbose logging statements will be written to the
     * log file. If false only error statements will be written to 
     * the log file.
     */
    private boolean log = true;

    //*****************************************
    //*****************************************
    //              Constructor
    //*****************************************
    //*****************************************

    /**
     * Primary constructor that builds a new logger instance. If
     * the boolean flag passed to this constructor is true,
     * verbose logging will be enabled. If the flag is false, 
     * verbose logging will be disabled, however error logging
     * will continue.
     * @param enableLogging the verbose logging flag.
     */
    public DebugLogger(boolean enableLogging) { log = enableLogging; }

    //*****************************************
    //*****************************************
    //              Methods
    //*****************************************
    //*****************************************

    /**
     * Enables verbose logging.
     */
    public void enableLogging(){ log = true;}
    
    /**
     * Disables verbose logging.
     */
    public void disableLogging(){ log = false; }

    /**
     * Outputs a logging message to the log file.
     * @param message the message to write to the log file.
     */
    public void out(String message)
    {
	if(log)
	    Logging.log(message);
    }

    /**
     * Outputs an error message to the error log file.
     * @param message a message describing where and how the error has occurred.
     * @param error the exception object.
     */
    public void error(String message,Object error) { Logging.errorLog(message, error); }
}
