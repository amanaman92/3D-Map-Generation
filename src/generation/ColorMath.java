package generation;

import java.awt.Color;

/**
 * This is a utilities class that serves to aid in math
 *      operations on Colors, defined with float RBG values
 *      on the interval [0,1].
 * @author jeffr
 */
public class ColorMath 
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
    
    public static void smooth(BWVector[][] bwVectors, int iterations)
    {
        for(int i = 0; i < iterations; i++)
        {
            final int SIZE = bwVectors.length;
            for(int x = 0; x < bwVectors.length; x++)
            {
                for(int y = 0; y < bwVectors[x].length; y++)
                {
                    BWVector[] selection;

                    //Upper Left
                    if(x == 0 && y == 0)
                    {
                        selection = new BWVector[4];
                        selection[0] = bwVectors[x][y];
                        selection[1] = bwVectors[x + 1][y];
                        selection[2] = bwVectors[x][y + 1];
                        selection[3] = bwVectors[x + 1][y + 1];
                    }

                    //Upper Right
                    else if(x == 0 && y == SIZE - 1)
                    {
                        selection = new BWVector[4];
                        selection[0] = bwVectors[x][y];
                        selection[1] = bwVectors[x + 1][y];
                        selection[2] = bwVectors[x][y - 1];
                        selection[3] = bwVectors[x + 1][y - 1];
                    }

                    //Lower Left
                    else if(x == SIZE - 1 && y == 0)
                    {
                        selection = new BWVector[4];
                        selection[0] = bwVectors[x][y];
                        selection[1] = bwVectors[x - 1][y];
                        selection[2] = bwVectors[x][y + 1];
                        selection[3] = bwVectors[x - 1][y + 1];
                    }

                    //Lower Right
                    else if(x == SIZE - 1 && y == SIZE - 1)
                    {
                        selection = new BWVector[4];
                        selection[0] = bwVectors[x][y];
                        selection[1] = bwVectors[x - 1][y];
                        selection[2] = bwVectors[x][y - 1];
                        selection[3] = bwVectors[x - 1][y - 1];
                    }

                    //Top
                    else if(x == 0)
                    {
                        selection = new BWVector[6];
                        selection[0] = bwVectors[x][y];
                        selection[1] = bwVectors[x][y - 1];
                        selection[2] = bwVectors[x][y + 1];
                        selection[3] = bwVectors[x + 1][y];
                        selection[4] = bwVectors[x + 1][y - 1];
                        selection[5] = bwVectors[x + 1][y + 1];
                    }

                    //Left
                    else if(y == 0)
                    {
                        selection = new BWVector[6];
                        selection[0] = bwVectors[x][y];
                        selection[1] = bwVectors[x + 1][y];
                        selection[2] = bwVectors[x - 1][y];
                        selection[3] = bwVectors[x][y + 1];
                        selection[4] = bwVectors[x - 1][y + 1];
                        selection[5] = bwVectors[x + 1][y + 1];
                    }

                    //Right
                    else if(y == SIZE - 1)
                    {
                        selection = new BWVector[6];
                        selection[0] = bwVectors[x][y];
                        selection[1] = bwVectors[x + 1][y];
                        selection[2] = bwVectors[x - 1][y];
                        selection[3] = bwVectors[x][y - 1];
                        selection[4] = bwVectors[x - 1][y - 1];
                        selection[5] = bwVectors[x + 1][y - 1];
                    }

                    //Bottom
                    else if(x == SIZE - 1)
                    {
                        selection = new BWVector[6];
                        selection[0] = bwVectors[x][y];
                        selection[1] = bwVectors[x][y - 1];
                        selection[2] = bwVectors[x][y + 1];
                        selection[3] = bwVectors[x - 1][y];
                        selection[4] = bwVectors[x - 1][y - 1];
                        selection[5] = bwVectors[x - 1][y + 1];
                    }

                    //Middle
                    else
                    {
                        selection = new BWVector[9];
                        selection[0] = bwVectors[x][y];
                        selection[1] = bwVectors[x][y - 1];
                        selection[2] = bwVectors[x][y + 1];
                        selection[3] = bwVectors[x - 1][y];
                        selection[4] = bwVectors[x - 1][y - 1];
                        selection[5] = bwVectors[x - 1][y + 1];
                        selection[6] = bwVectors[x + 1][y];
                        selection[7] = bwVectors[x + 1][y - 1];
                        selection[8] = bwVectors[x + 1][y + 1];
                    }

                    float averageBWValue = averageBWColors(selection);
                    for (BWVector vec : selection) 
                    {
                        float newValue = (vec.getValue() + averageBWValue) / 2;
                        vec.setValue(newValue);
                    }
                }
            }
        }
    }
    
    public static float averageBWColors(BWVector[] bwVectors)
    {
        float sum = 0;
        float divisor = 0;
        
        for(BWVector vec : bwVectors)
        {
            sum += vec.getValue() * vec.getStrength();
            divisor += 1 * vec.getStrength();
        }
        
        return sum / divisor;
    }
}
