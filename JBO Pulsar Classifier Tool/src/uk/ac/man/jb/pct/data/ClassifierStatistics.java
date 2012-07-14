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
 * File name: 	ClassifierStatistics.java
 * Package: uk.ac.man.jb.pct.data
 * Created:	Jun 21, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.jb.man.ac.uk>
 */
package uk.ac.man.jb.pct.data;

/**
 * ClassifierStatistics. This class is used to store
 * the statistics gathered on a generated classifier.
 * 
 * @author Rob Lyon
 */
public class ClassifierStatistics implements I_ClassifierStatistics
{
    //*****************************************
    //*****************************************
    //              Variables
    //*****************************************
    //*****************************************

    /**
     * The accuracy of the map.
     */
    private double accuracy = 0;

    /**
     * The precision of the map. Precision is the fraction
     * of retrieved instances that are relevant.
     */
    private double precision = 0;

    /**
     * The recall of the map. Recall is the fraction of relevant
     * instances that are retrieved
     */
    private double recall = 0;

    /**
     * The precision of the map. Specificity relates to the ability
     * of the map to identify negative results.
     */
    private double specificity = 0;

    /**
     *  The negative predictive value (NPV) is a summary statistic
     *  defined as the proportion of input patterns identified as not pulsars,
     *  that are correctly identified as such. A high NPV means that when the
     *  map yields a negative result, it is most likely correct in its assessment.
     */
    private double negativePredictiveValue = 0;

    /**
     * The Matthews correlation coefficient is used in machine learning
     * as a measure of the quality of binary (two-class) classifications.
     * It takes into account true and false positives and negatives and
     * is generally regarded as a balanced measure which can be used even
     * if the classes are of very different sizes. The MCC is in essence a
     * correlation coefficient between the observed and predicted binary
     * classifications; it returns a value between -1 and +1. A coefficient
     * of +1 represents a perfect prediction, 0 no better than random prediction
     * and -1 indicates total disagreement between prediction and observation.
     * The statistic is also known as the phi coefficient.
     */
    private double matthewsCorrelation = 0;

    /**
     * The F1 score (also F-score or F-measure) is a measure of a test's accuracy.
     * It considers both the precision p and the recall r of the test to compute
     * the score: p is the number of correct results divided by the number of all
     * returned results and r is the number of correct results divided by the
     * number of results that should have been returned.
     */
    private double fScore = 0;

    /**
     * The true positives.
     */
    private double TP = 0;

    /**
     * The true negatives.
     */
    private double TN = 0;

    /**
     * The false positives.
     */
    private double FP = 0;

    /**
     * The false negatives.
     */
    private double FN = 0;

    //*****************************************
    //*****************************************
    //         Getters & Setters
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getAccuracy()
     */
    @Override
    public double getAccuracy() { return accuracy; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setAccuracy(double)
     */
    @Override
    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getPrecision()
     */
    @Override
    public double getPrecision() { return precision; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setPrecision(double)
     */
    @Override
    public void setPrecision(double precision) { this.precision = precision; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getRecall()
     */
    @Override
    public double getRecall() { return recall; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setRecall(double)
     */
    @Override
    public void setRecall(double recall) { this.recall = recall; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getSpecificity()
     */
    @Override
    public double getSpecificity() { return specificity; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setSpecificity(double)
     */
    @Override
    public void setSpecificity(double specificity) { this.specificity = specificity; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getMatthewsCorrelation()
     */
    @Override
    public double getMatthewsCorrelation() { return matthewsCorrelation; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setMatthewsCorrelation(double)
     */
    @Override
    public void setMatthewsCorrelation(double matthewsCorrelation) { this.matthewsCorrelation = matthewsCorrelation; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getfScore()
     */
    @Override
    public double getfScore() { return fScore; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setFScore(double)
     */
    @Override
    public void setFScore(double fScore) { this.fScore = fScore; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getNegativePredictiveValue()
     */
    @Override
    public double getNegativePredictiveValue() { return negativePredictiveValue; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setNegativePredictiveValue(double)
     */
    @Override
    public void setNegativePredictiveValue(double d){ negativePredictiveValue = d; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getTP()
     */
    @Override
    public double getTP() { return TP; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setTP(double)
     */
    @Override
    public void setTP(double v) { this.TP = v; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getTN()
     */
    @Override
    public double getTN() { return TN; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setTN(double)
     */
    @Override
    public void setTN(double v) { this.TN = v; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getFP()
     */
    @Override
    public double getFP() { return FP; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setFP(double)
     */
    @Override
    public void setFP(double v) { this.FP = v; }

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#getFN()
     */
    @Override
    public double getFN() { return FN; }
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#setFN(double)
     */
    @Override
    public void setFN(double v) { this.FN = v; }

    //*****************************************
    //*****************************************
    //             Constructor
    //*****************************************
    //*****************************************

    /**
     * Default constructor.
     */
    public ClassifierStatistics(){}

    /**
     * Primary constructor.
     * @param tp the true positives.
     * @param tn the true negatives.
     * @param fp the false positives.
     * @param fn the false negatives.
     */
    public ClassifierStatistics(double tp,double tn,double fp,double fn)
    {
	this.TP = tp;
	this.TN = tn;
	this.FP = fp;
	this.FN = fn;
    }

    //*****************************************
    //*****************************************
    //               Methods
    //*****************************************
    //*****************************************

    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#calculate()
     */
    @Override
    public void calculate()
    {
	accuracy = (TP + TN) / (TP + FP + FN + TN);
	//accuracy = accuracy * 100; // present as %

	precision = (TP) / (TP+FP);

	recall = (TP) / (TP + FN);

	specificity = (TN) / (FP+TN);

	negativePredictiveValue = (TN) / (FN + TN);

	matthewsCorrelation = ((TP * TN) - (FP * FN)) / Math.sqrt((TP+FP) * (TP+FN) * (TN+FP) * (TN+FN));

	fScore = 2 * ((precision * recall) / (precision + recall));
    }
    
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#incrementTP()
     */
    public void incrementTP() { this.TP++; }
    
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#incrementTN()
     */
    public void incrementTN() { this.TN++; }
    
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#incrementFP()
     */
    public void incrementFP() { this.FP++; }
    
    /* (non-Javadoc)
     * @see uk.ac.man.jb.pct.data.I_ClassifierStatistics#incrementFN()
     */
    public void incrementFN() { this.FN++; }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	String output = "Accuracy:\t"+ (accuracy * 100) + "\n" +
	                "Precision:\t"+ (precision * 100) + "\n" +
	                "Recall:\t"+ (recall * 100) + "\n" +
	                "Specificity:\t"+ (specificity * 100) + "\n" +
	                "NPV:\t"+ (negativePredictiveValue * 100) + "\t(Negative Predictive Value)\n" +
	                "MCC:\t"+ matthewsCorrelation + "\t(Matthews Correlation Coefficient)\n" +
	                "F-Score:\t"+ fScore;
	return output;
    }
}
