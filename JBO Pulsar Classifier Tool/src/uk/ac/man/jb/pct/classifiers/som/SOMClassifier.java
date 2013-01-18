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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import uk.ac.man.jb.pct.data.ClassifierStatistics;
import uk.ac.man.jb.pct.data.I_ClassifierStatistics;
import uk.ac.man.jb.pct.data.I_DataSet;
import uk.ac.man.jb.pct.data.I_InputPattern;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.util.Common;
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
     * The negative pulsar patterns from the training data.
     */
    public ArrayList<I_InputPattern> negativePatterns = null;

    /**
     * The locations (co-ordinates) of positive pulsar instances from
     * the training data in the map.
     */
    public ArrayList<Point> positiveCoords = null;

    /**
     * The locations (co-ordinates) of negative pulsar instances from
     * the training data in the map.
     */
    public ArrayList<Point> negativeCoords = null;

    /**
     * The statisitis describing this classifier.
     */
    public I_ClassifierStatistics stats = null;

    /**
     * Parameter for the KNN algorithm.
     */
    private int K = 3;

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
    public ArrayList<I_InputPattern> getNegativePatterns() { return this.negativePatterns; }
    public ArrayList<Point> getNegativeCoords() { return this.negativeCoords; }
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
    public void setNegativePatterns(ArrayList<I_InputPattern> pp) { this.negativePatterns = pp; }
    public void setNegativeCoords(ArrayList<Point> pc) { this.negativeCoords = pc; }
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
	negativePatterns = new ArrayList<I_InputPattern>();

	for(int row = 0; row < t_data.getRows();row++)
	{
	    if(t_data.getDataRow(row).getClassMembership().equals(Constants.PULSAR))
		positivePatterns.add(t_data.getDataRow(row));
	    else if(t_data.getDataRow(row).getClassMembership().equals(Constants.RFI))
		negativePatterns.add(t_data.getDataRow(row));
	}

	// The number of input patterns trained on.
	int positive_patterns = positivePatterns.size();
	int negative_patterns = negativePatterns.size();

	if(positivePatterns != null & negativePatterns != null)
	{
	    System.out.println("Positive Patterns: "+positive_patterns);
	    System.out.println("Negative Patterns: "+negative_patterns);
	    // Now find where these positive instances occur in the map
	    positiveCoords = new ArrayList<Point>();
	    negativeCoords = new ArrayList<Point>();

	    for(int i = 0; i < positivePatterns.size() ; i++)
	    {
		Neuron winner = map.getWinningNeuron(positivePatterns.get(i).getData());
		Point p = new Point(winner.X,winner.Y);

		//if(!positiveCoords.contains(p))
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

	    for(int i = 0; i < negativePatterns.size() ; i++)
	    {
		Neuron winner = map.getWinningNeuron(negativePatterns.get(i).getData());
		Point p = new Point(winner.X,winner.Y);

		//if(!negativeCoords.contains(p))
		negativeCoords.add(p);
	    }

	    Collections.sort(this.positiveCoords, new PointCompare());
	    Collections.sort(this.negativeCoords, new PointCompare());
	    return true;
	}
	else
	    return false;
    }

    /**
     * Locates the simplified pulsar clusters in the self organising map.
     * This method does not retain all the examples that fall in a cluster,
     * just one single example that exemplifies the cluster.
     * @param t_data the training data to use to locate clusters.
     * @return true if clusters are located, else false.
     */
    public boolean locateClustersSimplified(I_DataSet t_data)
    {
	heatMap = new ArrayList<FrequencyPoint>();

	positivePatterns = new ArrayList<I_InputPattern>();
	negativePatterns = new ArrayList<I_InputPattern>();

	for(int row = 0; row < t_data.getRows();row++)
	{
	    if(t_data.getDataRow(row).getClassMembership().equals(Constants.PULSAR))
		positivePatterns.add(t_data.getDataRow(row));
	    else if(t_data.getDataRow(row).getClassMembership().equals(Constants.NOT_PULSAR))
		negativePatterns.add(t_data.getDataRow(row));

	}

	// The number of input patterns trained on.
	int positive_patterns = positivePatterns.size();
	int negative_patterns = negativePatterns.size();

	if(positivePatterns != null & negativePatterns != null)
	{
	    System.out.println("Positive Patterns: "+positive_patterns);
	    System.out.println("Negative Patterns: "+negative_patterns);

	    // Now find where these positive instances occur in the map
	    positiveCoords = new ArrayList<Point>();
	    negativeCoords = new ArrayList<Point>();

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

	    for(int i = 0; i < negativePatterns.size() ; i++)
	    {
		Neuron winner = map.getWinningNeuron(negativePatterns.get(i).getData());
		Point p = new Point(winner.X,winner.Y);

		if(!negativeCoords.contains(p))
		    negativeCoords.add(p);
	    }

	    Collections.sort(this.positiveCoords, new PointCompare());
	    Collections.sort(this.negativeCoords, new PointCompare());
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
	    case 2:
		return classifyKNNSimplified(p);
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

	// Stores the nearest neighbours.
	Vector<Neighbour> neighbours = new Vector<Neighbour>();

	// Calculate distances to neighbours
	for(int i = 0; i < positiveCoords.size();i++)
	{
	    double distance = distanceBetweenPoints(coord.x,coord.y,positiveCoords.get(i).x,positiveCoords.get(i).y);
	    neighbours.add(new Neighbour(distance, Constants.PULSAR));
	}

	for(int i = 0; i < negativeCoords.size();i++)
	{
	    double distance = distanceBetweenPoints(coord.x,coord.y,negativeCoords.get(i).x,negativeCoords.get(i).y);
	    neighbours.add(new Neighbour(distance, Constants.RFI));
	}

	// Sort the neighbours such that element zero has the smallest distance.
	Collections.sort(neighbours);

	/*
	for(int i =0; i < neighbours.size();i++)
	{
	    System.out.println("Neighbour: "+ i + " "+ neighbours.elementAt(i).toString());
	}*/ // Debugging purposes

	// Next we want to find the K-nearest neighbours. We search the list of
	// potential neighbours for those top K unique neighbours. In the case of
	// two neighbours with the same distance, these will both be retained.
	int k_count =0;
	Vector<Neighbour> k_nearest_neighbours = new Vector<Neighbour>();

	for(int i =0; i < neighbours.size();i++)
	{
	    // if the distance measure found is unique
	    if(!k_nearest_neighbours.contains(neighbours.elementAt(i)))
	    {
		// increment count of K unique neighbours found.
		k_count++;

		if(k_count > this.K)// if we have more than K unique examples found
		    break;// break the loop

		// else add the new unique neighbour
		k_nearest_neighbours.add(neighbours.elementAt(i));		
	    }
	    else // the neighbour is not unique, but repeated
		k_nearest_neighbours.add(neighbours.elementAt(i));// still keep the neighbour for majority voting 

	}

	// do some explicit house keeping
	// for no other reason other than it makes me feel better :D
	neighbours.clear();
	neighbours = null;

	double positive_weight = 0.0;// the weighting towards a positive classification
	double negative_weight = 0.0;// the weighting towards a negative classification

	/*
	for(int i =0; i < k_nearest_neighbours.size();i++)
	{
	    System.out.println("Nearest Neighbour: "+ i + " "+ k_nearest_neighbours.elementAt(i).toString());
	}*/ // Debugging purposes

	// For each of the nearest neighbours found
	for(int i =0; i < k_nearest_neighbours.size();i++)
	{
	    // Update the weights.
	    if(k_nearest_neighbours.elementAt(i).getClassification().equals(Constants.PULSAR))
	    {
		double distance = k_nearest_neighbours.elementAt(i).getDistance();

		if(Double.compare(distance, 0.0)==0)
		    positive_weight = positive_weight +1;
		else
		    positive_weight = positive_weight + (double)(1.0/distance);
	    }
	    else
	    {
		double distance = k_nearest_neighbours.elementAt(i).getDistance();

		if(Double.compare(distance, 0.0)==0)
		    negative_weight = negative_weight +1;
		else
		    negative_weight = negative_weight + (double)(1.0/distance);
	    }
	}

	/*
	try
	{
	    System.out.println("Positive Weight: "+positive_weight + " Negative Weight: "+negative_weight);

	    if(Double.compare(positive_weight, negative_weight) < 0) // positive weight less than negative weight.
		System.out.println("Class: "+Constants.RFI);
	    else if(Double.compare(positive_weight, negative_weight) == 0) // positive weight equal to negative weight, favour positive
		System.out.println("Class: "+Constants.PULSAR);
	    else // positive weight greater than negative weight
		System.out.println("Class: "+Constants.PULSAR);
	    System.in.read();
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}*/ //Debugging Purposes

	// Now compare the weights. Whichever is greater decides the classification label
	// in the case of a tie, positive classification is favoured, as we don't want to throw away
	// any potential positive examples.
	if(Double.compare(positive_weight, negative_weight) < 0) // positive weight less than negative weight.
	    return new Object[]{ Constants.RFI, coord };
	else if(Double.compare(positive_weight, negative_weight) == 0) // positive weight equal to negative weight, favour positive
	    return new Object[]{Constants.PULSAR,coord};
	else // positive weight greater than negative weight
	    return new Object[]{Constants.PULSAR,coord};
    }

    /**
     * This method is used to find the K-nearest neighbours. There are a number of 
     * possible ways to a) define the closest and b) choose the class to predict
     * based on these neighbours. Here the K closest are defined as those K
     * neighbours with the smallest euclidean distance from a test data point.
     *
     * But there are many cases we must consider, as this is a discreet
     * case of KNN. Hence there will be many repeating neighbours:
     * 
     *
     *    Neighbour 	| Distance to test point | Class | K
     *    	1 	| 	 0.0 		 |   1	 | 1
     *    	2 	| 	 0.0 		 |   2   | 2
     *    	3 	| 	 0.0 		 |   1   | 3
     *    	4 	| 	 0.0 		 |   2   | 4
     *    	5 	| 	 0.0 		 |   1   | 5
     *    	6 	| 	 0.0 		 |   2   | 6
     *    	7 	| 	 0.0 		 |   1   | 7
     *    	8 	| 	 0.0 		 |   2   | 8
     *    	9 	| 	 1.0 		 |   1   | 9
     *    	10 	| 	 1.0 		 |   1   | 10
     *    	11 	| 	 1.0 		 |   1   | 11
     *    	12 	| 	 1.0 		 |   1   | 12
     *    	13 	| 	 2.0 		 |   1   | 13
     *    	14 	| 	 2.0 		 |   2   | 14
     *    	15 	| 	 2.0 		 |   1   | 15
     *    	16 	| 	 2.0 		 |   1   | 16
     *    	17 	| 	 3.0 		 |   1   | 17
     *    	18 	| 	 3.0 		 |   1   | 18
     *
     * When there are more than K neighbours with the same Euclidean distance
     * from the test point, and these neighbours all belong to different classes,
     * how to predict the correct class? Other methods in this class (classifyKNN())
     * perform a majority vote of the K unique neighbours. I.e. from above, the
     * classifyKNN() method would take neighbours 1-16, and perform a distance
     * weighted majority vote. This method works slightly differently. 
     *
     * It literally takes the K neighbours, and from those randomly chooses a class to predict.
     * For instance, if K=3, then this algorithm will firstly look at neighbours
     * 1-8 above. Three neighbours will be randomly chosen from between 1-8. 
     * 
     * Other examples:
     * 
     * If k=6, then 6 neighbours would be randomly chosen from 1-8 above.
     * If k=10, then all 1-8 would be chosen as neighbours, and 2 additional chosen 
     * 	randomly from the range 9-12.
     * 
     * If k=5 in a situation where we already have 4 neighbours, then we must choose the last
     * one at random then one is chosen at random. For instance here the last neighbour is chosen
     * at random from the range 5-6:
     * 
     *    Neighbour 	| Distance to test point | Class | K
     *    	1 	| 	 0.0 		 |   1	 | 1
     *    	2 	| 	 0.0 		 |   2   | 2
     *    	3 	| 	 1.0 		 |   1   | 3
     *    	4 	| 	 1.0 		 |   2   | 4
     *    	5 	| 	 2.0 		 |   1   | 5 
     *    	6 	| 	 2.0 		 |   2   | 6
     *    
     *    Then the fifth nearest neighbour is chosen randomly
     *    from either Neighbour 5 or 6.
     * 
     * I also provide a parameter so that you can decide how ties should be broken.
     * 
     * 0 = assign the class of the nearest neighbour.
     * 1 = brake ties randomly.
     * 
     * @param p
     * @param k
     * @param tieBreakingParameter if equal to 0 then assign the class of the nearest neighbour in the event of a tie,
     * if equal to 1, then break ties randomly.
     * @return
     */
    public Object[] discreetKNNClassify(I_InputPattern p, int k,int tieBreakingParameter)
    {
	Neuron winner = map.getWinningNeuron(p.getData());
	Point coord = new Point(winner.X,winner.Y);

	// Stores the nearest neighbours.
	Vector<Neighbour> neighbours = new Vector<Neighbour>();

	// Calculate distances to neighbours
	for(int i = 0; i < positiveCoords.size();i++)
	{
	    double distance = Common.distance(coord.getX(),coord.getY(),positiveCoords.get(i).getX(),positiveCoords.get(i).getY());
	    neighbours.add(new Neighbour(distance, Constants.PULSAR));
	}

	for(int i = 0; i < negativeCoords.size();i++)
	{
	    double distance = Common.distance(coord.getX(),coord.getY(),negativeCoords.get(i).getX(),negativeCoords.get(i).getY());
	    neighbours.add(new Neighbour(distance, Constants.RFI));
	}

	// Sort the neighbours such that element zero has the smallest distance.
	Collections.sort(neighbours);

	if(k > neighbours.size())
	{
	    System.out.println("K larger than number of neihbours. K "+ k + " Neihbours"+ neighbours.size());
	    return null;
	}

	System.out.println("K value:"+ k);

	//for(int i =0; i < neighbours.size();i++)
	//{
	//    System.out.println("Neighbour: "+ i + " "+ neighbours.elementAt(i).toString());
	//} // Debugging purposes

	int k_count =0;
	Vector<Neighbour> k_nearest_neighbours = new Vector<Neighbour>();
	Vector<NearestNeighbour> knn = new Vector<NearestNeighbour>();

	NearestNeighbour currentNeighbour = null;

	for(int i =0; i < neighbours.size();i++)
	{
	    // if the distance measure found is unique
	    if(!k_nearest_neighbours.contains(neighbours.elementAt(i)))
	    {
		// increment count of K unique neighbours found.
		k_count++;

		if(currentNeighbour != null)
		{
		    knn.add(currentNeighbour);
		    currentNeighbour = null;
		}

		if(k_count > k)// if we have more than K unique examples found
		    break;// break the loop


		currentNeighbour = new NearestNeighbour(neighbours.elementAt(i).getDistance(),k_count);
		currentNeighbour.processNeighbour(neighbours.elementAt(i));

		// else add the new unique neighbour
		k_nearest_neighbours.add(neighbours.elementAt(i));		
	    }
	    else // the neighbour is not unique, but repeated
	    {
		currentNeighbour.processNeighbour(neighbours.elementAt(i));
		k_nearest_neighbours.add(neighbours.elementAt(i));// still keep the neighbour for majority voting 
	    }

	}

	if(currentNeighbour != null )
	{
	    knn.add(currentNeighbour);
	    currentNeighbour = null;
	}

	//for(int i =0; i < knn.size();i++)
	 //   System.out.println("Initial KNN:"+ knn.get(i));

	int neighbours_chosen = 0;
	k_nearest_neighbours.clear();
	k_nearest_neighbours = null;
	Vector<NearestNeighbour> final_knn = new Vector<NearestNeighbour>();

	for(int i = 0 ; i < knn.size();i++)
	{
	    //System.out.println("Neighbours Chosen:" +neighbours_chosen + " k:"+k);
	    if (neighbours_chosen == k)
	    {
		//System.out.println("Loop Broken");
		break;
	    }

	    if((neighbours_chosen + knn.get(i).getFrequency()) < k )
	    {
		//System.out.println("Neighbours Chosen (" +neighbours_chosen + ") + freq("+knn.get(i).getFrequency()+") < "+k);
		if(knn.get(i).getFrequency()==1)
		{
		    final_knn.add(knn.get(i).drawNeighbourNoReplacement());
		    neighbours_chosen++;
		}
		else
		{
		    for(int j=0; j < knn.get(i).getFrequency();j++)
			final_knn.add(knn.get(i).drawNeighbourNoReplacement());

		    neighbours_chosen += knn.get(i).getFrequency();
		}
	    }
	    else if((neighbours_chosen + knn.get(i).getFrequency()) > k )
	    {
		//System.out.println("Neighbours Chosen (" +neighbours_chosen + ") + freq("+knn.get(i).getFrequency()+") > "+k);
		int diff = k - neighbours_chosen;

		if(diff < knn.get(i).getFrequency())
		    for(int j=0; j < diff;j++)
			final_knn.add(knn.get(i).drawNeighbourNoReplacement());

		neighbours_chosen += diff;
	    }
	    else if((neighbours_chosen + knn.get(i).getFrequency()) == k )
	    {
		//System.out.println("Neighbours Chosen (" +neighbours_chosen + ") + freq("+knn.get(i).getFrequency()+") == "+k);
		for(int j=0; j < knn.get(i).getFrequency();j++)
		    final_knn.add(knn.get(i).drawNeighbourNoReplacement());

		neighbours_chosen += knn.get(i).getFrequency();
	    }
	}

	//System.out.println("Final KNN size():" +final_knn.size());
	//for(int i =0; i < final_knn.size();i++)
	   // System.out.println("Post KNN:" +final_knn.get(i));

	for(int i =0; i < final_knn.size();i++)
	    if(final_knn.elementAt(i).getUnknown()>0)
	    {
		
		try
		{
		    System.out.println("Fuckup");
		    System.in.read();
		}
		catch (IOException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
	    }
	
	// do some explicit house keeping
	// for no other reason other than it makes me feel better :D
	neighbours.clear();
	neighbours = null;

	String classification = "";

	if(tieBreakingParameter== 0)
	    classification = NearestNeighbour.getClassificationBTN(final_knn);
	else if (tieBreakingParameter == 1)
	    classification = NearestNeighbour.getClassificationBTR(final_knn);

	System.out.println("Classification:"+classification);
	return new Object[]{classification,coord};
    }
    
    /**
     * This method is used to find the K-nearest neighbours. There are a number of 
     * possible ways to a) define the closest and b) choose the class to predict
     * based on these neighbours. Here the K closest are defined as those K
     * neighbours with the smallest euclidean distance from a test data point.
     *
     * But there are many cases we must consider, as this is a discreet
     * case of KNN. Hence there will be many repeating neighbours:
     * 
     *
     *    Neighbour 	| Distance to test point | Class | K
     *    	1 	| 	 0.0 		 |   1	 | 1
     *    	2 	| 	 0.0 		 |   2   | 2
     *    	3 	| 	 0.0 		 |   1   | 3
     *    	4 	| 	 0.0 		 |   2   | 4
     *    	5 	| 	 0.0 		 |   1   | 5
     *    	6 	| 	 0.0 		 |   2   | 6
     *    	7 	| 	 0.0 		 |   1   | 7
     *    	8 	| 	 0.0 		 |   2   | 8
     *    	9 	| 	 1.0 		 |   1   | 9
     *    	10 	| 	 1.0 		 |   1   | 10
     *    	11 	| 	 1.0 		 |   1   | 11
     *    	12 	| 	 1.0 		 |   1   | 12
     *    	13 	| 	 2.0 		 |   1   | 13
     *    	14 	| 	 2.0 		 |   2   | 14
     *    	15 	| 	 2.0 		 |   1   | 15
     *    	16 	| 	 2.0 		 |   1   | 16
     *    	17 	| 	 3.0 		 |   1   | 17
     *    	18 	| 	 3.0 		 |   1   | 18
     *
     * When there are more than K neighbours with the same Euclidean distance
     * from the test point, and these neighbours all belong to different classes,
     * how to predict the correct class? Other methods in this class (classifyKNN())
     * perform a majority vote of the K unique neighbours. I.e. from above, the
     * classifyKNN() method would take neighbours 1-16, and perform a distance
     * weighted majority vote. This method works slightly differently. 
     *
     * It takes the K unique neighbours, and from those randomly chooses a class to predict.
     * For instance, if K=3, then this algorithm will firstly look at neighbours
     * 1-8. From these it will decide the 1st nearest neighbour. To do this it will
     * randomly choose one of the neighbours between 1-8. Whichever class the 
     * chosen neighbour belongs to, will be the class chosen for the 1st nearest neighbour.
     * This will then be repeated for the 2nd nearest neighbours which are neighbours
     * 9-12. Finally this will be done for the 3rd nearest neighbours at 13-16.
     * Once K nearest neighbours have been chosen, the majority class of the K nearest
     * will be used to classify the new test point. For instance if we have:
     *
     * Nearest neighbour = class 1
     * 2nd nearest neighbour = class 1
     * 3rd nearest neighbour = class 2
     *
     * Then the overall classification will be positive. Hence we always use a odd
     * value for K to ensure there will be a majority class.
     * 
     * I also provide a parameter so that you can decide how ties should be broken.
     * 
     * 0 = assign the class of the nearest neighbour.
     * 1 = brake ties randomly.
     * 
     * @param p
     * @param k
     * @param tieBreakingParameter if equal to 0 then assign the class of the nearest neighbour in the event of a tie,
     * if equal to 1, then break ties randomly.
     * @return
     */
    public Object[] discreetKNNClassifyUniqueK(I_InputPattern p, int k,int tieBreakingParameter)
    {
	Neuron winner = map.getWinningNeuron(p.getData());
	Point coord = new Point(winner.X,winner.Y);

	// Stores the nearest neighbours.
	Vector<Neighbour> neighbours = new Vector<Neighbour>();

	// Calculate distances to neighbours
	for(int i = 0; i < positiveCoords.size();i++)
	{
	    double distance = Common.distance(coord.getX(),coord.getY(),positiveCoords.get(i).getX(),positiveCoords.get(i).getY());
	    neighbours.add(new Neighbour(distance, Constants.PULSAR));
	}

	for(int i = 0; i < negativeCoords.size();i++)
	{
	    double distance = Common.distance(coord.getX(),coord.getY(),negativeCoords.get(i).getX(),negativeCoords.get(i).getY());
	    neighbours.add(new Neighbour(distance, Constants.RFI));
	}

	// Sort the neighbours such that element zero has the smallest distance.
	Collections.sort(neighbours);

	if(k > neighbours.size())
	{
	    System.out.println("K larger than number of neihbours. K "+ k + " Neihbours"+ neighbours.size());
	    return null;
	}

	System.out.println("K value:"+ k);

	//for(int i =0; i < neighbours.size();i++)
	//{
	//    System.out.println("Neighbour: "+ i + " "+ neighbours.elementAt(i).toString());
	//} // Debugging purposes

	int k_count =0;
	Vector<Neighbour> k_nearest_neighbours = new Vector<Neighbour>();
	Vector<NearestNeighbour> knn = new Vector<NearestNeighbour>();

	NearestNeighbour currentNeighbour = null;

	for(int i =0; i < neighbours.size();i++)
	{
	    // if the distance measure found is unique
	    if(!k_nearest_neighbours.contains(neighbours.elementAt(i)))
	    {
		// increment count of K unique neighbours found.
		k_count++;

		if(currentNeighbour != null)
		{
		    knn.add(currentNeighbour);
		    currentNeighbour = null;
		}

		if(k_count > k)// if we have more than K unique examples found
		    break;// break the loop


		currentNeighbour = new NearestNeighbour(neighbours.elementAt(i).getDistance(),k_count);
		currentNeighbour.processNeighbour(neighbours.elementAt(i));

		// else add the new unique neighbour
		k_nearest_neighbours.add(neighbours.elementAt(i));		
	    }
	    else // the neighbour is not unique, but repeated
	    {
		currentNeighbour.processNeighbour(neighbours.elementAt(i));
		k_nearest_neighbours.add(neighbours.elementAt(i));// still keep the neighbour for majority voting 
	    }

	}

	if(currentNeighbour != null )
	{
	    knn.add(currentNeighbour);
	    currentNeighbour = null;
	}

	// do some explicit house keeping
	// for no other reason other than it makes me feel better :D
	neighbours.clear();
	neighbours = null;

	String classification = "";

	if(tieBreakingParameter== 0)
	    classification = NearestNeighbour.getClassificationBTN(knn);
	else if (tieBreakingParameter == 1)
	    classification = NearestNeighbour.getClassificationBTR(knn);

	System.out.println("Classification:"+classification);
	return new Object[]{classification,coord};
    }

    /**
     * If three neighbours within a particular distance are pulsars.
     * Distance defaulted to one.
     * @param p the input pattern
     * @return the classification.
     */
    public Object[] classifyKNNSimplified(I_InputPattern p)
    {
	Neuron winner = map.getWinningNeuron(p.getData());
	Point coord = new Point(winner.X,winner.Y);

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
	    else if(classification.equals(Constants.PULSAR) && !v_data.getDataRow(r).getClassMembership().equals(Constants.PULSAR))
		stats.incrementFP(); // False positive
	    else if(!classification.equals(Constants.PULSAR) && v_data.getDataRow(r).getClassMembership().equals(Constants.PULSAR))
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

    /**
     * Simple nested inner class used to compare point objects.
     * 
     * @author Rob Lyon
     */
    public class PointCompare implements Comparator<Point> 
    {	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Point a, Point b) 
	{
	    if (a.x < b.x) 
		return -1;
	    else if (a.x > b.x) 
		return 1;
	    else 
		return 0;
	}
    }
}
