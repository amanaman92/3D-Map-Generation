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
import java.util.ArrayList;
import java.util.Random;


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
    
    // variables for rain particles
    private ParticleEmitter rain;
    private float rainGravity = 500f;
    private float rainEmitterRadius = 1000f;
    private float rainEmitterHeight = 50f;
    private int rainParticlesPerSec = 10000;
    
     // variables for snow particles
    private ParticleEmitter snow;
    private float snowGravity = 20f;
    private float snowEmitterRadius = 1000f;
    private float snowEmitterHeight = 50f;
    private int snowParticlesPerSec = 10000;
    
    // variables for fires
    private ArrayList<ParticleEmitter> fires = new ArrayList<>();
    
    // audio variables
    private AudioNode rainAudio;
    private AudioNode fireAudio;
    private AudioNode natureAudio;
    private AudioNode windAudio;
    private AudioNode thunderAudio;
    private AudioNode birdRainAudio;
    private AudioNode birdAudio;
    private AudioNode meadowlarkAudio;
     
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
        initSnow();
        initTreeFires();
        initRainAudio();
        initFireAudio();
        initThunderAudio();
        initNatureAudio();
        initWindAudio();
        initBirdRainAudio();
        initBirdAudio();
        initMeadowlarkAudio();
        Random r = new Random();
        int ran;
        HUDAppState h = new HUDAppState();
        switch(HUDAppState.getWeatherIndex()){
            case 0: 
                ran = r.nextInt(4);
                if(ran == 0){
                    natureAudio.play();
                }
                else if(ran == 1){
                    windAudio.play();
                }
                else if(ran == 2){
                    birdAudio.play();
                }
                else{
                    meadowlarkAudio.play();
                }
                break;
                
            case 1: main.getRootNode().attachChild(rain);
                    ran = r.nextInt(3);
                    if(ran==0){
                        rainAudio.play();
                    }
                    else if(ran==1){
                        thunderAudio.play();
                    }
                    else{
                        birdRainAudio.play();
                    }
                    break;
            
            case 2: main.getRootNode().attachChild(snow);
                    break;
                    
            case 3: for (ParticleEmitter fire: fires) {
                        fireAudio.play();
                        main.getRootNode().attachChild(fire); 
                    }
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
     * This method creates snow particles in a sphere centered on the camera.
     *
     * inspired by https://jmonkeyengine.github.io/wiki/jme3/beginner/hello_effects.html
    */
    private void initSnow()  
    {
    snow = new ParticleEmitter(
            "snow", ParticleMesh.Type.Triangle, snowParticlesPerSec);
    snow.setShape(new EmitterSphereShape(Vector3f.ZERO, snowEmitterRadius));
    Material material = new Material(main.getAssetManager(),
            "Common/MatDefs/Misc/Particle.j3md"); // Emitter emits Particle objects
    material.setTexture("Texture", main.getAssetManager().loadTexture(
            "Effects/snowdrop.png")); // The image is Texture type, white blot
    snow.setMaterial(material);  
    snow.setLocalTranslation(main.getCamera().getLocation()); //initial snow pos
    snow.getParticleInfluencer().setInitialVelocity
        (new Vector3f(0.0f, -1.1f, 0.0f));
    snow.setGravity(0, snowGravity, 0);
    snow.setLowLife(2); // Each particle lasts atleast 2 
    snow.setHighLife(5); // Each particles lasts at most 5
    snow.setImagesX(1); // Set x proportion of texture
    snow.setImagesY(1); // Set y proportion of texture
    snow.setStartSize(1f); // Particle created with size 1
    snow.setEndSize(0f); // Particle shrinks to 0
    snow.setStartColor(new ColorRGBA(1.0f, 1.0f, 1.0f, 1f)); // Start white
    snow.setEndColor(new ColorRGBA(1f, 1f, 1.0f, 1f)); // End white
    snow.setParticlesPerSec(snowParticlesPerSec); // How many particles per sec 
    snow.getParticleInfluencer().setVelocityVariation(1.1f);
    //main.getRootNode().attachChild(snow); 
    //Leave the attachChild part to me please. Once you finish, push it and let me 
    //me know so that I can get to integrating the code.
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
     * This method is meant to be called during the map initialization.
     * Iterates through the list of trees and makes a fire at each tree.
     */
    private void initTreeFires() {
        for (Tree tree: main.getTREES()){
            makeFire(tree.loc(), tree.getScale());
        }       
    }
    
    /**
     * This method creates a fire particle effect
     * inspired by https://jmonkeyengine.github.io/wiki/jme3/beginner/hello_effects.html
     * @param vec specifies location of the fire, centered on a tree
     * @param scale slides the fire up/down depending on the tree scale
     */
    private void makeFire(Vector3f vec, float scale) {
    ParticleEmitter fire =
            new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
    Material mat_red = new Material(main.getAssetManager(),
            "Common/MatDefs/Misc/Particle.j3md");
    mat_red.setTexture("Texture", main.getAssetManager().loadTexture(
            "Effects/flame.png"));
    fire.setMaterial(mat_red);
    fire.setImagesX(2);
    fire.setImagesY(2);
    fire.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   
    fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f));
    fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 20, 0));
    fire.setLocalTranslation(vec.add(0f, scale* scale * 25, 0f));
    fire.setStartSize(15f * scale);
    fire.setEndSize(0f * scale);
    fire.setGravity(0, 1, 0);
    fire.setLowLife(1f);
    fire.setHighLife(3f);
    fire.getParticleInfluencer().setVelocityVariation(0.3f);
    fires.add(fire);
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
    * This method creates and sets up the different parts of the audio
    * for a wind audio sound so that when there is wind it can be used
    */
   private void initWindAudio(){
    windAudio = new AudioNode(main.getAssetManager(), "Sounds/Wind.wav", false);
    windAudio.setPositional(false); //The noise is not positional
    windAudio.setLooping(true); //The noise loops as it is played
    windAudio.setVolume(2); //Sets the volume of the noise
    main.getRootNode().attachChild(windAudio);
   }
   
   /**
    * This method creates and sets up the different parts of the audio
    * for a thunder sound so that if there is a thunderstorm it can be used
    */
   private void initThunderAudio(){
       thunderAudio = new AudioNode(main.getAssetManager(), "Sounds/Thunderstorm.wav", false);
       thunderAudio.setPositional(false); //The noise is not positional
       thunderAudio.setLooping(true); //The noise loops as it is played
       thunderAudio.setVolume(2); //Sets the volume of the noise
       main.getRootNode().attachChild(thunderAudio);
   }
   
   /**
    * This method creates and sets up the different parts of the audio
    * for a birds in the rain noise so it can be another sound option for the rain
    */
   private void initBirdRainAudio(){
       birdRainAudio = new AudioNode(main.getAssetManager(), "Sounds/BirdRain.mp3", false);
       birdRainAudio.setPositional(false); //The noise is not positional
       birdRainAudio.setLooping(true); //The noise loops as it is played
       birdRainAudio.setVolume(2); //Sets the volume of the noise
       main.getRootNode().attachChild(birdRainAudio);
   }
   
   /**
    * This method creates and sets up the different parts of the audio
    * for a bird noise so that there can be a different sound option for
    * the sunny weather condition
    */
   private void initBirdAudio(){
       birdAudio = new AudioNode(main.getAssetManager(), "Sounds/Bird.wav", false);
       birdAudio.setPositional(false); //The noise is not positional
       birdAudio.setLooping(true); //The noise loops as it is played
       birdAudio.setVolume(2); //Sets the volume of the noise
       main.getRootNode().attachChild(birdAudio);
   }
   
   /**
    * This method creates and sets up the different parts of the audio
    * for a different bird noise so that there can be different sound options
    * for the sunny weather condition
    */
   private void initMeadowlarkAudio(){
       meadowlarkAudio = new AudioNode(main.getAssetManager(), "Sounds/Meadowlark.wav", false);
       meadowlarkAudio.setPositional(false); //The noise is not positional
       meadowlarkAudio.setLooping(true); //The noise loops as it is played
       meadowlarkAudio.setVolume(2); //Sets the volume of the noise
       main.getRootNode().attachChild(meadowlarkAudio);
   }
}