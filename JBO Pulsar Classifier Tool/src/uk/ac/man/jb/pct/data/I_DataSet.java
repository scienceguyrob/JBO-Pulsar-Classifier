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
 * File name: 	I_DataSet.java
 * Package: uk.ac.man.jb.pct.data
 * Created:	Jun 13, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

/**
 * I_DataSet
 * @author Rob Lyon
 */
public interface I_DataSet
{
    /**
     * Gets the specified row of data from the data set.
     * @param r the index of the row to return.
     * @return the specified row of data from the data set.
     */
    public I_InputPattern getDataRow(int r);
    
    /**
     * @return returns the name of the data set.
     */
    public String getID();
    
    /**
     * @return the number of rows in the data set.
     */
    public int getRows();
    
    /**
     * @return the number of columns in the data set.
     */
    public int getColumns();
    
    /**
     * Adds a new row to he data set.
     * @param p the input pattern to add as a new row.
     */
    public void addRow(I_InputPattern p);
    
    /**
     * Sets the number of columns in the data set.
     * @param c the number of columns.
     */
    public void setColumns(int c);
    
    /**
     * Makes this object check itself for consistency, i.e.
     * the number of rows/columns have been counted correctly.
     * 
     * @return true if validated successfully, else false.
     */
    public boolean validated();
}
