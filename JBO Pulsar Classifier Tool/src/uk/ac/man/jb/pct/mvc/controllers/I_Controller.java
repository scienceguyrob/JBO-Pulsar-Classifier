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
 * File name: 	I_Controller.java
 * Package: uk.ac.man.jb.pct.mvc
 * Created:	Jun 15, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.mvc.controllers;

import uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData;

/**
 * I_Controller
 * @author Rob Lyon
 */
public interface I_Controller
{
    /**
     * Runs the controlling task.
     */
    public void run();
    
    /**
     * @return true if the parameters passed to the controller are valid, else false.
     */
    public boolean validateParameters();
    
    /**
     * Sets the parameters used by the controller.
     * @param params the parameters.
     */
    public void setParameters(I_CommandLineInputData params);
}
