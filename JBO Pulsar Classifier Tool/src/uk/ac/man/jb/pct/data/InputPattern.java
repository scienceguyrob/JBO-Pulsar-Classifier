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
 * File name: 	InputPattern.java
 * Package: uk.ac.man.jb.pst.preprocessor
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

import java.util.Arrays;
import java.util.UUID;
import uk.ac.man.jb.pct.util.Common;

/**
 * Class representing an individual input pattern.
 * 
 * @author Rob Lyon
 *
 */
public class InputPattern implements I_InputPattern
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The class this pattern belongs to if known.
     */
    String classLabel;

    /**
     * The name of the input data (i.e. the beam details).
     */
    String name;

    /**
     * The pattern attributes.
     */
    double[] data;

    //*****************************************
    //*****************************************
    //             Constructor
    //*****************************************
    //*****************************************

    /**
     * Default constructor.
     */
    public InputPattern(){ name = UUID.randomUUID().toString(); }

    /**
     * Primary constructor.
     * @param n the name of the input pattern (beam file name).
     * @param d the data.
     * @param c the class this instance belongs to.
     */
    public InputPattern(String n, double[] d, String c)
    {
	name = n;
	data = d;
	classLabel = c;

	if(Common.isEmptyString(name))
	    name = UUID.randomUUID().toString();
    }

    /**
     * Copy constructor.
     * @param cSource the pattern to copy.
     */
    public InputPattern(InputPattern cSource)
    {
	name = cSource.name;
	data = cSource.data;
	classLabel = cSource.classLabel;
    }

    //*****************************************
    //*****************************************
    //              Methods
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_InputPattern#getClassMembership()
     */
    @Override
    public String getClassMembership(){ return this.classLabel; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_InputPattern#getData()
     */
    @Override
    public double[] getData(){ return this.data; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_InputPattern#getData(int[])
     */
    @Override
    public double[] getData(int[] indexes)
    {	
	if(indexes.length > 0 && indexes.length <= data.length)
	{
	    if(indexes.length > 1)
		Arrays.sort(indexes);

	    // If the first element in the indexes array
	    // is larger than the data array length,
	    // or if the first index is less than zero,
	    // then return null
	    if(indexes[0] > data.length | indexes[0] < 0)
		return null;

	    // If the last element in the indexes array
	    // is larger than the data array length then return null.
	    if(indexes[indexes.length-1] > data.length)
		return null;
	    
	    double[] filteredData = new double[indexes.length];
	    
	    for(int i = 0; i < indexes.length; i++)
		filteredData[i] = data[indexes[i]];
	    
	    return filteredData;
	}
	else
	    return null;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_InputPattern#getDataLength()
     */
    public int getDataLength()
    {
	if(data != null)
	    return data.length;
	else
	    return 0;
    }
    
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_InputPattern#getName()
     */
    @Override
    public String getName(){ return this.name; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_InputPattern#setData(java.util.Vector)
     */
    @Override
    public void setData(double[] d){ this.data = d; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_InputPattern#setName(java.lang.String)
     */
    @Override
    public void setName(String n){ this.name = n; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_InputPattern#setClassMembership(java.lang.String)
     */
    @Override
    public void setClassMembership(String c){ this.classLabel = c;}

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_InputPattern#getScoreCount()
     */
    @Override
    public int getScoreCount()
    {
	if(data != null)
	    return data.length;
	else return 0;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_InputPattern#isClassMember()
     */
    @Override
    public boolean isClassMember()
    {
	if(Common.isEmptyString(this.classLabel))
	    return false;
	else
	    return true;
    }
}