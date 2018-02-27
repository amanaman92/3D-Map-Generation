package generation;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioNode;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

import com.jme3.util.SkyFactory;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.material.Material;


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
    
    
    private ParticleEmitter rain;
    private float rainGravity = 500f;
    private float rainEmitterRadius = 1000f;
    private float rainEmitterHeight = 50f;
    private int rainParticlesPerSec = 10000;
    
    private AudioNode rainAudio;
    
    
    
    
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
        initRain();
        initRainAudio();
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
    public void update(float tpf){
        rain.setLocalTranslation(main.getCamera().getLocation());
    }
    
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
     * Projects a spherical texture onto the inside of a 
     * very large sphere centered on the map. Gives the illusion of distant
     * scenery like sun, cloudes, water. 
     */
    private void initSky()
    {
        // viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        main.getRootNode().attachChild(SkyFactory.createSky(main.getAssetManager(), 
                "Textures/Skysphere.jpg", SkyFactory.EnvMapType.SphereMap));
    }
    
    /*
     * This method creates rain particles in a sphere centered on the camera.
     *
     * inspired by https://jmonkeyengine.github.io/wiki/jme3/beginner/hello_effects.html
    */
    private void initRain()  
    {
    rain = new ParticleEmitter(
            "rain", ParticleMesh.Type.Triangle, rainParticlesPerSec);
    rain.setShape(new EmitterSphereShape(Vector3f.ZERO, rainEmitterRadius));
    Material material = new Material(main.getAssetManager(),
            "Common/MatDefs/Misc/Particle.j3md"); // Emitter emits Particle objects
    material.setTexture("Texture", main.getAssetManager().loadTexture(
            "Effects/raindrop.png")); // The image is Texture type, white line
    rain.setMaterial(material);  
    rain.setLocalTranslation(main.getCamera().getLocation());
    rain.getParticleInfluencer().setInitialVelocity(new Vector3f(0.0f, -1.1f, 0.0f));
    rain.setGravity(0, rainGravity, 0);
    rain.setLowLife(2); // Each particle lasts atleast 2 
    rain.setHighLife(5); // Each particles lasts at most 5
    rain.setImagesX(1); // Set x proportion of texture
    rain.setImagesY(10); // Set y proportion of texture
    rain.setStartSize(1f); // Particle created with size 1
    rain.setEndSize(0f); // Particle shrinks to 0
    rain.setStartColor(new ColorRGBA(1.0f, 1.0f, 1.0f, 1f)); // Start white
    rain.setEndColor(new ColorRGBA(1f, 1f, 1.0f, 1f)); // End white
    rain.setParticlesPerSec(rainParticlesPerSec); // How many particles per sec  
    main.getRootNode().attachChild(rain);
    }
    
    /**
     * This method adds a rain sound effect to the map when there is rain.
     */
    private void initRainAudio(){
    rainAudio = new AudioNode(main.getAssetManager(), "Sounds/Rain.wav", false);
    rainAudio.setPositional(false); //The noise is not positional
    rainAudio.setLooping(true); //While the noise is playing it will loop
    rainAudio.setVolume(2); //Sets the volume of the noise
    main.getRootNode().attachChild(rainAudio);
    rainAudio.play();
    }

    
   
}