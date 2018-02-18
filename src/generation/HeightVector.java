package generation;

/**
 * This class allows the user to track black and white 
 *      colors on a procedural image (HeightMap).
 * @author jeffr
 */
public class HeightVector extends MapVector
{
    private float height;

    /**
     * @param x The x-Coordinate of this HeightVector
     * @param y The y-Coordinate of this HeightVector
     * @param height The height of the map at this point x,y on a scale
     *      of [0, 1] 
     */
    public HeightVector(short x, short y, float height)
    {
        super(x, y);
        this.height = height;
    }
    
    /**
     * @param x The x-Coordinate of this HeightVector
     * @param y The y-Coordinate of this HeightVector
     * @param height The height of the map at this point x,y on a scale
     *      of [0, 1] 
     * @param strength How much the height value at this Vector resists 
     *      change when averaged or combined with nearby Vectors.
     */
    public HeightVector(short x, short y, float height, float strength)
    {
        this(x, y, height);
        super.setStrength(strength);
    }
    
    
    /**
     * @param height The new color value of this vector
     */
    public void setHeight(float height)
    {
        this.height = height;
    }
    
    /**
     * @return The height value on [0,1] of this Vector
     */
    public float getHeight()
    {
        return height;
    }
}