package generation;

/**
 * This class serves as a template for mapping points in 2-space
 *      to data values to be assigned in sub-classes.
 * @author jeffr
 */
public abstract class MapVector 
{
    private float strength = 1;
    
    /**
     * Strength defaults to 1
     */
    public MapVector(){}
    
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
}