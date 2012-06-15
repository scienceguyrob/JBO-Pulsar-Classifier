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
 * File name: 	JBOPulsarSearchTool.java
 * Package: uk.ac.man.jb.pct.main
 * Created:	Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.main;

import java.awt.EventQueue;
import javax.swing.UIManager;
import uk.ac.man.jb.pct.io.DebugLogger;
import uk.ac.man.jb.pct.io.Logging;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.mvc.model.CommandLineInputData;
import uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData;

/**
 * JBOPulsarSearchTool
 * @author Rob Lyon
 */
public class JBOPulsarClassificationTool
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * Object used to manage and process command line input
     */
    private static I_CommandLineInputData input = new CommandLineInputData();

    //*****************************************
    //*****************************************
    //               Main
    //*****************************************
    //*****************************************

    /**
     * Launches the application.
     * 
     * @param args the string arguments to the main method.
     */
    public static void main(String[] args)
    {	
	// Process any command line arguments.
	if (args != null)
	{		
	    if (args.length > 0)
	    {
		for(int i =0; i<args.length;i++)
		    input.processArgument(args[i]);
	    }
	}

	// Continue to load application.

	final DebugLogger  log = new DebugLogger(input.isLoggingEnabled());

	// temporary setting whilst testing
	//log.enableLogging();
	log.out("Starting");

	System.out.print(input.toString());
	
	if(input.getActionPossibleFromInputs().equals(Constants.AUTOMATED_TRAINING))
	{
	    System.out.print("AUTOMATED_TRAINING");
	}
	else if(input.getActionPossibleFromInputs().equals(Constants.SEMI_AUTOMATED_TRAINING))
	{
	    System.out.print("SEMI_AUTOMATED_TRAINING");
	}
	else if(input.getActionPossibleFromInputs().equals(Constants.AUTOMATED_CLASSIFICATION))
	{
	    System.out.print("AUTOMATED_CLASSIFICATION");
	}
	else if(input.getActionPossibleFromInputs().equals(Constants.AUTOMATED_TESTING))
	{
	    System.out.print("AUTOMATED_TESTING");
	}
	else // BUILD GUI
	{
	    System.out.print("GUI");
	    
	    EventQueue.invokeLater(new Runnable()
	    {
		public void run()
		{	
		    try 
		    {
			// Set the system look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } 
		    catch (Exception e) {} // Don't worry about the exceptions.

		    try
		    {
			// First clear log.				
			Logging.clearLog();

			// Initialise controller that will build view/model.
			//Controller c = new Controller(log);

			//if(!c.initialise(messages)) // if we can't initialise the controller.
			//Logging.log("Could not initialise Controller exiting");
			
			System.out.println("GUI not built yet.");
		    }
		    catch (Exception e)
		    { 
			e.printStackTrace();
			Logging.errorLog("Error intialising Controller", e); 
		    }
		}
	    });
	}
    }
}
