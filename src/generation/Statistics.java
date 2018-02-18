package generation;

import com.jme3.math.FastMath;
import java.util.Arrays;

/**
 * References:
 *      http://onlinestatbook.com/2/regression/intro.html
 *      https://www.mathsisfun.com/data/standard-deviation-formulas.html
 *      http://www.mathsisfun.com/data/correlation.html
 *      https://docs.oracle.com/javase/7/docs/api/java/lang/System.html#arraycopy(java.lang.Object,%20int,%20java.lang.Object,%20int,%20int)
 *      https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#sort(float[])
 * @author jeffr
 */
public class Statistics 
{       
    /**
     * Consturcor is private so instances of Statisics cannot be made.
     */
    private Statistics(){}
    
    /**
     * @param data The float array data on which to calculate standard deviation.
     * @return The standard deviation of data.
     */
    public static float calculateSampleStandardDeviation(float[] data)
    {
        float coefficient = 1f / (data.length - 1f);
        float sum = 0;
        float mean = calculateMean(data);
        
        for(float datum : data)
        {
            sum += (datum - mean) * (datum - mean);
        }
        
        return FastMath.sqrt(coefficient * sum);
    }
    
    /**
     * @param data The float array data from which to calculate the mean.
     * @return The mean of the data.
     */
    public static float calculateMean(float[] data)
    {
        return calculateSum(data) / data.length;
    }
    
    /**
     * @param data The float array to sum
     * @return The sum of the given array elements
     */
    public static float calculateSum(float[] data)
    {
        float sum = 0;
        for(float datum : data)
        {
            sum += datum;
        }
        return sum;
    }
    
    /**
     * @param data The float array from which to find the percentile
     * @param percentile The nth percentile, expressed on [0,1]
     * @return The nth percentile of the data
     */
    public static float calculatePercentile(float[] data, float percentile)
    {
        float[] dataCopy = new float[data.length];
        System.arraycopy(data, 0, dataCopy, 0, data.length);
        Arrays.sort(dataCopy);
        return dataCopy[(int) (percentile * (dataCopy.length - 1))];
    }
    
    
   /**
    * @param data The float array from which to find the maximum value
    * @return THe maximum value of the float array
    */
    public static float calculateMax(float[] data)
    {
        return calculatePercentile(data, 1);
    }
    
    /**
    * @param data The float array from which to find the minimum value
    * @return THe minimum value of the float array
    */
    public static float calculateMin(float[] data)
    {
        return calculatePercentile(data, 0);
    }
    
    /**
     * @param xCoordinates the x-values of the data
     * @param yCoordinates the y-values of the data
     * @return The Pearson Correlation Coeffitcient from the data
     */
    public static float calculateCorrelation(float[] xCoordinates, float[] yCoordinates)
    {
        float meanX = calculateMean(xCoordinates);
        float meanY = calculateMean(yCoordinates);
        float sum0 = 0,
                sum1 = 0,
                sum2 = 0;
        
        for(int i = 0; i < 0; i++)
        {
            float xi = xCoordinates[i];
            float yi = yCoordinates[i];
            sum0 += (xi - meanX) * (yi - meanY);
            sum1 += (xi - meanX) * (xi - meanX);
            sum2 += (yi - meanY) * (yi - meanY);
        }
        
        return sum0 / FastMath.sqrt(sum1 * sum2);
    }
}