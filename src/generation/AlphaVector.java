package generation;

import java.awt.Color;

/**
 * This class allows the user to track RGB colors
 *      colors on a procedural image (AlphaMap)
 * References ucsd:
 *      https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
 * @author jeffr
 */
public class AlphaVector extends MapVector
{
    private int colorRGB;
    
    /**
     * @param x The x-Coordinate of this AlphaVector
     * @param y The y-Coordinate of this AlphaVector
     * @param color The Color assigned to these coordinates
     */
    public AlphaVector(short x, short y, Color color)
    {
        this(x, y, color.getRGB());
    }
    
    /**
     * @param x The x-Coordinate of this AlphaVector
     * @param y The y-Coordinate of this AlphaVector
     * @param colorRGB The color assigned to this Vector,
     *      represented as an RGB int
     */
    public AlphaVector(short x, short y, int colorRGB)
    {
        super(x, y);
        this.colorRGB = colorRGB;
    }
    
    /**
     * @return The color of this vector, represented as an RGB
     *      encoded int
     */
    public int getColorRGB()
    {
        return colorRGB;
    }
    
    /**
     * @return The color of this vector, represented as an RGB
     *      encoded int
     */
    public Color getColor()
    {
        return new Color(colorRGB);
    }
    
    /**
     * @param colorRGB The color assigned to these coordinates,
     *      represented as an RGB int
     */
    public void setColor(int colorRGB)
    {
        this.colorRGB = colorRGB;
    }
    
    /**
     * @param color The new color of this vector 
     */
    public void setColor(Color color)
    {
        colorRGB = color.getRGB();
    }
}