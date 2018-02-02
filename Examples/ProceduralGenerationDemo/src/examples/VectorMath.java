package examples;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.Random;

/**
 * This utilities class serves to allow Vector computations beyond those included in the jme3 libraries.
 *      Also provides support for non-cartesian vectors.
 * @author jeffr
 */
public class VectorMath 
{
    //Instance Variables
    private static final Random GENERATOR = new Random();
    
    //Methods
    /**
     * Creates a Vector3f given a cylyndrical-coordinate vector on the x-z plane at a fixed heifght y
     * @param magnitude the magnitued of the vector
     * @param theta the angle of the vector from the posoitive x-axis
     * @param y the height of the vector
     * @return the cartesian Vector3f identical to the specified cylindrical vector
     */
    public static Vector3f vec(float magnitude, float theta, float y)
    {
        float x = magnitude * (float) Math.sin(theta);
        float z = magnitude * (float) Math.cos(theta);
        return new Vector3f(x, y, z);
    }
    
    /**
     * @param vec The Vector3f whose magnitude is wanted
     * @return the magnitude of the vector in 3-space.
     */
    public static float magnitude(Vector3f vec)
    {
        return (float) Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z);
    }
    
    /**
     * @param vec The Vector3f whose angle is wanted. Angles are measured on the x-z plane from te positive x axis (y is ignored)
     * @return The angle on the x-z plane from the positive x-axis of this vector.
     */
    public static float theta(Vector3f vec)
    {
        float theta = 0f;
        if(vec.z > 0)
        {
            theta = (float) Math.atan(vec.x / vec.z);
        }
        else if(vec.z < 0)
        {
            theta = (float) (Math.atan(vec.x / vec.z) + Math.PI);
        }
        return theta;
    }
    
    /**
     * @param xBound The maximum x-value of the vectror (may be negative)
     * @param yBound The maximum y-value of the vectror (may be negative)
     * @return A ranfom Vector3f with x and y coordinates between 0 and the given bounds.
     */
    public static Vector2f randomVector2f(float xBound, float yBound)
    {
        return new Vector2f(GENERATOR.nextFloat() * xBound, GENERATOR.nextFloat() * yBound);
    }
    
    /**
     * @param xMin the lowest acceptable x value
     * @param xMax the highest acceptable x value
     * @param yMin the lowest acceptable y value
     * @param yMax the highest acceptable y value
     * @return A random vector2f within the given bounds
     */
    public static Vector2f randomVector2f(float xMin, float xMax, float yMin, float yMax)
    {
        float x = GENERATOR.nextFloat() * ((xMax - xMin) + 1) + xMin;
        float y = GENERATOR.nextFloat() * ((xMax - xMin) + 1) + xMin;
        return new Vector2f(x, y);
    }
}