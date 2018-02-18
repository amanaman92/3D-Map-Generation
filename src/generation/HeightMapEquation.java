package generation;

import com.jme3.math.FastMath;

/**
 * A MapEquation represents a set of points that can be drawn
 *      on the Height / Alpha Map. For any input x, it returns the 
 *      corresponding output.
 * @author jeffr
 */
public class HeightMapEquation extends HeightLine
{
    private final FallOffMode FALL_OFF;
    private final float VALUE_SLOPE,
            VALUE_Y_INTERCEPT,
            TOLERANCE = .001f,
            FALL_OFF_SCALAR = 100;
    private final int VALUE_MIN_X,
            RADIUS = 20;
    
    /**
     * FallOffMode is defualted to LINEAR.
     * @param vec1 The BWVector at one end of the line
     * @param vec2 The BWVector at the other end of the line
     */
    public HeightMapEquation(HeightVector vec1, HeightVector vec2)
    {
        this(vec1, vec2, FallOffMode.SQUARE_ROOT);
    }
    
    /**
     * @param vec1 The BWVector at one end of the line
     * @param vec2 The BWVector at the other end of the line
     * @param fallOff The method used to calculate the value
     *      of BW colors near this equattion.
     */
    public HeightMapEquation(HeightVector vec1, HeightVector vec2, FallOffMode fallOff)
    {
        super(vec1, vec2);
        FALL_OFF = fallOff;
        
        VALUE_SLOPE = (END.getHeight() - START.getHeight()) / (END.getX() - START.getX());
        VALUE_Y_INTERCEPT = START.getHeight() - (SLOPE * END.getX());
        VALUE_MIN_X = START.getHeight() < END.getHeight() ? START.getX() : END.getX();
        
        //Local Region
        float radius = RADIUS / FALL_OFF_SCALAR;
        int width = (int) ((MAX_X - MIN_X) + radius);
        int height = (int) ((MAX_Y - MIN_Y) + radius);
    }
    
    /**
     * Calculates the height at a point on this
     *      MapEquation.
     * @param x The x-coordiante whose height is desired
     * @return The height corresponding to this point on the
     *      MapEquation.
     */
    public float heightAt(int x)
    {
        return (VALUE_SLOPE * x) + VALUE_Y_INTERCEPT;
    }
    
    /**
     * Calculates the height at a point near, but not on,
     *      this MapEquation.
     * @param point The point whose height is desired
     * @return The height corresponding to this point, using
     *      the MapEquation's FallOffMode to determine how the
     *      value is calculated.
     */
    public float heightAt(HeightVector point)
    { 
        float distance = calcualteDistance(point);
        if(distance < TOLERANCE)
        {
            return heightAt(point.getX());
        }
        
        if(distance > RADIUS)
        {
            //return point.getHeight();
        }
        
        float baseHeight = point.getHeight();
        float maxHeight = point.getHeight() + heightAt(calculateNearestXOnLine(point));
        float height = 0;
        switch(FALL_OFF)
        {
            /*case SQUARE:
                return FALL_OFF_SCALAR / (distance * distance);
            case SQUARE_ROOT:
                return FALL_OFF_SCALAR / (FastMath.sqrt(distance));
            case LINEAR:
            default:
                return FALL_OFF_SCALAR / distance; //was *
            */
            case SQUARE:
                height = baseHeight - ((distance * distance) / FALL_OFF_SCALAR);
                break;
            case SQUARE_ROOT:
                height = baseHeight - (FastMath.sqrt(distance) / FALL_OFF_SCALAR);
                break;
            case LINEAR:
            default:
                height = baseHeight - (distance / FALL_OFF_SCALAR);
                break;
        }
        return height < baseHeight ? baseHeight : (height > maxHeight ? maxHeight : height);
    }
    
    /**
     * @param point A MapVector point not on the equation
     * @return The x-value on the equation at the point
     *      closest to the given point.
     */
    private int calculateNearestXOnLine(MapVector point)
    {
        short x = point.getX();
        short y = point.getY();
        
        if(x < MIN_X)
        {
            return MIN_X;
        }
        else if(x > MAX_X)
        {
            return MAX_X;
        }
        
        float shortestLineSlope = (-1 / SLOPE);
        float shortestLineYIntercept = y - (shortestLineSlope * x);
        short xIntersection = (short) ((Y_INTERCEPT - shortestLineYIntercept) / (shortestLineSlope - SLOPE));
        MapVector intersectionPoint = new MapVector(xIntersection, (short) pointAt(xIntersection));
        return intersectionPoint.getX();
    }
    
    /**
     * @param point A MapVector point not on the equation
     * @return The point on the equation nearest this point
     */
    private MapVector calculateNearestPointOnLine(MapVector point)
    {
        short x = point.getX();
        short y = point.getY();
        
        if(x < MIN_X)
        {
            return START;
        }
        else if(x > MAX_X)
        {
            return END;
        }
        
        float shortestLineSlope = (-1 / SLOPE);
        float shortestLineYIntercept = y - (shortestLineSlope * x);
        short xIntersection = (short) ((Y_INTERCEPT - shortestLineYIntercept) / (shortestLineSlope - SLOPE));
        MapVector intersectionPoint = new MapVector(xIntersection, (short) pointAt(xIntersection));
        return intersectionPoint;
    }
    
    /**
     * Calculates the shortest ditance between this equation and
     *      a given point.
     * @param point The point whose distance from thie equation
     *      is wanted
     * @return The distance between the point and this equation 
     */
    public float calcualteDistance(MapVector point)
    {
        return VectorMath.distance(point, calculateNearestPointOnLine(point));
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
    
    /**
     * @return The slope of this MapEquation 
     */
    public float getSlope()
    {
        return SLOPE;
    }
    
    /**
     * @return The y-intercept of this MapEquation 
     */
    public float getYIntercept()
    {
        return Y_INTERCEPT;
    }
    
    /**
     * @return The fallOff mode associated with this
     *      MapEqaution.
     */
    public FallOffMode getFallOff()
    {
        return FALL_OFF;
    }
}