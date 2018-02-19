package generation;

import com.jme3.math.FastMath;

/**
 * This decribes method used to calculate the value
 *      of BW colors equations (how the values change
 *      as distance increases along the eq.);
 * @author jeffr
 */
public enum FallOffMode 
{

    /**
     * Defines a linear falloff of height (constant / distance)
     */
    LINEAR
    {
        /**
         * Uses a linear algorithm to compute fall-off
         * @param distance The distance from the falloff start point
         *      to the point whose relative height change is wanted.
         * @param fallOffScalar The Scalar used to calculate fall off
         * @return The change in height the fall off starting
         *      point should caused on this point.
         */
        @Override
        public float heightChangeAt(float distance, float fallOffScalar)
        {
            return distance;
            //return fallOffScalar / distance;
        }
    }, 
    
    /** 
     * Defines a square falloff of height (constant / distance^2)
     */
    SQUARE
    {
        /**
         * Uses a square algorithm to compute fall-off
         * @param distance The distance from the falloff start point
         *      to the point whose relative height change is wanted.
         * @param fallOffScalar The Scalar used to calculate fall off
         * @return The change in height the fall off starting
         *      point should caused on this point.
         */
        @Override
        public float heightChangeAt(float distance, float fallOffScalar)
        {
            return 0;
            //return fallOffScalar / (distance * distance);
        }
    }, 
    
    /**
     * Defines a linear falloff of height (constant / distance^.5)
     */
    SQUARE_ROOT
    {
        /**
         * Uses a square-root algorithm to compute fall-off
         * @param distance The distance from the falloff start point
         *      to the point whose relative height change is wanted.
         * @param fallOffScalar The Scalar used to calculate fall off
         * @return The change in height the fall off starting
         *      point should caused on this point.
         */
        @Override
        public float heightChangeAt(float distance, float fallOffScalar)
        {
            return 0;
            //return fallOffScalar / FastMath.sqrt(distance);
        }
    };
    
        /**
         * @param distance The distance from the falloff start point
         *      to the point whose relative height change is wanted.
         * @param fallOffScalar The Scalar used to calculate fall off
         * @return The change in height the fall off starting
         *      point should caused on this point.
         */
    public abstract float heightChangeAt(float distance, float fallOffScalar);
}