package generation;

import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import jme3tools.converters.ImageToAwt;

/**
 * This class serves to build an alpha (texture) map given heightmap data.
 *      The colors in an alpha map tell JME3 which textues to place
 *      and where. Without this, the map would be black.
 * @author jeffr
 */
public class AlphaMap 
{
    private final HeightMap HEIGHT_MAP;
    private final int MAP_SIZE;
    private final Texture2D ALPHAMAP_DATA = new Texture2D();
    private final BufferedImage BUFFERED_IMAGE;
    
    /**
     * @param heightMap The heightMap for which to create an 
     *      AlphaMap
     */
    public AlphaMap(HeightMap heightMap)
    {
        HEIGHT_MAP = heightMap;
        MAP_SIZE = HEIGHT_MAP.getSize();
        BUFFERED_IMAGE = new BufferedImage(MAP_SIZE, MAP_SIZE, BufferedImage.TYPE_INT_RGB);
    }
    
    /**
     * This method writes the color-data for textures on the
     *      AplhaMap's BufferedImage variable.
     */
    public void writeAlphaMap()
    {
        createTexture2D();
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
     * @return The Texture2D object containing the color data of
     *      this AlphaMap.
     */
    public Texture2D getAplhaMapData()
    {
        return ALPHAMAP_DATA;
    }
}