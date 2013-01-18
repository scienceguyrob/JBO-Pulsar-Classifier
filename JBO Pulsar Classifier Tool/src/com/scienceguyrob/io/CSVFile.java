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
 * File name: 	CSVFile.java
 * Created:	Nov 6, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package com.scienceguyrob.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The class CSVFile.java is a part of the package com.scienceguyrob.io, in the 
 * project "Stats".
 *
 * This class represents a CSV file.
 * 
 * @author Rob Lyon
 *
 */
public class CSVFile 
{
	/**
	 * Method that reads a CSV file and returns a matrix of its data
	 * in a nested array list structure.
	 * 
	 * @param path The path to the file to read.
	 * @return The nested array list structure containing the contents of the file.
	 */
	public static ArrayList<ArrayList<String>> getCSVContentsAsMatrix(String path)
	{
		//stores the data from the file
		ArrayList<ArrayList<String>> contents = new ArrayList<ArrayList<String>>();

		// Read the file and display it line by line.
		String line = "";

		// Read the file and display it line by line. 
		BufferedReader in = null;

		try
		{
			//open stream to file
			in = new BufferedReader(new FileReader(path));

			try
			{
				while ((line = in.readLine()) != null)
				{
					//split the line on commas
					String[] cells = line.split(",");

					// An array list will represent a row of data from a CSV file
					ArrayList<String> row = new ArrayList<String>();

					//For each comma separated String we retrieve from the file
					for(String c : cells )
					{
						//add it to the row array list
						row.add(c.replace("\"", ""));
					}

					//add the row to the over all contents array list
					contents.add(row);
				}
			}
			catch(IOException e){return null;}
			finally{in.close();}
		}
		catch(FileNotFoundException fnf){ System.out.println("File not found: "+path); }
		catch (IOException e) { return null; }

		return contents;
	}

	/**
	 * Counts the number of columns and rows that appear in a CSV file.
	 * The number of rows and columns is returned as an integer array, where:
	 * 
	 * COLUMNS = valueAt[0];
	 * ROWS = valueAt[1];
	 * 
	 * @param path The CSV file to count the rows and columns of.
	 * @return The number of columns and rows in a CSV file, in an integer array.
	 */
	public static int[] getRow_AND_ColumnCount(String path)
	{
		int columns = 0;
		int rows = 0;

		// Read the file and display it line by line.
		String line = "";

		// Read the file and display it line by line. 
		BufferedReader in = null;

		try
		{
			//open stream to file
			in = new BufferedReader(new FileReader(path));

			try
			{
				while ((line = in.readLine()) != null)
				{
					if (rows == 0)
					{
						String[] column = line.split(",");

						for(String c : column)
						{
							if (!c.equals(""))
								columns++;
						}
					}

					rows++;
				}
			}
			catch(IOException e){return null;}
			finally{in.close();}
		}
		catch(FileNotFoundException fnf){ System.out.println("File not found: "+path); }
		catch (IOException e) { return null; }

		int[] dimensions = { columns, rows };
		return dimensions;
	}
}
