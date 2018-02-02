package examples;

import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Random;
import jme3tools.converters.ImageToAwt;

/**
 * This class serves to build an alpha (texture) map given heightmap data.
 *      The colors in an alpha map tell JME3 which textues to place
 *      and where. Without this, the map would be black.
 * @author jeffr
 */
public class AlphaMap
{
    private final int SIZE;
    private final Random GENERATOR = new Random();
    private final ColorVector[][] HEIGHT_VECTORS;
    private final ColorVector[][] APLHA_VECTORS;
    private final int SMOOTHING_FACTOR;
    private Texture2D texture2D;
    
    //Constructor
    /**
     * @param heightMap The heightMap for which to create an AlphaMap
     */
    public AlphaMap(HeightMap heightMap)
    {
        HEIGHT_VECTORS = heightMap.getVectors();
        SIZE = HEIGHT_VECTORS.length;
        APLHA_VECTORS = new ColorVector[SIZE][SIZE];
        SMOOTHING_FACTOR = GENERATOR.nextInt(3);
    }

    /**
     * Creates the aplha map by painting colors on an image.
     *      Each color represents on texture to be panted on the terrain.
     *      Mixed colors represent blended textures.
     * Try modifying this to see what you can do!
     */
    public void writeImage()
    {
        BufferedImage bufferedImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        
        float desertFactor = GENERATOR.nextFloat() * 255;

        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                int color = (int) HEIGHT_VECTORS[i][j].c().getBlue();
                
                if(color > 200)
                {
                    float red = desertFactor * (color / 255);
                    APLHA_VECTORS[i][j] = new ColorVector(ColorMath.safeColor(red, 255, 0), i, j);
                }
                else if (color < 50)
                {
                    float red = desertFactor * (1 - (color / 255));
                    APLHA_VECTORS[i][j] = new ColorVector(ColorMath.safeColor(red, 255, 0), i, j);
                }
                else
                {
                    float green = color < 128 ? desertFactor * ((color / 255)) : desertFactor * (1 - (color / 255));
                    APLHA_VECTORS[i][j] = new ColorVector(ColorMath.safeColor(255, green, 0), i, j);
                }
            }
        }

        //Smoothing
        ColorMath.smooth(SMOOTHING_FACTOR, APLHA_VECTORS);
        
        //Paint vector results on graphics object
        for(ColorVector[] vecRow : APLHA_VECTORS)
        {
            for(ColorVector vec : vecRow)
            {
                g.setPaint(vec.c());
                Rectangle r = new Rectangle(vec.x(), vec.y(), 1, 1);
                g.draw(r);
            }
        }
        
        //Finalize in memory
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(SIZE * SIZE * 4);
        ImageToAwt.convert(bufferedImage, Image.Format.RGBA8, byteBuffer);
        Image image = new Image();
        image.setFormat(Image.Format.RGBA8);
        image.setData(byteBuffer);
        texture2D = new Texture2D();
        texture2D.setImage(image);
    }
    
    /**
     * @return the created textureMap
     */
    public Texture2D textureMap()
    {
        return texture2D;
    }
    
    /**
     * @return THe COlorVector array that defines this map
     */
    public ColorVector[][] vectors()
    {
        return APLHA_VECTORS;
    }
}