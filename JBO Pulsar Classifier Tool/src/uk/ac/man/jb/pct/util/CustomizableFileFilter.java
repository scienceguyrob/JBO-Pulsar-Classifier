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
 * File name: 	CustomizableFileFilter.java
 * Package: uk.ac.man.jb.pct.util
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.util;

import java.io.File;

/**
 * This class is used to filter the files accepted in a file chooser dialog.
 * 
 * @author Rob Lyon
 */
public class CustomizableFileFilter extends javax.swing.filechooser.FileFilter
{
    //*****************************************
    //*****************************************
    //               Variables
    //*****************************************
    //*****************************************

    /**
     * The acceptable file extension.
     */
    private String extension;

    //*****************************************
    //*****************************************
    //               Variables
    //*****************************************
    //*****************************************

    /**
     * Primary constructor.
     * @param e the only file extension accepted by this filter.
     */
    public CustomizableFileFilter(String e) { extension = e; }

    
    /* (non-Javadoc)
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    public boolean accept(File file) 
    {
	String filename = file.getName();
	
	if(file.isDirectory())
	    return true;
	
	return filename.endsWith(extension);
    }

    /* (non-Javadoc)
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    public String getDescription() { return extension; }
}