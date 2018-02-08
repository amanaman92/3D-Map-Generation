package generation;

import com.jme3.math.FastMath;

/**
 * A MapEquation represents a set of points that can be drawn
 *      on the Height / Alpha Map. For any input x, it returns the 
 *      corresponding output.
 * @author jeffr
 */
public class MapEquation 
{
    private final FallOffMode FALL_OFF;
    private final BWVector START,
            END;
    private final float SLOPE,
            Y_INTERCEPT,
            VALUE_SLOPE,
            VALUE_Y_INTERCEPT;
    private final int MIN_X,
            MAX_X,
            VALUE_MIN_X;
    
    /**
     * FallOffMode is defualted to LINEAR.
     * @param start The BWVector at one end of the line
     * @param end The BWVector at the other end of the line
     */
    public MapEquation(BWVector start, BWVector end)
    {
        this(start, end, FallOffMode.LINEAR);
    }
    
    /**
     * @param start The BWVector at one end of the line
     * @param end The BWVector at the other end of the line
     * @param fallOff The method used to calculate the value
     *      of BW colors near this equattion.
     */
    public MapEquation(BWVector start, BWVector end, FallOffMode fallOff)
    {
        START = start;
        END = end;
        FALL_OFF = fallOff;
        
        SLOPE = (END.getY() - START.getY()) / (END.getX() - START.getX());
        Y_INTERCEPT = START.getY() - (SLOPE * END.getX());
        MIN_X = START.getX() < END.getX() ? START.getX() : END.getX();
        MAX_X = START.getX() > END.getX() ? START.getX() : END.getX();
        
        VALUE_SLOPE = (END.getValue() - START.getValue()) / (END.getX() - START.getX());
        VALUE_Y_INTERCEPT = START.getValue() - (SLOPE * END.getX());
        VALUE_MIN_X = START.getX()< END.getX() ? START.getX() : END.getX();
    }
        
    /**
     * @param x The x-coordiate
     * @return The y-coordinate corresponding to this x
     */
    public int pointAt(float x)
    {
        return (int) ((x * SLOPE) + Y_INTERCEPT);
    }
    
    /**
     * @param x The x-coordiate
     * @return TheBW color value corresponding to this x, using
     *      the MapEquation's FallOffMode to determine how the
     *      value is calculated.
     */
    public float valueAt(float x)
    {
        float linearFallOffValue = (x * VALUE_SLOPE) + VALUE_Y_INTERCEPT;
        float relativeX = x - VALUE_MIN_X;
        switch(FALL_OFF)
        {
            case LINEAR:
                return linearFallOffValue;
            case SQUARE:
                return linearFallOffValue / (relativeX * relativeX);
            case SQUARE_ROOT:
                return linearFallOffValue / (FastMath.sqrt(relativeX));
            case HYPERBOLIC:
                return linearFallOffValue / relativeX;
            case EXPONENTIAL:
                return linearFallOffValue / FastMath.pow(2, relativeX);
            default:
                return Float.NaN;
        }
    }
    
    /**
     * @return The lowest x value on the domain of the
     *      point function.
     */
    public int getMinX()
    {
        return MIN_X;
    }
    
    /**
     * @return The highest x value on the domain of the
     *      point function.
     */
    public int getMaxX()
    {
        return MAX_X;
    }
}