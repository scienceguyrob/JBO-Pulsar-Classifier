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
 * File name: 	FrequencyPoint.java
 * Package: uk.ac.man.jb.pct.classifiers.som
 * Created:	March 5th, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.classifiers.som;

/**
 * Represents the coordinate of a positive instance in the feature map,
 * with an additional frequency parameter indicating how many times the
 * neuron at each particular location was fired.
 * @author Rob Lyon
 *
 */
public class FrequencyPoint 
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The x-coordinate.
     */
    public int X;

    /**
     * The y-coordinate.
     */
    public int Y;

    /**
     * The frequency with which the neuron at this location is fired.
     */
    public int frequency;

    //*****************************************
    //*****************************************
    //           Getters & Setters
    //*****************************************
    //*****************************************

    public int getX() { return X; }
    public void setX(int x) { X = x; }
    public int getY() { return Y; }
    public void setY(int y) { Y = y; }
    public int getFrequency() { return frequency; }
    public void setFrequency(int frequency) { this.frequency = frequency; }

    //*****************************************
    //*****************************************
    //             Constructors
    //*****************************************
    //*****************************************

    /**
     * Default constructor.
     */
    public FrequencyPoint(){}

    /**
     * Primary constructor.
     * @param the x coordinate.
     * @param the y coordinate.
     * @param f the frequency the neuron at this coordinate is fired.
     */
    public FrequencyPoint(int x, int y, int f)
    { 
	this.X = x;
	this.Y = y;
	this.frequency = f;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
	return "X: "+ Integer.toString(X) + " Y: " + Integer.toString(Y) +" Frequency: " +Integer.toString(frequency);
    }
}
