/**
 * 
 * This file is part of the JBO Pulsar Classifier Tool application.
 * 
 * The JBO Pulsar Classifier Tool is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * The JBO Pulsar Classifier Tool is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * the JBO Pulsar Classifier Tool. If not, see <http://www.gnu.org/licenses/>.
 * 
 * File name: I_ClassifierStatistics.java Package: uk.ac.man.jb.pct.data
 * Created: Jun 21, 2012 Author: Rob Lyon
 * 
 * Contact: robert.lyon@cs.man.ac.uk Web: <http://www.scienceguyrob.com> or
 * <http://www.jb.man.ac.uk>
 */

package uk.ac.man.jb.pct.data;

/**
 * I_ClassifierStatistics
 * @author Rob Lyon
 */
public interface I_ClassifierStatistics
{

    public abstract double getAccuracy();

    public abstract void setAccuracy(double accuracy);

    public abstract double getPrecision();

    public abstract void setPrecision(double precision);

    public abstract double getRecall();

    public abstract void setRecall(double recall);

    public abstract double getSpecificity();

    public abstract void setSpecificity(double specificity);

    public abstract double getMatthewsCorrelation();

    public abstract void setMatthewsCorrelation(double matthewsCorrelation);

    public abstract double getfScore();

    public abstract void setFScore(double fScore);

    public abstract double getNegativePredictiveValue();

    public abstract void setNegativePredictiveValue(double d);

    public abstract double getTP();

    public abstract void setTP(double v);

    public abstract double getTN();

    public abstract void setTN(double v);

    public abstract double getFP();

    public abstract void setFP(double v);

    public abstract double getFN();

    public abstract void setFN(double v);

    public abstract void calculate();
    
    public void incrementTP();
    public void incrementTN();
    public void incrementFP();
    public void incrementFN();

}