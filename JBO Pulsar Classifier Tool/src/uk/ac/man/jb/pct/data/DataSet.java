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
 * File name: 	DataSet.java
 * Package: uk.ac.man.jb.pct.data
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

import java.util.UUID;
import java.util.Vector;

/**
 * DataSet
 * @author Rob Lyon
 */
public class DataSet implements I_DataSet
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The unique identifier for the data.
     */
    private String ID;

    /**
     * The number of rows in the data set.
     */
    private int rows;

    /**
     * The number of columns in the data set.
     */
    private int columns;  

    /**
     * The data that makes up the data set.
     */
    private Vector<I_InputPattern> data;

    //*****************************************
    //*****************************************
    //             Constructor
    //*****************************************
    //*****************************************

    /**
     * Default constructor.
     */
    public DataSet()
    {
	this.ID = UUID.randomUUID().toString();
	this.rows = 0;
	this.columns = 0;
	this.data = new Vector<I_InputPattern>();
    }

    /**
     * Builds a new data set with the specified name.
     * @param id the unique identifier of the data set to be created.
     */
    public DataSet(String id, int cols)
    {
	this.ID = id;
	this.rows = 0;
	this.columns = cols;
	this.data = new Vector<I_InputPattern>();
    }

    //*****************************************
    //*****************************************
    //               Getters
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSet#getDataRow(int)
     */
    public I_InputPattern getDataRow(int r)
    { 
	if(data.size() > 0) 
	    return data.elementAt(r); 
	else
	    return null;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSet#getID()
     */
    public String getID(){ return ID; }


    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSet#getRows()
     */
    public int getRows(){return rows; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSet#getColumns()
     */
    public int getColumns()
    { 
	if(data.elementAt(0) != null) 
	    return columns; 
	else return 0;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSet#addRow(uk.ac.man.jb.pct.data.I_InputPattern)
     */
    public void addRow(I_InputPattern p)
    {
	data.add(p);
	rows = rows++;
    }

    //*****************************************
    //*****************************************
    //               Setters
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSet#setColumns(int)
     */
    public void setColumns(int c){ this.columns = c;}

    //*****************************************
    //*****************************************
    //               Validate
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSet#validate()
     */
    public boolean validated()
    {
	// if there is not data then this object is invalid.
	if(data.elementAt(0) != null) 
	{
	    columns = data.elementAt(0).getDataLength();
	    
	    // Check that each data row agrees on the number 
	    // of columns in the data.
	    for(int i = 0; i < data.size(); i++)
	    {
		// A row does not agree, meaning input data
		// is inconsistent.
		if(data.elementAt(i).getDataLength() != columns)
		    return false;
	    }
	    
	    // If we arrive here then all is well
	    return true;
	}
	else 
	    return false;
    }
}
