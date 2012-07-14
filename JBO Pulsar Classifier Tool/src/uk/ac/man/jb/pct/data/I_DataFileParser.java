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
 * File name: 	I_DataFileParser.java
 * Package: uk.ac.man.jb.pst.preprocessor
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

/**
 * I_DataFileParser, defines an interface specifying the methods
 * needed to read the data files used by the application.
 * 
 * @author Rob Lyon
 */
public interface I_DataFileParser
{
    /**
     * Process a file that contains links to other files,
     * that contain the data to be loaded.
     * @param path the path to the file to process.
     * @return the input data as a DataSet object that
     *         implements the {@link I_DataSet} interface, otherwise
     *         null if there is an error or inconsistent data.
     * @see I_DataSet
     */
    public I_DataSet processLinkFile(String path);
    
    /**
     * Process a comma separated value (CSV) file for input data.
     * @param path the path to the file to process.
     * @return the input data as a DataSet object that
     *         implements the {@link I_DataSet} interface, otherwise
     *         null if there is an error or inconsistent data.
     * @see I_DataSet
     */
    public I_DataSet processCSV(String path);
    
    /**
     * Process a tab delimited file for input data.
     * @param path the path to the file to process.
     * @return the input data as a DataSet object that
     *         implements the {@link I_DataSet} interface, otherwise
     *         null if there is an error or inconsistent data.
     * @see I_DataSet
     */
    public I_DataSet processTAB(String path);
    
    /**
     * Process a SNNS pattern file for input data.
     * @param path the path to the file to process.
     * @return the input data as a DataSet object that
     *         implements the {@link I_DataSet} interface, otherwise
     *         null if there is an error or inconsistent data.
     * @see I_DataSet
     */
    public I_DataSet processSNNS(String path);
    
    /**
     * Process an XML file for input data.
     * @param path the path to the file to process.
     * @return the input data as a DataSet object that
     *         implements the {@link I_DataSet} interface, otherwise
     *         null if there is an error or inconsistent data.
     * @see I_DataSet
     */
    public I_DataSet processXML(String path);
    
    /**
     * Process an file for input data.
     * @param path the path to the file to process.
     * @return the input data as a DataSet object that
     *         implements the {@link I_DataSet} interface, otherwise
     *         null if there is an error or inconsistent data.
     * @see I_DataSet
     */
    public I_DataSet process(String path);
    
    /**
     * Process a file containing raw data in tab delimited form.
     * @param path the path to the file to read.
     * @return the raw data as an I_InputPattern instance,
     *         else null if the data is invalid.
     */
    public I_InputPattern processPatternFile(String path);
}
