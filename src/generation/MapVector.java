package generation;

/**
 * This class serves as a template for mapping points in 2-space
 *      to data values to be assigned in sub-classes. All data are
 *      represented using lowest-possible memory types, since
 *      MANY (millions) of MapVectors will be created.
 * @author jeffr
 */
public class MapVector 
{
    private final short X, Y;
    private float strength = 1;
    
    /**
     * Strength defaults to 1
     * @param x The x-Coordinate of this MapVector
     * @param y The y-Coordinate of this MapVector
     */
    public MapVector(short x, short y)
    {
        X = x;
        Y = y;
    }
    
    /**
     * @return The strenght of this Vector over others 
     */
    public float getStrength()
    {
        return strength;
    }
    /**
     * @param strength The new strength value of this Vector.
     */
    public void setStrength(float strength)
    {
        this.strength = strength;
    }
    
    public short getX()
    {
        return X;
    }
    
    public short getY()
    {
        return Y;
    }
}