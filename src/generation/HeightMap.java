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
    private final HeightVector[][] HEIGHT_VECTORS;
    private final int MAP_SIZE;
    private final float BASE_VALUE = 0;
    
    public HeightMap(int mapSize)
    {
        MAP_SIZE = mapSize;
        HEIGHT_DATA = new float[MAP_SIZE * MAP_SIZE];
        HEIGHT_VECTORS = new HeightVector[MAP_SIZE][MAP_SIZE];
    }
    
    /**
     * Creates the heightmap procedurally by iterating over an array
     *      of BWVectors to change their color. Multiple passes are
     *      used to refine results.
     */
    public void writeHeightMap()
    {
        //Pass 1: Init all BW Vectors
        for(int x = 0; x < HEIGHT_VECTORS.length; x++)
        {
            for(int y = 0; y < HEIGHT_VECTORS[x].length; y++)
            {
                HEIGHT_VECTORS[x][y] = new HeightVector(BASE_VALUE);
            }
        }
        
        //Pass 2: Features
        for(int i = 0; i < 20; i++)
        {
            int xLoc = RandomUtils.randomInt(0, MAP_SIZE);
            int yLoc = RandomUtils.randomInt(0, MAP_SIZE);
            HeightVector start = HEIGHT_VECTORS[xLoc][yLoc];
            start.setHeight(BASE_VALUE);
            
            int x1Loc = RandomUtils.randomInt(0, MAP_SIZE);
            int y1Loc = RandomUtils.randomInt(0, MAP_SIZE);
            HeightVector end = HEIGHT_VECTORS[x1Loc][y1Loc];
            end.setHeight(BASE_VALUE);
            
            MapEquation mapEq = new MapEquation(start, end);

            for(int x = mapEq.getMinX(); x < mapEq.getMaxX(); x++)
            {
                //Place Feature Center
                int y = mapEq.pointAt(x);
                if(y > 0 && y < HEIGHT_VECTORS[x].length)
                {
                    HEIGHT_VECTORS[x][y].setHeight(mapEq.heightAt(x));
                }
            }
        }

        //Pass 3: Smooth
        MapMath.smooth(HEIGHT_VECTORS, 2);
        
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
        for(HeightVector[] vecRow : HEIGHT_VECTORS)
        {
            for(HeightVector vec : vecRow)
            {
                HEIGHT_DATA[index] = vec.getHeight(); //* COLOR_MAX
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