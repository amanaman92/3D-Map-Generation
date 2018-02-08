package generation;

import com.jme3.math.FastMath;

/**
 *
 * References:
 *      http://onlinestatbook.com/2/regression/intro.html
 *      https://www.mathsisfun.com/data/standard-deviation-formulas.html
 *      http://www.mathsisfun.com/data/correlation.html
 * @author jeffr
 */
public class Statistics 
{       
    /**
     * Consturcor is private so instances of Statisics cannot be made.
     */
    private Statistics(){}
    
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
    
    public static float calculateMean(float[] data)
    {
        return calculateSum(data) / data.length;
    }
    
    public static float calculateSum(float[] data)
    {
        float sum = 0 ;
        for(float datum : data)
        {
            sum += datum;
        }
        return sum;
    }
    
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