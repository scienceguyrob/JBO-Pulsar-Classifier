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
 * File name: 	Logging.java
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package com.scienceguyrob.io;

import java.io.File;

import com.scienceguyrob.utils.Common;

/**
 * This class is used to write out log files.
 * 
 * @author Rob Lyon
 *
 */
public class Logging
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * Path to the log file this application will write to.
     */
    private static String LOG_PATH = System.getProperty("user.dir")+File.separatorChar+"jbpct.log";

    /**
     * Path to the error log file this application will write to.
     */
    private static String ERROR_PATH = System.getProperty("user.dir")+File.separatorChar+"jbpct.elog";

    //*****************************************
    //*****************************************
    //           Logging Methods
    //*****************************************
    //*****************************************

    /**
     * Writes text information to the log file.
     * @param text the text information to log.
     * @return true if the logging operation was successful, else false.
     */
    public synchronized static boolean log(String text){ return Writer.write(LOG_PATH , debugFormat(text)); }

    /**
     * Writes text information to the log file.
     * @param path the path to write the log file to.
     * @param text the text information to log.
     * @return true if the logging operation was successful, else false.
     */
    public synchronized static boolean log(String path, String text){ return Writer.write(path , debugFormat(text)); }

    /**
     * Writes error information to the log file.
     * @param text the text information that describes the circumstances under which the error occurred.
     * @param e the exception that occurred.
     * @return true if the logging operation was successful, else false.
     */
    public synchronized static boolean errorLog(String text,Object e)
    { 
	try
	{
	    Exception exception = (Exception)e;
	    return Writer.write(ERROR_PATH , errorFormat(text,exception));
	}
	catch(Exception ex)
	{
	    return Writer.write(ERROR_PATH , errorFormat(text,new Exception("Unknown Error")));
	}
    }

    /**
     * Writes error information to the log file.
     * @param path the path to write the log file to.
     * @param text the text information that describes the circumstances under which the error occurred.
     * @param e the exception that occurred.
     * @return true if the logging operation was successful, else false.
     */
    public synchronized static boolean errorLog(String path,String text,Object e)
    { 
	try
	{
	    Exception exception = (Exception)e;
	    return Writer.write(path , errorFormat(text,exception));
	}
	catch(Exception ex)
	{
	    return Writer.write(path , errorFormat(text,new Exception("Unknown Error")));
	}
    }

    //*****************************************
    //*****************************************
    //          Clear Log Methods
    //*****************************************
    //*****************************************

    /**
     * Clears the log file.
     * @return true if the log file was cleared successfully, else false.
     */
    public static boolean clearLog(){return Writer.clear(LOG_PATH);}

    /**
     * Clears the error log file.
     * @return true if the error log file was cleared successfully, else false.
     */
    public static boolean clearErrorLog(){return Writer.clear(ERROR_PATH);}

    //*****************************************
    //*****************************************
    //      Log message formatting
    //*****************************************
    //*****************************************

    /**
     * Formats a debug message into a suitable log format.
     * @param text the output type.
     * @return the formatted string.
     */
    private static String debugFormat(String text)
    {
	String DATE = Common.getDate();
	String TIME = Common.getTime();

	//Get Tick count
	long TICKS = System.currentTimeMillis();

	String MESSAGE =  "INFO,"+DATE + "," + TIME + "," + TICKS + "," + text + ",\n";

	//Return String Formatted
	return MESSAGE;
    }

    /**
     * Formats an error message into a suitable error log format.
     * @param text the output type.
     * @param e the exception that.
     * @return the formatted string describing how the error occurred and of course the error itself..
     */
    private static String errorFormat(String text,Exception e)
    {
	String DATE = Common.getDate();
	String TIME = Common.getTime();

	//Get Tick count
	long TICKS = System.currentTimeMillis();

	//Form a string describing the exception
	String EXCEPTION = e.toString();

	String MESSAGE =  "Error," + DATE + "," + TIME + "," + TICKS + "," + text + ","+ EXCEPTION + ",\n";

	//Return String Formatted
	return MESSAGE;
    }
}
