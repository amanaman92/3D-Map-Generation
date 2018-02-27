package generation;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import java.awt.Dimension;
import java.awt.Toolkit;
import com.jme3.input.InputManager;

/**
 * This class contains basic data needed by the entire game.
 *      Most game daa should be localized to appstates instead.
 * References used:
 *      https://jmonkeyengine.github.io/wiki/jme3/advanced/hud.html
 * @author jeffr
 */
public class Main extends SimpleApplication 
{
    private static final Main MAIN = new Main();
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private final static int MAX_FRAMERATE = 100;
    //private final static InputManager INPUT_MANAGER = new InputManager(null, null, null, null);
    //Make input manager here and make function getInputManager; OVERRIDE IT.

    private  BaseAppState mapAppState,             
            hudAppState;
    private final BulletAppState BULLET_APP_STATE = new BulletAppState();
    private TerrainQuad terrain;
    private final int MAP_SIZE = 1024;
    private final boolean DEBUG = true;
    
    /**
     * This is private so no other Main objects can be made
     *      (that would probably crash the program). The only
     *      Main object that can exist is examples.Main.MAIN 
     *      (see the instance variables)
     */
    private Main(){}
    
    
    
    /**
     * Program execution begins here. Do not modify this method
     *      unless you have a very secific reason you must place
     *      code here. Put code in simpleInitApp() instead.
     * @param args 
     */
    public static void main(String[] args)
    {
        //Global Application Settings
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(MAX_FRAMERATE);
        settings.setTitle("Procedural Generation");
        settings.setFullscreen(true);
        settings.setResolution(SCREEN_SIZE.width, SCREEN_SIZE.height);
        MAIN.setSettings(settings);
        //Begin game execution
        MAIN.start();
    }

    /**
     * This function is meant to do basic initialization for the
     *      entire game. Specifically, it should attach appStates
     *      to control game logic.
     */
    @Override
    public void simpleInitApp()
    {   
         //Note: inputManager had not been initalized before putting the two variables below. As a result, main was null even when trying to
        //initialize it.
        hudAppState = new HUDAppState();
        mapAppState = new MapAppState();
        //Init Global Variables / Data
        initJME3GlobalGUI();
        //Init GUI App State
        stateManager.attach(hudAppState);
    }
    
    /**
     * Called by GUI AppState when the game is ready to start.
     *      Pass game parameters to this method as needed.
     */
    public void beginGame()
    {
        //Init App States
        stateManager.attach(BULLET_APP_STATE);
        stateManager.attach(mapAppState);
        
        //Init Game Variables / Data
        generateProceduralMap();
        placeTrees();
        initCamera();
    }
    
    public void placeTrees()
    {
        
    }
    
    /**
     * Set JME3 global GUI variables
     */
    private void initJME3GlobalGUI()
    {
        if(!DEBUG)
        {
            setDisplayStatView(false);
            setDisplayFps(false);
        }
        guiNode.setQueueBucket(Bucket.Gui);
    }
    
    /**
     * This method takes the height and alpha maps from procedural
     *      Generation and turns them into a physical world map.
     */
    private void generateProceduralMap()
    {
        //Generate HeightMap
        HeightMap heightMap = new HeightMap(MAP_SIZE);
        heightMap.writeHeightMap();
        float[] heightMapData = heightMap.getHeightData();
        
        //Generate AlphaMap
        AlphaMap alphaMap = new AlphaMap(heightMap);
        alphaMap.writeAlphaMap();
        Texture2D alphaMapData = alphaMap.getAplhaMapData();
        
        //Configure Material
        Material terrainMaterial = new Material(assetManager, "Common/MatDefs/Terrain/TerrainLighting.j3md");
        terrainMaterial.setTexture("AlphaMap", alphaMapData);

        //Map texture for red layer in alphamap
        Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        terrainMaterial.setTexture("DiffuseMap", grass);
        terrainMaterial.setFloat("DiffuseMap_0_scale", 64f);

        //Map texture for green layer in alphamap
        Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        terrainMaterial.setTexture("DiffuseMap_1", dirt);
        terrainMaterial.setFloat("DiffuseMap_1_scale", 32f);

        //Map texture for blue layer in alphamap
        Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
        rock.setWrap(Texture.WrapMode.Repeat);
        terrainMaterial.setTexture("DiffuseMap_2", rock);
        terrainMaterial.setFloat("DiffuseMap_2_scale", 128f);
        
        //Configure Terrain Basic Data
        int patchSize = 65;
        terrain = new TerrainQuad("terrain", patchSize, 1025, heightMapData);
        terrain.setMaterial(terrainMaterial);
        terrain.setLocalTranslation(0, 0, 0);

        //Set Level-of-Detail control to improve rendering performance
        TerrainLodControl control = new TerrainLodControl(terrain, cam);
        terrain.addControl(control);

        //Attach terrain to world and physics space
        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape((Node) terrain);
        RigidBodyControl mapBody = new RigidBodyControl(sceneShape, 0);
        terrain.addControl(mapBody);
        rootNode.attachChild(terrain);
        BULLET_APP_STATE.getPhysicsSpace().add(mapBody);
    }
    
    /**
     * Set up the camera view range and location
     */
    private void initCamera()
    {
        flyCam.setMoveSpeed(100);
        Vector3f camLoc = coordinateOf(0, 0);
        if(camLoc != null)
        {
            cam.setLocation(camLoc);
        }
        else
        {
            cam.setLocation(new Vector3f(0, 128, 0));
        }
        cam.setFrustumFar(MAP_SIZE * MAP_SIZE);
    }
    
    /**
     * Finds the Vectror3f coordinate of a point on the map
     *      given in x, z
     * @param x The x-ccordinate of the point
     * @param z The y-ccordinate of the point
     * @return the Vector3f representing this point on
     *      the map in 3-space
     */
    public Vector3f coordinateOf(float x, float z)
    {
        float y = terrain.getHeight(new Vector2f(x, z));
        if(Float.isNaN(y))
        {
            return null;
        }
        return new Vector3f(x, y, z);
    }

    /**
     * This method runs every frame of the game. Game logic
     *      should generally not be here, but instead in
     *      AppStates. Only put code in here to determine when
     *      to attach or remove appstates during the game.
     * @param tpf The time taken to run by the last frame.
     */
    @Override
    public void simpleUpdate(float tpf)
    {

    }
    
    /**
     * @return The BulletAppState, used to process physics data
     */
    public BulletAppState getBulletAppState()
    {
        return BULLET_APP_STATE;
    }
    
    /**
     * @return The default GUI Node font
     */
    public BitmapFont getGuiFont()
    {
        return guiFont;
    }
    
    /**
     * @return The reference to the only Main Object
     */
    public static Main getMain()
    {
        System.out.println("getMain");
        return MAIN;
    }
    
    /**
     * @return A Dimension object contianing the size of the screen
     */
    public Dimension getScreenSize()
    {
        //System.out.println("Hello! screenSize");
        return SCREEN_SIZE;
    }
    
    /**
     * @return If the application is running in debug mode
     */
    public boolean getDebug()
    {
        return DEBUG;
    }
    
    /**
     * @return the SimpleApplication's default inputManager object
     */
    @Override
    public InputManager getInputManager()
    {
        //System.out.println("Hello!");
        return this.inputManager;
    }
    
}
    //Lines 269 - 271 is what I wrote