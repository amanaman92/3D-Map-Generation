package generation;

import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import jme3tools.converters.ImageToAwt;

/**
 * This class serves to build an alpha (texture) map given heightmap data.
 *      The colors in an alpha map tell JME3 which textues to place
 *      and where. Without this, the map would be black.
 * References:
 *      https://stackoverflow.com/questions/27071351/change-the-color-of-each-pixel-in-an-image-java
 * @author jeffr
 */
public class AlphaMap 
{
    private final HeightMap HEIGHT_MAP;
    private final int MAP_SIZE;
    private final Texture2D ALPHAMAP_DATA = new Texture2D();
    private final BufferedImage BUFFERED_IMAGE;
    private final AlphaVector[][] ALPHA_VECTORS;
    private final int GRASS = Color.RED.getRGB();
    private final int DIRT = Color.GREEN.getRGB();
    private final int ROAD = Color.BLUE.getRGB();
    
    /**
     * @param heightMap The heightMap for which to create an 
     *      AlphaMap
     */
    public AlphaMap(HeightMap heightMap)
    {
        HEIGHT_MAP = heightMap;
        MAP_SIZE = HEIGHT_MAP.getSize();
        ALPHA_VECTORS = new AlphaVector[MAP_SIZE][MAP_SIZE];
        BUFFERED_IMAGE = new BufferedImage(MAP_SIZE, MAP_SIZE, BufferedImage.TYPE_INT_RGB);
    }
    
    /**
     * This method writes the color-data for textures on the
     *      AplhaMap's BufferedImage variable.
     */
    public void writeAlphaMap()
    {
        float q3 = Statistics.calculatePercentile(HEIGHT_MAP.getHeightData(), .75f);
        System.out.println("---------------------------" + q3);
        
        //Pass 1: Init all Alpha Vectors
        int a = 0, b = 0;
        for(short x = 0; x < ALPHA_VECTORS.length; x++)
        {
            for(short y = 0; y < ALPHA_VECTORS[x].length; y++)
            {
                if(HEIGHT_MAP.getHeightVectors()[x][y].getHeight() > q3)
                {
                    a++;
                    ALPHA_VECTORS[x][y] = new AlphaVector(x, y, GRASS);
                }
                else
                {
                    b++;
                    ALPHA_VECTORS[x][y] = new AlphaVector(x, y, DIRT);
                }
            }
        }
        
        //Final Pass: Write BufferedImage
        for(int x = 0; x < MAP_SIZE; x++)
        {
            for(int y = 0; y < MAP_SIZE; y++)
            {
                BUFFERED_IMAGE.setRGB(x, y, ALPHA_VECTORS[x][y].getColorRGB());
            }
        }

        createTexture2D();
        writeToDisk();
    }
    
    /**
     * This method converted the BufferedImage into a Texture2D
     *      that can be read as an AlphaMap by JME3.
     */
    private void createTexture2D()
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(MAP_SIZE * MAP_SIZE * 4);
        ImageToAwt.convert(BUFFERED_IMAGE, Image.Format.RGBA8, byteBuffer);
        Image image = new Image();
        image.setFormat(Image.Format.RGBA8);
        image.setData(byteBuffer);
        ALPHAMAP_DATA.setImage(image);
    }
    
    /**
     * Write the AlphaMap to dist for later Reference
     */
    private void writeToDisk()
    {
        try 
        {
            File outputfile = new File("AlphaMap.png");
            ImageIO.write(BUFFERED_IMAGE, "png", outputfile);
        } 
        catch (IOException e){}
    }
    
    /**
     * @return The Texture2D object containing the color data of
     *      this AlphaMap.
     */
    public Texture2D getAplhaMapData()
    {
        return ALPHAMAP_DATA;
    }
}