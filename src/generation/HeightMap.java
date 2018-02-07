package generation;

/**
 * This class is used to build and store data for a height
 *      map object. It is one of the most important parts of
 *      the current procedural generation algorithm.
 * @author jeffr
 */
public class HeightMap
{
    private final float[] heightData;
    
    private final int MAP_SIZE;
    
    public HeightMap(int mapSize)
    {
        MAP_SIZE = mapSize;
        heightData = new float[MAP_SIZE * MAP_SIZE];
    }
    
    public void writeHeightMap()
    {
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
        
    }
    
    /**
     * @return the RawHeightMap that contains all
     *      geographical data.
     */
    public float[] getHeightData()
    {
        return heightData;
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