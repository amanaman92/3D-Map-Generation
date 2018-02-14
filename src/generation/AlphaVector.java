package generation;

import java.awt.Color;

/**
 * This class allows the user to track RGB colors
 *      colors on a procedural image (AlphaMap)
 * @author jeffr
 */
public class AlphaVector extends MapVector
{
    private int colorRGB;
    
    /**
     * @param color The Color assigned to these coordinates
     */
    public AlphaVector(Color color)
    {
        this(color.getRGB());
    }
    
    /**
     * @param colorRGB The color assigned to this Vector,
     *      represented as an RGB int
     */
    public AlphaVector(int colorRGB)
    {
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