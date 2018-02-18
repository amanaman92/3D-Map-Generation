package generation;

import com.jme3.math.FastMath;

/**
 * This enum serves to define base terrain equations for initializing
 *      the HeightMap values.
 * References:
 *      https://stackoverflow.com/questions/18883646/java-enum-methods
 *      https://stackoverflow.com/questions/965690/java-optional-parameters
 * @author jeffr
 */
public enum BaseTerrain 
{
    /**
     * Represents a BaseTerrain with constant height
     */
    FLAT
    {
        /**
         * @param x An x-coordinate on the terrain heightMap
         * @param z A z-coordinate on the terrain heightMap
         * @return The height of the terrain at (x, z)
         */
        @Override
        public float getHeight(int x, int z, int sideLength)
        {
            return 0;
        }
    },
    
    /**
     * Represents a BaseTerrain with sinusoidially changing height
     */
    HILLS
    {
        /**
         * @param x An x-coordinate on the terrain heightMap
         * @param z A z-coordinate on the terrain heightMap
         * @return The height of the terrain at (x, z)
         */
        @Override
        public float getHeight(int x, int z, int sideLength)
        {
            float amplitude = 20;
            float divisor = 50;
            return amplitude * FastMath.sin(x / divisor) + amplitude * FastMath.cos(z / divisor);
        }
    },
    
    /**
     * Represents a BaseTerrain with unidirectionall increasing height
     */
    SLOPED
    {
        /**
         * @param x An x-coordinate on the terrain heightMap
         * @param z A z-coordinate on the terrain heightMap
         * @param sideLenth The lenfth of one side of the map
         * @return The height of the terrain at (x, z)
         */
        @Override
        public float getHeight(int x, int z, int sideLenth)
        {
            return x - z;
        }
    },
    
    /**
     * Represents a BaseTerrain with a central trough
     */
    VALLEY
    {
        /**
         * @param x An x-coordinate on the terrain heightMap
         * @param z A z-coordinate on the terrain heightMap
         * @param sideLenth The lenfth of one side of the map
         * @return The height of the terrain at (x, z)
         */
        @Override
        public float getHeight(int x, int z, int sideLenth)
        {
            int halfExtent = sideLenth / 2;
            return 0 + VectorMath.distance(new MapVector((short) x, (short) z), 
                    new MapVector((short) halfExtent, (short) halfExtent));
        }
    },
    
    /**
     * Represents a BaseTerrain with a central peak
     */
    MOUNTAIN
    {
        /**
         * @param x An x-coordinate on the terrain heightMap
         * @param z A z-coordinate on the terrain heightMap
         * @param sideLenth The lenfth of one side of the map
         * @return The height of the terrain at (x, z)
         */
        @Override
        public float getHeight(int x, int z, int sideLenth)
        {
            int halfExtent = sideLenth / 2;
            return sideLenth - VectorMath.distance(new MapVector((short) x, (short) z), 
                    new MapVector((short) halfExtent, (short) halfExtent));
        }
    };
    
    /**
     * @param x An x-coordinate on the terrain heightMap
     * @param z A y-coordinate on the terrain heightMap
     * @param sideLenth The lenfth of one side of the map
     * @return The height of the terrain at (x, z)
     */
    public abstract float getHeight(int x, int z, int sideLenth);
}