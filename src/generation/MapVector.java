package generation;

/**
 * This class serves as a template for mapping points in 2-space
 *      to data values to be assigned in sub-classes.
 * @author jeffr
 */
public abstract class MapVector 
{
    private final int X, Y;
    private float strength;
    
    /**
     * @param x The x-coordinate. This may not be changed once set.
     * @param y The y-coordinate. This may not be changed once set.
     */
    public MapVector(int x, int y)
    {
        this.X = x;
        this.Y = y;
        strength = 1;
    }
    
    /**
     * @return The x-coordinate of this vector
     */
    public int getX()
    {
        return X;
    }
    
    /**
     * @return The y-coordinate of this vector
     */
    public int getY()
    {
        return Y;
    }
    
    /**
     * @return The strenght of this Vector over others 
     */
    public float getStrength()
    {
        return strength;
    }
    /**
     * @param strength THe new strength value of this Vector.
     */
    public void setStrength(float strength)
    {
        this.strength = strength;
    }
}