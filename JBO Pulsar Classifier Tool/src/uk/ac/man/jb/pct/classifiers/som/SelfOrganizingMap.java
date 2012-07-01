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
 * File name: 	SelfOrganizingMap.java
 * Package: uk.ac.man.jb.pct.classifiers.som
 * Created:	4th March 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.classifiers.som;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import uk.ac.man.jb.pct.util.SerializableBaseObject;

/**
 * Class that represents a self organising map, with additional methods for classification.
 * 
 * @author Rob Lyon
 *
 */
public class SelfOrganizingMap extends SerializableBaseObject
{
    //*****************************************
    //*****************************************
    //               Variables
    //*****************************************
    //*****************************************

    /**
     * Stores the output neurons.
     */ 
    public Neuron[][] outputNeurons;

    /**
     * The current training iteration.
     */ 
    public int iteration = 0;

    /**
     * The number of input data attributes.
     */ 
    public int attributes = 22;

    /**
     * The width of each side of the Map used to classify the output.
     */ 
    public int mapWidth = 10;

    /**
     * The maximum permissible error rate for the SOM neural network.
     */ 
    public double maximumErrorRate = 0.000001;

    /**
     * The input data.
     */ 
    private ArrayList<double[]> data;

    /**
     * Random number generator used to generate initial connection weights.
     */ 
    private Random randomGenerator = new Random();

    //*****************************************
    //*****************************************
    //              Constructor
    //*****************************************
    //*****************************************

    /**
     * Default constructor.
     */
    public SelfOrganizingMap(){}
    
    /**
     * Primary constructor.
     * 
     * @param t the training data to be passed to the map.
     */
    public SelfOrganizingMap(ArrayList<double[]> t){ data = t;}

    //*****************************************
    //*****************************************
    //          Getters & Setters
    //*****************************************
    //*****************************************

    public Neuron[][] getOutputNeurons() { return outputNeurons; }
    public void setOutputNeurons(Neuron[][] outputNeurons) { this.outputNeurons = outputNeurons; }
    public int getIteration() { return iteration; }
    public void setIteration(int iteration) { this.iteration = iteration; }
    public int getAttributes() { return attributes; }
    public void setAttributes(int attributes) { this.attributes = attributes; }
    public int getMapWidth() { return mapWidth; }
    public void setMapWidth(int mapWidth) { this.mapWidth = mapWidth; }
    public double getMaximumErrorRate() { return maximumErrorRate; }
    public void setMaximumErrorRate(double maximumErrorRate) { this.maximumErrorRate = maximumErrorRate; }

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /**
     * Method which initialises the 2D output Map with neurons.
     */
    public void Build()
    {
	//setup output units as 2D map
	outputNeurons = new Neuron[mapWidth][mapWidth];

	for (int i = 0; i < mapWidth; i++)
	{
	    for (int j = 0; j < mapWidth; j++)
	    {
		//create the output neuron
		outputNeurons[i][j] = new Neuron(i, j, mapWidth);

		//setup the number of connections for the neuron,
		//each of which requires a weighting.
		//For the planetary data in the application,
		//there are 7 attributes.
		outputNeurons[i][j].weights = new double[attributes];

		//For each connection, initialise a random connection
		// weight.
		for (int k = 0; k < attributes; k++)
		{
		    outputNeurons[i][j].weights[k] = randomGenerator.nextDouble();
		}
	    }
	}
    }

    /**
     * Method used to normalise the input data to smooth out major statistical anomalies.
     */
    public void NormalisePatterns()
    {
	for (int j = 0; j < attributes; j++)
	{
	    double sum = 0;

	    for (int i = 0; i < data.size(); i++)
		sum += data.get(i)[j];

	    double average = sum / data.size();

	    for (int i = 0; i < data.size(); i++)
		data.get(i)[j] = data.get(i)[j] / average;
	}
    }

    /**
     * Trains the SOM neural network.
     * 
     * @param maxError The maximum permissible error rate for the network.
     */
    public void Train(double maxError)
    {
	//set the current error level to largest
	//permissible double value.
	double currentError = Double.MAX_VALUE;

	//While the error rate is unacceptable.
	while (currentError > maxError)
	{
	    currentError = 0;

	    //Create a data structure that can store training data.
	    ArrayList<double[]> trainingSet = new ArrayList<double[]>();

	    //For each input data instance, add this to the training set.
	    Iterator<double[]> dataSetIterator = data.iterator(); 
	    while(dataSetIterator.hasNext()) { trainingSet.add(dataSetIterator.next()); }

	    //Now we actually train the network using the data.
	    for (int i = 0; i < data.size(); i++)
	    {
		//Choose a pattern at random from the training set.
		double[] pattern = trainingSet.get(randomGenerator.nextInt(data.size() - i));

		//String concat = " ";
		//for(int z = 0; z < pattern.length ; z++)
		//{
		//	concat += Double.toString(pattern[z]);
		//	concat += " ";
		//}

		//c.output("Training on pattern: "+concat);

		//Train the network on the random pattern,
		//and update the error rate.

		//the error for the pattern about to be passed into the network
		double patternError = 0;

		//Retrieve the output neuron that fires 
		//upon reading the input pattern.
		Neuron winner = getWinningNeuron(pattern);
		//c.output("Winning Neuron: X:"+winner.X+" Y: " +winner.Y);

		//For each neuron in the 2D map space
		for (int j = 0; j < mapWidth; j++)
		{
		    for (int k = 0; k < mapWidth; k++)
		    {
			//Calculate the corrections that need to be made
			//to the weight of connections around each neuron. Those
			//closest to the output neuron at position [i,j] will have their
			//connection weights strengthened. The average corrections to connection
			//weights is returned and added to the error rate.
			patternError += outputNeurons[j][k].UpdateWeights(pattern, winner, iteration);
		    }
		}

		//Increment the number of training iterations.
		iteration++;

		//update the current error
		currentError += Math.abs(patternError / (mapWidth * mapWidth));

		//Remove the pattern from the training set.
		trainingSet.remove(pattern);
	    }
	}
    }

    /**
     * Returns the 'winning' output neuron that fires upon reading
     * the specified input pattern.
     * 
     * @param pattern The input pattern.
     * @return The winning neuron.
     */
    public Neuron getWinningNeuron(double[] pattern)
    {
	//The neuron that will fire open reading the input pattern.
	Neuron winner = null;

	//Stores the distance between the input pattern,
	//and the closest neuron to the pattern in the 2D output 
	//space. Initially set it to farthest possible distance away.
	double closest = Double.MAX_VALUE;

	//For each neuron in the 2D output space.
	for (int i = 0; i < mapWidth; i++)
	{
	    for (int j = 0; j < mapWidth; j++)
	    {
		//Find the distance between the input pattern
		//and the weights of connections for the neuron at position [i,j] 

		double distance = CalcEuclideanDistance(pattern, outputNeurons[i][j].weights);

		//if the distance is less than the current closest output neuron,
		//then we change the distance to the newer smaller value,
		//and select the neuron at this closer distances as the winning
		//output neuron.
		if (distance < closest)
		{
		    winner = outputNeurons[i][j];
		    closest = distance;
		}
	    }
	}

	//return the winning neuron.
	return winner;
    }

    /**
     * Calculates the Euclidean distance between each node's weight
     * vector and the current input vector.
     * @param inputVector The input vector of the neuron.
     * @param weights The connection weights.
     * @return The distance as a double.
     */
    private double CalcEuclideanDistance(double[] inputVector, double[] weights)
    {
	double distance = 0;

	for (int i = 0; i < inputVector.length; i++){ distance += Math.pow((inputVector[i] - weights[i]), 2); }

	return Math.sqrt(distance);
    }
}
