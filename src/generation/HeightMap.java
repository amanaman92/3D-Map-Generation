package generation;

/**
 * This class is used to build and store data for a height
 *      map object. It is one of the most important parts of
 *      the current procedural generation algorithm.
 * @author jeffr
 */
public class HeightMap
{
    private final float[] HEIGHT_DATA;
    private final BWVector[][] BW_VECTORS;
    private final int MAP_SIZE;
    private final float BASE_VALUE = .5f;
    private final float COLOR_MAX = 255;
    
    public HeightMap(int mapSize)
    {
        MAP_SIZE = mapSize;
        HEIGHT_DATA = new float[MAP_SIZE * MAP_SIZE];
        BW_VECTORS = new BWVector[MAP_SIZE][MAP_SIZE];
    }
    
    public void writeHeightMap()
    {
        //Pass 1: Init all BW Vectors
        for(int x = 0; x < BW_VECTORS.length; x++)
        {
            for(int y = 0; y < BW_VECTORS[x].length; y++)
            {
                BW_VECTORS[x][y] = new BWVector(x, y, BASE_VALUE);
            }
        }
        
        //Pass 2: Features
        for(int i = 0; i < 20; i++)
        {
            BWVector start = new BWVector(RandomUtils.randomInt(0, MAP_SIZE), RandomUtils.randomInt(0, MAP_SIZE), RandomUtils.RANDOM.nextFloat());
            BWVector end = new BWVector(RandomUtils.randomInt(0, MAP_SIZE), RandomUtils.randomInt(0, MAP_SIZE), RandomUtils.RANDOM.nextFloat());
            MapEquation mapEq = new MapEquation(start, end);

            for(int x = mapEq.getMinX(); x < mapEq.getMaxX(); x++)
            {
                int y = mapEq.pointAt(x);
                if(y > 0 && y < BW_VECTORS[x].length)
                {
                    BW_VECTORS[x][y].setValue(mapEq.valueAt(x));
                }
            }
        }

        //Pass 3: Smooth
        ColorMath.smooth(BW_VECTORS, 2);
        
        createHeightData();
    }
    
    /**
     * This method takes the ColorVector array created
     *      beforehand and writes it into a RawHeightMap
     *      Object (That is just a way JME3 understands
     *      heightmaps). This allows the game engine to
     *      use the map.
     */
    private void createHeightData()
    {
        int index = 0;
        for(BWVector[] vecRow : BW_VECTORS)
        {
            for(BWVector vec : vecRow)
            {
                HEIGHT_DATA[index] = vec.getValue() * COLOR_MAX;
                index++;
            }
        }
           
    }
    
    /**
     * @return the RawHeightMap that contains all
     *      geographical data.
     */
    public float[] getHeightData()
    {
        return HEIGHT_DATA;
    }
    
    /**
     * @return The size of one side of this HeightMap
     *      in pixels.
     */
    public int getSize()
    {
        return MAP_SIZE;
    }
}