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
 * File name: 	DiscreetKNNTestHarness.java
 * Package: uk.ac.man.jb.pct.test
 * Created:	Nov 22, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.test;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import uk.ac.man.jb.pct.classifiers.som.NearestNeighbour;
import uk.ac.man.jb.pct.classifiers.som.Neighbour;
import uk.ac.man.jb.pct.classifiers.som.Neuron;
import uk.ac.man.jb.pct.data.I_InputPattern;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.util.Common;

/**
 * DiscreetKNNTestHarness
 * @author Rob Lyon
 */
public class DiscreetKNNTestHarness
{
    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /**public static Object[] discreetKNNClassify(Point coord,ArrayList<Point> positiveCoords ,ArrayList<Point> negativeCoords, int k)
    {
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

	int[] splitIndexes = new int[k+1];
	int splitCount = 0;
	int k_count =0;
	Vector<Neighbour> k_nearest_neighbours = new Vector<Neighbour>();

	for(int i =0; i < neighbours.size();i++)
	{
	    // if the distance measure found is unique
	    if(!k_nearest_neighbours.contains(neighbours.elementAt(i)))
	    {
		// increment count of K unique neighbours found.
		k_count++;

		splitIndexes[splitCount] = i;
		splitCount++;

		if(k_count > k)// if we have more than K unique examples found
		    break;// break the loop

		// else add the new unique neighbour
		k_nearest_neighbours.add(neighbours.elementAt(i));		
	    }
	    else // the neighbour is not unique, but repeated
		k_nearest_neighbours.add(neighbours.elementAt(i));// still keep the neighbour for majority voting 

	}

	splitIndexes[k]=k_nearest_neighbours.size();
	for(int i =0; i < splitIndexes.length;i++)
	    System.out.println("si:"+splitIndexes[i]);

	for(int i =0; i < k_nearest_neighbours.size();i++)
	{
	    System.out.println("KNN: "+ i + " "+ k_nearest_neighbours.elementAt(i).toString());
	}

	// do some explicit house keeping
	// for no other reason other than it makes me feel better :D
	neighbours.clear();
	neighbours = null;

	String[] n = new String[k];
	Random r = new Random();

	for (int i =0; i < n.length;i++)
	{
	    n[i] = decideClass(k_nearest_neighbours.subList(splitIndexes[i],splitIndexes[i+1]));
	}

	int posCount = 0;
	int negCount = 0;
	int unknownCount = 0;

	for(int i =0; i < n.length;i++)
	{
	    if(n[i].toString().equals(Constants.PULSAR))
		posCount++;
	    else if(n[i].toString().equals(Constants.RFI))
		negCount++;
	    else
		unknownCount++;

	    System.out.println("n: "+ i + " "+ n[i].toString());
	} 

	System.out.println("+: "+ posCount + " -:"+ negCount + " unknown:"+unknownCount);

	// Whichever is greater decides the classification label
	// in the case of a tie, randomly choose.
	if(negCount > posCount) 
	{
	    System.out.println("Assigning -" +Constants.RFI + "- classification");
	    return new Object[]{ Constants.RFI, coord };
	}
	else if(posCount > negCount  ) 
	{
	    System.out.println("Assigning -" +Constants.PULSAR + "- classification");
	    return new Object[]{Constants.PULSAR,coord};
	}
	else if(posCount == negCount)
	{
	    int choice = r.nextInt(1 - 0 + 1) + 0;
	    if(choice==1)
	    {
		System.out.println("Assigning via TIE -" +Constants.PULSAR + "- classification");
		return new Object[]{ Constants.PULSAR, coord };
	    }
	    else
	    {
		System.out.println("Assigning via TIE -" +Constants.RFI + "- classification");
		return new Object[]{ Constants.RFI, coord };
	    }

	}
	else // positive weight greater than negative weight
	{
	    System.out.println("Assigning -" +Constants.UNKNOWN + "- classification");
	    return new Object[]{Constants.UNKNOWN,coord};
	}
    }*/
    
    public static Object[] discreetKNNClassify2(Point coord,ArrayList<Point> positiveCoords ,ArrayList<Point> negativeCoords, int k)
    {

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

	for(int i =0; i < knn.size();i++)
	    System.out.println("Initial KNN:"+ knn.get(i));
	
	int neighbours_chosen = 0;
	k_nearest_neighbours.clear();
	k_nearest_neighbours = null;
	Vector<NearestNeighbour> final_knn = new Vector<NearestNeighbour>();
	
	for(int i = 0 ; i < knn.size();i++)
	{
	    System.out.println("Neighbours Chosen:" +neighbours_chosen + " k:"+k);
	    if (neighbours_chosen == k)
	    {
		System.out.println("Loop Broken");
		break;
	    }
	    
	    if((neighbours_chosen + knn.get(i).getFrequency()) < k )
	    {
		System.out.println("Neighbours Chosen (" +neighbours_chosen + ") + freq("+knn.get(i).getFrequency()+") < "+k);
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
		System.out.println("Neighbours Chosen (" +neighbours_chosen + ") + freq("+knn.get(i).getFrequency()+") > "+k);
		int diff = k - neighbours_chosen;
		
		if(diff < knn.get(i).getFrequency())
		    for(int j=0; j < diff;j++)
			final_knn.add(knn.get(i).drawNeighbourNoReplacement());
		
		 neighbours_chosen += diff;
	    }
	    else if((neighbours_chosen + knn.get(i).getFrequency()) == k )
	    {
		System.out.println("Neighbours Chosen (" +neighbours_chosen + ") + freq("+knn.get(i).getFrequency()+") == "+k);
		for(int j=0; j < knn.get(i).getFrequency();j++)
			final_knn.add(knn.get(i).drawNeighbourNoReplacement());
		    
		  neighbours_chosen += knn.get(i).getFrequency();
	    }
	}

	System.out.println("Final KNN size():" +final_knn.size());
	for(int i =0; i < final_knn.size();i++)
	    System.out.println("Post KNN:" +final_knn.get(i));


	// do some explicit house keeping
	// for no other reason other than it makes me feel better :D
	neighbours.clear();
	neighbours = null;

	String classification = "";

	int tieBreakingParameter = 1;
	if(tieBreakingParameter== 0)
	    classification = NearestNeighbour.getClassificationBTN(final_knn);
	else if (tieBreakingParameter == 1)
	    classification = NearestNeighbour.getClassificationBTR(final_knn);

	System.out.println("Classification:"+classification);
	return new Object[]{classification,coord};
    }
    
    public static Object[] discreetKNNClassifyUniqueK(Point coord,ArrayList<Point> positiveCoords ,ArrayList<Point> negativeCoords, int k)
    {
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

	for(int i = 0 ; i < knn.size();i++)
	{
	    System.out.println(knn.get(i).toString());
	}
	
	// do some explicit house keeping
	// for no other reason other than it makes me feel better :D
	neighbours.clear();
	neighbours = null;
	
	String classification = NearestNeighbour.getClassificationBTR(knn);
	System.out.println("Classification:"+classification);
	return new Object[]{classification,coord};
    }

    /**
     * Run the tests.
     * @param args unused arguments.
     */
    public static void main2(String[] args)
    {
	Point w1 = new Point(0,0);
	Point w2 = new Point(0,0);
	Point w3 = new Point(0,0);
	Point w4 = new Point(0,0);
	Point w5 = new Point(0,0);
	//System.out.println(Common.distance((double)0, (double)0, (double)1, (double)0));
	//System.out.println(Common.distance((double)1, (double)0, (double)0, (double)0));

	ArrayList<Point> pCoords_1 = new ArrayList<Point>();
	pCoords_1.add(new Point(0,0));
	pCoords_1.add(new Point(0,0));
	pCoords_1.add(new Point(0,0));
	pCoords_1.add(new Point(0,0));
	pCoords_1.add(new Point(1,0));
	pCoords_1.add(new Point(1,0));
	pCoords_1.add(new Point(1,0));
	pCoords_1.add(new Point(1,0));
	pCoords_1.add(new Point(1,0));
	pCoords_1.add(new Point(1,0));


	ArrayList<Point> nCoords_1 = new ArrayList<Point>();
	nCoords_1.add(new Point(0,0));
	nCoords_1.add(new Point(0,0));
	nCoords_1.add(new Point(0,0));
	nCoords_1.add(new Point(0,0));
	nCoords_1.add(new Point(0,0));
	nCoords_1.add(new Point(0,0));
	nCoords_1.add(new Point(0,0));
	nCoords_1.add(new Point(0,0));
	nCoords_1.add(new Point(0,0));
	nCoords_1.add(new Point(2,0));
	nCoords_1.add(new Point(3,0));

	int count = 0;
	
	while(count < 15)
	{
	    System.out.println("*----------------------------------------*");
	    runTest(w1,pCoords_1,nCoords_1,3);
	    System.out.println("*----------------------------------------*");
	    count++;
	}
    }

    private static void runTest(Point coord,ArrayList<Point> positiveCoords ,ArrayList<Point> negativeCoords, int k)
    {
	discreetKNNClassify2(coord, positiveCoords, negativeCoords, k);
    }
}
