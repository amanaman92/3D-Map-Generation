package generation;

import com.jme3.math.FastMath;

/**
 * This class serves to provide utilites for dealing
 *      with vector math.
 * @author jeffr
 */
public class VectorMath 
{
    /**
     * Private constructor to prevent instances from 
     *      being created
     */
    private VectorMath(){}
    
    public static float distance(MapVector vec1, MapVector vec2)
    {
        float deltaX = (vec1.getX() - vec2.getX());
        float deltaY = (vec1.getY() - vec2.getY());
        return FastMath.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }
}
