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
 * File name: 	TestHarness.java
 * Package: uk.ac.man.jb.pct.test
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.test;

import org.junit.Test;
import uk.ac.man.jb.pct.util.StringOps;

/**
 * TestHarness
 * @author Rob Lyon
 */
public class TestHarness
{

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    @Test
    public void testSeperateString()
    {
	String[] output = {"r","o","b","e","r","t"};
	org.junit.Assert.assertArrayEquals(output,StringOps.seperateString("r,o,b,e,r,t", ","));
    }
}
