package generation;

/**
 * This class represents a general linear equation.
 * @author jeffr
 */
public class HeightLine 
{
    protected final HeightVector START,
            END;
    protected final float SLOPE,
            Y_INTERCEPT;
    protected final int MIN_X,
            MAX_X,
            MIN_Y,
            MAX_Y;
    
    /**
     * Creates a HeightLine from the given tow points
     * @param vec1 One point on the line
     * @param vec2 Another point on the line
     */
    public HeightLine(HeightVector vec1, HeightVector vec2)
    {
        if(vec1.getX() - vec2.getX() == 0)
        {
            throw new IllegalArgumentException("Points must have different"
                    + "X-values!");
        }
        if(vec1.getX() < vec2.getX())
        {
            START = vec1;
            END = vec2;
        }
        else
        {
            START = vec2;
            END = vec1;
        }
        
        SLOPE = (END.getY() - START.getY()) / (END.getX() - START.getX());
        Y_INTERCEPT = START.getY() - (SLOPE * END.getX());
        MIN_X = START.getX();
        MAX_X = END.getX();
        
        if(pointAt(MAX_X) > pointAt(MIN_X))
        {
            MIN_Y = pointAt(MIN_X);
            MAX_Y = pointAt(MAX_X);
        }
        else
        {
            MIN_Y = pointAt(MAX_X);
            MAX_Y = pointAt(MIN_X);
        }
    }
            
    /**
     * @param x The x-coordiate
     * @return The y-coordinate corresponding to this x
     */
    public final int pointAt(float x)
    {
        return (int) ((x * SLOPE) + Y_INTERCEPT);
    }
}