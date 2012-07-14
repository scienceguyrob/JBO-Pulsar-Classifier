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


import java.awt.EventQueue;
import javax.swing.UIManager;
import uk.ac.man.jb.pct.io.DebugLogger;
import uk.ac.man.jb.pct.io.Logging;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.mvc.controllers.AutoClassificationController;
import uk.ac.man.jb.pct.mvc.controllers.AutoTrainerController;
import uk.ac.man.jb.pct.mvc.controllers.I_Controller;
import uk.ac.man.jb.pct.mvc.model.CommandLineInputData;
import uk.ac.man.jb.pct.mvc.model.I_CommandLineInputData;

/**
 * The JBOPulsarClassificationTool is an application used to classify
 * pulsar data as either RFI or a potential pulsar candidate. This is the
 * main entry point to the application, which can be used as a command
 * line tool, or a GUI tool.
 * 
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
	System.out.println("\n\n***********************************");
	System.out.println("* JBO Pulsar Classifiication Tool *");
	System.out.println("***********************************");
	System.out.println("* Version 1.0                     *");
	System.out.println("* By Rob Lyon                     *");
	System.out.println("* Contact robert.lyon@cs.man.ac.uk*");
	System.out.println("***********************************\n");
	System.out.println("Processing command line arguments.");

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

	System.out.println("\n***********************************");
	System.out.println("* Data Recieved From Command Line *");
	System.out.println("***********************************\n");
	System.out.println(input.toString());
	System.out.println("\n***********************************\n");

	if(input.getActionPossibleFromInputs().equals(Constants.AUTOMATED_TRAINING))
	{
	    System.out.println("AUTOMATED_TRAINING");
	    I_Controller c = new AutoTrainerController(input);
	    c.run();
	}
	else if(input.getActionPossibleFromInputs().equals(Constants.AUTOMATED_CLASSIFICATION))
	{
	    System.out.println("AUTOMATED_CLASSIFICATION");
	    I_Controller c = new AutoClassificationController(input);
	    c.run();
	}
	else if(input.getActionPossibleFromInputs().equals(Constants.AUTOMATED_TESTING))
	{
	    System.out.println("AUTOMATED_TESTING");
	}
	else // BUILD GUI
	{
	    System.out.println("No Arguments supplied.\n");
	    
	    System.out.println("********************************************************************************");
	    System.out.println("Usage: java -jar JBOPulsarClassificationTool <arg 1> ... < arg n>" );
	    System.out.println("Arguments:");
	    System.out.println(" -acc=          The desired accuracy of the network being trained (0 - 100)." );   
	    System.out.println(" -country=      The country choice for GUI setup, i.e. UK." );
	    System.out.println(" -classifier=   The desired classifier to use, 1 is KNN, 0 is naive." );
	    System.out.println(" -lang=         The language choice for GUI setup, i.e. EN." );
	    System.out.println(" -load=         The neural network to load." );
	    System.out.println(" -save=         The path to save a neural network at." );
	    System.out.println(" -t=            Path to the training set." );
	    System.out.println(" -v=            Path to the validation set." );
	    System.out.println(" -k=            Value of K for a KNN classifier (between 2 and 9 arbitrarily)." );
	    System.out.println(" -tests=        The number of tests to run." );
	    System.out.println(" -w=            The width of the self organizing map, defaults to 10." );
	    System.out.println(" -settings=     The path to a settings file that may contain these arguments.");
	    System.out.println("********************************************************************************\n");

	    System.out.println("Attempting to build GUI...");

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

			System.out.println("GUI not built yet!");
		    }
		    catch (Exception e)
		    { 
			e.printStackTrace();
			Logging.errorLog("Error intialising Controller", e); 
		    }
		}
	    });
	}

	System.out.println("\n********************************************************************************");
	System.out.println("Usage: java -jar JBOPulsarClassificationTool <arg 1> ... < arg n>" );
	System.out.println("Arguments:");
	System.out.println(" -acc=          The desired accuracy of the network being trained (0 - 100)." );   
	System.out.println(" -country=      The country choice for GUI setup, i.e. UK." );
	System.out.println(" -classifier=   The desired classifier to use, 1 is KNN, 0 is naive." );
	System.out.println(" -lang=         The language choice for GUI setup, i.e. EN." );
	System.out.println(" -load=         The neural network to load." );
	System.out.println(" -save=         The path to save a neural network at." );
	System.out.println(" -t=            Path to the training set." );
	System.out.println(" -v=            Path to the validation set." );
	System.out.println(" -k=            Value of K for a KNN classifier (between 2 and 9 arbitrarily)." );
	System.out.println(" -tests=        The number of tests to run." );
	System.out.println(" -w=            The width of the self organizing map, defaults to 10." );
	System.out.println(" -settings=     The path to a settings file that may contain these arguments.");
	System.out.println("********************************************************************************\n\n");
	System.out.println("Done.");
    }
}