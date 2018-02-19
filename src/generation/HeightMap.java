package generation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class is used to build and store data for a height
 *      map object. It is one of the most important parts of
 *      the current procedural generation algorithm.
 * References
 *      https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
 * @author jeffr
 */
public class HeightMap
{
    private final float[] HEIGHT_DATA;
    private final HeightVector[][] HEIGHT_VECTORS;
    private final int MAP_SIZE;
    private static final float BASE_VALUE = 0;
    private final BaseTerrain TERRAIN_TYPE;
    
    /**
     * Creates a new Height Map (square) with a given side
     *      length.
     * @param mapSize The length of a side of the map
     */
    public HeightMap(int mapSize)
    {
        MAP_SIZE = mapSize;
        HEIGHT_DATA = new float[MAP_SIZE * MAP_SIZE];
        HEIGHT_VECTORS = new HeightVector[MAP_SIZE][MAP_SIZE];
        TERRAIN_TYPE = BaseTerrain.HILLS;
    }
    
    /**
     * Creates a new Height Map (square) with a given side
     *      length.
     * @param mapSize The length of a side of the map
     * @param terrainType The base terrain of this heightMap
     */
    public HeightMap(int mapSize, BaseTerrain terrainType)
    {
        MAP_SIZE = mapSize;
        HEIGHT_DATA = new float[MAP_SIZE * MAP_SIZE];
        HEIGHT_VECTORS = new HeightVector[MAP_SIZE][MAP_SIZE];
        TERRAIN_TYPE = terrainType;
    }
    
    /**
     * Creates the heightmap procedurally by iterating over an array
     *      of BWVectors to change their color. Multiple passes are
     *      used to refine results.
     */
    public void writeHeightMap()
    {
        //Pass 1: Init all Height Vectors with a TerrainType
        for(short x = 0; x < HEIGHT_VECTORS.length; x++)
        {
            for(short z = 0; z < HEIGHT_VECTORS[x].length; z++)
            {
                HEIGHT_VECTORS[x][z] = new HeightVector(x, z, TERRAIN_TYPE.getHeight(x, z, MAP_SIZE) + BASE_VALUE);
            }
        }
        
        //Pass 2: Features
        final int FEATURES = 50;
        HeightMapEquation[] mapEqs = new HeightMapEquation[FEATURES];
        for(int i = 0; i < FEATURES; i++)
        {
            int xLoc = RandomUtils.randomInt(0, MAP_SIZE - 1);
            int yLoc = RandomUtils.randomInt(0, MAP_SIZE - 1);
            HeightVector start = HEIGHT_VECTORS[xLoc][yLoc];
            start.setHeight(BASE_VALUE * RandomUtils.RANDOM.nextFloat() * 2);
            
            int x1Loc = RandomUtils.randomInt(0, MAP_SIZE - 1);
            while(x1Loc == xLoc) //prevent them from being the same
            {
                x1Loc = RandomUtils.randomInt(0, MAP_SIZE - 1);
            }
            int y1Loc = RandomUtils.randomInt(0, MAP_SIZE - 1);
            HeightVector end = HEIGHT_VECTORS[x1Loc][y1Loc];
            end.setHeight(BASE_VALUE * RandomUtils.RANDOM.nextFloat() * 2);
            
            mapEqs[i] = new HeightMapEquation(start, end);
            for(int x = mapEqs[i].getMinX(); x < mapEqs[i].getMaxX(); x++)
            {
                //Place Feature Center
                int y = mapEqs[i].pointAt(x);
                if(y > 0 && y < HEIGHT_VECTORS[x].length)
                {
                    HEIGHT_VECTORS[x][y].setHeight(mapEqs[i].heightAt(x));
                }
            }
        }

        //Pass 3: Refinement of features
        for(short x = 0; x < HEIGHT_VECTORS.length; x++)
        {
            for(short y = 0; y < HEIGHT_VECTORS[x].length; y++)
            {
                for(HeightMapEquation mapEq : mapEqs)
                {
                    HEIGHT_VECTORS[x][y].setHeight(mapEq.heightAt(HEIGHT_VECTORS[x][y]));
                }
            }
        }

        //Pass 4: Smooth
        MapMath.smooth(HEIGHT_VECTORS, 2);
        
        createHeightData();
        if(Main.getMain().getDebug())
        {
            writeToDisk();
        }
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
     * Write the HeightMap to dist for later Reference
     */
    private void writeToDisk()
    {
        //Crerte BufferedImage
        BufferedImage bufferedImage = new BufferedImage(MAP_SIZE, MAP_SIZE, BufferedImage.TYPE_INT_RGB);
        float max = Statistics.calculateMax(HEIGHT_DATA);
        float min = Statistics.calculateMin(HEIGHT_DATA);
        float white = max - min;
        for(int x = 0; x < MAP_SIZE; x++)
        {
            for(int y = 0; y < MAP_SIZE; y++)
            {
                float colorBW = (HEIGHT_VECTORS[x][y].getHeight() - min) / white;
                Color c = MapMath.fromValue(colorBW);
                bufferedImage.setRGB(x, y, c.getRGB());
            }
        }

        //Write
        try 
        {
            File outputfile = new File("HeightMap.png");
            ImageIO.write(bufferedImage, "png", outputfile);
        } 
        catch (IOException e){}
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
     * @return The HeightVectors used to define the HeightMap
     *      in complete detail.
     */
    public HeightVector[][] getHeightVectors()
    {
        return HEIGHT_VECTORS;
    }
    
    /**
     * @return The size of one side of this HeightMap
     *      in pixels.
     */
    public int getSize()
    {
        return MAP_SIZE;
    }
    
    /**
     * @return The base height of this HeightMap.
     */
    public static float getBaseValue()
    {
        return BASE_VALUE;
    }
}