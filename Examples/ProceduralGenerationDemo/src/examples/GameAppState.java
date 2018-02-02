package examples;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.FlyByCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.texture.Texture;
import java.util.Random;

/**
 * This class contains most of the world data for what the user
 *      will see, Put this here (NOT in the Main class) because
 *      it is much easier to clone, re-attach, and remove App
 *      States then it is to modify the Main class. All game
 *      logic for in-game activities should be on an appState--
 *      but not necesarily this one. You can have as many appstates
 *      running at once as you want, so feel free to make as many
 *      as you need!
 * @author jeffr
 */
public class GameAppState extends BaseAppState 
{
    //Game and Physics Handles
    private Node rootNode;
    private final Node GEOGRAPHY = new Node();
    private BulletAppState bulletAppState;
    private AssetManager assetManager;
    private ViewPort viewPort;
    private FlyByCamera flyCam;
    private Camera cam;
    
    private final Random GENERATOR = new Random();
    private final int SIZE = 1024,
            SCALE = 1,
            MAP_SIZE = SIZE * SCALE;
    private TerrainQuad terrain;
    
    //Methods
    /**
     * Initialized the AppState. Not called from user code.
     * @param app the Application object that controls the program
     */
    @Override
    protected void initialize(Application app) 
    {
        bulletAppState = Main.getMain().getBulletAppState();
        assetManager = Main.getMain().getAssetManager();
        viewPort = Main.getMain().getViewPort();
        flyCam = Main.getMain().getFlyByCamera();
        cam = Main.getMain().getCamera();
        rootNode = Main.getMain().getRootNode();
        
        setUpMap();
        setUpLight();
        setUpSky();
        setUpFlyCam();
        spawnBoxes(3000);
    }

    /**
     * Cleans up the AppState for removal. Not called form user code.
     * @param app the Application object that controls the program
     */
    @Override
    protected void cleanup(Application app){}

    /**
     * Runs once when the AppState is attached. Not called form user code.
     */
    @Override
    protected void onEnable(){}

    /**
     * Runs once when the AppState is dettached. Not called form user code.
     */
    @Override
    protected void onDisable(){}
    
    /**
     * Runs continuously; controls game logic. Not called form user code.
     *      Any growth algorithms should go in here.
     * @param tpf (time per frame) the time in seconds the last frame took to run
     */
    @Override
    public void update(float tpf) {}
    
    /**
     * This method takes the height and alpha maps from procedural
     *      Generation and turns them into a physical world map.
     */
    private void setUpMap()
    {
        HeightMap heightMap = new HeightMap(MAP_SIZE);
        heightMap.writeImage();
        AlphaMap alphaMap = new AlphaMap(heightMap);
        alphaMap.writeImage();

        Material mat_terrain = new Material(assetManager, "Common/MatDefs/Terrain/TerrainLighting.j3md");
        mat_terrain.setTexture("AlphaMap", alphaMap.textureMap());

        //Red  layer in alphamap
        Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("DiffuseMap", grass);
        mat_terrain.setFloat("DiffuseMap_0_scale", 64f);

        //Green layer in alphamap
        Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("DiffuseMap_1", dirt);
        mat_terrain.setFloat("DiffuseMap_1_scale", 32f);

        //Blue layer in alphamap
        Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
        rock.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("DiffuseMap_2", rock);
        mat_terrain.setFloat("DiffuseMap_2_scale", 128f);

        AbstractHeightMap heightmap = heightMap.rawHeightMap();

        int patchSize = 65;
        terrain = new TerrainQuad("my terrain", patchSize, 1025, heightmap.getHeightMap());
    
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, 0, 0);
        terrain.setLocalScale(SCALE, SCALE, SCALE);

        GEOGRAPHY.attachChild(terrain);
        rootNode.attachChild(GEOGRAPHY);

        TerrainLodControl control = new TerrainLodControl(terrain, cam);
        terrain.addControl(control);

        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape((Node) terrain);
        RigidBodyControl mapBody = new RigidBodyControl(sceneShape, 0);
        terrain.addControl(mapBody);
        bulletAppState.getPhysicsSpace().add(mapBody);
    }
    
    /**
     * This methods sets up ambient and directional lighting for the scene.
     * Ambient light is everywherre, directional light can create patches
     *      of relative light and dark.
     */
    private void setUpLight() 
    {
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(1.3f));
        
        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setDirection(new Vector3f(-1, -1, 0));
        directionalLight.setColor(ColorRGBA.White);
        
        rootNode.addLight(ambientLight);
        rootNode.addLight(directionalLight);
    }
    
    /**
     * Creates a monochromatic sky-blue sky and places it infiitely far
     *      away in the background.
     */
    private void setUpSky()
    {
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    }
    
    /**
     * This sets up the defualt camera, with WASD controls (Plus Q for up,
     *      Z for down). Move mouse to rotate view.
     */
    private void setUpFlyCam()
    {
        flyCam.setMoveSpeed(100);
    }
    
    /**
     * @param x The x-coordinate on the map.
     * @param z The z-coordinate on the map.
     * @return The Vector3f describing the point x,z and the height
     *      y of this point.
     */
    public Vector3f coordinateOf(int x, int z)
    {
        float y = terrain.getHeight(new Vector2f(x, z));
        return new Vector3f(x, y, z);
    }
    
    /**
     * This method places boxes randomly around the map. Feel free
     *      to play around and see if you can make a forest with
     *      the boxes (Note--it is VERY easy to change a box to a
     *      tree, bush, etc.)
     * @param num The number of boxes to spawn
     */
    public void spawnBoxes(int num)
    {
        for(int i = 0; i < num; i++)
        {
            int x = GENERATOR.nextInt(MAP_SIZE) - (int) (.5f * MAP_SIZE);
            int z = GENERATOR.nextInt(MAP_SIZE) - (int) (.5f * MAP_SIZE);
            spawnBox(coordinateOf(x, z));
        }
    }
    
    /**
     * This methods creates a single box at a single point in space.
     * @param loc The location to create the box in 3-space.
     */
    public void spawnBox(Vector3f loc)
    {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box at: " + loc, b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        geom.setLocalTranslation(loc);
        rootNode.attachChild(geom);
    }
}