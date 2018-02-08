package generation;

/**
 * This class allows the user to track black and white 
 *      colors on a procedural image (HeightMap).
 * Color are BW to increase memory performance.
 * @author jeffr
 */
public class BWVector extends MapVector
{
    private float value;
    private float flag;

    /**
     * @param x The x-coordinate for MapVector
     * @param y The y-coordinate for MapVector
     * @param value The value of the color
     */
    public BWVector(int x, int y, float value)
    {
        super(x, y);
        this.value = value;
    }
    
    /**
     * @param x The x-coordinate. This may not be changed once set.
     * @param y The y-coordinate. This may not be changed once set.
     * @param value The value of the color
     * @param strength How much the BW value at this Vector resists 
     *      change when averaged or combined with nearby Vectors.
     */
    public BWVector(int x, int y, float value, float strength)
    {
        this(x, y, value);
        super.setStrength(strength);
    }
    
    
    /**
     * @param value The new color value of this vector
     */
    public void setValue(float value)
    {
        this.value = value;
    }
    
    /**
     * @return The BW value on [0,1] of this Vector
     */
    public float getValue()
    {
        return value;
    }
}