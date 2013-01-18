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
 * File name: 	Experiments.java
 * Package: uk.ac.man.jb.pct.test
 * Created:	Nov 20, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import uk.ac.man.jb.pct.classifiers.som.FrequencyPoint;
import uk.ac.man.jb.pct.classifiers.som.SOMClassifier;
import uk.ac.man.jb.pct.classifiers.som.SelfOrganizingMap;
import uk.ac.man.jb.pct.data.ClassifierStatistics;
import uk.ac.man.jb.pct.data.I_ClassifierStatistics;
import uk.ac.man.jb.pct.data.I_DataSet;
import uk.ac.man.jb.pct.data.PatternFileProcessor;
import uk.ac.man.jb.pct.io.Writer;
import uk.ac.man.jb.pct.mvc.Constants;
import uk.ac.man.jb.pct.mvc.controllers.AutoTrainerController;
import uk.ac.man.jb.pct.mvc.model.CommandLineInputData;
import uk.ac.man.jb.pct.util.Common;
import uk.ac.man.jb.pct.util.StringOps;
import com.scienceguyrob.stats.StatsDataSet;
import com.scienceguyrob.stats.StatsOps;

/**
 * Experiments.
 * 
 * This class contains methods that undertake particular experiments on the SOM classifier.
 * 
 * @author Rob Lyon
 */
public class Experiments
{
    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /**
     * Outputs statistics on the similarity between positive and negative clusters.
     * @param classifier_type
     * @param initialisedNetworkPath
     * @param t_DataPath
     * @param v_DataPath
     * @param outputFilePath
     */
    @SuppressWarnings("unused")
    public static void getClusterSimilarity(int classifier_type,String initialisedNetworkPath,String t_DataPath,String v_DataPath,String outputFilePath)
    {

	// Clear the output file
	Common.fileDelete(outputFilePath);

	// Write the CSV headers to the file.
	Writer.write(outputFilePath,",STATS,X,Y,\n");

	// Get the Training Data
	I_DataSet t_data = new PatternFileProcessor().process(t_DataPath);
	I_DataSet v_data = new PatternFileProcessor().process(v_DataPath);

	System.out.println("Training Data Rows:"+t_data.getRows());
	System.out.println("Training Data Cols:"+t_data.getColumns());

	// Major error if we return at this stage. This
	// means that the training data
	// can't be read, or is just plain invalid.
	if(t_data == null)
	{
	    System.out.println("Training data is null.");
	    System.exit(0);
	}

	// Read in pre-initialised SOM network. By loading in the 
	// same network state prior to training, we should be able
	// to reproduce map states, given that the starting state
	// is now the same (i.e. no longer randomly initialised).
	SOMClassifier classifier = (SOMClassifier)SOMClassifier.read(initialisedNetworkPath);

	// Ensure the SOM has the correct training data.
	classifier.map.setData(t_data.getDataAsArrayList());

	// Train the map.

	// This method ensures reproducibility, as the map is not
	// trained using calls to random.
	//classifier.map.TrainNoRandom(0.000001);
	classifier.map.Train(0.000001);

	// If no clusters are found, then something has gone wrong.
	// Perhaps the training data has no positive instance?
	if(!classifier.locateClusters(t_data))
	{
	    System.out.println("No clusters found.");
	    System.exit(0); // Just exit.
	}

	// Validate against validation data set
	if(classifier_type < 0)
	    classifier.validate(v_data, 0);
	else
	    classifier.validate(v_data, classifier_type);

	// Get Statistics
	I_ClassifierStatistics stats = classifier.getStatistics();

	// If there are more than zero positive instances.
	if(classifier.positiveCoords.size() < 0)
	{
	    System.out.println("No positive co-ordinates found.");
	    return;
	}

	System.out.println("Positive Co-ords: "+ classifier.positiveCoords.size());

	double totalDistance = 0.0;
	List<String> distances = new ArrayList<String>();
	int totalPositive = classifier.positiveCoords.size();

	// For each positive point
	for(int i = 0; i < totalPositive-1;i++)
	{
	    // Work out its distance to all other points.
	    Point p = classifier.positiveCoords.get(i);
	    Writer.write(outputFilePath,",,"+p.getX()+","+p.getY()+",\n");

	    for(int j = i+1; j < classifier.positiveCoords.size();j++)
	    {
		Point tmp = classifier.positiveCoords.get(j);
		double dist = Common.distance(p.getX(),p.getY(),tmp.getX(),tmp.getY());
		distances.add(Double.toString(dist));
		totalDistance += dist;
	    }
	}

	// Append some details and formatting to output file.
	Writer.write(outputFilePath,",,"+classifier.positiveCoords.get(totalPositive-1).getX()+","+classifier.positiveCoords.get(totalPositive-1).getX()+",\n");
	Writer.write(outputFilePath,",,,,\n");

	// Now calculate the statistics for these positive points.
	double min = StatsOps.Min(com.scienceguyrob.utils.Common.convertStringListToDoubleArray(distances));
	Writer.write(outputFilePath,",MIN,"+min+",,\n");
	double max = StatsOps.Max(com.scienceguyrob.utils.Common.convertStringListToDoubleArray(distances));
	Writer.write(outputFilePath,",MAX,"+max+",,\n");
	double var = StatsOps.Varience(com.scienceguyrob.utils.Common.convertStringListToDoubleArray(distances));
	Writer.write(outputFilePath,",VAR,"+var+",,\n");
	double stdev = StatsOps.StandardDeviation(com.scienceguyrob.utils.Common.convertStringListToDoubleArray(distances));
	Writer.write(outputFilePath,",STDEV,"+stdev+",,\n");			    
	double avg = totalDistance / (classifier.positiveCoords.size() * (classifier.positiveCoords.size() -1))/2;
	Writer.write(outputFilePath,",AVG,"+avg+",,\n");
	Writer.write(outputFilePath,",,,,\n");

	// Un-comment as necessary
	/*System.out.println("Average distance: "+ avg);
	System.out.println("Min distance: "+ min);
	System.out.println("Max distance: "+ max);
	System.out.println("Varience distance: "+ var);
	System.out.println("Standard Deviation: "+ stdev);*/


	// Now we do the same for the negative co-ordinates.
	if(classifier.negativeCoords.size() > 0)
	{
	    System.out.println("No negative co-ordinates found.");
	    return;
	}
	Writer.write(outputFilePath,",,X,Y,\n");

	System.out.println("Negative Co-ords: "+ classifier.negativeCoords.size());

	totalDistance = 0.0;

	// Clear previous points.
	distances.clear();
	distances = new ArrayList<String>();

	int totalNegative = classifier.negativeCoords.size();
	for(int i = 0; i < totalNegative-1;i++)
	{
	    Point p = classifier.negativeCoords.get(i);
	    Writer.write(outputFilePath,",,"+p.getX()+","+p.getY()+",\n");

	    for(int j = i+1; j < classifier.negativeCoords.size();j++)
	    {
		Point tmp = classifier.negativeCoords.get(j);
		double dist = Common.distance(p.getX(),p.getY(),tmp.getX(),tmp.getY());
		distances.add(Double.toString(dist));
		totalDistance += dist;
	    }
	}

	// Append some details and formatting to output file.
	Writer.write(outputFilePath,",,"+classifier.negativeCoords.get(totalNegative-1).getX()+","+classifier.negativeCoords.get(totalNegative-1).getX()+",\n");
	Writer.write(outputFilePath,",,,,\n");

	min = StatsOps.Min(com.scienceguyrob.utils.Common.convertStringListToDoubleArray(distances));
	Writer.write(outputFilePath,",MIN,"+min+",,\n");
	max = StatsOps.Max(com.scienceguyrob.utils.Common.convertStringListToDoubleArray(distances));
	Writer.write(outputFilePath,",MAX,"+max+",,\n");
	var = StatsOps.Varience(com.scienceguyrob.utils.Common.convertStringListToDoubleArray(distances));
	Writer.write(outputFilePath,",VAR,"+var+",,\n");
	stdev = StatsOps.StandardDeviation(com.scienceguyrob.utils.Common.convertStringListToDoubleArray(distances));
	Writer.write(outputFilePath,",STDEV,"+stdev+",,\n");			    
	avg = totalDistance / (classifier.negativeCoords.size() * (classifier.negativeCoords.size() -1))/2;
	Writer.write(outputFilePath,",AVG,"+avg+",,\n");
	Writer.write(outputFilePath,",,,,\n");

	// Un-comment as necessary
	/*System.out.println("Average distance: "+ avg);
	System.out.println("Min distance: "+ min);
	System.out.println("Max distance: "+ max);
	System.out.println("Varience distance: "+ var);
	System.out.println("Standard Deviation: "+ stdev);*/

	// More formatting
	Writer.write(outputFilePath,",,,,\n");
	Writer.write(outputFilePath,",X,Y,FREQ,\n");

	// Now I obtain the frequencies of the points.
	ArrayList<FrequencyPoint> fPoints = new ArrayList<FrequencyPoint>();
	for(int i = 0; i < classifier.positiveCoords.size();i++)
	{
	    Point p = classifier.positiveCoords.get(i);

	    if(fPoints.size() == 0)
		fPoints.add(new FrequencyPoint(p.x,p.y,1));
	    else
	    {
		boolean recorded = false;

		for(int j = 0 ; j <fPoints.size(); j++)
		{
		    if(fPoints.get(j).X == p.x &&
			    fPoints.get(j).Y == p.y)
		    {
			fPoints.get(j).frequency++;
			recorded = true;
			break;
		    }
		}

		if(!recorded) { fPoints.add(new FrequencyPoint(p.x,p.y,1)); }
	    }
	}

	for(int i = 0; i < fPoints.size();i++)
	{
	    FrequencyPoint p = fPoints.get(i);
	    Writer.write(outputFilePath,","+p.X+","+p.Y+","+p.frequency+",\n");
	}

	Writer.write(outputFilePath,",,,,\n");
	Writer.write(outputFilePath,",X,Y,FREQ,\n");

	fPoints.clear();
	for(int i = 0; i < classifier.negativeCoords.size();i++)
	{
	    Point p = classifier.negativeCoords.get(i);

	    if(fPoints.size() == 0)
		fPoints.add(new FrequencyPoint(p.x,p.y,1));
	    else
	    {
		boolean recorded = false;

		for(int j = 0 ; j <fPoints.size(); j++)
		{
		    if(fPoints.get(j).X == p.x &&
			    fPoints.get(j).Y == p.y)
		    {
			fPoints.get(j).frequency++;
			recorded = true;
			break;
		    }
		}

		if(!recorded) { fPoints.add(new FrequencyPoint(p.x,p.y,1)); }
	    }
	}

	for(int i = 0; i < fPoints.size();i++)
	{
	    FrequencyPoint p = fPoints.get(i);
	    Writer.write(outputFilePath,","+p.X+","+p.Y+","+p.frequency+",\n");
	}
    }

    /**
     * Builds a SOM with a preset random initialisation.
     * @param t_DataPath
     * @param savedNetworkPath
     * @param mapWidth
     */
    public static void preInitialiseSOM(String t_DataPath,String savedNetworkPath,int mapWidth)
    {
	//Get training data
	I_DataSet t_data = new PatternFileProcessor().process(t_DataPath);

	// Build Map
	SelfOrganizingMap map = new SelfOrganizingMap(t_data.getDataAsArrayList());

	// How many attributes does the data have?
	// Find out and pass this value to the map, so that
	// the map can accommodate them.
	map.setAttributes(t_data.getColumns());

	// What map width to use? 
	map.setMapWidth(mapWidth);

	// Normalise Inputs
	map.NormalisePatterns();

	// Build map
	map.Build();

	// Train map
	//map.Train(map.maximumErrorRate);

	// Find Clusters of positive pulsar instances in the map
	SOMClassifier classifier = new SOMClassifier(map);

	// Save 
	Common.fileDelete(savedNetworkPath);

	if(SOMClassifier.write(classifier,savedNetworkPath))
	    System.out.println("Self Organizing Map state persisted.");
    }

    /**
     * 
     * @param pathToTrainingFiles
     * @param pathToClassificationFiles
     * @param classifier_type
     * @param mapWidth
     * @param k
     */
    public static void classificationTestsStaticK(String pathToTrainingFiles,String pathToClassificationFiles, int classifier_type, int mapWidth, int k)
    {
	// Training and Validation Data
	String tDataPath = "/Users/rob/git/JBO-Pulsar-Classifier/JBO Pulsar Classifier Tool/Resources/training_set_mod.pat";
	String vDataPath = "/Users/rob/git/JBO-Pulsar-Classifier/JBO Pulsar Classifier Tool/Resources/validation_set.pat";

	// The path that the temporary neural net is saved to.
	String savedNetworkPath = "/Users/rob/DELETEMENETWORK.xml";
	String outputFilePathRubbish = "/Users/rob/DELETEMEFILEPATH.txt";

	// The path to write results to
	String outputFilePath = "/Users/rob/RESULTS/READ.txt";

	// The test data patterns to classify
	String patternsDirectory = pathToClassificationFiles;

	// The file to write result statistics to.
	String stats_output_file = "/Users/rob/RESULTS/STATS.txt";
	// The headers for the CSV statistics file.
	String[] headers = {"STATS","TEST","FILE","TP","FP","TN","FN","Recall","Precision","F-Score","Specificity","NPV","MCC","Accuracy"};

	// Add this information to the input object, usually
	// used to process command line information.
	CommandLineInputData input = new CommandLineInputData();
	input.setPathToTrainingFile(tDataPath);
	input.setPathToValidationFile(vDataPath);
	input.setNetworkSavePath(savedNetworkPath);
	input.setPathToSavedNetwork(savedNetworkPath);
	input.setPathToOutputFile(outputFilePathRubbish);
	input.setClassifier(classifier_type);
	input.setDesiredNetworkAccuracy(90);

	// The paths to the training files to use.
	String[] classificationFiles = Common.getFilePaths(patternsDirectory);

	String[] trainingFiles = Common.getFilePaths(pathToTrainingFiles);

	if(trainingFiles.length < 1)
	{
	    System.out.println("No training files!");
	    System.exit(0);
	}

	// The class used to train the neural network.
	AutoTrainerController trainer;

	// The test data to be classified.
	I_DataSet classification_data;

	for(int z = 0; z < trainingFiles.length ; z++) // For each training file
	{
	    if(!trainingFiles[z].toString().endsWith(".pat"))
		continue;

	    input.setPathToTrainingFile(trainingFiles[z]); // Update path to the training file

	    for(int f = 0; f < classificationFiles.length;f++)// for each file of data to be classified
	    {
		if(!classificationFiles[f].toString().endsWith(".pat"))
		    continue;

		// Get the data
		classification_data = new PatternFileProcessor().process(classificationFiles[f]);

		for(int t=0; t < 1; t++)// do a number of tests
		{
		    for(int s = 0; s < 10; s++)
		    {
			// Delete any existing saved neural network.
			Common.fileDelete(input.getPathToSavedNetwork());
			// Train the network.
			input.setMapSize(mapWidth);
			trainer = new AutoTrainerController(input);
			trainer.run();

			// Load the trained Map back in.
			SOMClassifier classifier = (SOMClassifier)SOMClassifier.read(input.getPathToSavedNetwork());
			classifier.stats.calculate(); // get the performance statistics of the network.

			// Get the type of classifier that the user wants to use.
			int classifierChoice = input.getClassifier(); 

			// Create an object to store the statistics collected during this test.
			ClassifierStatistics testStats = new ClassifierStatistics();

			//classify the data
			for(int r = 0; r < classification_data.getRows(); r++) // For each pattern to be classified.
			{
			    // Classify the pattern.
			    Object[] results = classifier.classify(classification_data.getDataRow(r), classifierChoice);
			    String classification = (String) results[0].toString();

			    // Here we classify the pattern using the classify, but we already know the class a priori.
			    // So we check the classification given, with the actual class.
			    //  CLASSIFICATION APPLIED --------------------------------- ACTUAL CLASS
			    if( classification.equals(Constants.PULSAR) && classification_data.getDataRow(r).getClassMembership().equals(Constants.PULSAR))
				testStats.incrementTP(); // True Positive
			    else if(classification.equals(Constants.PULSAR) && classification_data.getDataRow(r).getClassMembership().equals(Constants.RFI))
				testStats.incrementFP(); // False positive
			    else if(classification.equals(Constants.RFI) && classification_data.getDataRow(r).getClassMembership().equals(Constants.PULSAR))
				testStats.incrementFN(); // False negative
			    else
				testStats.incrementTN(); // True negative
			}

			// Causes the statistics object to calculate its internal metrics (i.e. F-score, precision, recall etc).
			testStats.calculate();

			// Write the output to a file.

			// Describe test: 
			String test = "Test[ td =" + trainingFiles[z] + "][ file = " + f + "][ test = " + t + "][ mapsize test (12)= " + s + "]," + classificationFiles[f] + ",";

			// Provide statistics: TP, FP, TN, FN, RECALL, PRECISION, FSCORE, SPECIFICITY, NPV, MCC, ACCURACY, 
			String stats = testStats.getTP() + "," + testStats.getFP() + "," + testStats.getTN() + "," + testStats.getFN() + "," + testStats.getRecall() + "," +
				testStats.getPrecision() + ","+testStats.getfScore() + "," + testStats.getSpecificity() + "," +testStats.getNegativePredictiveValue() + "," +
				testStats.getMatthewsCorrelation() + "," + testStats.getAccuracy() + ",\n";

			String text = test + stats;// Simply add two parts together.
			Writer.write(outputFilePath,text);

			//clean up variables.
			trainer = null;
			classifier = null;
		    }

		    // Create a new object that converts the statistics collected in to CSV format.
		    StatsDataSet statsDataSet = new StatsDataSet(outputFilePath); // Read neural network output file.
		    String summaryStats = statsDataSet.printAsCSVWithStats(headers);

		    // Write the CSV format to file.
		    Writer.write(stats_output_file, summaryStats);

		    // Delete the neural net output file, as these results have now
		    // been written to a statistics file.
		    Common.fileDelete(outputFilePath);
		    Common.fileCreate(outputFilePath);
		    System.gc();
		}

		// Explicit cleanup.
		classification_data = null;
		System.gc();
	    }
	}
    }

    /**
     * I also provide a parameter so that you can decide how ties should be broken.
     * 
     * 0 = assign the class of the nearest neighbour.
     * 1 = brake ties randomly.
     * @param pathToTrainingFiles
     * @param pathToClassificationFiles
     * @param mapWidth
     * @param k
     * @param tieBreakingParam
     */
    public static void classificationTestsVariableK(String pathToTrainingFiles,String pathToClassificationFiles, int mapWidth, int k,int tieBreakingParam)
    {
	// Training and Validation Data
	String tDataPath = "/Users/rob/git/JBO-Pulsar-Classifier/JBO Pulsar Classifier Tool/Resources/training_set_mod.pat";
	String vDataPath = "/Users/rob/git/JBO-Pulsar-Classifier/JBO Pulsar Classifier Tool/Resources/validation_set.pat";

	// The path that the temporary neural net is saved to.
	String savedNetworkPath = "/Users/rob/DELETEMENETWORK.xml";
	String outputFilePathRubbish = "/Users/rob/DELETEMEFILEPATH.txt";

	// The path to write results to
	String outputFilePath = "/Users/rob/RESULTS/READ.txt";

	// The test data patterns to classify
	String patternsDirectory = pathToClassificationFiles;

	// The file to write result statistics to.
	String stats_output_file = "/Users/rob/RESULTS/STATS_knn_btr.txt";
	// The headers for the CSV statistics file.
	String[] headers = {"STATS","TEST","FILE","TP","FP","TN","FN","Recall","Precision","F-Score","Specificity","NPV","MCC","Accuracy"};

	// Add this information to the input object, usually
	// used to process command line information. It 
	// is not actually used here.
	CommandLineInputData input = new CommandLineInputData();
	input.setPathToTrainingFile(tDataPath);
	input.setPathToValidationFile(vDataPath);
	input.setNetworkSavePath(savedNetworkPath);
	input.setPathToSavedNetwork(savedNetworkPath);
	input.setPathToOutputFile(outputFilePathRubbish);
	input.setClassifier(0);// Doesn't matter
	input.setDesiredNetworkAccuracy(90);

	// The paths to the training files to use.
	String[] classificationFiles = Common.getFilePaths(patternsDirectory);

	String[] trainingFiles = Common.getFilePaths(pathToTrainingFiles);

	if(trainingFiles.length < 1)
	{
	    System.out.println("No training files!");
	    System.exit(0);
	}

	// The class used to train the neural network.
	AutoTrainerController trainer;

	// The test data to be classified.
	I_DataSet classification_data;

	for(int kloop = 23 ; kloop < 28; kloop++)
	    if( kloop % 2 != 0)
	    {
		for(int z = 0; z < trainingFiles.length ; z++) // For each training file
		{
		    if(!trainingFiles[z].toString().endsWith(".pat"))
			continue;

		    input.setPathToTrainingFile(trainingFiles[z]); // Update path to the training file

		    for(int f = 0; f < classificationFiles.length;f++)// for each file of data to be classified
		    {
			if(!classificationFiles[f].toString().endsWith(".pat"))
			    continue;

			// Get the data
			classification_data = new PatternFileProcessor().process(classificationFiles[f]);

			for(int t=0; t < 1; t++)// do a number of tests
			{
			    for(int s = 0; s < 10; s++)
			    {
				// Delete any existing saved neural network.
				Common.fileDelete(input.getPathToSavedNetwork());
				// Train the network.
				input.setMapSize(mapWidth);
				trainer = new AutoTrainerController(input);
				trainer.run();

				// Load the trained Map back in.
				SOMClassifier classifier = (SOMClassifier)SOMClassifier.read(input.getPathToSavedNetwork());
				classifier.stats.calculate(); // get the performance statistics of the network.

				// Create an object to store the statistics collected during this test.
				ClassifierStatistics testStats = new ClassifierStatistics();

				//classify the data
				for(int r = 0; r < classification_data.getRows(); r++) // For each pattern to be classified.
				{
				    // Classify the pattern.
				    Object[] results = classifier.discreetKNNClassify(classification_data.getDataRow(r),kloop,tieBreakingParam);
				    String classification = (String) results[0].toString();

				    // Here we classify the pattern using the classify, but we already know the class a priori.
				    // So we check the classification given, with the actual class.
				    //  CLASSIFICATION APPLIED --------------------------------- ACTUAL CLASS
				    if( classification.equals(Constants.PULSAR) && classification_data.getDataRow(r).getClassMembership().equals(Constants.PULSAR))
					testStats.incrementTP(); // True Positive
				    else if(classification.equals(Constants.PULSAR) && classification_data.getDataRow(r).getClassMembership().equals(Constants.RFI))
					testStats.incrementFP(); // False positive
				    else if(classification.equals(Constants.RFI) && classification_data.getDataRow(r).getClassMembership().equals(Constants.PULSAR))
					testStats.incrementFN(); // False negative
				    else
					testStats.incrementTN(); // True negative
				}

				// Causes the statistics object to calculate its internal metrics (i.e. F-score, precision, recall etc).
				testStats.calculate();

				// Write the output to a file.

				// Describe test: 
				String test = "Test[ td =" + StringOps.getFileNameFromPath(trainingFiles[z]) + "][k="+kloop+"][ file = " + f + "][ test = " + t + "][ mapsize test (12)= " + s + "]," + StringOps.getFileNameFromPath(classificationFiles[f]) + ",";

				// Provide statistics: TP, FP, TN, FN, RECALL, PRECISION, FSCORE, SPECIFICITY, NPV, MCC, ACCURACY, 
				String stats = testStats.getTP() + "," + testStats.getFP() + "," + testStats.getTN() + "," + testStats.getFN() + "," + testStats.getRecall() + "," +
					testStats.getPrecision() + ","+testStats.getfScore() + "," + testStats.getSpecificity() + "," +testStats.getNegativePredictiveValue() + "," +
					testStats.getMatthewsCorrelation() + "," + testStats.getAccuracy() + ",\n";

				String text = test + stats;// Simply add two parts together.
				Writer.write(outputFilePath,text);

				//clean up variables.
				trainer = null;
				classifier = null;
			    }

			    // Create a new object that converts the statistics collected in to CSV format.
			    StatsDataSet statsDataSet = new StatsDataSet(outputFilePath); // Read neural network output file.
			    String summaryStats = statsDataSet.printAsCSVWithStats(headers);

			    // Write the CSV format to file.
			    Writer.write(stats_output_file, summaryStats);

			    // Delete the neural net output file, as these results have now
			    // been written to a statistics file.
			    Common.fileDelete(outputFilePath);
			    Common.fileCreate(outputFilePath);
			    System.gc();
			}

			// Explicit cleanup.
			classification_data = null;
			System.gc();
		    }
		}
		System.gc();
	    }
    }
}
