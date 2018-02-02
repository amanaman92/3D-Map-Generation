package examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class has a bunch of useful statistical equations
 *      that help determine how to analyze heightmaps (avg.
 *      height, standard deviation, etc.)
 * @author jeffr
 */
public class Statistics 
{
    /**
     * @param numList
     * @return The sum of all integers in the list
     */
    public static int sum(List<Integer> numList)
    {
        int sum = 0;
        for(Integer i : numList)
        {
            sum += i;
        }
        return sum;
    }
    
    /**
     * @param numList
     * @return The mean of all integers in the list
     */
    public static float mean(List<Integer> numList)
    {
        return sum(numList) / (float) numList.size();
    }
    
    /**
     * @param numList
     * @return The standard deviation of the data in the list
     */
    public static float standardDeviation(List<Integer> numList)
    {
        float n = (float) numList.size();
        float mean = mean(numList);
        float summation = 0;
        for(int i = 0; i < n; i++)
        {
            summation += (float) Math.pow(numList.get(i) - mean, 2);
        }
        return (float) Math.sqrt((1 / n) * summation);
    }
    
    /**
     * @param numList
     * @return The median of the data in the list
     */
    public static float median(List<Integer> numList)
    {
        int size = numList.size();
        if(size % 2 == 0)
        {
            return (numList.get(size / 2) + numList.get((size / 2) + 1)) / (float) 2;
        }
        else
        {
            return numList.get((size / 2) + 1);
        }
    }
    
    /**
     * @param numList
     * @param percentile
     * @return The item that is at the given percentile in the list
     */
    public static float percentile(List<Integer> numList, float percentile)
    {
        Integer[] array = new Integer[numList.size()];
        numList.toArray(array);
        Arrays.sort(array);
        return array[(int) (array.length * percentile)];
    }
    
    /**
     * @param numList
     * @return The first quartile of the data in the list
     */
    public static float Q1(List<Integer> numList)
    {
        return median(numList.subList(0, numList.size() / 2));
    }
    
    /**
     * @param numList
     * @return The third quartile of the data in the list 
     */
    public static float Q3(ArrayList<Integer> numList)
    {
        return median(numList.subList(0, (numList.size() / 2) + 1));
    }
    
    /**
     * @param numList
     * @return The smallest integer in the list
     */
    public static float min(List<Integer> numList)
    {
        int lowest = Integer.MAX_VALUE;
        for(Integer i : numList)
        {
            if(i < lowest)
            {
                lowest = i;
            }
        }
        return lowest;
    }
    
    /**
     * @param numList
     * @return The largest integer in the list
     */
    public static float max(List<Integer> numList)
    {
        int highest = Integer.MIN_VALUE;
        for(Integer i : numList)
        {
            if(i > highest)
            {
                highest = i;
            }
        }
        return highest;
    }
}