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
 * File name: 	I_InputPattern.java
 * Package: uk.ac.man.jb.pst.preprocessor
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

/**
 * I_InputPattern is an interface that defines the operations to be applied to input patterns
 * read in from input files.
 * 
 * @author Rob Lyon
 */
public interface I_InputPattern
{
    /**
     * The different types of data an input pattern may contain.
     * @author Rob Lyon
     */
    public static enum DataType 
    {
	BYTE, SHORT, INT ,LONG, FLOAT, DOUBLE, BOOL, CHAR, STRING
    }
    
    /**
     * @return The name of the class an input pattern belongs to, if known. Else null.
     */
    public String getClassMembership();
    
    /**
     * @return The raw data that forms an input pattern.
     */
    public double[] getData();
    
    /**
     * @return The raw data at the specified indexes .
     */
    public double[] getData(int[] indexes);
    
    /**
     * @return a unique identifier for an input pattern.
     */
    public String getName();
    
    /**
     * @return The length of the data array, or zero if the array is empty.
     */
    public int getDataLength();
    
    /**
     * Sets the data that forms an input pattern.
     * @param d the raw data.
     */
    public void setData(double[] d);
    
    /**
     * Sets the unique name of an input pattern.
     * @param n the unique name.
     */
    public void setName(String n);
    
    /**
     * Sets the name of the class that this input pattern belongs to. For instance
     * if this input pattern represents a binary pulsar, this would be its class.
     * @param c the name of the class.
     */
    public void setClassMembership(String c);
    
    /**
     * Returns a count of the number of objects that forms an input pattern.
     * The number of objects corresponds to the number of attributes that forms
     * this input pattern.
     * @return 0 if there are no attributes, else the number of attributes.
     */
    public int getScoreCount();
    
    /**
     * Tests to see if an input pattern belongs to a class. Returns true if the pattern
     * does belong to a class, else false if the class is not known.
     * @return true if the class is known, else false.
     */
    public boolean isClassMember();
}
