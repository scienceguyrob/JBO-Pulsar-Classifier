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
 * File name: 	SOMClassifier.java
 * Package: uk.ac.man.jb.pct.classifiers.som
 * Created:	Jun 20, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.classifiers.som;

import java.awt.Point;
import java.util.ArrayList;
import uk.ac.man.jb.pct.data.ClassifierStatistics;
import uk.ac.man.jb.pct.data.I_ClassifierStatistics;
import uk.ac.man.jb.pct.data.I_DataSet;
import uk.ac.man.jb.pct.data.I_InputPattern;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.util.SerializableBaseObject;

/**
 * SOMClassifier. This class acts as a wrapper for the SelfOrganizingMap
 * class, providing a classifier in the process.
 * 
 * @author Rob Lyon
 */
public class SOMClassifier extends SerializableBaseObject
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The self organizing map to be used for classification.
     */
    public SelfOrganizingMap map = null;

    /**
     * A simple secondary representation of positive pulsar instances,
     * which can be used to build a visual heat map.
     */
    public ArrayList<FrequencyPoint> heatMap = null;

    /**
     * The positive pulsar patterns from the training data.
     */
    public ArrayList<I_InputPattern> positivePatterns = null;

    /**
     * The locations (co-ordinates) of positive pulsar instances from
     * the training data in the map.
     */
    public ArrayList<Point> positiveCoords = null;

    /**
     * The statisitis describing this classifier.
     */
    public I_ClassifierStatistics stats = null;

    //*****************************************
    //*****************************************
    //             Constructor
    //*****************************************
    //*****************************************

    /**
     * Default Constructor.
     */
    public SOMClassifier(){}

    /**
     * Primary constructor.
     * @param m the map to be used for classification.
     */
    public SOMClassifier(SelfOrganizingMap m){ map = m; }

    //*****************************************
    //*****************************************
    //               Getters
    //*****************************************
    //*****************************************

    public SelfOrganizingMap getMap() { return this.map; }
    public ArrayList<FrequencyPoint> getHeatMap() { return this.heatMap; }
    public ArrayList<I_InputPattern> getPositivePatterns() { return this.positivePatterns; }
    public ArrayList<Point> getPositiveCoords() { return this.positiveCoords; }
    public I_ClassifierStatistics getStats() { return this.stats; }
    
    //*****************************************
    //*****************************************
    //               Setters
    //*****************************************
    //*****************************************

    public void setMap(SelfOrganizingMap m) { this.map = m; }
    public void setHeatMap(ArrayList<FrequencyPoint> fp) { this.heatMap = fp; }
    public void setPositivePatterns(ArrayList<I_InputPattern> pp) { this.positivePatterns = pp; }
    public void setPositiveCoords(ArrayList<Point> pc) { this.positiveCoords = pc; }
    public void setStats(I_ClassifierStatistics cs) { this.stats = cs; }
    
    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /**
     * Locates the pulsar clusters in the self organising map.
     * @param t_data the training data to use to locate clusters.
     * @return true if clusters are located, else false.
     */
    public boolean locateClusters(I_DataSet t_data)
    {
	heatMap = new ArrayList<FrequencyPoint>();

	positivePatterns = new ArrayList<I_InputPattern>();

	for(int row = 0; row < t_data.getRows();row++)
	{
	    if(t_data.getDataRow(row).getClassMembership().equals(Constants.PULSAR))
		positivePatterns.add(t_data.getDataRow(row));
	}

	// The number of input patterns trained on.
	int patterns = positivePatterns.size();

	System.out.println("Patterns: "+patterns);

	if(positivePatterns != null)
	{
	    // Now find where these positive instances occur in the map
	    positiveCoords = new ArrayList<Point>();

	    for(int i = 0; i < positivePatterns.size() ; i++)
	    {
		Neuron winner = map.getWinningNeuron(positivePatterns.get(i).getData());
		Point p = new Point(winner.X,winner.Y);

		if(!positiveCoords.contains(p))
		    positiveCoords.add(p);

		boolean recorded = false;

		for(int j = 0 ; j < heatMap.size(); j++)
		{
		    if(heatMap.get(j).X == p.x &&
			    heatMap.get(j).Y == p.y)
		    {
			heatMap.get(j).frequency++;
			recorded = true;
			break;
		    }
		}

		if(!recorded) { heatMap.add(new FrequencyPoint(p.x,p.y,1)); }
	    }

	    return true;
	}
	else
	    return false;
    }

    /**
     * Classifies an input pattern using the specified algorithm.
     * @param p the input pattern.
     * @param algorithm the algorithm to use for classification.
     * @return a string classification.
     */
    public Object[] classify(I_InputPattern p, int algorithm)
    {
	switch(algorithm)
	{
	    case 0:
		return classifyNaive(p);
	    case 1:
		return classifyKNN(p);
	    default:
		return classifyNaive(p);
	}
    }

    /**
     * Naive classification, that uses the location of known
     * pulsar instances to predict if an input pattern is
     * a pulsar.
     * @param p the input pattern.
     * @return the classification returned by this algorithm.
     */
    public Object[] classifyNaive(I_InputPattern p)
    {
	Neuron winner = map.getWinningNeuron(p.getData());
	Point coord = new Point(winner.X,winner.Y);

	if(positiveCoords.contains(coord))
	    return new Object[]{Constants.PULSAR,coord};
	else
	    return new Object[]{Constants.RFI,coord};
    }

    public Object[] classifyKNN(I_InputPattern p)
    {
	Neuron winner = map.getWinningNeuron(p.getData());
	Point coord = new Point(winner.X,winner.Y);

	int K = 3; // Default.

	// Neighbours that are pulsars.
	int neighbours = 0;

	for(int i = 0; i < positiveCoords.size();i++)
	{
	    double distance = distanceBetweenPoints(coord.x,coord.y,positiveCoords.get(i).x,positiveCoords.get(i).y);

	    //System.out.println("Winner: ("+ coord.x + "," + coord.y+") compared to: ("+positiveCoords.get(i).x+","+positiveCoords.get(i).y+") Distance: "+distance);

	    if(distance < 1.1)
	    {
		neighbours++;

		if(neighbours >= K)
		    return new Object[]{Constants.PULSAR,coord};
	    }
	}

	return new Object[]{ Constants.RFI, coord };
    }

    /**
     * Method used to validate the performance of a SOM's classification
     * accuracy, using validation data.
     * @param v_data validation data.
     * @param algorithm the algorithm to use for validation.
     */
    public void validate(I_DataSet v_data, int algorithm)
    {
	stats = new ClassifierStatistics();

	for(int r = 0; r < v_data.getRows();r++)
	{
	    String classification = (String) this.classify(v_data.getDataRow(r), algorithm)[0].toString();

	    //  CLASSIFICATION APPLIED --------------------------------- ACTUAL CLASS
	    if( classification.equals(Constants.PULSAR) && v_data.getDataRow(r).getClassMembership().equals(Constants.PULSAR))
		stats.incrementTP(); // True Positive
	    else if(classification.equals(Constants.PULSAR) && v_data.getDataRow(r).getClassMembership().equals(Constants.RFI))
		stats.incrementFP(); // False positive
	    else if(classification.equals(Constants.RFI) && v_data.getDataRow(r).getClassMembership().equals(Constants.PULSAR))
		stats.incrementFN(); // False negative
	    else
		stats.incrementTN(); // True negative
	}

	stats.calculate();
	System.out.println(stats.toString());
    }

    /**
     * @return the statisitis that described the constructed classifier.
     */
    public I_ClassifierStatistics getStatistics() { return stats; }

    /**
     * Calculates the distance between two points in the 2D co-ordinate plane.
     * @param x1 Point x1.
     * @param y1 Point y1.
     * @param x2 Point x2.
     * @param y2 Point y2.
     * @returns The distance between (x1,y1) and (x2,y2).
     */
    static double distanceBetweenPoints(int x1, int y1, int x2, int y2)
    {
	double result = 0;
	//Take x2-x1, then square it
	double part1 = Math.pow((x2 - x1), 2);
	//Take y2-y1, then square it
	double part2 = Math.pow((y2 - y1), 2);
	//Add both of the parts together
	double underRadical = part1 + part2;
	//Get the square root of the parts
	result = (double)Math.sqrt(underRadical);
	//Return our result
	return result;
    }
}
