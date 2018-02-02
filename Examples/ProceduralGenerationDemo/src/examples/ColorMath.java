package examples;

import java.awt.Color;
import java.util.ArrayList;

/**
 * This utilities class handles color-related computation
 * @author jeffr
 */
public class ColorMath
{   
    //Methods
    /**
     * @param c A color
     * @param frac The scalar by which to scale the values of c
     * @return The color whose r,g,b have been multiplied by frac (values never exceed 255)
     */
    public static Color multiply(Color c, float frac)
    {
        int red = (int) (c.getRed() * frac);
        int green = (int) (c.getGreen() * frac);
        int blue = (int) (c.getBlue() * frac);
        red = red > 255 ? 255 : red;
        green = green > 255 ? 255 : green;
        blue = blue > 255 ? 255 : blue;
        return new Color(red, green, blue);
    }
    
    /**
     * @param c a Color
     * @param co the Color to subtract from c using Vector subtraction
     * @return The vector difference between the two colors
     */
    public static Color subtract(Color c, Color co)
    {
        int red = c.getRed() - co.getRed();
        int green = c.getGreen() - co.getGreen();
        int blue = c.getBlue() - co.getBlue();
        red = red > 255 ? 255 : red;
        green = green > 255 ? 255 : green;
        blue = blue > 255 ? 255 : blue;
        return new Color(red, green, blue);
    }
    
    /**
     * @param c a Color
     * @param co the Color to add to c using Vector addition
     * @return The vector sum between the two colors
     */
    public static Color add(Color c, Color co)
    {
        int red = c.getRed() + co.getRed();
        int green = c.getGreen() + co.getGreen();
        int blue = c.getBlue() + co.getBlue();
        red = red > 255 ? 255 : red;
        green = green > 255 ? 255 : green;
        blue = blue > 255 ? 255 : blue;
        return new Color(red, green, blue);
    }
    
    /**
     * @param c a color
     * @param co the color to average with c
     * @return the color created by averagin each of the r,g,b elements of c and co
     */
    public static Color average(Color c, Color co)
    {
        return multiply(add(c, co), .5f);
    }
    
    public static float average(float c, float co)
    {
        return (c + co) / 2;
    }
    
    /**
     * @param red the R value for an RGB color
     * @param green the G value for an RGB color
     * @param blue the B value for an RGB color
     * @return A color with these bounds. If any bound exceeds 255 of is below 0, it is approximated to the respective value.
     */
    public static Color safeColor(float red, float green, float blue)
    {
        int r = (int) (red > 255 ? 255 : red < 0 ? 0 : red);
        int g = (int) (green > 255 ? 255 : green < 0 ? 0 : green);
        int b = (int) (blue > 255 ? 255 : blue < 0 ? 0 : blue);
        return new Color(r, g, b);
    }
    
    /**
     * @param c value the value of each r,g,b parameters of Color
     * @return a grayscale color with r,g,b, set to the given value
     */
    public static Color colorBW(float c)
    {
        int co = (int) (c > 255 ? 255 : c < 0 ? 0 : c);
        return new Color(co, co, co);
    }
    
    /** 
     * @param c an array of Colors in RGB format
     * @param capAt255 whether the returned values should be capped at 255, or allowed to exceed this value.
     * @return a float[] of length 3 resulting form the sum (bt vecotr addition) of the rgb colors given
     */
    public static float[] addAll(Color[] c, boolean capAt255)
    {
        int red = 0;
        for(Color c1 : c)
        {
            red += c1.getRed();
        }
        int green = 0;
        for(Color c1 : c)
        {
            green += c1.getGreen();
        }
        int blue = 0;
        for(Color c1 : c)
        {
            blue += c1.getBlue();
        }
        
        if(capAt255)
        {
            red = red > 255 ? 255 : red;
            green = green > 255 ? 255 : green;
            blue = blue > 255 ? 255 : blue;
        }
        float[] color = {red, green, blue};
        return color;
    }
    
    /**
     * @param c a float array of length 3 representing a color in RGB form
     * @param capAt255 Whether the sum should be capped at 255
     * @return A float that is the sum of each of the three elements in c
     */
    public static float addAll(float[] c, boolean capAt255)
    {
        int sum = 0;
        for(int i = 0; i < c.length; i++)
        {
            sum += c[i];
        }
        
        if(capAt255)
        {
            sum = sum > 255 ? 255 : sum;
        }
        return sum;
    }
    
    /**
     * @param c a color array of RGB colorss
     * @return A color that is the average of all colors in c
     */
    public static Color averageAll(Color[] c)
    {
        float[] colorArray = addAll(c, false);
        for(int i = 0; i < colorArray.length; i++)
        {
            colorArray[i] /= (float) c.length;
        }
        int red = (int) colorArray[0];
        int green = (int) colorArray[1];
        int blue = (int) colorArray[2];
        
        red = red > 255 ? 255 : red;
        green = green > 255 ? 255 : green;
        blue = blue > 255 ? 255 : blue;
        
        return new Color(red, green, blue);
    }
    
    /**
     * @param c a float array of length 3 representing a color in RGB form
     * @return A float whose value is the average of all values in c
     */
    public static float averageAll(float[] c)
    {
        float color = addAll(c, false);
        color /= (float) c.length;
        return color;
    }
    
    /**
     * 
     * @param c the Color to invert
     * @return A color whose values are opposite of those in c (so white become black, etc.)
     */
    public static Color invert(Color c)
    {
        int red = 255 - c.getRed();
        int green = 255 - c.getGreen();
        int blue = 255 - c.getBlue();
        
        return new Color(red, green, blue);
    }
    
    /**
     * @param c The float to invert
     * @return A float equalt to 255 - c
     */
    public static float invert(float c)
    {
        return 255 - c;
    }
    
    /**
     * Blurs sharp lines in a ColorVector array (a proto-image)
     * @param SMOOTHING_FACTOR The number of times to run the algorithm
     * @param vectors The ColorVector array to smooth / blur
     */
    public static void smooth(final float SMOOTHING_FACTOR, ColorVector[][] vectors)
    {
        int SIZE = vectors.length;
        for(int a = 0; a < SMOOTHING_FACTOR; a++)
        {
            for(int i = 0; i < SIZE; i++)
            {
                for(int j = 0; j < SIZE; j++)
                {
                    //Get average of all nearby (one box layer) pixels
                    ColorVector[] c;
                    if(i == 0 && j == 0) //upper left
                    {
                        c = new ColorVector[4];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i][j + 1];
                        c[3] = vectors[i + 1][j + 1];
                    }
                    else if(i == SIZE - 1 && j == SIZE - 1) //bottom right
                    {
                        c = new ColorVector[4];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i - 1][j];
                        c[2] = vectors[i][j - 1];
                        c[3] = vectors[i - 1][j - 1];
                    }
                    else if(i == 0 && j == SIZE - 1) //upper right
                    {
                        c = new ColorVector[4];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i][j - 1];
                        c[3] = vectors[i + 1][j - 1];
                    }
                    else if(i == SIZE - 1 && j == 0) //upper left
                    {
                        c = new ColorVector[4];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i - 1][j];
                        c[2] = vectors[i][j + 1];
                        c[3] = vectors[i - 1][j + 1];
                    }
                    else if(i == 0)
                    {
                        c = new ColorVector[6];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i + 1][j + 1];
                        c[3] = vectors[i + 1][j - 1];
                        c[4] = vectors[i][j + 1];
                        c[5] = vectors[i][j - 1];
                    }
                    else if(i == SIZE - 1)
                    {
                        c = new ColorVector[6];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i - 1][j];
                        c[2] = vectors[i - 1][j + 1];
                        c[3] = vectors[i - 1][j - 1];
                        c[4] = vectors[i][j + 1];
                        c[5] = vectors[i][j - 1];
                    }
                    else if(j == 0)
                    {
                        c = new ColorVector[6];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i + 1][j + 1];
                        c[3] = vectors[i - 1][j + 1];
                        c[4] = vectors[i - 1][j];
                        c[5] = vectors[i][j + 1];
                    }
                    else if(j == SIZE - 1)
                    {
                        c = new ColorVector[6];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i + 1][j - 1];
                        c[3] = vectors[i - 1][j - 1];
                        c[4] = vectors[i - 1][j];
                        c[5] = vectors[i][j - 1];
                    }
                    else
                    {
                        c = new ColorVector[9];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i + 1][j + 1];
                        c[3] = vectors[i + 1][j - 1];
                        c[4] = vectors[i - 1][j];
                        c[5] = vectors[i - 1][j + 1];
                        c[6] = vectors[i - 1][j - 1];
                        c[7] = vectors[i][j - 1];
                        c[8] = vectors[i][j + 1];
                    }
                    Color[] colors = new Color[c.length];
                    for(int k = 0; k < c.length; k++)
                    {
                        colors[k] = c[k].c();
                    }
                    Color avg = averageAll(colors);
                    for(int k = 0; k < c.length; k++)
                    {
                        colors[k] = average(colors[k], avg);
                    }
                    for(int k = 0; k < c.length; k++)
                    {
                        c[k].setColor(colors[k]);
                    }
                }
            }
        }
    }
    
    /**
     * Blurs sharp lines in a 2D float array representing a set of RGB colors(a proto-image)
     * @param SMOOTHING_FACTOR The number of times to run the algorithm
     * @param vectors The float[][] to smooth / blur
     */
    public static void smooth(final float SMOOTHING_FACTOR, float[][] vectors)
    {
        int SIZE = vectors.length;
        for(int a = 0; a < SMOOTHING_FACTOR; a++)
        {
            for(int i = 0; i < SIZE; i++)
            {
                for(int j = 0; j < SIZE; j++)
                {
                    //Get average of all nearby (one box layer) pixels
                    float[] c;
                    if(i == 0 && j == 0) //upper left
                    {
                        c = new float[4];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i][j + 1];
                        c[3] = vectors[i + 1][j + 1];
                    }
                    else if(i == SIZE - 1 && j == SIZE - 1) //bottom right
                    {
                        c = new float[4];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i - 1][j];
                        c[2] = vectors[i][j - 1];
                        c[3] = vectors[i - 1][j - 1];
                    }
                    else if(i == 0 && j == SIZE - 1) //upper right
                    {
                        c = new float[4];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i][j - 1];
                        c[3] = vectors[i + 1][j - 1];
                    }
                    else if(i == SIZE - 1 && j == 0) //upper left
                    {
                        c = new float[4];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i - 1][j];
                        c[2] = vectors[i][j + 1];
                        c[3] = vectors[i - 1][j + 1];
                    }
                    else if(i == 0)
                    {
                        c = new float[6];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i + 1][j + 1];
                        c[3] = vectors[i + 1][j - 1];
                        c[4] = vectors[i][j + 1];
                        c[5] = vectors[i][j - 1];
                    }
                    else if(i == SIZE - 1)
                    {
                        c = new float[6];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i - 1][j];
                        c[2] = vectors[i - 1][j + 1];
                        c[3] = vectors[i - 1][j - 1];
                        c[4] = vectors[i][j + 1];
                        c[5] = vectors[i][j - 1];
                    }
                    else if(j == 0)
                    {
                        c = new float[6];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i + 1][j + 1];
                        c[3] = vectors[i - 1][j + 1];
                        c[4] = vectors[i - 1][j];
                        c[5] = vectors[i][j + 1];
                    }
                    else if(j == SIZE - 1)
                    {
                        c = new float[6];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i + 1][j - 1];
                        c[3] = vectors[i - 1][j - 1];
                        c[4] = vectors[i - 1][j];
                        c[5] = vectors[i][j - 1];
                    }
                    else
                    {
                        c = new float[9];
                        c[0] = vectors[i][j];
                        c[1] = vectors[i + 1][j];
                        c[2] = vectors[i + 1][j + 1];
                        c[3] = vectors[i + 1][j - 1];
                        c[4] = vectors[i - 1][j];
                        c[5] = vectors[i - 1][j + 1];
                        c[6] = vectors[i - 1][j - 1];
                        c[7] = vectors[i][j - 1];
                        c[8] = vectors[i][j + 1];
                    }
                    float[] colors = new float[c.length];
                    System.arraycopy(c, 0, colors, 0, c.length);
                    float avg = averageAll(colors);
                    for(int k = 0; k < c.length; k++)
                    {
                        colors[k] = average(colors[k], avg);
                    }
                    System.arraycopy(colors, 0, c, 0, c.length);
                }
            }
        }
    }
    
    /**
     * Blurs sharp lines in a ColorVector array representing a proto-image
     * @param recur the number of times to run the algorithm
     * @param vectors the ColorVecto[][] array to smooth
     * @param x the x-coordiante of the pixel that is the centerpoint of smoothing
     * @param y the y-coordiante of the pixel that is the centerpoint of smoothing
     */
    public static void smoothAbout(int recur, ColorVector[][] vectors, int x, int y)
    {
        if(recur == 0) return;
        int SIZE = vectors.length;
        ColorVector[] c;
        
        //Get average of all nearby (one box layer) pixels
        if(x == 0 && y == 0) //upper left
        {
            c = new ColorVector[4];
            c[0] = vectors[x][y];
            c[1] = vectors[x + 1][y];
            c[2] = vectors[x][y + 1];
            c[3] = vectors[x + 1][y + 1];
        }
        else if(x == SIZE - 1 && y == SIZE - 1) //bottom right
        {
            c = new ColorVector[4];
            c[0] = vectors[x][y];
            c[1] = vectors[x - 1][y];
            c[2] = vectors[x][y - 1];
            c[3] = vectors[x - 1][y - 1];
        }
        else if(x == 0 && y == SIZE - 1) //upper right
        {
            c = new ColorVector[4];
            c[0] = vectors[x][y];
            c[1] = vectors[x + 1][y];
            c[2] = vectors[x][y - 1];
            c[3] = vectors[x + 1][y - 1];
        }
        else if(x == SIZE - 1 && y == 0) //upper left
        {
            c = new ColorVector[4];
            c[0] = vectors[x][y];
            c[1] = vectors[x - 1][y];
            c[2] = vectors[x][y + 1];
            c[3] = vectors[x - 1][y + 1];
        }
        else if(x == 0) //side
        {
            c = new ColorVector[6];
            c[0] = vectors[x][y];
            c[1] = vectors[x + 1][y];
            c[2] = vectors[x + 1][y + 1];
            c[3] = vectors[x + 1][y - 1];
            c[4] = vectors[x][y + 1];
            c[5] = vectors[x][y - 1];
        }
        else if(x == SIZE - 1) //side
        {
            c = new ColorVector[6];
            c[0] = vectors[x][y];
            c[1] = vectors[x - 1][y];
            c[2] = vectors[x - 1][y + 1];
            c[3] = vectors[x - 1][y - 1];
            c[4] = vectors[x][y + 1];
            c[5] = vectors[x][y - 1];
        }
        else if(y == 0) //side
        {
            c = new ColorVector[6];
            c[0] = vectors[x][y];
            c[1] = vectors[x + 1][y];
            c[2] = vectors[x + 1][y + 1];
            c[3] = vectors[x - 1][y + 1];
            c[4] = vectors[x - 1][y];
            c[5] = vectors[x][y + 1];
        }
        else if(y == SIZE - 1) //side
        {
            c = new ColorVector[6];
            c[0] = vectors[x][y];
            c[1] = vectors[x + 1][y];
            c[2] = vectors[x + 1][y - 1];
            c[3] = vectors[x - 1][y - 1];
            c[4] = vectors[x - 1][y];
            c[5] = vectors[x][y - 1];
        }
        else //middle
        {
            c = new ColorVector[9];
            c[0] = vectors[x][y];
            c[1] = vectors[x + 1][y];
            c[2] = vectors[x + 1][y + 1];
            c[3] = vectors[x + 1][y - 1];
            c[4] = vectors[x - 1][y];
            c[5] = vectors[x - 1][y + 1];
            c[6] = vectors[x - 1][y - 1];
            c[7] = vectors[x][y - 1];
            c[8] = vectors[x][y + 1];
        }
        Color[] colors = new Color[c.length];
        for(int k = 0; k < c.length; k++)
        {
            colors[k] = c[k].c();
        }
        Color avg = averageAll(colors);
        for(int k = 0; k < c.length; k++)
        {
            colors[k] = average(colors[k], avg);
        }
        for(int k = 0; k < c.length; k++)
        {
            c[k].setColor(colors[k]);
        }
        for(ColorVector co : c)
        {
            smoothAbout(recur - 1, vectors, co.x(), co.y());
        }
    }
    
    /**
     * Reduces sharp edges in a  ColorVector[][] array (proto-image)
     * @param vectors A ColorVector[][] array whose elements are are BW Colors
     * @param difference The value above which adjacent colors will be blended to reducce sharp edges
     */
    public static void smoothEdges(ColorVector[][] vectors, int difference)
    {
        int SIZE = vectors.length;
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                if(i == 0 && j == 0) //upper left
                {
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j].setColor(colorBW(average(vectors[i + 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j + 1].setColor(colorBW(average(vectors[i][j + 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j + 1].setColor(colorBW(average(vectors[i + 1][j + 1].c().getBlue(), avg)));
                    }
                }
                else if(i == SIZE - 1 && j == SIZE - 1) //bottom right
                {
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j].setColor(colorBW(average(vectors[i - 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j - 1].setColor(colorBW(average(vectors[i][j - 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j - 1].setColor(colorBW(average(vectors[i - 1][j - 1].c().getBlue(), avg)));
                    }
                }
                else if(i == 0 && j == SIZE - 1) //upper left
                {
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j].setColor(colorBW(average(vectors[i + 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j - 1].setColor(colorBW(average(vectors[i][j - 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j - 1].setColor(colorBW(average(vectors[i + 1][j - 1].c().getBlue(), avg)));
                    }
                }
                else if(i == SIZE - 1 && j == 0) //upper right
                {                    
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j].setColor(colorBW(average(vectors[i - 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j + 1].setColor(colorBW(average(vectors[i][j + 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j + 1].setColor(colorBW(average(vectors[i - 1][j + 1].c().getBlue(), avg)));
                    }
                }
                else if(i == 0) //left
                {
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j].setColor(colorBW(average(vectors[i + 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j + 1].setColor(colorBW(average(vectors[i + 1][j + 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j - 1].setColor(colorBW(average(vectors[i + 1][j - 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j + 1].setColor(colorBW(average(vectors[i][j + 1].c().getBlue(), avg)));
                    }              
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j - 1].setColor(colorBW(average(vectors[i][j - 1].c().getBlue(), avg)));
                    }
                }
                else if(i == SIZE - 1) //right
                {                    
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j].setColor(colorBW(average(vectors[i - 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j + 1].setColor(colorBW(average(vectors[i - 1][j + 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j - 1].setColor(colorBW(average(vectors[i - 1][j - 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j + 1].setColor(colorBW(average(vectors[i][j + 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j - 1].setColor(colorBW(average(vectors[i][j - 1].c().getBlue(), avg)));
                    }
                }
                else if(j == 0) //top
                {
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j].setColor(colorBW(average(vectors[i + 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j + 1].setColor(colorBW(average(vectors[i - 1][j + 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j].setColor(colorBW(average(vectors[i - 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j + 1].setColor(colorBW(average(vectors[i][j + 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j + 1].setColor(colorBW(average(vectors[i + 1][j + 1].c().getBlue(), avg)));
                    }
                }
                else if(j == SIZE - 1) //bottom
                {
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j].setColor(colorBW(average(vectors[i + 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j - 1].setColor(colorBW(average(vectors[i + 1][j - 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j - 1].setColor(colorBW(average(vectors[i - 1][j - 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j].setColor(colorBW(average(vectors[i - 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j - 1].setColor(colorBW(average(vectors[i][j - 1].c().getBlue(), avg)));
                    }
                }
                else //middle
                {
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j].setColor(colorBW(average(vectors[i + 1][j].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j + 1].setColor(colorBW(average(vectors[i + 1][j + 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i + 1][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i + 1][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i + 1][j - 1].setColor(colorBW(average(vectors[i + 1][j - 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j].setColor(colorBW(average(vectors[i - 1][j].c().getBlue(), avg)));
                    }                    
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j + 1].setColor(colorBW(average(vectors[i - 1][j + 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i - 1][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i - 1][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i - 1][j - 1].setColor(colorBW(average(vectors[i - 1][j - 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j - 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j - 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j - 1].setColor(colorBW(average(vectors[i][j - 1].c().getBlue(), avg)));
                    }
                    if(Math.abs(vectors[i][j].c().getBlue() - vectors[i][j + 1].c().getBlue()) > difference)
                    {
                        float avg = average(vectors[i][j].c().getBlue(), vectors[i][j + 1].c().getBlue());
                        vectors[i][j].setColor(colorBW(average(vectors[i][j].c().getBlue(), avg)));
                        vectors[i][j + 1].setColor(colorBW(average(vectors[i][j + 1].c().getBlue(), avg)));
                    }
                }
            }
        }
    }
    
    /**
     * Converts ColorVector[][] to an ArrayList of Integers for use in the Statistics utilities class
     * @param vectors A 2D ColorVector Array, whose components are all BW colors
     * @return A 1D Integer ArrayList with length equal to the area of vecors, whose values are the values of the ColorVectror Colors.
     */
    public static ArrayList<Integer> asIntegerList(ColorVector[][] vectors)
    {
        ArrayList<Integer> bw = new ArrayList<>();
        for(ColorVector[] vector : vectors)
        {
            for(ColorVector vec : vector)
            {
                bw.add(vec.c().getBlue());
            }
        }
        return bw;
    }
    
    /**
     * Converts float[][] representing colors to an ArrayList of Integers for use in the Statistics utilities class
     * @param vectors A 2D float Array, whose components all represent BW colors
     * @return A 1D Integer ArrayList with length equal to the area of vecors, whose values are the values of the float[][] vectors.
     */
    public static ArrayList<Integer> asIntegerList(float[][] vectors)
    {
        ArrayList<Integer> bw = new ArrayList<>();
        for(float[] col : vectors)
        {
            for(float c : col)
            {
                bw.add((int) c);
            }
        }
        return bw;
    }
}