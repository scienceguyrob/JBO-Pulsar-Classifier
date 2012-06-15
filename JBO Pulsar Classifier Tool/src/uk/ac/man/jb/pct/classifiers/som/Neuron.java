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
 * File name: 	Neuron.java
 * Package: uk.ac.man.jb.pct.classifiers.som
 * Created:	4th March 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.classifiers.som;

import uk.ac.man.jb.pct.util.SerializableBaseObject;

/**
 * Class that represents a single neuron in the Self Organizing Map neural network.
 * @author Rob Lyon
 *
 */
public class Neuron extends SerializableBaseObject
{
    //*****************************************
    //*****************************************
    //               Variables
    //*****************************************
    //*****************************************

    /**
     * The vector of weights of connections.
     */
    public double[] weights;

    /**
     * The x position of the neuron.
     */
    public int X;

    /**
     * The y position of the neuron.
     */
    public int Y;

    /**
     * The length of the 2D space the neuron is in.
     */
    public int length;

    //*****************************************
    //*****************************************
    //           Getters & Setters
    //*****************************************
    //*****************************************

    public double[] getWeights() { return weights; }
    public void setWeights(double[] weights) { this.weights = weights; }
    public int getX() { return X; }
    public void setX(int x) { X = x; }
    public int getY() { return Y; }
    public void setY(int y) { Y = y; }
    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    //*****************************************
    //*****************************************
    //              Constructor
    //*****************************************
    //*****************************************

    /**
     * Default constructor required for serialisation.
     */
    public Neuron(){}

    /**
     * Primary constructor for the neuron.
     * 
     * @param x The x co-ordinate for the neuron.
     * @param y The y co-ordinate for the neuron.
     * @param length The length of the 2D space the neuron is in.
     */
    public Neuron(int x, int y, int length)
    {
	X = x;
	Y = y;
	this.length = length;
    }

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /**
     * Gaussian neighbourhood function.
     * 
     * @param winner The winning neuron.
     * @param it The current training iteration.
     */
    private double Gauss(Neuron winner, int it)
    {
	//Calculate the distance between this neuron, and
	//the winning neuron

	double distance = Math.sqrt(Math.pow(winner.X - X, 2) + Math.pow(winner.Y - Y, 2));
	//Decreasing factor        Neighbourhood factor             
	return Math.exp(-Math.pow(distance, 2) / (Math.pow(Math.exp(-it / (1000 / Math.log(length))) * length, 2)));
    }

    /**
     * Updates the weights of the winning neuron.
     * 
     * @param pattern The input pattern, containing planetary data.
     * @param winner The winning neuron during this training iteration.
     * @param iteration The current training iteration of the SOM.
     * @returns The average correction to the weights of each connection.
     */
    public double UpdateWeights(double[] pattern, Neuron winner, int iteration)
    {
	double sum = 0;

	//For each of the connections
	for (int i = 0; i < weights.length; i++)
	{
	    //Calculate the change to be made to the connection weights.
	    //Learning rate                     //Gaussian neighbourhood function.
	    double delta = (Math.exp(-iteration / 1000) * 0.1) * Gauss(winner, iteration) * (pattern[i] - weights[i]);

	    //increment the connection weight.
	    weights[i] += delta;
	    sum += delta;
	}

	//the total changes made to weights divided by length.
	return sum / weights.length;
    }
}
