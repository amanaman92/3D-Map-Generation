package generation;

import com.jme3.math.FastMath;

/**
 * This class serves to provide utilites for dealing
 *      with vector math.
 * @author jeffr
 */
public class VectorMath 
{
    private static final float TOLERANCE = .0001f;
    
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
    
    public static float distance(MapVector vec, HeightMapEquation eq)
    {
        float shortestLineSlope = -1 / eq.getSlope();
        float shortestLineYIntercept = vec.getY() - (vec.getX() * shortestLineSlope);
        
        int x_intersectction = interesction(shortestLineSlope, eq.getSlope(), 
                shortestLineYIntercept, eq.getYIntercept());
        int y_intersectction = eq.pointAt(x_intersectction);
        MapVector other = new MapVector((short) x_intersectction, (short) y_intersectction);
        return distance(vec, other);
    }
    
    public static int interesction(float m1, float m2, float b1, float b2)
    {
        if(FastMath.abs(m1 - m2) < TOLERANCE)
        {
            throw new IllegalArgumentException("Slopes may not be equal");
        }
        return (int) ((b1 - b2) / (m1 - m2) + .5f);
    }
}
