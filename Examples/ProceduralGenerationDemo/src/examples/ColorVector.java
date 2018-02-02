package examples;

import java.awt.Color;
import java.util.ArrayList;

/**
 * This class defines a Vector to represent a point in 2-space.
 *      Each vector has a color, which defines how this point is
 *      represented as a pixel.
 * @author jeffr
 */
public class ColorVector 
{
    //Instance Variables
    private Color c;
    private final int x, y;
    private final ArrayList<Color> REQUESTED_COLORS = new ArrayList<>();
    
    //Contructor
    /**
     * @param c The color for this ColorVector
     * @param x THe x-coordinate of this COlorVector
     * @param y The y-coordinate of this ColorVector
     */
    public ColorVector(Color c, int x, int y)
    {
        this.c = c;
        this.x = x;
        this.y = y;
    }
    
    //Methods
    /**
     * Changes the color of the ColorVector to a BW color.
     * @param val The new color of the ColorVector, on a black-
     *      and-white scale. If an invalid entry (< 0 or > 255)
     *      is given, it will be corrected to 0 or 255, respectively.
     *      For example, if val = 500, the colwo created will have
     *          an RBG code of (255, 255, 255).
     */
    public void modColor(int val)
    {
        int colorNum = c.getBlue() + val;
        colorNum = colorNum < 0 ? 0 : colorNum > 255 ? 255 : colorNum;
        Color co = new Color(colorNum, colorNum, colorNum);
        c = co;
    }
    
    /**
     * Sets the color of this COlorVector to the Color provided.
     * @param c The enw Color
     */
    public void setColor(Color c)
    {
        this.c = c;
    }
    
    /**
     * @return The Color of this ColorVector
     */
    public Color c()
    {
        return c;
    }
    
    /**
     * @return The x-coordinate of this ColorVector
     */
    public int x()
    {
        return x;
    }
    
    /**
     * @return The y-coordinate of this ColorVector
     */
    public int y()
    {
        return y;
    }
}
