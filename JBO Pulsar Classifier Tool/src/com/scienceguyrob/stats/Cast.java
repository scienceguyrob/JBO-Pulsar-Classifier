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
 * File name: 	Cast.java
 * Created:	Nov 6, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package com.scienceguyrob.stats;

/**
 * The class Cast.java is a part of the package com.scienceguyrob.stats, in the 
 * project "Stats".
 *
 * This class  wraps around cast operations, making in
 * safer to convert between types in code.
 * 
 * @author Rob Lyon
 */
public class Cast
{
	//*****************************************
	//*****************************************
	//              String TO...
	//*****************************************
	//*****************************************

	/**
	 * Method that converts dir_one String true or false value, into dir_one boolean.
	 *
	 * @param v the String to convert 
	 * @return true or false.
	 */
	public static boolean StringTobool(String v)
	{
		String value = v.toLowerCase();

		if (value.contains("true"))
			return true;
		else if (value.contains("false"))
			return false;
		else
			return false;
	}

	/**
	 * Method that converts a String to an int.
	 *
	 * @param text The String to convert. 
	 * @return The converted int.
	 */
	public static int StringToInt32(String text)
	{
		int result = 0;

		try { result = Integer.parseInt(text); }
		catch (Exception e)
		{
			System.out.println("Error Converting:" + text + " To Int32...");
			return result;
		}

		return result;
	}

	/**
	 * Method that converts a String to a double.
	 *
	 * @param text the data to convert.
	 * @return the converted string.
	 */
	public static double StringToDouble(String text)
	{
		double result = 0;

		try { result = Double.parseDouble(text); }
		catch (Exception e) 
		{ 
			System.out.println("Error Converting:" + text + " To Double...");
			return result; 
		}

		return result;
	}

	/**
	 * Method that converts a String to a float.
	 *
	 * @param text the data to convert.
	 * @return the converted string.
	 */
	public static float StringToFloat(String text)
	{
		float result = 0;

		try { result = Float.parseFloat(text); }
		catch (Exception e)
		{
			System.out.println("Error Converting:" + text + " To Float...");
			return result;
		}

		return result;
	}

	/**
	 * Converts a String argument containing the word true or false
	 * to a boolean value.
	 *
	 * @param value The String to convert. 
	 * @return True if the String contains "true" and false if it contains "false".
	 *          Else false is returned.
	 */
	public static String StringToboolean(String value)
	{
		if (value.equals(""))
			return "false";
		else if (value.contains("true"))
			return "true";
		else if (value.contains("false"))
			return "false";
		else
			return "false";
	}

	//*****************************************
	//*****************************************
	//              INT TO...
	//*****************************************
	//*****************************************

	/**
	 * Method that converts an int to a String.
	 * @param data the data to convert.
	 * @return the converted int.
	 */
	public static String IntToString(int data)
	{
		String result = "";

		try { result = Integer.toString(data); }
		catch (Exception e)
		{
			System.out.println("Error Converting Int32:" + data + " To String...");
			return "Error";
		}

		return result;
	}

	/**
	 * Method that converts an int to a double.
	 *
	 * @param data the data to convert.
	 * @return the converted int.
	 */
	public static double IntToDouble(int data)
	{
		double result = 0;

		try { result = (double)data; }
		catch (Exception e)
		{
			System.out.println("Error Converting Int32:" + data + " To Double...");
			return result;
		}

		return result;
	}

	/**
	 * Method that converts an int to a float.
	 *
	 * @param data the data to convert.
	 * @return the converted int.
	 */
	public static float IntToFloat(int data)
	{
		float result = 0;

		try { result = (float)data; }
		catch (Exception e)
		{
			System.out.println("Error Converting Int32:" + data + " To Float...");
			return result;
		}

		return result;
	}

	//*****************************************
	//*****************************************
	//              DOUBLE TO...
	//*****************************************
	//*****************************************

	/**
	 * Method that converts a double to a String.
	 * @param data the data to convert.
	 * @return the converted double.
	 */
	public static String DoubleToString(double data)
	{
		String result = "";

		try { result = Double.toString(data); }
		catch (Exception e)
		{
			System.out.println("Error Converting Double:" + data + " To String...");
			return "Error";
		}

		return result;
	}

	/**
	 * Method that converts a double to a String.
	 * @param data the data to convert.
	 * @return the converted double.
	 */
	public static int DoubleToInt(double data)
	{
		int result = -1;

		try { result = (int) data; }
		catch (Exception e)
		{
			System.out.println("Error Converting Double:" + data + " To String...");
			return result;
		}

		return result;
	}

	//*****************************************
	//*****************************************
	//              FLOAT TO...
	//*****************************************
	//*****************************************

	/**
	 * Method that converts a float to a String.
	 * 
	 * @param data the data to convert.
	 * @return the converted float.
	 */
	public static String FloatToString(float data)
	{
		String result = "";

		try { result = Float.toString(data); }
		catch (Exception e)
		{
			System.out.println("Error Converting Float:" + data + " To String...");
			return "Error";
		}

		return result;
	}
}
