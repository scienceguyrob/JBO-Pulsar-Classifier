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
 * File name: 	StringOps.java
 * Package: uk.ac.man.jb.pct.util
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains methods used to manipulate strings.
 * 
 * @author Rob Lyon
 *
 */
public class StringOps
{
    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /**
     * Method that separates a string into sub-strings, broken up where each
     * specified character appears.
     * @param source the string to break up.
     * @param sepChar the character used to breakup the string.
     * @return the broken up string as an array, otherwise null.
     */
    public static String[] seperateString(String source, String sepChar)
    {
	if (Common.notNullOrEmpty(source) && !source.equals("") && !sepChar.equals(""))
	{
	    try
	    {
		String[] subStrings = source.split(sepChar);

		if(subStrings.length < 1){ return null; }
		else if(subStrings.length == 1)
		{
		    // if the array has only one element, it may not have been
		    // possible to split the string, in which case the call to 
		    // split will return an array containing the source string.
		    // As we don't want the source string to be returned in
		    // the array, we explicitly return null.
		    if(subStrings[0].equals(source))
			return null;
		}		

		return subStrings;
	    }
	    catch (Exception e){ return null; }
	}
	else{ return null; }
    }

    /**
     * Method that separates a string into sub-strings, broken up where each
     * specified character appears.
     * @param source the string to break up.
     * @param sepChar the character used to breakup the string.
     * @return the broken up string as an array, otherwise null.
     */
    public static double[] splitStringToDouble(String source, String sepChar)
    {
	if (Common.notNullOrEmpty(source) && !source.equals("") && !sepChar.equals(""))
	{
	    try
	    {
		String[] subStrings = source.split(sepChar);

		if(subStrings.length < 1){ return null; }
		else if(subStrings.length == 1)
		{
		    // if the array has only one element, it may not have been
		    // possible to split the string, in which case the call to 
		    // split will return an array containing the source string.
		    // As we don't want the source string to be returned in
		    // the array, we explicitly return null.
		    if(subStrings[0].equals(source))
			return null;
		}		

		double[] values = new double[subStrings.length];

		for(int i = 0; i < values.length;i++)
		    values[i] = Double.parseDouble(subStrings[i]);

		return values;
	    }
	    catch (Exception e){ return null; }
	}
	else{ return null; }
    }

    /**
     * Method that removes a number of sub strings, from an existing string.
     *
     * @param source the string to remove the sub-strings from.
     * @param tokens the strings to remove.
     * @return the shortened string.
     */
    public static String removeFromString(String source,String[] tokens)
    {
	if(Common.notNullOrEmpty(source))
	{
	    if(Common.notNullOrEmpty(tokens))
	    {
		String result = source;

		for(String s: tokens){ result = result.replace(s, "");}

		return result;
	    }
	    else{ return source; }
	}
	else{ return null; }
    }

    /**
     * Method that checks that a string actually contains a full integer.
     * @param source the string to check.
     * @return true if the string is representing a valid int value.
     */
    public static boolean doesStringContainInt(String source)
    {
	if(Common.notNullOrEmpty(source))
	{
	    String regex="([-+]?\\d+)";	// Integer Number 1

	    Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(source);

	    if (m.find())
	    {
		try
		{
		    Integer.parseInt(source);
		    return true;
		}
		catch (NumberFormatException nfe){  return false; }
	    }
	    else {return false;}
	}
	else { return false; }
    }

    /**
     * Checks if multiple keywords occur in a string.
     *
     * @param source the string to look at for keywords.
     * @param keywords the words to look for in the source string.
     * @return true if all the keywords are in the string.
     */
    public static boolean areStringsInSource(String source, String[] keywords)
    {
	if(Common.notNullOrEmpty(source))
	{
	    if(Common.notNullOrEmpty(keywords))
	    {
		for(String s : keywords)
		{
		    String source_lower = source.toLowerCase();
		    String s_lower = s.toLowerCase();

		    if (!source_lower.contains(s_lower)){ return false; }
		}

		return true;
	    }
	    else{ return false; }
	}
	else{ return false; }
    }

    /**
     * Extracts the string data between a specific character, i.e. ':extract this:'.
     * @param source the string to try and extract from.
     * @param separator the separator character used to split up the string.
     * @return the first string occurring between the first and last index of
     *         the separation character, else null.
     */
    public static String extractBetweenSeperator(String source, String separator)
    {
	if(Common.notNullOrEmpty(source) | Common.notNullOrEmpty(separator))
	{
	    @SuppressWarnings("unused")
	    String result = null;

	    try
	    {
		int firstIndexOfSeparator = source.indexOf(separator) + 1;
		int secondIndexOfSeparator = source.lastIndexOf(separator);
		return result = source.substring(firstIndexOfSeparator, secondIndexOfSeparator);
	    }
	    catch (Exception e) { return null; }
	}else {return null;}
    }

    /**
     * Extracts the path to the parent directory of the specified file.
     * @param path the path to the file whose parent directory we are to extract.
     * @return the path to the parent directory of the specified file.
     */
    public static String getCanonicalPathToParent(String path)
    {
	if(Common.notNullOrEmpty(path) && Common.isPathValid(path))
	{
	    if(Common.fileExist(path))
	    {
		File file = new File(path);
		String fileName = file.getName();

		if(Common.notNullOrEmpty(fileName))
		{
		    // Get the path to the parent directory.
		    return path.replace(File.separatorChar+fileName, "");
		}else { return null; }
	    }
	    else
	    {
		int indexOfLastSeperator = path.lastIndexOf(File.separatorChar);
		return path.substring(0,indexOfLastSeperator);
	    }
	}
	else { return null; }
    }

    /**
     * Gets the name of a file from a path.
     * @param path the full path to the file.
     * @return the name of the file with its extension.
     */
    public static String getFileNameFromPath(String path)
    {
	if(Common.notNullOrEmpty(path) && Common.isPathValid(path))
	{
	    File file = new File(path);
	    String fileName = file.getName();

	    if(Common.notNullOrEmpty(fileName))
	    {
		// Get the path to the parent directory.
		return fileName;
	    }else { return null; }
	}
	else { return null; }
    }

    /**
     * Checks if a string contains a file extension, but only if the extension
     * exists at the end of the file name, i.e. "file.txt" whereas "file.file.txt"
     * would be deemed invalid.
     * @param s the string to check.
     * @return true if the string contains a single file extension.
     */
    public static boolean containsFileExtension(String s)
    {
	if(Common.notNullOrEmpty(s))
	{
	    if(s.contains("."))
	    {
		// dot symbol at the start of string.
		if(s.indexOf(".") == 0)
		    return false;

		// count occurrences of dot character
		// if greater than one return false.
		int count = 0;
		for (int i=0; i < s.length(); i++)
		{
		    if (s.charAt(i) == '.')
			count++;

		    if(count>1)
			return false;
		}

		// dot symbol at the end of string.
		if(s.lastIndexOf(".") == s.length()-1)
		    return false;

		return true;
	    }
	    else
		return false;
	}
	else
	    return false;
    }

    /**
     * Trims any unwanted text out of command line arguments.
     * 
     * @param arg The argument.
     * @param excess the string to remove from the argument.
     *@return The updated argument.
     */
    public static String trimArgument(String arg, String excess)
    {
	return arg.replace(excess.toUpperCase(), "").replace(excess.toLowerCase(), "");
    }
    
    /**
     * Checks if any of the supplied strings are empty
     * @param strings
     * @return true if at least one string is empty, else false.
     */
    public static boolean isAStringsEmpty(String ...strings )
    {
	for (String s : strings ) 
	{
	    if(Common.isEmptyString(s))
		return true;
	}
	
	return false;
    }
}
