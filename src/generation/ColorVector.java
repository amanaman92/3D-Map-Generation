package generation;

import java.awt.Color;

/**
 * This class allows the user to track RGB colors
 *      colors on a procedural image (AlphaMap)
 * @author jeffr
 */
public class ColorVector extends MapVector
{
    private Color color;
    
    /**
     * @param x The x-coordinate for MapVector
     * @param y The y-coordinate for MapVector
     * @param color The color assigned to these coordinates
     */
    public ColorVector(int x, int y, Color color)
    {
        super(x, y);
        this.color = color;
    }
    
    /**
     * @return The color of this vector 
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
     * @param color The new color of this vector 
     */
    public void setColor(Color color)
    {
        this.color = color;
    }
}