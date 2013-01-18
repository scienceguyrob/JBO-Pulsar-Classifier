/**
 *
 * This file is part of Stats.
 *
 * Stats is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Stats is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Stats.  If not, see <http://www.gnu.org/licenses/>.
 *
 * File name: 	RegexWrapper.java
 * Created:	Nov 6, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package com.scienceguyrob.stats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.scienceguyrob.utils.StringOps;

/**
 * The class RegexWrapper.java is a part of the package com.scienceguyrob.stats, in the 
 * project "Stats".
 * 
 * This Class is used to determine the data type of the contents of a String.
 * 
 * For example if a String contains:  13:24:58  -> It is a date time
 *                                    12345678  -> It is an integer
 *                                    1.232345  -> It is a double
 *                                    Hello Me  -> Simple String
 * 
 * So this class can be used to find out if a String contains a valid datatype.
 * 
 *  @author Rob Lyon
 */

public class RegexWrapper
{
	//*****************************************
	//*****************************************
	//        Check String data type
	//*****************************************
	//*****************************************

	/**
	 * Checks weather a String contains an INT, FLOAT, DOUBLE or DATETIME.
	 * 
	 * This method will return:
	 * 
	 * unknown -> if the data type cannot be deduced
	 * int    -> if the String contains an Integer
	 * Double  -> if the String contains a Float or Double value
	 * date   -> if the String contains a Date Time
	 * String -> if it is a standard String
	 *
	 * @param candidate the String to check for possible data types.
	 * @return The data type.
	 */
	public static String getStringType(String candidate)
	{
		//remember zero does not indicate the value very well!
		if (candidate.equals("") || candidate.equals(" ") || candidate.equals("0"))
		{
			//System.out.println("STRNG: " + candidate + " + TYPE UNKOWN");
			return "unknown";
		}
		else if (isInteger(candidate))
		{
			//System.out.println("STRNG: " + candidate + " + TYPE INT");
			return "int";
		}
		else if (isFloat(candidate))
		{
			//System.out.println("STRNG: " + candidate + " + TYPE FLOAT");
			return "double";
		}
		else if (isDate(candidate))
		{
			//System.out.println("STRNG: " + candidate + " + TYPE DATE");
			return "date";
		}
		else
		{
			//System.out.println("STRNG: " + candidate + " + TYPE String");
			return "String";
		}
	}

	//*****************************************
	//*****************************************
	//       Regular Expression methods
	//*****************************************
	//*****************************************

	/**
	 * Checks if a String contains a valid integer.
	 *
	 * @param candidate the String to check for a valid integer.
	 * @return True if the String contains a valid integer.*/
	public static boolean isInteger(String candidate)
	{
		String txt = candidate;

		String expression = "(\\d+)";	// Integer Number 1

		Pattern p = Pattern.compile(expression,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(txt);

		String temp = candidate.toLowerCase();
		candidate = temp;

		if (m.find() && !candidate.contains(".") && !StringOps.doesStringContainLetters(candidate) && 
			!candidate.contains(":") && !candidate.equals("0"))
		{
			return true;
		}
		else
			return false;

	}

	/**
	 * Checks if a String contains a valid float.
	 *
	 * @param candidate the String to check for a valid float.
	 * @return true if the String contains a valid float.
	 */
	public static boolean isFloat(String candidate)
	{
		String txt = candidate;

		String expression = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";	// Float 1

		Pattern p = Pattern.compile(expression,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(txt);

		if (m.find() && !StringOps.doesStringContainLetters(candidate) && !candidate.equals("0") &&
			!candidate.contains("/") && !candidate.contains(":"))
		{
			return true;
		}
		else
			return false;
	}

	/**
	 * Checks if a String contains a valid date time.
	 *
	 * @param candidate the String to check for a valid date time.
	 * @return True if the String contains a valid date time.*/
	public static boolean isDate(String candidate)
	{
		String txt = candidate;

		String expression = "((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:\\s?(?:am|AM|pm|PM))?)";	// HourMinuteSec 1

		Pattern p = Pattern.compile(expression,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(txt);

		if (m.find() && !candidate.contains("-") && !StringOps.doesStringContainLetters(candidate) && !candidate.equals("0"))
		{
			return true;
		}
		else
			return false;
	}
}
