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
 * File name: 	OutputResults.java
 * Package: uk.ac.man.jb.pct.data
 * Created:	Jul 14, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

import java.awt.Point;
import uk.ac.man.jb.pct.io.Reader;
import uk.ac.man.jb.pct.io.Writer;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.util.StringOps;

/**
 * OutputResults, a class used to write classifications to an output file.
 * 
 * @author Rob Lyon
 */
public class OutputResults implements I_OutputResults
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The number of positive instances classified.
     */
    private int positives = 0;

    /**
     * The number of negative instance classified.
     */
    private int negatives = 0;

    /**
     * The total number of instances classified.
     */
    private int total = 0;
    
    /**
     * The output path.
     */
    private String path = "";

    //*****************************************
    //*****************************************
    //               Getters
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_OutputResults#getPositiveClassifications()
     */
    @Override
    public int getPositiveClassifications() { return this.positives; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_OutputResults#getNegativeClassifications()
     */
    @Override
    public int getNegativeClassifications() { return this.negatives; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_OutputResults#getTotalClassifications()
     */
    @Override
    public int getTotalClassifications() { return this.total; }

    //*****************************************
    //*****************************************
    //               Setters
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_OutputResults#setOutputPath(java.lang.String)
     */
    public void setOutputPath(String p) { this.path = p; }

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_OutputResults#writeSummaryStatisitics(uk.ac.man.jb.pct.data.I_ClassifierStatistics)
     */
    @Override
    public void writeSummaryStatisitics(I_ClassifierStatistics s,String classifierPath)
    {
	String preliminaryData = Reader.getContents(this.path);
	
	String finalData = "# Network Used: " + classifierPath + "\n" +
		           "# Accuracy: " + s.getAccuracy() + "\n" +
		           "# MCC:"+ s.getMatthewsCorrelation() + "\n" +
		           "# Total Patterns:"+ this.total + "\n" +
		           "# Positive Patterns:"+ this.positives + "\n" +
		           "# Negative Patterns:"+ this.negatives + "\n" + 
		           "# 1 indicates pulsar, 0 RFI.\n" + 
		           "# File Structure:\n"+
		           "# <File Name>,<X co-ord>,<Y co-ord>,<binary classification>,<text classification>,\n"+
		           preliminaryData;
	
	Writer.clear(this.path);
	Writer.write(path, finalData);
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_OutputResults#writeSingleResult(uk.ac.man.jb.pct.data.I_InputPattern, java.lang.String)
     */
    @Override
    public void writeSingleResult(I_InputPattern p, String classification, Point coord)
    {
	if(classification.equals(Constants.PULSAR))
	{
	    String text = StringOps.getFileNameFromPath(p.getName()) + ","+coord.x+","+coord.y+",1,Pulsar,\n";
	    Writer.write(path,text);
	}
	else
	{
	    String text = StringOps.getFileNameFromPath(p.getName()) + ","+coord.x+","+coord.y+",0,RFI,\n";
	    Writer.write(path,text);
	}
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_OutputResults#process(uk.ac.man.jb.pct.data.I_InputPattern, java.lang.String)
     */
    @Override
    public void process(I_InputPattern p, String classification, Point coord)
    {
	if(classification.equals(Constants.PULSAR))
	{
	    this.incrementPositiveClassifications();
	    writeSingleResult(p,classification,coord);
	}
	else
	{
	    this.incrementNegativeClassifications();
	    writeSingleResult(p,classification,coord);
	}
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_OutputResults#incrementPositiveClassifications()
     */
    @Override
    public void incrementPositiveClassifications() { this.positives++; this.total++;}

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_OutputResults#incrementNegativeClassifications()
     */
    @Override
    public void incrementNegativeClassifications() { this.negatives++; this.total++;}
}