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
 * File name: 	I_DataSetTransformer.java
 * Package: uk.ac.man.jb.pct.data
 * Created:	Jun 13, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

/**
 * I_DataSetTransformer
 * @author Rob Lyon
 */
public interface I_DataSetTransformer
{
    /**
     * Converts a class that implements the {@link I_DataSet} interface
     * to a string in CSV format, ready to be written to a file. The CSV
     * format is as follows:
     * 
     * <Input Pattern ID>,<Class>,<Data item i>,...<Data item n>,
     * 
     * @param ds the {@link I_DataSet} instance to convert.
     * @return a string representation of the the {@link I_DataSet} object.
     * @see I_DataSet
     */
    public String toCSVString(I_DataSet ds);
}
