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
 * File name: 	PatternFileProcessor.java
 * Package: uk.ac.man.jb.pst.preprocessor
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.util.Common;
import uk.ac.man.jb.pct.util.StringOps;

/**
 * Class that processes a file containing input patterns, and returns the data
 * as DataSet instances.
 * 
 * @author Rob Lyon
 */
public class PatternFileProcessor implements I_DataFileParser
{
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataFileParser#process(java.lang.String)
     */
    public I_DataSet process(String path)
    {
	if(path.endsWith(".csv"))
	    return this.processCSV(path);
	else if(path.endsWith(".tab"))
	    return this.processTAB(path);
	else if(path.endsWith(".pat"))
	    return this.processSNNS(path);
	else if(path.endsWith(".xml"))
	    return this.processXML(path);
	else
	    return null;	    
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_DataFileParser#processCSV()
     */
    public I_DataSet processCSV(String path)
    {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_DataFileParser#processTAB()
     */
    public I_DataSet processTAB(String path)
    {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_DataFileParser#processSNNS()
     */
    public I_DataSet processSNNS(String path)
    {
	// The input file is of the form:
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
	//
	// Thanks to Dan Thornton for this explanation.

	// Variable to store the information.
	DataSet dataSet = new DataSet(path.replace("\\", "_"),0);

	// Now read each line of the file one by one.

	//Firstly try to create the file
	File file = new File(path);

	//if the file exists
	if(file.exists())
	{
	    // Variables used to store the line of the being read
	    // using the input stream, and an array list to store the input
	    // patterns into.
	    String line = "";

	    // Read the file and display it line by line. 
	    BufferedReader in = null;

	    try
	    {
		//open stream to file
		in = new BufferedReader(new FileReader(file));

		try
		{   
		    while ((line = in.readLine()) != null)
		    {
			if(line.startsWith("#"))
			{		
			    //Declare input pattern to store data
			    InputPattern pattern = new InputPattern();

			    pattern.setName(line.substring(2, line.length()));

			    line = in.readLine();

			    double[] patternData = StringOps.splitStringToDouble(line," ");
			    pattern.setData(patternData);

			    String value = Constants.RFI;

			    // 10 pulsar 01 not pulsar
			    line = in.readLine();
			    if(line.startsWith("1 0"))
				value = Constants.PULSAR;

			    pattern.setClassMembership(value);

			    dataSet.addRow(pattern);
			}
		    }
		}
		catch(IOException e){return null;}
		finally{in.close();}
	    }
	    catch (FileNotFoundException e) { return null; }
	    catch (IOException e) { return null; }
	}
	else{ return null; }

	// causes data set to validate itself.
	if(dataSet.validated())
	    return dataSet;
	else return null;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pst.preprocessor.I_DataFileParser#processXML()
     */
    public I_DataSet processXML(String path)
    {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataFileParser#processPatternFile(java.lang.String)
     */
    public I_InputPattern processPatternFile(String path)
    {
	InputPattern pattern = null;
	
	//Firstly try to create the file
	File file = new File(path);

	//if the file exists
	if(file.exists())
	{
	    // Variables used to store the line of the being read
	    // using the input stream, and an array list to store the input
	    // patterns into.
	    String line = "";

	    // Read the file and display it line by line. 
	    BufferedReader in = null;

	    try
	    {
		//open stream to file
		in = new BufferedReader(new FileReader(file));

		try
		{   
		    while ((line = in.readLine()) != null)
		    {

			//Declare input pattern to store data
			pattern = new InputPattern();

			pattern.setName(path);

			double[] patternData = StringOps.splitStringToDouble(line," ");
			pattern.setData(patternData);
		    }
		}
		catch(IOException e){return null;}
		finally{in.close();}
	    }
	    catch (FileNotFoundException e) { return null; }
	    catch (IOException e) { return null; }
	}
	else{ return null; }
	
	return pattern;
    }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataFileParser#processLinkFile(java.lang.String)
     */
    public I_DataSet processLinkFile(String path)
    {
	// The input file is of the general form:
	//
	// < File path 1> 
	// ...
	// < File path n>
	//
	// The file paths lead to other files that contain the raw,
	// space separated data.

	DataSet dataSet = new DataSet(path.replace("\\", "_"),0);

	// Now read each line of the file one by one.

	//Firstly try to create the file
	File file = new File(path);

	//if the file exists
	if(file.exists())
	{
	    // Variables used to store the line of the being read
	    // using the input stream, and an array list to store the input
	    // patterns into.
	    String line = "";

	    // Read the file and display it line by line. 
	    BufferedReader in = null;

	    try
	    {
		//open stream to file
		in = new BufferedReader(new FileReader(file));

		try
		{   
		    while ((line = in.readLine()) != null)
		    {
			if(Common.fileExist(line))
			    dataSet.addRow(this.processPatternFile(line));
		    }
		}
		catch(IOException e){return null;}
		finally{in.close();}
	    }
	    catch (FileNotFoundException e) { return null; }
	    catch (IOException e) { return null; }
	}
	else{ return null; }

	// causes data set to validate itself.
	if(dataSet.validated())
	    return dataSet;
	else return null;
    }
}
