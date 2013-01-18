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
 * I_DataSetTransformer defines methods that can be used
 * to convert {@link I_DataSet} objects in to other forms.
 * 
 * @author Rob Lyon
 */
public interface I_DataSetTransformer
{
    /**
     * Converts a class that implements the {@link I_DataSet} interface,
     * in to a string in CSV format, ready to be written to a file. The CSV
     * format is as follows:
     * 
     * <Input Pattern ID>,<Class>,<Data item i>,...<Data item n>,
     * 
     * @param ds the {@link I_DataSet} instance to convert.
     * @return a string representation of the the {@link I_DataSet} object.
     * @see I_DataSet
     */
    public String toCSVString(I_DataSet ds);
    
    /**
     * Converts a class that implements the {@link I_DataSet} interface,
     * in to a string in Weka ARFF format, ready to be written to a file. The ARFF
     * format is as follows:
     * 
     * % 1. Title: Iris Plants Database
     * %
     * % 2. Sources:
     * %      (a) Creator: R.A. Fisher
     * %      (b) Donor: Michael Marshall (MARSHALL%PLU@io.arc.nasa.gov)
     * %      (c) Date: July, 1988
     * %
     * @RELATION iris
     * 
     * @ATTRIBUTE sepallength  NUMERIC
     * @ATTRIBUTE sepalwidth   NUMERIC
     * @ATTRIBUTE petallength  NUMERIC
     * @ATTRIBUTE petalwidth   NUMERIC
     * @ATTRIBUTE class        {Iris-setosa,Iris-versicolor,Iris-virginica}
     * 
     * @DATA
     * 5.1,3.5,1.4,0.2,Iris-setosa
     * 4.9,3.0,1.4,0.2,Iris-setosa
     * 4.7,3.2,1.3,0.2,Iris-setosa
     * 
     * @param ds the {@link I_DataSet} instance to convert.
     * @return a string representation of the the {@link I_DataSet} object.
     * @see I_DataSet
     */
    public String toARFF(I_DataSet ds);
    
    /**
     * Converts a class that implements the {@link I_DataSet} interface,
     * in to a string in Weka ARFF format, ready to be written to a file. The ARFF
     * format is as follows:
     * 
     * % 1. Title: Iris Plants Database
     * %
     * % 2. Sources:
     * %      (a) Creator: R.A. Fisher
     * %      (b) Donor: Michael Marshall (MARSHALL%PLU@io.arc.nasa.gov)
     * %      (c) Date: July, 1988
     * %
     * @RELATION iris
     * 
     * @ATTRIBUTE sepallength  NUMERIC
     * @ATTRIBUTE sepalwidth   NUMERIC
     * @ATTRIBUTE petallength  NUMERIC
     * @ATTRIBUTE petalwidth   NUMERIC
     * @ATTRIBUTE class        {Iris-setosa,Iris-versicolor,Iris-virginica}
     * 
     * @DATA
     * 5.1,3.5,1.4,0.2,Iris-setosa
     * 4.9,3.0,1.4,0.2,Iris-setosa
     * 4.7,3.2,1.3,0.2,Iris-setosa
     * 
     * @param ds the {@link I_DataSet} instance to convert.
     * @param path the file to write the transformation to.
     * @return a string representation of the the {@link I_DataSet} object.
     * @see I_DataSet
     */
    public boolean toARFF(I_DataSet ds,String path);
    
    /**
     * Converts a class that implements the {@link I_DataSet} interface,
     * in to a string in SNSS format, ready to be written to a file. The SNSS
     * format is as follows:
     * 
     * 1. # input pattern 2009-01-13-07:54:15_08.fil_147.phcx.gz
     * 2. 78.9045991099 2.8927618807 8.0 4357.0 33.8000282795 0.729217101493
     * 3. 0 1
     * 
     * Line 1. is the name of the file, which has format
     * XXXX-XX-XX-XX:XX:XX_YY.fil_ZZZ.phcx.gz where
     * X is the UTC of the observation.
     * Y is the beam number - we do 13 simultaneous beams per UTC (01-13)
     * ZZZ is the candidate number from that beam.
     * 
     * Line 2. has the 22 scores which are generated as diagnostics of the candidate.
     * 
     * The third line is the YES NO scores which go into the net, so "1 0" for a pulsar
     * and "0 1" for something we want to reject.
     * 
     * @param ds the {@link I_DataSet} instance to convert.
     * @return a string representation of the the {@link I_DataSet} object.
     * @see I_DataSet
     */
    public String toSNSS(I_DataSet ds);
    
    /**
     * Converts a class that implements the {@link I_DataSet} interface,
     * in to a string in SNSS format, ready to be written to a file. The SNSS
     * format is as follows:
     * 
     * 1. # input pattern 2009-01-13-07:54:15_08.fil_147.phcx.gz
     * 2. 78.9045991099 2.8927618807 8.0 4357.0 33.8000282795 0.729217101493
     * 3. 0 1
     * 
     * Line 1. is the name of the file, which has format
     * XXXX-XX-XX-XX:XX:XX_YY.fil_ZZZ.phcx.gz where
     * X is the UTC of the observation.
     * Y is the beam number - we do 13 simultaneous beams per UTC (01-13)
     * ZZZ is the candidate number from that beam.
     * 
     * Line 2. has the 22 scores which are generated as diagnostics of the candidate.
     * 
     * The third line is the YES NO scores which go into the net, so "1 0" for a pulsar
     * and "0 1" for something we want to reject.
     * 
     * @param ds the {@link I_DataSet} instance to convert.
     * @param the path to write the transformation to.
     * @return a string representation of the the {@link I_DataSet} object.
     * @see I_DataSet
     */
    public boolean toSNSS(I_DataSet ds, String path);
    
    /**
     * Removes positive examples from the supplied data set.
     * @param ds the {@link I_DataSet} instance to convert.
     * @return a representation of the the {@link I_DataSet} object without positives.
     * @see I_DataSet
     */
    public I_DataSet prunePositives(I_DataSet ds);
    
    /**
     * Removes negative examples from the supplied data set.
     * @param ds the {@link I_DataSet} instance to convert.
     * @return a representation of the the {@link I_DataSet} object without positives.
     * @see I_DataSet
     */
    public I_DataSet pruneNegatives(I_DataSet ds);
}
