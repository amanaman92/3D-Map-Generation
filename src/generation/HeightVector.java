package generation;

/**
 * This class allows the user to track black and white 
 *      colors on a procedural image (HeightMap).
 * @author jeffr
 */
public class HeightVector extends MapVector
{
    private float height;
    private short requests, requestedHeightSum;

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
        setHeightRequest(height);
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
     * @param height The new height value of this vector
     */
    public void setHeight(float height)
    {
        this.height = height;
    }
    
    /**
     * Collects all requested heights and into one sum. This sum will be
     *      used to compute the average of all heights requested and return
     *      the final height decision at the end.
     * @param height The requested height value of this vector
     */
    public final void setHeightRequest(float height)
    {
        requestedHeightSum += height;
        requests++;
    }
    
    /**
     * Only call this method when all height requests are done. This determines
     *      the averrage of all height requests and the initial height. This
     *      local height variable is set to this value.
     */
    public void resolveHeight()
    {
        height = requestedHeightSum / requests;
    }
    
    /**
     * @return The height value on [0,1] of this Vector
     */
    public float getHeight()
    {
        return height;
    }
}