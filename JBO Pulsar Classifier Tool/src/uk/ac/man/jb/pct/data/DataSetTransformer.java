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

/**
 * DataSetTransformer
 * @author Rob Lyon
 */
public class DataSetTransformer implements I_DataSetTransformer
{

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_DataSetTransformer#toCSVString(uk.ac.man.jb.pct.data.I_DataSet)
     */
    @Override
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
    
    /**
	// ***********************************
	// Addition for weka format extraction
	String path = m.getTrainingSet().replace(".pat", ".arff");

	Common.fileCreate(path);
	if(!Common.fileExist(path))
	{
	    this.showErrorMessage("ARFF Fuckup");
	    break;
	}

	for( int i = 0; i < tData.size();i++)
	{
	    double[] data = tData.get(i).getData();
	    boolean isPulsar = tData.get(i).getIsPulsar();

	    String concat = ""+Double.toString(data[0]);

	    for(int k = 1; k < data.length; k++)
	    {
		concat = concat + "," + Double.toString(data[k]);
	    }

	    String value = "Pulsar";

	    if(!isPulsar)
		value = "RFI";

	    concat = concat + "," + value + "\n";

	    Writer.write(path, concat);
	}

	// ***********************************
	 */
}
