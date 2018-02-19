package generation;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

/**
 * This AppState should be attached to view and interact with the
 *      map. Put all game logic relating to the map after its
 *      initial generation here.
 * @author jeffr
 */
public class MapAppState extends BaseAppState
{
    private Main main;
    private Node rootNode;
    private ViewPort viewPort;
    private final AmbientLight AMBIENT_LIGHT = new AmbientLight();
    private final DirectionalLight DIRECTIONAL_LIGHT = new DirectionalLight();
    
    /**
     * This function does basic initialiation of the AppState.
     *      Not called directly from user code.
     * @param app The application object that controls the program
     */
    @Override
    protected void initialize(Application app)
    {
        //Handles
        main = Main.getMain();
        rootNode = main.getRootNode();
        viewPort = main.getViewPort();
        
        //Init Methods
        initSky();
        initLight();
    }

    /**
     * This cleans up the AppState for removal. Not called
     *      directly from user code.
     * @param app The application object that controls the program
     */
    @Override
    protected void cleanup(Application app){}

    /**
     * This code runs when the appstate is attached to the
     *      stateManager.
     */
    @Override
    protected void onEnable(){}

    /**
     * This code runs when the appstate is dettached from the
     *      stateManager.
     */
    @Override
    protected void onDisable(){}
    
    /**
     * This is the AppState Update loop. It runs every frame and
     *      should be responsible for the majority of in-game
     *      logic.
     * @param tpf The time taken to run by the last frame.
     */
    @Override
    public void update(float tpf){}
    
    /**
     * This methods sets up ambient and directional lighting for the scene.
     * Ambient light is everywherre, directional light can create patches
     *      of relative light and dark.
     */
    private void initLight() 
    {
        AMBIENT_LIGHT.setColor(ColorRGBA.White);
        
        DIRECTIONAL_LIGHT.setDirection(new Vector3f(-1, -1, 0));
        DIRECTIONAL_LIGHT.setColor(ColorRGBA.White);
        
        rootNode.addLight(AMBIENT_LIGHT);
        rootNode.addLight(DIRECTIONAL_LIGHT);
    }
    
    /**
     * Creates a monochromatic sky-blue sky and places it infiitely far
     *      away in the background.
     */
    private void initSky()
    {
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    }
}