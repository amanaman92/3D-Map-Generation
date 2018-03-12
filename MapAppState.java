package generation;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
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
import com.jme3.audio.AudioNode;


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
    private final DirectionalLight DIRECTIONAL_LIGHT = new DirectionalLight();
    
    
    private ParticleEmitter rain;
    private float rainGravity = 500f;
    private float rainEmitterRadius = 1000f;
    private float rainEmitterHeight = 50f;
    private int rainParticlesPerSec = 10000;
    
    private AudioNode rainAudio;
    private AudioNode fireAudio;
    private AudioNode natureAudio;
    private AudioNode windAudio;
     
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
        initFireAudio();
        initNatureAudio();
        initWindAudio();
        HUDAppState h = new HUDAppState();
        switch(HUDAppState.getWeatherIndex()){
            case 0: natureAudio.play();
                break;
                
            case 1: rainAudio.play();
                    main.getRootNode().attachChild(rain);
                    break;
    
        }
        /** Previous way of getting the weather string that was inputted, which led to null pointer exception due to 
         * HUDAppState not being made. Will attempt to do thru main.
         * 
         *System.out.println("Hello! If you see this, you may be about to hit null pointer exception!");
         //Clearly, HUDAppState is not initalized at this time, which means that there has to be a way to do so.
         hudAppState = new HUDAppState();
         System.out.println("hudAppState initialized to HUDAppState program");
         if(hudAppState.getWeather().equals("Rain")){
            initRain();
        } else if(HUDAppState.getWeather().equals("Snow")){
            
        } 
        */
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
        DIRECTIONAL_LIGHT.setDirection(new Vector3f(-1, -1, 0));
        DIRECTIONAL_LIGHT.setColor(ColorRGBA.White);
        
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


    
    //Idea: Use the main from MapAppState to be able to try and detach the child of rain on Main.java
    //Which is where the hudAppState is located at. When that is done, you'd be able to control rain.
    //I'd need a method to be able to do so by returning main, then doing the getRootNode and detachChild via main.
    
    
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
    rain.getParticleInfluencer().setInitialVelocity
        (new Vector3f(0.0f, -1.1f, 0.0f));
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
    //main.getRootNode().attachChild(rain); Leave the attachChild part to me please. Once you finish, push it and let me 
    //me know so that I can get to integrating the code.
    }

    /**
     * This method creates and sets up the different parts of the audio
     * for rain so that when it is raining it can be used
     */
    private void initRainAudio(){
    rainAudio = new AudioNode(main.getAssetManager(), "Sounds/Rain.wav", false);
    rainAudio.setPositional(false); //The noise is not positional
    rainAudio.setLooping(true); //While the noise is playing it will loop
    rainAudio.setVolume(2); //Sets the volume of the noise
    main.getRootNode().attachChild(rainAudio);
    }
    
    /**
     * This method creates and sets up the different parts of the audio
     * for a tree burning so that when the trees are on fire it can be used
     */
    private void initFireAudio(){
    fireAudio = new AudioNode(main.getAssetManager(), "Sounds/Fire.wav", false);
    fireAudio.setPositional(false); //The noise is not positional
    fireAudio.setLooping(true); //The noise loops as it is played
    fireAudio.setVolume(2); //Sets the volume of the noise
    main.getRootNode().attachChild(fireAudio);
    fireAudio.play();
    }
    
    /**
     * This method creates and sets up the different parts of the audio for a 
     * nature ambiance sound so that when there is no specific weather it can be used
     */
   private void initNatureAudio(){
    natureAudio = new AudioNode(main.getAssetManager(), "Sounds/Nature Ambiance.wav", false);
    natureAudio.setPositional(false); //The noise is not positional
    natureAudio.setLooping(true); //The noise loops as it is played
    natureAudio.setVolume(2); //Sets the volume of the noise
    main.getRootNode().attachChild(natureAudio);
   }
   
   /**
    * This method creates and sets up the different parst of the audio
    * for a wind audio sound so that when there is wind it can be used
    */
   private void initWindAudio(){
       windAudio = new AudioNode(main.getAssetManager(), "Sounds/Wind.wav", false);
       windAudio.setPositional(false);
       windAudio.setLooping(true);
       windAudio.setVolume(2);
       main.getRootNode().attachChild(windAudio);
   }
}