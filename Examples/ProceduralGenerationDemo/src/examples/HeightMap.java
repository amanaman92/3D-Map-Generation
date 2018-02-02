package examples;

import com.jme3.math.Vector2f;
import com.jme3.terrain.heightmap.RawHeightMap;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used to build and store data for a height
 *      map object. It is the most important parts of
 *      the current procedural generation algorithm.
 * @author jeffr
 */
public class HeightMap
{
    private final int SIZE;
    private final Random GENERATOR = new Random();
    private ColorVector[][] vectors;
    private final int SCALE;
    private ArrayList<Vector2f> troughs, summits;
    private int baseColor;
    private RawHeightMap rawHeightMap;
    private RawHeightMap[][] splintered;
    private final boolean INVERT = GENERATOR.nextBoolean();
    
    /**
     * 
     * @param MAP_SIZE the length of one side of the map.
     *      Should be a power of 2.
     */
    public HeightMap(int MAP_SIZE)
    {
        this.SIZE = MAP_SIZE;
        this.SCALE = 2;
    }

    /**
     * This is where the actual map creation happens. Do
     *      not get too bogged down on the exact details--
     *      this algorithm is not perfect. But overall,
     *      it runs like this:
     * It starts by doing some basic initialization. Using a
     *      Random object, it decides how many summits (hills)
     *      and troughs to place, as well as their averaeg size.
     * The algorithm has several stages, each refered to as
     *      a "pass". In each pass, it iterates over an array
     *      of ColorVectors (basically pixels--it defined a 
     *      class for it to get some more features). Each time,
     *      it modified the array to place colors at each index.
     *      Over time, it accumulates dark and light colors in
     *      different areas, creating the geography.
     * An optional invert step at the end flips black to white, 
     *      and white to black, in the image.
     */
    public void writeImage()
    {
        troughs = new ArrayList<>();
        summits = new ArrayList<>();

        //Primary feature
        baseColor = 128;
        int dark_base = 0;
        int troughNum = (SIZE * SCALE) / (GENERATOR.nextInt(25) + 100 / SCALE); //(gen.nextInt(25 * COMPRESS) + 100)
        float radius = (SIZE * SCALE) / (GENERATOR.nextInt(SCALE * 2) + 5);
        
        final float SMOOTHING_FACTOR = 1;
        
        for(int i = 0; i < troughNum; i++)
        {
            troughs.add(VectorMath.randomVector2f(SIZE, SIZE));    
        }
        
        //Auxiliary feature
        int light_base = 255;
        int summitNum = troughNum / 10;
        float summitRadius = (radius / 2) + GENERATOR.nextInt((int) (radius / 2));
        
        for(int i = 0; i < summitNum; i++)
        {
            summits.add(VectorMath.randomVector2f(SIZE, SIZE));    
        }
        
        //Paint image
        vectors = new ColorVector[SIZE][SIZE];

        /*
        * This pass does initialization of the heightmap to a random color (height).
        * It prevents any null data in the map by ensuring each pixel has a non-null
        *       starting value.
        */
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                vectors[i][j] = new ColorVector(ColorMath.colorBW(baseColor), i , j);
            }
        }
        
        //Second pass: Place Pits / Mountains
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {        
                Vector2f pixelLoc = new Vector2f(i, j);
                for(Vector2f trough : troughs)
                {
                    float distance = pixelLoc.distance(trough);
                    if(distance < radius)
                    {
                        vectors[i][j].setColor(ColorMath.colorBW(dark_base + (baseColor / radius) * distance));
                    }
                }
            }
        }
        
        //Third pass: Place Pits / Mountains (again)
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {        
                Vector2f pixelLoc = new Vector2f(i, j);
                for(Vector2f summit : summits)
                {
                    float distance = pixelLoc.distance(summit);
                    if(distance < summitRadius)
                    {
                        vectors[i][j].setColor(ColorMath.colorBW(light_base + (baseColor / summitRadius) * distance));
                    }
                }
            }
        }
        
        /*
         * Fourth Pass: Smoothing
         * THe previous passed often create very sharp contrasts
         *      in elevation (no slopes, just cliffs). This pass
         *      calls ColorMath (a utilities class) to smooth the
         *      image, and prevent this fomr happening.
         */ 
        ColorMath.smooth(SMOOTHING_FACTOR, vectors);
        
        //Fifth Pass: Invert
        if(INVERT)
        {
            for(int i = 0; i < SIZE; i++)
            {
                for(int j = 0; j < SIZE; j++)
                {
                    vectors[i][j].setColor(ColorMath.invert(vectors[i][j].c()));
                }
            }
        }
        createRawHeightMap();
    }
    /**
     * This method takes the ColorVector array created
     *      beforehand and writes it into a RawHeightMap
     *      Object (That is just a way JME3 understands
     *      heightmaps). This allows the game engine to
     *      use the map.
     */
    private void createRawHeightMap()
    {
        //Paint vector results on graphics object
        float[] heightData = new float[SIZE * SIZE];
        ArrayList<Integer> bw = ColorMath.asIntegerList(vectors);
        float min = Statistics.min(bw);
        System.out.println("MIN: " + min);
        int index = 0;
        for(ColorVector[] vecRow : vectors)
        {
            for(ColorVector vec : vecRow)
            {
                heightData[index] = vec.c().getBlue();
                if(heightData[index] <= min) System.out.println(heightData[index]);
                index++;
            }
        }
        rawHeightMap = new RawHeightMap(heightData);
    }
    
    /**
     * @return The size of the map 
     */
    public int size()
    {
        return SIZE;
    }
    
    /**
     * @return the RawHeightMap that contains all
     *      geographical data.
     */
    public RawHeightMap rawHeightMap()
    {
        return rawHeightMap;
    }
    
    /**
     * @return vectors, the ColorVectors used to define
     *      the heightMap.
     */
    public ColorVector[][] getVectors()
    {
        return vectors;
    }
}