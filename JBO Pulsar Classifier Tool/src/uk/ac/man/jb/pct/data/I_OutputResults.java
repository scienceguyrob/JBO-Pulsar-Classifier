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
 * File name: 	I_OutputResults.java
 * Package: uk.ac.man.jb.pct.data
 * Created:	Jul 14, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

/**
 * I_OutputResults, specifies the methods that can be used
 * to write classification results to output files.
 * 
 * @author Rob Lyon
 */
public interface I_OutputResults
{   
    /**
     * Writes summary statisitis to the output file. This is both the statistics
     * of the classifier used, and of the classification run.
     * @param s the statistics of the classifier used.
     * @param classifierPath the path to the classifier used.
     */
    public void writeSummaryStatisitics(I_ClassifierStatistics s,String classifierPath);
    
    /**
     * Writes a single classification to the output file.
     * @param p the pattern classified
     * @param classification the classification assigned
     */
    public void writeSingleResult(I_InputPattern p, String classification);
    
    /**
     * Processes an input pattern and its classification.
     * @param p the input pattern
     * @param classification the classification assigned to the pattern.
     */
    public void process(I_InputPattern p, String classification);
    
    /**
     * @return the total number of input patterns classified as positive (i.e. pulsar instances).
     */
    public int getPositiveClassifications();
    
    /**
     * @return the total number of input patterns classified as negative (i.e. not pulsar instances).
     */
    public int getNegativeClassifications();
    
    /**
     * Increment the number of positive classifications seen.
     */
    public void  incrementPositiveClassifications();
    
    /**
     * Increment the number of negative classifications.
     */
    public void incrementNegativeClassifications();
    
    /**
     * @return the total number of classification performed,
     * i.e. the number of positive classifications, plus the number of
     * negative.
     */
    public int getTotalClassifications();
    
    /**
     * Sets the output path to write classifications to.
     * @param p the path
     */
    public void setOutputPath(String p);
}
