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
 * File name: 	NearestNeighbour.java
 * Package: uk.ac.man.jb.pct.classifiers.som
 * Created:	Nov 23, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.classifiers.som;

import java.util.Random;
import java.util.Vector;
import uk.ac.man.jb.pct.mvc.Constants;

/**
 * The class NearestNeighbour represents a nearest neighbour
 * for use by a KNN algorithm.
 * @author Rob Lyon
 */
public class NearestNeighbour
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The distance from the test data point to this neighbour.
     */
    private double distance = 0;

    /**
     * The K value that this nearest neighbour occurs at.
     */
    private int K = -1;

    /**
     * The number of positive values at this point.
     */
    private int positive = 0;

    /**
     * The number of negative values at this point.
     */
    private int negative = 0;

    /**
     * The number of unknown instances at this point.
     */
    private int unknown = 0;

    private int posCounter = 0;
    private int negCounter = 0;
    private int unknownCounter = 0;

    //*****************************************
    //*****************************************
    //            Constructor
    //*****************************************
    //*****************************************

    /**
     * Default constructor.
     */
    public NearestNeighbour(){}

    /**
     * The primary constructor.
     * @param d the distance from the test data point to this neighbour.
     * @param k the k value this neighbour occurs at.
     */
    public NearestNeighbour(double d, int k)
    {
	this.distance = d;
	this.K = k;
    }

    //*****************************************
    //*****************************************
    //          Getters & Setters
    //*****************************************
    //*****************************************

    /**
     * @return the k
     */
    public int getK() { return K; }

    /**
     * @param k the k to set
     */
    public void setK(int k) { K = k; }

    /**
     * @return the positive
     */
    public int getPositive() { return positive; }

    /**
     * @param positive the positive to set
     */
    public void setPositive(int positive) { this.positive = positive; }

    /**
     * @return the negative
     */
    public int getNegative() { return negative; }

    /**
     * @param negative the negative to set
     */
    public void setNegative(int negative) { this.negative = negative; }

    /**
     * @return the unknown
     */
    public int getUnknown() { return unknown; }

    /**
     * @param unknown the unknown to set
     */
    public void setUnknown(int unknown) { this.unknown = unknown; }

    /**
     * @return the distance
     */
    public double getDistance() { return distance; }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) { this.distance = distance; }

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    public void incrementPositives(){ this.positive++; this.posCounter++;}
    public void incrementNegatives(){ this.negative++; this.negCounter++;}
    public void incrementUnknown(){ this.unknown++; this.unknownCounter++;}
    public int getFrequency(){ return this.positive + this.negative + this.unknown;}

    public NearestNeighbour drawNeighbourNoReplacement()
    {
	Random r = new Random();
	//System.out.println("Draw no replacement ()");
	//System.out.println("+:"+this.posCounter +" -:"+this.negCounter +" unknown:" +this.unknownCounter);
	
	int min= 1 , max = this.posCounter+ this.negCounter + this.unknownCounter;
	//System.out.println("min:"+min +" max:"+max);

	// nextInt is normally exclusive of the top value,
	// so add 1 to make it inclusive
	int randomNum = r.nextInt(max - min + 1) + min;
	//System.out.println("Random:"+randomNum);

	// if the random number is in the range of the positives
	// then assign a positive class
	if (randomNum >= 1 && randomNum <= this.posCounter)
	{
	    this.posCounter--;
	    NearestNeighbour nn = new NearestNeighbour();
	    nn.incrementPositives();
	    nn.setDistance(this.distance);
	    return nn;
	}
	else if (randomNum >= this.posCounter+1 && randomNum <= this.posCounter+negCounter)// assign a negative classification.
	{
	    this.negCounter--;
	    NearestNeighbour nn = new NearestNeighbour();
	    nn.incrementNegatives();
	    nn.setDistance(this.distance);
	    return nn;
	}
	else
	{
	    this.unknownCounter--;
	    NearestNeighbour nn = new NearestNeighbour();
	    nn.incrementUnknown();
	    nn.setDistance(this.distance);
	    return nn;
	}
    }
    /**
     * Processes each neighbour found at this position.
     * For instance, if more than one test instance occurs 
     * at the same test point.
     * @param n the neighbour.
     */
    public void processNeighbour(Neighbour n)
    {
	String classification = n.getClassification();
	if(classification.equals(Constants.PULSAR))
	    this.incrementPositives();
	else if(classification.equals(Constants.RFI))
	    this.incrementNegatives();
	else
	    this.incrementUnknown();
    }

    /**
     * @return a majority vote classification. In the case of a tie, the tie is broken randomly.
     * For instance here are example neighbours all occurring at the same distance from the test point.
     * Lets imagine any one of these is a valid 1st nearest neighbour. How to choose a class label?
     * 
     *       Neighbour 	| Distance to test point | Class 
     *    	1 	| 	 0.0 		 |   1	 
     *    	2 	| 	 0.0 		 |   2   
     *    	3 	| 	 0.0 		 |   1   
     *    	4 	| 	 0.0 		 |   2   
     *    	5 	| 	 0.0 		 |   1   
     *    	6 	| 	 0.0 		 |   2   
     *    	7 	| 	 0.0 		 |   1  
     *    
     * If the neighbours belong to different classes as above, then this method 
     * will perform a majority vote to decide which to use. In this case the frequency of
     * class 1 = 4 and the frequency of class 2 = 3. Hence the class 1 label will be
     * applied. However if the frequencies are the same, then a random choice is performed.
     */
    public String getNeighbourClassification_majorityrule_BTR()
    {
	if(this.positive > this.negative)
	    return Constants.PULSAR;
	else if(this.positive < this.negative)
	    return Constants.RFI;
	else if(this.positive == this.negative) // Randomly break tie
	{
	    Random r = new Random();
	    double d = r.nextDouble();// generate number between 0.0-1.0
	    double split = 0.5;

	    // Just in case d is equal to the split point.
	    while(Double.compare(d, split) == 0)
		d = r.nextDouble();

	    if(Double.compare(d, split) > 0)// d greater than 0.5
		return Constants.PULSAR; 
	    else
		return Constants.RFI;
	}
	else
	    return Constants.UNKNOWN;
    }

    /**
     * @return a non majority vote classification of those instances at a single point. 
     * If more than 1 neighbour is found at the same point (with different classes),
     * then just one of these is randomly chosen to supply a class label for this neighbour. 
     * Otherwise if all neighbours at the same point have the same label, then this label is returned.
     * For instance here are example neighbours all occurring at the same distance from the test point.
     * Lets imagine any one of these is a valid 1st nearest neighbour. How to choose a class label?
     * 
     *       Neighbour 	| Distance to test point | Class 
     *    	1 	| 	 0.0 		 |   1	 
     *    	2 	| 	 0.0 		 |   2   
     *    	3 	| 	 0.0 		 |   1   
     *    	4 	| 	 0.0 		 |   2   
     *    	5 	| 	 0.0 		 |   1   
     *    	6 	| 	 0.0 		 |   2   
     *    	7 	| 	 0.0 		 |   1  
     *    
     * If the neighbours belong to different classes as above, then this method 
     * will randomly choose the label of one of these points to use. However if all the labels
     * are the same, then this is the label returned.
     */
    public String getNeighbourClassificationBTR()
    {
	if(this.getFrequency() > 1)
	{
	    if(this.getFrequency() == this.positive) // all instances are positive.
		return Constants.PULSAR;
	    else if(this.getFrequency() == this.negative)
		return Constants.RFI; // all instances are negative.
	    else if(this.getFrequency() == this.unknown)
		return Constants.RFI; // all instances are unknown.
	    else
	    {
		// If we reach here then there are a number of positive/negative/unknown
		// all at the precise same point. In this case we randomly pick one.
		Random r = new Random();
		int min= 1 , max = this.getFrequency();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = r.nextInt(max - min + 1) + min;

		// if the random number is in the range of the positives
		// then assign a positive class
		if (randomNum >= 1 && randomNum <= positive)
		    return Constants.PULSAR;
		else if (randomNum >= positive+1 && randomNum <= positive+negative)// assign a negative classification.
		    return Constants.RFI;
		else
		    return Constants.UNKNOWN; // else unknown has been chosen.
	    }
	}
	else // only one instance at this point
	{
	    if(this.positive > this.negative)
		return Constants.PULSAR;
	    else if(this.positive < this.negative)
		return Constants.RFI;
	    else
		return Constants.UNKNOWN;
	}
    }

    /**
     * Derives a classification from K nearest neighbours.
     * Simple majority vote rule. Ties are broken randomly.
     * @param nn the nearest neighbours.
     * @return the String classification.
     */
    public static String getClassificationBTR(Vector<NearestNeighbour> nn)
    {
	// The nearest neighbour will be first in the list.

	int posCount = 0;
	int negCount = 0;
	int unknownCount = 0;

	for(int i =0; i < nn.size();i++)
	{
	    String classification = nn.get(i).getNeighbourClassificationBTR();
	    if(classification.equals(Constants.PULSAR))
		posCount++;
	    else if(classification.equals(Constants.RFI))
		negCount++;
	    else
		unknownCount++;

	    System.out.println("neighbour: "+ i + " class:"+ classification);
	} 

	System.out.println("+: "+ posCount + " -:"+ negCount + " unknown:"+unknownCount);

	// Whichever is greater decides the classification label
	// in the case of a tie, randomly choose.
	if( negCount > posCount ) 	
	    return Constants.RFI;
	else if( posCount > negCount  ) 
	    return Constants.PULSAR;
	else if( posCount == negCount )
	{
	    Random r = new Random();
	    double d = r.nextDouble();// generate number between 0.0-1.0
	    double split = 0.5;

	    // Just in case d is equal to the split point.
	    while(Double.compare(d, split) == 0)
		d = r.nextDouble();

	    if(Double.compare(d, split) > 0)// d greater than 0.5
		return Constants.PULSAR; 
	    else
		return Constants.RFI;
	}
	else // label unknown
	    return Constants.UNKNOWN;

    }

    /**
     * Derives a classification from K nearest neighbours.
     * Simple majority vote rule. Ties are broken by choosing the
     * class of the nearest example to the test point.
     * @param nn the nearest neighbours.
     * @return the String classification.
     */
    public static String getClassificationBTN(Vector<NearestNeighbour> nn)
    {
	// The nearest neighbour will be first in the list.

	int posCount = 0;
	int negCount = 0;
	int unknownCount = 0;

	for(int i =0; i < nn.size();i++)
	{
	    String classification = nn.get(i).getNeighbourClassificationBTR();
	    if(classification.equals(Constants.PULSAR))
		posCount++;
	    else if(classification.equals(Constants.RFI))
		negCount++;
	    else
		unknownCount++;

	    System.out.println("neighbour: "+ i + " class:"+ classification);
	} 

	System.out.println("+: "+ posCount + " -:"+ negCount + " unknown:"+unknownCount);

	// Whichever is greater decides the classification label
	// in the case of a tie, randomly choose.
	if(negCount > posCount) 
	{
	    System.out.println("Assigning -" +Constants.RFI + "- classification");
	    return Constants.RFI;
	}
	else if(posCount > negCount  ) 
	{
	    System.out.println("Assigning -" +Constants.PULSAR + "- classification");
	    return Constants.PULSAR;
	}
	else if(posCount == negCount)
	    return nn.get(0).getNeighbourClassificationBTR();
	else // label unknown
	    return Constants.UNKNOWN;
    }

    /**
     * Derives a classification from K nearest neighbours.
     * Simple majority vote rule. Ties are broken by choosing the
     * class of the nearest example to the test point.
     * @param nn the nearest neighbours.
     * @return the String classification.
     */
    public static String getClassificationNaive(Vector<NearestNeighbour> nn)
    {
	// The nearest neighbour will be first in the list.

	int posCount = 0;
	int negCount = 0;
	int unknownCount = 0;

	for(int i =0; i < nn.size();i++)
	{
	    String classification = nn.get(i).getNeighbourClassificationBTR();
	    if(classification.equals(Constants.PULSAR))
		posCount++;
	    else if(classification.equals(Constants.RFI))
		negCount++;
	    else
		unknownCount++;

	    System.out.println("neighbour: "+ i + " class:"+ classification);
	} 

	System.out.println("+: "+ posCount + " -:"+ negCount + " unknown:"+unknownCount);

	// Whichever is greater decides the classification label
	// in the case of a tie, randomly choose.
	if(negCount > posCount) 
	{
	    System.out.println("Assigning -" +Constants.RFI + "- classification");
	    return Constants.RFI;
	}
	else if(posCount > negCount  ) 
	{
	    System.out.println("Assigning -" +Constants.PULSAR + "- classification");
	    return Constants.PULSAR;
	}
	else if(posCount == negCount)
	    return nn.get(0).getNeighbourClassificationBTR();
	else // label unknown
	    return Constants.UNKNOWN;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "Nearest neighbour "+this.K+ " dist:"+ this.distance+ " +:"+this.positive + " -:"+this.negative + " unknown:"+this.unknown;
    }
}
