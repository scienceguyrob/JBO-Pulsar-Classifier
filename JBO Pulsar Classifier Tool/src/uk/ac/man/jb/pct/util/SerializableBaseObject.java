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
 * File name: 	SerializableBaseObject.java
 * Package: uk.ac.man.jb.pct.mvc.controllers
 * Created:	1st March 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 *
 * This class is extended by other classes that require the capability to serialize their states to XML.
 * 
 * @author Rob Lyon
 *
 */
public class SerializableBaseObject extends Thread
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The unique identifier of this class.
     */
    public String ID = "";

    //*****************************************
    //*****************************************
    //             Constructors
    //*****************************************
    //*****************************************

    /**
     * Default constructor, generates a new unique ID for this class.
     */
    public SerializableBaseObject(){ ID = UUID.randomUUID().toString(); }

    //*****************************************
    //*****************************************
    //          Getters & Setters
    //*****************************************
    //*****************************************

    /**
     * @return the iD
     */
    public String getID(){ return ID; }

    /**
     * @param iD the iD to set
     */
    public void setID(String iD){ ID = iD; } 

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /**
     * Writes an object to an XML file.
     * @param obj the object to write to XML.
     * @param path the path to the file the object should be written to.
     * @throws Exception
     * @return true if the object is written to XML correctly, else false.
     */
    public static boolean write(Object obj, String path)
    {
	try
	{
	    XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream( new FileOutputStream(path)));
	    encoder.writeObject(obj);
	    encoder.close();
	    return true;
	}
	catch(Exception e){System.out.println(e.toString());return false;}
    }

    /**
     * Reads the state of an object in from XML.
     * @param path the path to the XML containing the state of the object.
     * @return the Object if successful, else null.
     */
    public static Object read(String path) 
    {
	try
	{
	    XMLDecoder decoder = new XMLDecoder(new BufferedInputStream( new FileInputStream(path)));
	    Object o = (Object)decoder.readObject();
	    decoder.close();
	    return o;
	}
	catch(Exception e){System.out.println(e.toString());return null;}
    }
}
