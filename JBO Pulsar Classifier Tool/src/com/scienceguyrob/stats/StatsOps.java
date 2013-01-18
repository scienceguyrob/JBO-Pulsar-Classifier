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
 * File name: 	StatsOps.java
 * Created:	Nov 6, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package com.scienceguyrob.stats;

import java.util.ArrayList;

/**
 * The class StatsOps.java is a part of the package com.scienceguyrob.stats, in the 
 * project "Stats".
 *
 * Class that wraps commonly used mathematical operations.
 * 
 * @author Rob Lyon
 *
 */
public class StatsOps
{
	//*****************************************
	//*****************************************
	//              AVERAGES
	//*****************************************
	//*****************************************

	/**
	 * Calculates the average (arithmetic mean) of doubles in an array. 
	 *
	 * @param num the double array to calculate the average of.
	 * @return the average of the array of doubles.
	 */ 
	public static double Average(double[] num)
	{
		double sum = 0.0;
		for (int i = 0; i < num.length; i++)
			sum += num[i];

		return sum / (double)num.length;
	}

	/**
	 * Calculates the average (arithmetic mean) given a sum and a number of elements. 
	 *
	 * @param sum the sum of the values.
	 * @param elements the number of elements to calculate the average over.
	 * @return the average.
	 */
	public static double Average(double sum, int elements)
	{
		return sum / (double)elements;
	}

	/**
	 * Calculates the average (arithmetic mean) of integers in an array. 
	 *
	 * @param num the array of integers to calculate the average of.
	 * @return the average of the array of floats.
	 */    
	public static double Average(int[] num)
	{
		double sum = 0.0;
		for (int i = 0; i < num.length; i++)
			sum += num[i];

		double avg = sum / (double)num.length;

		return avg;
	}

	/**
	 * Calculates the average (arithmetic mean) of numbers of integer data type in an array 
	 *
	 * @param num the array of floats to calculate the average of.
	 * @return the average of the array of floats.
	 */    
	public static float Average(float[] num)
	{
		float sum = 0;

		for (int i = 0; i < num.length; i++)
			sum += num[i];

		float avg = sum / num.length;

		return avg;
	}

	/**
	 * Calculates the average (arithmetic mean) of numbers of integer data type in an ArrayList
	 * 
	 * @param num the array list of integers to calculate the average of.
	 * @return the average of the array list of integers.
	 */  
	public static double Average(ArrayList<Integer> num)
	{
		double sum = 0.0;
		for (int i = 0; i < num.size(); i++)
			sum += (double)num.get(i);

		double avg = sum / (double)num.size();

		return avg;
	}

	//*****************************************
	//*****************************************
	//              SUMS
	//*****************************************
	//*****************************************

	/**
	 * Calculates average of numbers of integer data type in an array
	 * 
	 * @param num the array of integers to calculate the sum of.
	 * @return the sum of the array of integers.
	 */    
	public static double Sum(int[] num)
	{
		double sum = 0.0;
		for (int i = 0; i < num.length; i++)
			sum += num[i];

		return sum;
	}

	/**
	 * Calculates average of numbers of integer data type in an array 
	 * 
	 * @param num the array of floats to calculate the sum of.
	 * @return the sum of the array of floats.
	 */    
	public static float Sum(float[] num)
	{
		float sum = 0;

		for (int i = 0; i < num.length; i++)
			sum += num[i];

		return sum;
	}

	/**
	 * Calculates average of numbers of double data type in an array 
	 * 
	 * @param num the array of doubles to calculate the sum of.
	 * @return the sum of the array of doubles.
	 */    
	public static double Sum(double[] num)
	{
		double sum = 0;

		for (int i = 0; i < num.length; i++)
			sum += num[i];

		return sum;
	}
	//*****************************************
	//*****************************************
	//              STDEV
	//*****************************************
	//*****************************************

	/**
	 * Calculates the standard deviation of an integer array list of values.
	 * 
	 * @param num the values to calculate the standard deviation for.
	 * @return the standard deviation.
	 */       
	public static double StandardDeviation(ArrayList<Integer> num)
	{
		double SumOfSqrs = 0;
		double avg = Average(num);
		for (int i = 0; i < num.size(); i++)
		{
			SumOfSqrs += Math.pow(((double)num.get(i) - avg), 2);
		}
		double n = (double)num.size();
		return Math.sqrt(SumOfSqrs / (n - 1));
	}

	/**
	 * Calculates the standard deviation of a integer array of values.
	 * 
	 * @param num the values to calculate the standard deviation for.
	 * @return the standard deviation.
	 */         
	public static double StandardDeviation(int[] num)
	{
		double SumOfSqrs = 0;
		double avg = Average(num);
		for (int i = 0; i < num.length; i++)
		{
			SumOfSqrs += Math.pow(((double)num[i] - avg), 2);
		}
		double n = (double)num.length;
		return Math.sqrt(SumOfSqrs / (n - 1));
	}

	/**
	 * Calculates the standard deviation of a double array of values.
	 * 
	 * @param num the values to calculate the standard deviation for.
	 * @return the standard deviation.
	 */   
	public static double StandardDeviation(double[] num)
	{
		double Sum = 0.0, SumOfSqrs = 0.0;
		for (int i = 0; i < num.length; i++)
		{
			Sum += num[i];
			SumOfSqrs += Math.pow(num[i], 2);
		}
		double topSum = (num.length * SumOfSqrs) - (Math.pow(Sum, 2));
		double n = (double)num.length;
		return Math.sqrt(topSum / (n * (n - 1)));
	}

	/**
	 * Calculates the standard deviation of a float array of values.
	 * 
	 * @param num the values to calculate the standard deviation for.
	 * @return the standard deviation.
	 */   
	public static double StandardDeviation(float[] num)
	{
		double Sum = 0.0, SumOfSqrs = 0.0;
		for (int i = 0; i < num.length; i++)
		{
			Sum += num[i];
			SumOfSqrs += Math.pow(num[i], 2);
		}
		double topSum = (num.length * SumOfSqrs) - (Math.pow(Sum, 2));
		double n = (double)num.length;
		return Math.sqrt(topSum / (n * (n - 1)));
	}

	/**
	 * Calculates the standard deviation of a specific column,
	 * from a data set, where the data set is stored as a multi-dimensional
	 * double array.
	 * 
	 * @param num the multi-dimensional double array.
	 * @param col the column to calculate the standard deviation for.
	 * @return the standard deviation.
	 */  
	public static double StandardDeviation(double[][] num, int col)
	{
		double Sum = 0.0, SumOfSqrs = 0.0;
		int len = num[0].length;
		for (int i = 0; i < len; i++)
		{
			Sum += num[i][col];
			SumOfSqrs += Math.pow(num[i][col], 2);
		}
		double topSum = (len * SumOfSqrs) - (Math.pow(Sum, 2));
		double n = (double) len;
		return Math.sqrt(topSum / (n * (n - 1)));
	}

	//*****************************************
	//*****************************************
	//              MIN
	//*****************************************
	//*****************************************

	/**
	 * Calculates minimum value of a set of integer data types in an array.
	 * 
	 * @param num the array of integers over which to calculate the max value.
	 * @return the max value.
	 */    
	public static int Min(int[] num)
	{
		int min = 2147483647;
		int number = 0;

		for (int i = 0; i < num.length; i++)
		{
			number = num[i];

			if (number < min)
				min = number;
		}

		return min;
	}

	/**
	 * Calculates minimum value of a set of float data types in an array.
	 * 
	 * @param num the array of floats over which to calculate the max value.
	 * @return the max value.
	 */    
	public static float Min(float[] num)
	{
		float min = Float.MAX_VALUE;
		float number = 0;

		for (int i = 0; i < num.length; i++)
		{
			number = num[i];

			if (number < min)
				min = number;
		}

		return min;
	}

	/**
	 * Calculates minimum value of a set of double data types in an array.
	 * 
	 * @param num the array of doubles over which to calculate the max value.
	 * @return the max value.
	 */    
	public static double Min(double[] num)
	{
		double min = Double.MAX_VALUE;
		double number = 0;

		for (int i = 0; i < num.length; i++)
		{
			number = num[i];

			//          d1      d2
			int result = Double.compare(number, min);
			if(result < 0)// d1 > d2
			{
				min = number;
			}
		}

		return min;
	}

	/**
	 * Calculates minimum value of a set of integer data types in an ArrayList.
	 * 
	 * @param num the array of integers over which to calculate the max value.
	 * @return the max value.
	 */ 
	public static double Min(ArrayList<Integer> num)
	{
		double min = Double.MAX_VALUE;
		double number = 0.0;

		for (int i = 0; i < num.size(); i++)
		{
			number = (double)num.get(i);

			if (number < min)
				min = number;
		}

		return min;
	}

	//*****************************************
	//*****************************************
	//              MAX
	//*****************************************
	//*****************************************

	/**
	 * Calculates maximum value of a set of integer data types in an array.
	 *
	 * @param num the array of integers over which to calculate the max value.
	 * @return the max value.
	 */    
	public static int Max(int[] num)
	{
		int max = Integer.MIN_VALUE;
		int number = 0;

		for (int i = 0; i < num.length; i++)
		{
			number = num[i];

			if (number > max)
				max = number;
		}

		return max;
	}

	/**
	 * Calculates maximum value of a set of floats in an array.
	 * 
	 * @param num the array of floats over which to calculate the max value.
	 * @return the maximum value.
	 */    
	public static float Max(float[] num)
	{
		float max = Float.MIN_VALUE;
		float number = 0;

		for (int i = 0; i < num.length; i++)
		{
			number = num[i];

			if (number > max)
				max = number;
		}

		return max;
	}

	/**
	 * Calculates maximum value of a set of doubles in an array.
	 * 
	 * @param num the array of doubles over which to calculate the max value.
	 * @return the maximum value.
	 */    
	public static double Max(double[] num)
	{
		double max = Double.MIN_VALUE;
		double number = 0;

		for (int i = 0; i < num.length; i++)
		{
			number = num[i];

			if (number > max)
				max = number;
		}

		return max;
	}

	/**
	 * Calculates maximum value of a set of integers in an array list.
	 * 
	 * @param num the array of integers over which to calculate the max value.
	 * @return the max value.
	 */  
	public static double Max(ArrayList<Integer> num)
	{
		double max = Double.MIN_VALUE;
		double number = 0.0;

		for (int i = 0; i < num.size(); i++)
		{
			number = (double)num.get(i);

			//                          d1      d2
			int result = Double.compare(number, max);
			if(result > 0)// d1 > d2
			{
				max = number;
			}

		}

		return max;
	}

	//*****************************************
	//*****************************************
	//              VARIENCE
	//*****************************************
	//*****************************************

	/**
	 * Calculates the variance of an array of doubles.
	 * 
	 * @param values the values over which to calculate the variance.
	 * @return the variance.
	 */
	public static double Varience(double[] values)
	{
		double valuesMean = Average(values);
		double sum = 0;

		for (int i = 0; i < values.length; i++)
		{
			sum += (values[i] - valuesMean) * (values[i] - valuesMean);
		}

		return sum / (values.length - 1);
	}

	/**
	 * Calculates the variance of an array of integers.
	 * 
	 * @param values the array over which to calculate the variance.
	 * @return the variance.
	 */
	public static double Varience(int[] values)
	{
		double valuesMean = Average(values);
		double sum = 0;

		for (int i = 0; i < values.length; i++)
		{
			sum += (values[i] - valuesMean) * (values[i] - valuesMean);
		}

		return sum / (values.length - 1);
	}

	/**
	 * Calculates the variance of an array of floats.
	 * 
	 * @param values the array over which to calculate the variance.
	 * @return the variance.
	 */
	public static double Varience(float[] values)
	{
		double valuesMean = Average(values);
		double sum = 0;

		for (int i = 0; i < values.length; i++)
		{
			sum += (values[i] - valuesMean) * (values[i] - valuesMean);
		}

		return sum / (values.length - 1);
	}

	//*****************************************
	//*****************************************
	//              OTHER
	//*****************************************
	//*****************************************


	/**
	 * Calculates Normal Distribution or Probability Density given the mean, and standard deviation 
	 *
	 * @param x The value for which you want the distribution. 
	 * @param mean The arithmetic mean of the distribution. 
	 * @param deviation The standard deviation of the distribution. 
	 * @return Returns the normal distribution for the specified mean and standard deviation.
	 */
	public static double NormalDistribution(double x, double mean, double deviation)
	{
		return NormalDensity(x, mean, deviation);
	}

	/**
	 * 
	 * @param x
	 * @param mean
	 * @param deviation
	 * @return the normal density.
	 */
	private static double NormalDensity(double x, double mean, double deviation)
	{
		return Math.exp(-(Math.pow((x - mean) / deviation, 2) / 2)) / Math.sqrt(2 * Math.PI) / deviation;
	}
}
