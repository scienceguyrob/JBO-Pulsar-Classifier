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
 * File name: 	DataSetTransformer.java
 * Package: uk.ac.man.jb.pct.data
 * Created:	Jun 13, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

import uk.ac.man.jb.pct.io.Writer;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.util.StringOps;

/**
 * DataSetTransformer. This class provides methods that
 * can transform classes that implement the I_DataSet interface
 * in to other formats.
 * 
 * @author Rob Lyon
 */
public class DataSetTransformer implements I_DataSetTransformer
{

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSetTransformer#toCSVString(uk.ac.man.jb.pct.data.I_DataSet)
     */
    public String toCSVString(I_DataSet ds)
    {
	String csv = "";

	for(int r = 0; r < ds.getRows();r++)
	{
	    // Get the raw numerical data

	    double[] data = ds.getDataRow(r).getData();

	    // Builds string such that: <pattern name>,<pattern class (if known)>
	    csv = csv + ds.getDataRow(r).getName() + "," +  ds.getDataRow(r).getClassMembership();

	    for(int c = 0; c < data.length; c++)
	    {
		csv = csv + "," + data[c];
	    }

	    csv = csv + ",\n";
	}

	return csv;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSetTransformer#toARFF(uk.ac.man.jb.pct.data.I_DataSet)
     */
    public String toARFF(I_DataSet ds) 
    {
	/**
	 * Weka ARFF format example
	 * 
	 * % 1. Title: Iris Plants Database
	 * %
	 * % 2. Sources:
	 * %      (a) Creator: R.A. Fisher
	 * %      (b) Donor: Michael Marshall
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

	String header = "% Title : Pulsar Candidates\r% Created using the Decision Tree Classifier\r% Rob Lyon (robert.lyon@cs.man.ac.uk)\r\r";
	String relation = "@RELATION pulsar\r\r";

	String[][] attributes = {
		{"@ATTRIBUTE ","Score1\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score2\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score3\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score4\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score5\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score6\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score7\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score8\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score9\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score10\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score11\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score12\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score13\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score14\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score15\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score16\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score17\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score18\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score19\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score20\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score21\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score22\t","NUMERIC\r"},
		{"@ATTRIBUTE ","class\t","\t{" + Constants.PULSAR + "," + Constants.NOT_PULSAR + "," + Constants.RFI + "," + Constants.UNKNOWN + "}\r\r"}
	};

	String attribute = "";
	for(int i = 0; i < attributes.length;i++)
	    for(int k = 0; k < attributes[i].length;k++)
		attribute = attribute + attributes[i][k];

	String data = "@DATA\r";
	for(int r = 0; r < ds.getRows();r++)
	{
	    if (r % 100 == 0) {System.out.println("Row:"+r); }
	    I_InputPattern p = ds.getDataRow(r);
	    String instanceClass = p.getClassMembership();
	    double[] d = p.getData();

	    String rawData = d[0]+",";
	    for(int k = 1; k < d.length;k++)
		rawData = rawData + d[k] + ",";

	    rawData = rawData + instanceClass + "\r";

	    data = data + rawData;
	}

	String contents = header + relation + attribute + data;

	return contents;
    }


    public String toSNSS(I_DataSet ds) 
    {
	// The output file is of the form:
	//
	// 1. # input pattern 2009-01-13-07:54:15_08.fil_147.phcx.gz
	// 2. 78.9045991099 2.8927618807 8.0 4357.0 33.8000282795 0.729217101493
	// 3. 0 1
	//
	// Line 1. is the name of the file, which has format
	// XXXX-XX-XX-XX:XX:XX_YY.fil_ZZZ.phcx.gz where
	// X is the UTC of the observation.
	// Y is the beam number - we do 13 simultaneous beams per UTC (01-13)
	// ZZZ is the candidate number from that beam.
	//
	// Line 2. has the 22 scores which are generated as diagnostics of the candidate.
	//
	// The third line is the YES NO scores which go into the net, so "1 0" for a pulsar
	// and "0 1" for something we want to reject.

	String data = "";

	for(int r = 0; r < ds.getRows();r++)
	{
	    if (r % 100 == 0) { System.out.println("Row:"+r); }

	    I_InputPattern p = ds.getDataRow(r);
	    String rowData = "";

	    String className = p.getName();

	    if(!StringOps.isAStringsEmpty(className))
		rowData = "# input pattern " + className + "\r";
	    else
		rowData = "# input pattern UNKOWN\r";

	    double[] d = p.getData();

	    String rawData = Double.toString(d[0]);

	    for(int k = 1; k < d.length;k++)
		rawData = rawData + " " + d[k];

	    rowData = rowData + rawData + "\r";

	    if(p.getClassMembership().equals(Constants.PULSAR))
		rowData = rowData + "1 0\r";
	    else if(p.getClassMembership().equals(Constants.RFI))
		rowData = rowData + "0 1\r";
	    else
		rowData = rowData + "0 2\r";// Unknown

	    data = data + rowData;
	}

	return data;
    }


    public boolean toARFF(I_DataSet ds, String path) 
    {
	/**
	 * Weka ARFF format example
	 * 
	 * % 1. Title: Iris Plants Database
	 * %
	 * % 2. Sources:
	 * %      (a) Creator: R.A. Fisher
	 * %      (b) Donor: Michael Marshall
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

	String header = "% Title : Pulsar Candidates\r% Created using the Decision Tree Classifier\r% Rob Lyon (robert.lyon@cs.man.ac.uk)\r\r";
	String relation = "@RELATION pulsar\r\r";

	String[][] attributes = {
		{"@ATTRIBUTE ","Score1\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score2\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score3\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score4\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score5\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score6\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score7\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score8\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score9\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score10\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score11\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score12\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score13\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score14\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score15\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score16\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score17\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score18\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score19\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score20\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score21\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score22\t","NUMERIC\r"},
		{"@ATTRIBUTE ","class\t","\t{" + Constants.PULSAR + "," + Constants.NOT_PULSAR + "," + Constants.RFI + "," + Constants.UNKNOWN + "}\r\r"}
	};

	String attribute = "";
	for(int i = 0; i < attributes.length;i++)
	    for(int k = 0; k < attributes[i].length;k++)
		attribute = attribute + attributes[i][k];

	String data = "@DATA\r";

	Writer.write(path, header + relation + attribute + data);

	header = null;
	relation = null;
	attribute = null;
	data = null;
	System.gc();

	String rawData = "";
	String instanceClass = "";
	I_InputPattern p;

	for(int r = 0; r < ds.getRows();r++)
	{
	    if (r % 100 == 0) {System.out.println("Row:"+r); }

	    p = null;
	    p = ds.getDataRow(r);

	    instanceClass = "";
	    instanceClass = p.getClassMembership();
	    double[] d = p.getData();

	    rawData = "";
	    rawData = d[0]+",";
	    for(int k = 1; k < d.length;k++)
		rawData = rawData + d[k] + ",";

	    rawData = rawData + instanceClass + "\r";

	    Writer.write(path , rawData);
	}

	return true;
    }


    public boolean toSNSS(I_DataSet ds, String path) 
    {
	// The output file is of the form:
	//
	// 1. # input pattern 2009-01-13-07:54:15_08.fil_147.phcx.gz
	// 2. 78.9045991099 2.8927618807 8.0 4357.0 33.8000282795 0.729217101493
	// 3. 0 1
	//
	// Line 1. is the name of the file, which has format
	// XXXX-XX-XX-XX:XX:XX_YY.fil_ZZZ.phcx.gz where
	// X is the UTC of the observation.
	// Y is the beam number - we do 13 simultaneous beams per UTC (01-13)
	// ZZZ is the candidate number from that beam.
	//
	// Line 2. has the 22 scores which are generated as diagnostics of the candidate.
	//
	// The third line is the YES NO scores which go into the net, so "1 0" for a pulsar
	// and "0 1" for something we want to reject.

	String className = "";
	String rowData = "";
	String rawData = "";
	I_InputPattern p;

	for(int r = 0; r < ds.getRows();r++)
	{
	    if (r % 100 == 0) { System.out.println("Row:"+r); }

	    p = ds.getDataRow(r);
	    rowData = "";
	    className = "";
	    className = p.getName();

	    if(!StringOps.isAStringsEmpty(className))
		rowData = "# input pattern " + className + "\r";
	    else
		rowData = "# input pattern UNKOWN\r";

	    double[] d = p.getData();

	    rawData = "";
	    rawData = Double.toString(d[0]);

	    for(int k = 1; k < d.length;k++)
		rawData = rawData + " " + d[k];

	    rowData = rowData + rawData + "\r";

	    if(p.getClassMembership().equals(Constants.PULSAR))
		rowData = rowData + "1 0\r";
	    else
		rowData = rowData + "0 1\r";

	    Writer.write(path, rowData);
	}

	return true;
    }


    @SuppressWarnings("unused")
    public I_DataSet prunePositives(I_DataSet ds) 
    {
	if(ds != null)
	{
	    I_DataSet prunedDataset = new DataSet();

	    for(int r = 0; r < ds.getRows(); r++)
	    {
		I_InputPattern p = ds.getDataRow(r);

		String classification = p.getClassMembership();
		if(!classification.equals(Constants.PULSAR))
		    prunedDataset.addRow(p);
	    }

	    if(prunedDataset != null)
		return prunedDataset;
	    else
		return null;
	}
	else
	    return null;
    }


    @SuppressWarnings("unused")
    public I_DataSet pruneNegatives(I_DataSet ds) 
    {
	if(ds != null)
	{
	    I_DataSet prunedDataset = new DataSet();

	    for(int r = 0; r < ds.getRows(); r++)
	    {
		I_InputPattern p = ds.getDataRow(r);

		String classification = p.getClassMembership();
		if(classification.equals(Constants.PULSAR))
		    prunedDataset.addRow(p);
	    }

	    if(prunedDataset != null)
		return prunedDataset;
	    else
		return null;
	}
	else
	    return null;
    }
}
