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
 * File name: 	I_CommandLineInputData.java
 * Package: uk.ac.man.jb.pct.mvc.model
 * Created:	Jun 15, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.mvc.model;

import java.util.ResourceBundle;

/**
 * I_CommandLineInputData
 * @author Rob Lyon
 */
public interface I_CommandLineInputData
{
    /**
     * Enables logging.
     */
    public void enableLogging();
    
    /**
     * Disables logging.
     */
    public void disableLogging();
    
    /**
     * @return true if logging is enabled, else false.
     */
    public boolean isLoggingEnabled();
    
    /**
     * Sets the language to be used within the application.
     * @param l the language, i.e. EN, FR, DE etc.
     */
    public void setLanguage(String l);
    
    /**
     * @return the language to be used throughout the application.
     */
    public String getLanguage();
    
    /**
     * Gets the country for the application.
     * @param l a string representation of the locale, i.e. UK or US.
     */
    public void setCountry(String l);
    
    /**
     * @return gets the current country.
     */
    public String getCountry();
    
    /**
     * Sets the path to the file to which a neural
     * network has previously been saved.
     * @param p the path to previously saved neural network.
     */
    public void setPathToSavedNetwork(String p);
    
    /**
     * @return the path to previously saved neural network.
     */
    public String getPathToSavedNetwork();
    
    /**
     * Sets the path to the file to which the neural
     * network constructed should be persisted.
     * @param p the path to write a version of the neural network to.
     */
    public void setNetworkSavePath(String p);
    
    /**
     * @return the path to write a version of the neural network to.
     */
    public String getNetworkSavePath();
    
    /**
     * Sets the path to the file that contains neural network training data.
     * @param p the path to the file that contains neural network training data.
     */
    public void setPathToTrainingFile(String p);
    
    /**
     * @return the path to the file that contains neural network training data.
     */
    public String getPathToTrainingFile();
    
    /**
     * Sets the path to the file that contains neural network validation data.
     * @param p the path to the file that contains neural network validation data.
     */
    public void setPathToValidationFile(String p);
    
    /**
     * @return the path to the file that contains neural network validation data.
     */
    public String getPathToValidationFile();
    
    /**
     * Sets the path to the file that contains data to be classified.
     * @param p the path to the file containing data to be classified.
     */
    public void setPathToClassificationFile(String p);
    
    /**
     * @return p the path to the file containing data to be classified.
     */
    public String getPathToClassificationFile();
    
    /**
     * Sets the path to the file where the output of
     * classification will be written to.
     * @param p the path to the file to write output information to.
     */
    public void setPathToOutputFile(String p);
    
    /**
     * @return the path to the file to write output information to.
     */
    public String getPathToOutputFile();
    
    /**
     * Sets the size of the self organising map to be
     * constructed by this application.
     * @param s the size of the map, i.e. the width and length.
     */
    public void setMapSize(int s);
    
    /**
     * @return the width of one side of the self organising
     * map to be constructed by this application.
     */
    public int getMapSize();
    
    /**
     * Sets the desired accuracy of the neural network to be
     * trained (a percentage value between 0 and 100 %.
     * @param a the desired accuracy of the classifier.
     */
    public void setDesiredNetworkAccuracy(int a);
    
    /**
     * @return the desired accuracy of the classifier.
     */
    public int getDesiredNetworkAccuracy();
    
    /**
     * Sets the desired number of tests to run against the classifier
     * @param a the desired number of tests.
     */
    public void setTests(int a);
    
    /**
     * @return the number of tests to run on the classifier.
     */
    public int getTests();
    
    /**
     * Configures the settings for Internationalisation and localisation.
     * @return Returns the bundle that contains internationalisation and localisation
     *         information for the application, else false if there is an error.
     */
    public ResourceBundle getResourceBundle();
    
    /**
     * Checks the actions that the application can perform automatically
     * given the input provided by the user at the command line.
     * @return a string representing the actions possible
     */
    public String getActionPossibleFromInputs();
    
    /**
     * Sets the type of classifier to use on the data.
     * @param i the type of classifier to use.
     */
    public void setClassifier(int i);
    
    /**
     * @return the type of classifier to use.
     */
    public int getClassifier();
    
    /**
     * Processes a command line argument.
     * @param arg the argument to process.
     */
    public void processArgument(String arg);
}
