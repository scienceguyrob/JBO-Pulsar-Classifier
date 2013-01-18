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
 * File name: 	Neighbour.java
 * Package: uk.ac.man.jb.pct.classifiers.som
 * Created:	Jun 20, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.classifiers.som;

/**
 * Neighbour class represents neighbour nodes to the winning neuron.
 * @author Rob Lyon
 */
public class Neighbour implements Comparable<Object>
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The distance to the point being classified.
     */
    private double distance;

    /**
     * The class of this neighbour.
     */
    private String classification;
    
    /**
     * The k nearest neighbour this object is.
     */
    private int k = -1;

    //*****************************************
    //*****************************************
    //             Constructor
    //*****************************************
    //*****************************************

    /**
     * Default Constructor.
     */
    public Neighbour(){}

    /**
     * Default Constructor.
     */
    public Neighbour(double d,String c)
    {
	this.distance = d;
	this.classification = c;
    }

    //*****************************************
    //*****************************************
    //               Getters
    //*****************************************
    //*****************************************

    public double getDistance() { return this.distance; }

    public String getClassification() { return this.classification; }
    
    public int getK(){ return this.k; }
    
    public void setK(int knn){ this.k = knn; }

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
	int hash = 1;
	hash = hash * 17 + Double.toString(this.distance).hashCode();
	hash = hash * 31 + this.classification.hashCode();
	return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
	if (obj == null)
	    return false;
	if (obj == this)
	    return true;
	if (obj.getClass() != getClass())
	    return false;

	Neighbour n = (Neighbour) obj;
	//if(n.hashCode()== this.hashCode() & n.classification.equals(this.classification))
	if(Double.compare(this.getDistance(),n.getDistance())==0)
	    return true;
	else
	    return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object obj) 
    {
	if (obj.getClass() != getClass())
	    return -1;

	Neighbour n = (Neighbour) obj;
	// Negative result means that obj is less than.
	// Zero result means that obj is equal to.
	// Positive result means that obj is more than.
	return Double.compare(this.getDistance(),n.getDistance());
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "Neighbour, Distance: "+Double.toString(this.distance) + " Class: "+this.classification + " K:"+this.k;
    }
}
