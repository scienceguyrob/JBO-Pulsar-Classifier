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
 * File name: 	StatsDataSet.java
 * Created:	Nov 6, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package com.scienceguyrob.stats;

import java.util.ArrayList;
import java.util.List;

import com.scienceguyrob.io.CSVFile;
import com.scienceguyrob.utils.Common;
import com.scienceguyrob.utils.StringOps;

/**
 * The class StatsDataSet.java is a part of the package com.scienceguyrob.stats, in the 
 * project "Stats".
 *
 * This class represents a data set. As such the class offers various operations for 
 * the data set. For instance:
 * 
 * 1. The average value for a column
 * 2. The minimum value in a column
 * 3. The maximum value in a column
 * 
 * This class stores data in a Multi-dimensional Array list.
 * Each row of a data is stored in an array list of Strings. 
 * So a collection of lists should be thought of as a matrix
 * of sorts.
 * 
 * @author Rob Lyon
 *
 */
public class StatsDataSet 
{
	//*****************************************
	//*****************************************
	//              Variables
	//*****************************************
	//*****************************************

	/*
	 * The path to the physical file that this object represents.
	 */
	private String path = "";

	/*
	 * The number of columns in this file if it is a CSV file.
	 */
	public int COLUMNS = -1;

	/*
	 * The number of rows in this file if it is a CSV file.
	 */
	public int ROWS = -1;

	/*
	 * An array list that contains the data from the CSV file this object represents.
	 */
	ArrayList<ArrayList<String>> content;

	//*****************************************
	//*****************************************
	//             Constructor
	//*****************************************
	//*****************************************

	/**
	 * Default Constructor that populates the "contents"
	 * array lists with the rows from the CSV file this object
	 * represents.
	 * @param p the path to the CSV file this class represents.
	 */
	public StatsDataSet(String p)
	{
		//set the path
		this.path = p;

		//check the path exists
		if (Common.fileExist(this.path))
		{
			//if it exists, get the row and column count for the CSV file
			int[] dimensions = CSVFile.getRow_AND_ColumnCount(this.path);
			COLUMNS = dimensions[0];
			ROWS = dimensions[1];

			//populate the array list with data
			content = CSVFile.getCSVContentsAsMatrix(this.path);
		}
	}

	//*****************************************
	//*****************************************
	//               Getters
	//*****************************************
	//*****************************************

	/**
	 * Returns the number of columns in the file represented by this class.
	 * 
	 * @return The number of columns in the file represented by this class
	 */
	public int getColumns() { return this.COLUMNS; }

	/**
	 * Returns the number of rows in the file represented by this class.
	 * 
	 * @return The number of rows in the file represented by this class
	 */
	public int getRows() { return this.ROWS; }

	/**
	 * Returns a specific column as an array list of Strings.
	 * 
	 * Column indexing begins at 0.
	 *
	 * @param  c the column index.
	 * @return The column contents as an ArrayList of type String.
	 */
	public ArrayList<String> getColumn(int c)
	{
		if(c < 0 | c > this.COLUMNS)
			return null;

		ArrayList<String> column = new ArrayList<String>();

		for(ArrayList<String> a: content)
			column.add(a.get(c));

				return column;
	}

	/**
	 * Returns a specific row as an array list of Strings.
	 * 
	 * Row indexing begins at 0.
	 * 
	 *@param r the row index.
	 * @return The row contents as an ArrayList of type String.
	 */
	public ArrayList<String> getRow(int r)
	{
		if(r < 0 | r > this.ROWS)
			return null;

		return (ArrayList<String>)content.get(r);
	}


	/**
	 * Returns a specific row as an array of Strings.
	 * 
	 * Row indexing begins at 0.
	 * 
	 * @param r the row index.
	 * @return the row contents as an Array of type String.
	 */
	public String[] getRowAsArray(int r)
	{
		if(r < 0 | r > this.ROWS)
			return null;

		ArrayList<String> row = (ArrayList<String>)content.get(r);

		return (String[]) row.toArray(new String[row.size()]);
	}

	/**
	 * Returns a specific column as an array of Strings.
	 * 
	 * Column indexing begins at 0.
	 * 
	 * @param c the column index.
	 * @return the column contents as an Array of type String.
	 */
	public String[] getColumnAsArray(int c)
	{
		if(c < 0 | c > this.COLUMNS)
			return null;

		ArrayList<String> column = this.getColumn(c);

		return (String[]) column.toArray(new String[column.size()]);
	}

	/**
	 * Method that retrieves the contents of a specific cell of a data set, as a String.
	 * 
	 * Row and column indexing begins at zero.
	 * 
	 * @param r the row index.
	 * @param c the column index.
	 * @return The String in the cell specified.
	 */
	public String getCell(int r, int c)
	{
		if(r < 0 | r > this.ROWS)
			return null;

		if(c < 0 | c > this.COLUMNS)
			return null;

		ArrayList<String> row = (ArrayList<String>)content.get(r);

		return (String) row.get(c);
	}

	//*****************************************
	//*****************************************
	//            SUM COLUMNS
	//*****************************************
	//*****************************************

	/**
	 * Method that returns the sum of a column of data.
	 *  
	 *  Column indexing begins at zero.
	 *
	 * @param c The index of the column to sum.
	 * @return the sum of the column of data.
	 */
	public String sumColumn(int c)
	{
		//determine type of value in 1st cell
		String type = RegexWrapper.getStringType(getCell(0, c));

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Sum(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(0, ROWS));
			return Cast.FloatToString(StatsOps.Sum(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Sum(cells));
		}
		else if (type.equals("date"))
			return "NaN date";
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	/**
	 * Method that returns the sum of a column of data.
	 *  
	 *  Column indexing begins at zero.
	 *
	 * @param c The index of the column to sum.
	 * @param int startIndex the index of the column to start calculating from.
	 * @param int endIndex the index of the column to finish calculating.
	 * @return the sum of the column of data.
	 * */
	private String sumColumn(int c, int startIndex,int endIndex)
	{
		//determine type of value in 1st cell
		String type = RegexWrapper.getStringType(getCell(0, c));

		if(startIndex < 0 | endIndex > this.ROWS)
			return "NaN";

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Sum(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.FloatToString(StatsOps.Sum(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Sum(cells));
		}
		else if (type.equals("date"))
			return "NaN date";
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	//*****************************************
	//*****************************************
	//            AVERAGE COLUMNS
	//*****************************************
	//*****************************************

	/**
	 * Method that gets the quartile values for a set of data points
	 * present in a "column" of a file.
	 *  
	 * Column indexing begins at zero.
	 *
	 * @param c index of the column to calculate the quartiles for.
	 * @return an array containing the quartile values.
	 */
	public String[] getQuartiles(int c)
	{
		String[] quartiles = { "NaN", "NaN", "NaN", "NaN" };

		//determine type of value in 1st cell
		String type = getColumnType(c);

		//if this is a value we can average it
		if (type.equals("int"))
		{
			int dataLength = ROWS - 1;
			int Q1 = dataLength / 4;
			int Q2 = dataLength / 2;
			int Q3 = Q1 * 3;

			int Q1_DIFF = Q1;
			int Q2_DIFF = Q2 - Q1 - 1;
			int Q3_DIFF = Q3 - Q2 - 1;
			int Q4_DIFF = dataLength - Q3 - 1;

			List<String> l = getColumn(c).subList(0, ROWS);

			int[] Q1_cells = Common.convertStringListToIntArray(l.subList(0, Q1_DIFF));
			String Q1_Average = Cast.DoubleToString(StatsOps.Average(Q1_cells));
			quartiles[0] = Q1_Average;

			int[] Q2_cells = Common.convertStringListToIntArray(l.subList(Q1 + 1, Q2_DIFF));
			String Q2_Average = Cast.DoubleToString(StatsOps.Average(Q2_cells));
			quartiles[1] = Q2_Average;

			int[] Q3_cells = Common.convertStringListToIntArray(l.subList(Q2 + 1, Q3_DIFF));
			String Q3_Average = Cast.DoubleToString(StatsOps.Average(Q3_cells));
			quartiles[2] = Q3_Average;

			int[] Q4_cells = Common.convertStringListToIntArray(l.subList(Q3 + 1, Q4_DIFF));
			String Q4_Average = Cast.DoubleToString(StatsOps.Average(Q4_cells));
			quartiles[3] = Q4_Average;

			return quartiles;
		}
		else if (type.equals("double"))
		{
			int dataLength = ROWS - 1;
			int Q1 = dataLength / 4;
			int Q2 = dataLength / 2;
			int Q3 = Q1 * 3;

			int Q1_DIFF = Q1;
			int Q2_DIFF = Q2 - Q1 - 1;
			int Q3_DIFF = Q3 - Q2 - 1;
			int Q4_DIFF = dataLength - Q3 - 1;

			List<String> l = getColumn(c).subList(0, ROWS);

			double[] Q1_cells = Common.convertStringListToDoubleArray(l.subList(0, Q1_DIFF));
			String Q1_Average = Cast.DoubleToString(StatsOps.Average(Q1_cells));
			quartiles[0] = Q1_Average;

			double[] Q2_cells = Common.convertStringListToDoubleArray(l.subList(Q1 + 1, Q2_DIFF));
			String Q2_Average = Cast.DoubleToString(StatsOps.Average(Q2_cells));
			quartiles[1] = Q2_Average;

			double[] Q3_cells = Common.convertStringListToDoubleArray(l.subList(Q2 + 1, Q3_DIFF));
			String Q3_Average = Cast.DoubleToString(StatsOps.Average(Q3_cells));
			quartiles[2] = Q3_Average;

			double[] Q4_cells = Common.convertStringListToDoubleArray(l.subList(Q3 + 1, Q4_DIFF));
			String Q4_Average = Cast.DoubleToString(StatsOps.Average(Q4_cells));
			quartiles[3] = Q4_Average;

			return quartiles;
		}
		else if (type.equals("float"))
		{
			int dataLength = ROWS - 1;
			int Q1 = dataLength / 4;
			int Q2 = dataLength / 2;
			int Q3 = Q1 * 3;

			int Q1_DIFF = Q1;
			int Q2_DIFF = Q2 - Q1 - 1;
			int Q3_DIFF = Q3 - Q2 - 1;
			int Q4_DIFF = dataLength - Q3 - 1;

			List<String> l = getColumn(c).subList(0, ROWS);

			float[] Q1_cells = Common.convertStringListToFloatArray(l.subList(0, Q1_DIFF));
			String Q1_Average = Cast.FloatToString(StatsOps.Average(Q1_cells));
			quartiles[0] = Q1_Average;

			float[] Q2_cells = Common.convertStringListToFloatArray(l.subList(Q1 + 1, Q2_DIFF));
			String Q2_Average = Cast.FloatToString(StatsOps.Average(Q2_cells));
			quartiles[1] = Q2_Average;

			float[] Q3_cells = Common.convertStringListToFloatArray(l.subList(Q2 + 1, Q3_DIFF));
			String Q3_Average = Cast.FloatToString(StatsOps.Average(Q3_cells));
			quartiles[2] = Q3_Average;

			float[] Q4_cells = Common.convertStringListToFloatArray(l.subList(Q3 + 1, Q4_DIFF));
			String Q4_Average = Cast.FloatToString(StatsOps.Average(Q4_cells));
			quartiles[3] = Q4_Average;

			return quartiles;
		}
		else
			return quartiles;
	}

	/**
	 *  Method that gets the average (arithmetic mean) from a single Multi-Dimensional array list,
	 *  and returns the result as a String.
	 *  
	 *  Column indexing begins at zero.
	 *
	 * @param c the index of the column to calculate the average for.
	 * @return the average as a String.
	 */
	public String avgColumn(int c)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Average(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(0, ROWS));
			return Cast.FloatToString(StatsOps.Average(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Average(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	/**
	 *  Method that gets the average (arithmetic mean) from a single Multi-Dimensional array list,
	 *  and returns the result as a String.
	 *  
	 *  Column indexing begins at zero.
	 *
	 * @param c the index of the column to calculate the average for.
	 * @param int startIndex the index of the column to start calculating from.
	 * @param int endIndex the index of the column to finish calculating.
	 * @return the average as a String.
	 */
	private String avgColumn(int c,int startIndex,int endIndex)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		if(startIndex < 0 | endIndex > this.ROWS)
			return "NaN";

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Average(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.FloatToString(StatsOps.Average(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Average(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	/**
	 * Method that gets the averages (arithmetic means) for multiple columns in a
	 * Multi-Dimensional array list columns.
	 *
	 * @param c integer indexes of the columns to calculate the averages for.
	 * @return the averages as an array list.
	 */
	public ArrayList<String> avgColumns(int[] c)
	{
		ArrayList<String> results = new ArrayList<String>();

		//for each column we need the average for
		for(int index : c)
		{
			//get the average for an individual column
			String result = avgColumn(index);

			//if the result is valid
			if (!result.contains("NaN") && !result.contains(""))
			{
				//add it to the results array list
				results.add(result);
			}
		}

		return results;
	}

	/**
	 *  Method that calculates the average over many Multi-Dimensional array list columns.
	 *  However rather than calculating the average per column, this method concatenates
	 *  the columns and calculates the average across them as a single column. the result 
	 *  is returned as a String.
	 *
	 * @param cols the indexes of the columns to average over.
	 * @return the average as a String.
	 */
	public String avgOverColumnRange(int[] cols)
	{
		//determine type of value in 1st cell
		String type = getColumnType(cols);

		ArrayList<String> concat = new ArrayList<String>();

		for(int index : cols)
		{
			List<String> temp = getColumn(index).subList(0, ROWS);

			for(String s : temp)
				concat.add(s);
		}

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(concat);
			return Cast.DoubleToString(StatsOps.Average(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(concat);
			return Cast.FloatToString(StatsOps.Average(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(concat);
			return Cast.DoubleToString(StatsOps.Average(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	/**
	 *  Method that will calculate the average value of a row,
	 *  but only if the values in each cell in the row, are of the same
	 *  data type.
	 *  
	 *  EXAMPLE.
	 *  
	 *  So the following average over row 1, columns 1 and 2 will return NaN.
	 *  
	 *  COL 1 | COL 2
	 *  -------------
	 *  23456 | Hello 
	 *  33466 | Bye 
	 *  75689 | Who 
	 *  
	 * @param r the row to calculate the average for.
	 * @param columns indexes of the columns which have rows to be included in calculation.
	 * @return the average across a row.
	 */
	@SuppressWarnings("unused")
	public String avgRow(int r, int[] columns)
	{
		//counters used to make sure that all items found across the
		//row are of the same type. To calculate an average, one of these
		//counters must be greater than one, while the rest should be zero.
		int Strings = 0;
		int integers = 0;
		int floats = 0;
		int doubles = 0;
		int datetimes = 0;

		ArrayList<String> row = new ArrayList<String>();

		//for the columns we want to average the rows over
		for (int i = 0; i < columns.length; i++)
		{
			//get the data type of the value ion the cell
			String type = RegexWrapper.getStringType(getCell(r, columns[i]));

			//if this is a value we can sum
			if (type.equals("int"))
			{
				integers++;
				row.add(getCell(r, columns[i]));
			}
			else if (type.equals("float"))
			{
				floats++;
				row.add(getCell(r, columns[i]));
			}
			else if (type.equals("double"))
			{
				doubles++;
				row.add(getCell(r, columns[i]));
			}
			else if (type.equals("date"))
			{
				datetimes++;
				row.add(getCell(r, columns[i]));
			}
			else if (type.equals("String"))
			{
				Strings++;
				return "";
			}
			else
			{
				return "NaN";
			}
		}

		//now check all row values are of the same type.
		if (integers == row.size())
		{
			int[] cells = Common.convertStringListToIntArray(row);
			return Cast.DoubleToString(StatsOps.Average(cells));
		}
		else if (floats == row.size())
		{
			float[] cells = Common.convertStringListToFloatArray(row);
			return Cast.FloatToString(StatsOps.Average(cells));
		}
		else if (doubles == row.size())
		{
			double[] cells = Common.convertStringListToDoubleArray(row);
			return Cast.DoubleToString(StatsOps.Average(cells));
		}
		else
			return "NaN";
	}

	/**
	 * Method that calculates the average for each column in the 2D array list,
	 * and adds a new row with these averages, to the array list.
	 *
	 * @return a String array containing the average of each column in the data array list.
	 */
	public String[] avgColumns_CreateNewRow()
	{
		ArrayList<String> new_row = new ArrayList<String>();

		for (int i = 0; i < COLUMNS; i++)
		{
			new_row.add(avgColumn(i));
		}

		content.add(new_row);
		ROWS++;

		return Common.convertStringListToArray(new_row);
	}

	//*****************************************
	//*****************************************
	//      FIND MINIMUM COLUMN VALUE
	//*****************************************
	//*****************************************

	/**
	 * Calculates the minimum value in a specific column.
	 *  
	 * Column indexes begin with 0.
	 *
	 * @param c the index of the column to find the minimum value from.
	 * @return the minimum value.
	 */
	public String minInColumn(int c)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Min(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(0, ROWS));
			return Cast.FloatToString(StatsOps.Min(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Min(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	/**
	 * Calculates the minimum value in a specific column.
	 *  
	 * Column indexes begin with 0.
	 *
	 * @param c the index of the column to find the minimum value from.
	 * @param int startIndex the index of the column to start calculating from.
	 * @param int endIndex the index of the column to finish calculating.
	 * @return the minimum value.
	 */
	private String minInColumn(int c,int startIndex,int endIndex)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		if(startIndex < 0 | endIndex > this.ROWS)
			return "NaN";

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Min(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.FloatToString(StatsOps.Min(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Min(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	//*****************************************
	//*****************************************
	//      FIND MAXIMUM COLUMN VALUE
	//*****************************************
	//*****************************************

	/**
	 * Calculates the maximum value in a specific column.
	 *  
	 * Column indexes begin with 0.
	 *
	 * @param c the index of the column to find the maximum value from.
	 * @return the maximum value.
	 */
	public String maxInColumn(int c)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Max(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(0, ROWS));
			return Cast.FloatToString(StatsOps.Max(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Max(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	/**
	 * Calculates the maximum value in a specific column.
	 *  
	 * Column indexes begin with 0.
	 *
	 * @param c the index of the column to find the maximum value from.
	 * @param int startIndex the index of the column to start calculating from.
	 * @param int endIndex the index of the column to finish calculating.
	 * @return the maximum value.
	 */
	private String maxInColumn(int c,int startIndex,int endIndex)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		if(startIndex < 0 | endIndex > this.ROWS)
			return "NaN";

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Max(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.FloatToString(StatsOps.Max(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Max(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	//*****************************************
	//*****************************************
	//           FIND VARIENCE
	//*****************************************
	//*****************************************

	/**
	 * Calculates the variance of a specific column.
	 *  
	 * Column indexes begin with 0.
	 *
	 * @param c the index of the column to find the maximum value from.
	 * @return the variance value.
	 */
	public String varInColumn(int c)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Varience(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Varience(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.Varience(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	/**
	 * Calculates the variance of a specific column.
	 *  
	 * Column indexes begin with 0.
	 *
	 * @param c the index of the column to find the maximum value from.
	 * @param int startIndex the index of the column to start calculating from.
	 * @param int endIndex the index of the column to finish calculating.
	 * @return the variance value.
	 */
	private String varInColumn(int c,int startIndex,int endIndex)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		if(startIndex < 0 | endIndex > this.ROWS)
			return "NaN";

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Varience(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Varience(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.Varience(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}
	//*****************************************
	//*****************************************
	//      FIND STANDARD DEVIATION
	//*****************************************
	//*****************************************

	/**
	 * Calculates the standard deviation of a specific column.
	 *  
	 * Column indexes begin with 0.
	 *
	 * @param c the index of the column to find the maximum value from.
	 * @return the standard deviation value.
	 */
	public String stdevInColumn(int c)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.StandardDeviation(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.StandardDeviation(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(0, ROWS));
			return Cast.DoubleToString(StatsOps.StandardDeviation(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	/**
	 * Calculates the standard deviation of a specific column.
	 *  
	 * Column indexes begin with 0.
	 *
	 * @param c the index of the column to find the maximum value from.
	 * @param int startIndex the index of the column to start calculating from.
	 * @param int endIndex the index of the column to finish calculating.
	 * @return the standard deviation value.
	 */
	private String stdevInColumn(int c,int startIndex,int endIndex)
	{
		//determine type of value in 1st cell
		String type = getColumnType(c);

		if(startIndex < 0 | endIndex > this.ROWS)
			return "NaN";

		//if this is a value we can sum
		if (type.equals("int"))
		{
			int[] cells = Common.convertStringListToIntArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.StandardDeviation(cells));
		}
		else if (type.equals("float"))
		{
			float[] cells = Common.convertStringListToFloatArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.StandardDeviation(cells));
		}
		else if (type.equals("double"))
		{
			double[] cells = Common.convertStringListToDoubleArray(getColumn(c).subList(startIndex, endIndex));
			return Cast.DoubleToString(StatsOps.StandardDeviation(cells));
		}
		else if (type.equals("String"))
			return "";
		else
			return "NaN";
	}

	//*****************************************
	//*****************************************
	//      FIND COLUMN TYPE
	//*****************************************
	//*****************************************

	/**
	 * Gets the data type for a column.
	 *
	 * @param c the index of the column to find the data type for.
	 * @return The data type as a string, i.e. "integer" or "float".
	 */
	private String getColumnType(int c)
	{
		String type = "unknown";
		int rowCounter = 1;

		while (type.equals("unknown") | type.equals("int") && rowCounter < ROWS)
		{
			String temp = RegexWrapper.getStringType(getCell(rowCounter, c));

			if (!temp.equals("unknown"))
				type = temp;

			rowCounter++;
		}

		return type;
	}

	/**
	 * Gets the data type for the String values in an array list.
	 *
	 * @param a The array list to find the element data type for.
	 * @return The data type as a string, i.e. "integer" or "float".
	 */
	@SuppressWarnings("unused")
	private String getColumnType(ArrayList<String> list)
	{
		String type = "unknown";
		int index = 0;

		while (type.equals("unknown") && index < list.size())
		{
			String temp = RegexWrapper.getStringType(list.get(index).toString());

			if (!temp.equals("unknown"))
				type = temp;

			index++;
		}

		return type;
	}

	/**
	 * Gets the data type for a number of specified columns.
	 *
	 * @param cols Array containing the indexes of the columns to find the data type for.
	 * @return The collective data type, i.e. "integer" or "float".
	 */
	private String getColumnType(int[] cols)
	{
		String type = "unknown";
		int columnCounter = 0;

		while (type.equals("unknown") && columnCounter < cols.length)
		{
			String temp = getColumnType(cols[columnCounter]);

			if (!temp.equals("unknown"))
				type = temp;

			columnCounter++;
		}

		return type;
	}

	//*****************************************
	//*****************************************
	//         Check value of columns
	//*****************************************
	//*****************************************

	/**
	 * Returns true if a specified String/s appear in the header of a specified column.
	 *
	 * @param keywords The Strings to check for.
	 * @param c The index of the column to check.
	 * @return True if all elements in the keywords array, appear in the column header
	 */
	private boolean areStringsInColumnName(String[] keywords, int c)
	{
		//get the column header
		String source = getCell(0, c);

		if (StringOps.areStringsInSource(source,keywords))
			return true;
		else
			return false;
	}

	/**
	 * Gets the index of the column that contains the specified keywords in its header.
	 *
	 * @param keywords The keywords to check for.
	 * @return The index of the column that contains the keywords.
	 */
	public int getColumnIndex(String[] keywords)
	{
		int columnCounter = 0;

		while (columnCounter < COLUMNS)
		{
			if (areStringsInColumnName(keywords, columnCounter))
				return columnCounter;

			columnCounter++;
		}

		return 0;
	}

	//*****************************************
	//*****************************************
	//         Print or Write Values
	//*****************************************
	//*****************************************

	/**
	 * Writes the contents of a specified column to the console.
	 *
	 * @param c The column to write to the console.
	 */
	public void printColumn(int c)
	{
		for(String s : this.getColumn(c))
			System.out.println(s);
	}

	/**
	 * Writes the contents of a specified row to the console.
	 *
	 * @param r The row to write to the console.
	 */
	public void printRow(int r)
	{
		for(String s : this.getRow(r))
			System.out.println(s);
	}

	/**
	 *  Writes the contents of a specified row to the CSV format.
	 *
	 * @param r The row to write to CSV format.
	 * @return The row in CSV format, as a String.
	 */
	public String printRowAsCSV(int r)
	{
		String rowString = "";

		ArrayList<String> row = this.getRow(r);

		for(String s : row)
		{
			rowString = rowString + s + ",";
		}

		return rowString;
	}

	/**
	 * Method that returns the contents of the 2D array list held by this class,
	 * in CSV format.
	 * @return a String in CSV format.
	 */
	public String printAsCSV()
	{
		String results = "";

		ArrayList<String> row;

		for(int r = 0; r < this.ROWS; r++)
		{
			row = this.getRow(r);

			for(String s : row)
				results = results + s + ",";

					results = results + "\n";
		}

		// trim trailing newline
		results = results.substring(0,results.length()-1);
		return results;	
	}

	/**
	 * Method that returns the contents of the 2D array list held by this class,
	 * in CSV format.
	 * @param spacerRowInterval the interval at which a new empty row should be inserted.
	 * @return a String in CSV format.
	 */
	public String printAsCSV(int spacerRowInterval)
	{
		if(spacerRowInterval > 0)
		{
			String results = "";

			ArrayList<String> row;

			for(int r = 0; r < this.ROWS; r++)
			{
				if(r % spacerRowInterval == 0 && r!=0)
					results = results + getSpacerRow();

				row = this.getRow(r);

				for(String s : row)
					results = results + s + ",";

						results = results + "\n";
			}

			// trim trailing newline
			results = results.substring(0,results.length()-1);

			return results;
		}
		else
			return this.printAsCSV();

	}

	/**
	 * Constructs an empty CSV row, containing the correct
	 * number of commas for the columns in the data held
	 * by this class.
	 * @return the empty row.
	 */
	private String getSpacerRow()
	{
		String spacer = "";

		for(int c = 0; c < this.COLUMNS;c++)
			spacer=spacer +",";

		spacer=spacer +"\n";
		return spacer;
	}

	/**
	 * Constructs an empty CSV row, containing the specified
	 * number of commas for the columns in the data held
	 * by this class.
	 * @param the number of commas.
	 * @return the empty row.
	 */
	private String getSpacerRow(int commas)
	{
		String spacer = "";

		for(int c = 0; c < commas ;c++)
			spacer=spacer +",";

		spacer=spacer +"\n";
		return spacer;
	}

	/**
	 * Method that returns the contents of the 2D array list held by this class,
	 * in CSV format.
	 * @param columnHeaders the headers to insert at the top of the CSV file.
	 * @param spacerRowInterval the interval at which a new empty row should be inserted. Set to zero for no interval.
	 * @return the data in CSV format.
	 */
	public String printAsCSV(String[] columnHeaders,int spacerRowInterval)
	{
		String header = "";

		for(String h : columnHeaders)
			header = header+ h+ ",";

		header = header + "\n";

		String contents = this.printAsCSV(spacerRowInterval);

		return header + contents;
	}

	/**
	 * Method that returns the contents of the 2D array list held by this class,
	 * in CSV format with summary statistics.
	 * @param columnHeaders the headers to insert at the top of the CSV file.
	 * @param statsInterval the interval at which stats should be computed.
	 * @return the data in csv format.
	 */
	public String printAsCSVWithStats(String[] columnHeaders,int statsInterval)
	{
		String header = "";

		for(String h : columnHeaders)
			header = header+ h+ ",";

		header = header + "\n";

		String contents = this.printAsCSVWithStats(statsInterval);

		return header + contents;
	}

	/**
	 * Method that returns the contents of the 2D array list held by this class,
	 * in CSV format with summary statistics.
	 * @param columnHeaders the headers to insert at the top of the CSV file.
	 * @return the data in csv format.
	 */
	public String printAsCSVWithStats(String[] columnHeaders)
	{
		String header = "";

		for(String h : columnHeaders)
			header = header+ h+ ",";

		header = header + "\n";

		String contents = this.printAsCSVWithStats();

		return header + contents;
	}
	
	/**
	 * Method that returns the contents of the 2D array list held by this class,
	 * in CSV format, with summary statistics.
	 * @param statsInterval the interval at which stats should be computed.
	 * @return a String in CSV format.
	 */
	private String printAsCSVWithStats(int statsInterval)
	{
		if(statsInterval > 0)
		{
			String results = ",";

			ArrayList<String> row;

			int startIndex = 0;
			int statsCounter = 0;

			for(int r = 0; r < this.ROWS; r++)
			{
				row = this.getRow(r);
				for(String s : row)
					results = results + s + ",";

						results = results + "\n,";

						if(statsCounter+1 == statsInterval)
						{
							statsCounter = -1;

							results = results.substring(0,results.length()-1);// trim last comma
							String sumRow = "SUM,";
							String avgRow = "AVG,";
							String minRow = "MIN,";
							String maxRow = "MAX,";
							String varRow = "VAR,";
							String stdevRow = "STDEV,";

							for(int c = 0; c < this.COLUMNS;c++)
							{
								sumRow = sumRow + this.sumColumn(c, startIndex, startIndex+statsInterval) + ",";
								avgRow = avgRow + this.avgColumn(c, startIndex, startIndex+statsInterval) + ",";
								minRow = minRow + this.minInColumn(c, startIndex, startIndex+statsInterval) + ",";
								maxRow = maxRow + this.maxInColumn(c, startIndex, startIndex+statsInterval) + ",";
								varRow = varRow + this.varInColumn(c, startIndex, startIndex+statsInterval) + ",";
								stdevRow = stdevRow + this.stdevInColumn(c, startIndex, startIndex+statsInterval) + ",";
							}

							sumRow = sumRow + "\n";
							avgRow = avgRow + "\n";
							minRow = minRow + "\n";
							maxRow = maxRow + "\n";
							varRow = varRow + "\n";
							stdevRow = stdevRow + "\n";

							results = results + sumRow;
							results = results + avgRow;
							results = results + minRow;
							results = results + maxRow;
							results = results + varRow;
							results = results + stdevRow;

							results = results + this.getSpacerRow(this.COLUMNS+1)+",";
							startIndex = r+1;
						}

						statsCounter++;
			}

			// trim trailing newline
			results = results.substring(0,results.length()-1);
			return results;
		}
		else
			return this.printAsCSV();

	}

	/**
	 * Method that returns the contents of the 2D array list held by this class,
	 * in CSV format, with summary statistics.
	 * @return a String in CSV format.
	 */
	private String printAsCSVWithStats()
	{
		String results = ",";

		ArrayList<String> row;

		for(int r = 0; r < this.ROWS; r++)
		{
			row = this.getRow(r);

			for(String s : row)
				results = results + s + ",";

					results = results + "\n,";
		}

		results = results.substring(0,results.length()-1);// trim last comma
		String sumRow = "SUM,";
		String avgRow = "AVG,";
		String minRow = "MIN,";
		String maxRow = "MAX,";
		String varRow = "VAR,";
		String stdevRow = "STDEV,";

		for(int c = 0; c < this.COLUMNS;c++)
		{
			sumRow = sumRow + this.sumColumn(c) + ",";
			avgRow = avgRow + this.avgColumn(c) + ",";
			minRow = minRow + this.minInColumn(c) + ",";
			maxRow = maxRow + this.maxInColumn(c) + ",";
			varRow = varRow + this.varInColumn(c) + ",";
			stdevRow = stdevRow + this.stdevInColumn(c) + ",";
		}

		sumRow = sumRow + "\n";
		avgRow = avgRow + "\n";
		minRow = minRow + "\n";
		maxRow = maxRow + "\n";
		varRow = varRow + "\n";
		stdevRow = stdevRow + "\n";
		
		results = results + sumRow;
		results = results + avgRow;
		results = results + minRow;
		results = results + maxRow;
		results = results + varRow;
		results = results + stdevRow;

		return results + this.getSpacerRow(this.COLUMNS+1);
	}
}
