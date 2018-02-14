package generation;

import java.awt.Color;

/**
 * This is a utilities class that serves to aid in math
 *      operations on Map data, often using heights defined
 *      on [0,1] or colors defined with float RBG values
 *      on the interval [0,1].
 * @author jeffr
 */
public class MapMath 
{
    /**
     * @param value The value of each of th RGB elements
     * @return A color with R,G,B all set to value
     */
    public static Color fromValue(float value)
    {
        if(value > 1)
        {
            value = 1;
        }
        else if(value < 0)
        {
            value = 0;
        }
        return new Color(value, value, value);
    }
    
    /**
     * Smooths the map by averaging colors of pixels around a point
     *      and then combining the color of each pixel with this average.
     *      The strenght value of a BWVector influences how much it is
     *      influenced (and how much it influences nearby pixels) in this
     *      algorithm.
     * @param heightVectors The HeightVector array to smooth
     * @param iterations How many cycles of the smoothing algorithm to run
     */
    public static void smooth(HeightVector[][] heightVectors, int iterations)
    {
        for(int i = 0; i < iterations; i++)
        {
            System.out.println(iterations);
            final int SIZE = heightVectors.length;
            for(int x = 0; x < heightVectors.length; x++)
            {
                for(int y = 0; y < heightVectors[x].length; y++)
                {
                    HeightVector[] selection;

                    //Upper Left
                    if(x == 0 && y == 0)
                    {
                        selection = new HeightVector[4];
                        selection[0] = heightVectors[x][y];
                        selection[1] = heightVectors[x + 1][y];
                        selection[2] = heightVectors[x][y + 1];
                        selection[3] = heightVectors[x + 1][y + 1];
                    }

                    //Upper Right
                    else if(x == 0 && y == SIZE - 1)
                    {
                        selection = new HeightVector[4];
                        selection[0] = heightVectors[x][y];
                        selection[1] = heightVectors[x + 1][y];
                        selection[2] = heightVectors[x][y - 1];
                        selection[3] = heightVectors[x + 1][y - 1];
                    }

                    //Lower Left
                    else if(x == SIZE - 1 && y == 0)
                    {
                        selection = new HeightVector[4];
                        selection[0] = heightVectors[x][y];
                        selection[1] = heightVectors[x - 1][y];
                        selection[2] = heightVectors[x][y + 1];
                        selection[3] = heightVectors[x - 1][y + 1];
                    }

                    //Lower Right
                    else if(x == SIZE - 1 && y == SIZE - 1)
                    {
                        selection = new HeightVector[4];
                        selection[0] = heightVectors[x][y];
                        selection[1] = heightVectors[x - 1][y];
                        selection[2] = heightVectors[x][y - 1];
                        selection[3] = heightVectors[x - 1][y - 1];
                    }

                    //Top
                    else if(x == 0)
                    {
                        selection = new HeightVector[6];
                        selection[0] = heightVectors[x][y];
                        selection[1] = heightVectors[x][y - 1];
                        selection[2] = heightVectors[x][y + 1];
                        selection[3] = heightVectors[x + 1][y];
                        selection[4] = heightVectors[x + 1][y - 1];
                        selection[5] = heightVectors[x + 1][y + 1];
                    }

                    //Left
                    else if(y == 0)
                    {
                        selection = new HeightVector[6];
                        selection[0] = heightVectors[x][y];
                        selection[1] = heightVectors[x + 1][y];
                        selection[2] = heightVectors[x - 1][y];
                        selection[3] = heightVectors[x][y + 1];
                        selection[4] = heightVectors[x - 1][y + 1];
                        selection[5] = heightVectors[x + 1][y + 1];
                    }

                    //Right
                    else if(y == SIZE - 1)
                    {
                        selection = new HeightVector[6];
                        selection[0] = heightVectors[x][y];
                        selection[1] = heightVectors[x + 1][y];
                        selection[2] = heightVectors[x - 1][y];
                        selection[3] = heightVectors[x][y - 1];
                        selection[4] = heightVectors[x - 1][y - 1];
                        selection[5] = heightVectors[x + 1][y - 1];
                    }

                    //Bottom
                    else if(x == SIZE - 1)
                    {
                        selection = new HeightVector[6];
                        selection[0] = heightVectors[x][y];
                        selection[1] = heightVectors[x][y - 1];
                        selection[2] = heightVectors[x][y + 1];
                        selection[3] = heightVectors[x - 1][y];
                        selection[4] = heightVectors[x - 1][y - 1];
                        selection[5] = heightVectors[x - 1][y + 1];
                    }

                    //Middle
                    else
                    {
                        selection = new HeightVector[9];
                        selection[0] = heightVectors[x][y];
                        selection[1] = heightVectors[x][y - 1];
                        selection[2] = heightVectors[x][y + 1];
                        selection[3] = heightVectors[x - 1][y];
                        selection[4] = heightVectors[x - 1][y - 1];
                        selection[5] = heightVectors[x - 1][y + 1];
                        selection[6] = heightVectors[x + 1][y];
                        selection[7] = heightVectors[x + 1][y - 1];
                        selection[8] = heightVectors[x + 1][y + 1];
                    }

                    float averageBWValue = averageBWColors(selection);
                    for (HeightVector vec : selection) 
                    {
                        float newValue = (vec.getHeight() + averageBWValue) / 2;
                        vec.setHeight(newValue);
                    }
                }
            }
        }
    }
    
    /**
     * @param heightVector The HeightVector array conaining the value data
     *      to average
     * @return The average value of the HeightVector, weighted by the
     *      BWVector strength.
     */
    public static float averageBWColors(HeightVector[] heightVector)
    {
        float sum = 0;
        float divisor = 0;
        
        for(HeightVector vec : heightVector)
        {
            sum += vec.getHeight() * vec.getStrength();
            divisor += 1 * vec.getStrength();
        }
        
        return sum / divisor;
    }
}
