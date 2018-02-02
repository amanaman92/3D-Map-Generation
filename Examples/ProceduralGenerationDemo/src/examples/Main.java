package examples;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;
import java.util.Random;

/**
 * This class contains basic data needed by the entire game.
 *      Most game daa should be localized to appstates instead.
 * @author jeffr
 */
public class Main extends SimpleApplication 
{
    private final Node GEOGRAPHY = new Node();
    private final Random GENERATOR = new Random();
    private final int SIZE = 1024,
            SCALE = 1,
            MAP_SIZE = SIZE * SCALE;
    private final BulletAppState BULLET_APP_STATE = new BulletAppState();
    private static final Main MAIN = new Main();
    private final AppState GAME_APP_STATE = new GameAppState();
    
    //Constructcor
    /**
     * This is private so no other Main objects can be made
     *      (that would probably crash the program). The only
     *      Main object that can exist is examples.Main.MAIN 
     *      (see the instance variables)
     */
    private Main(){}
    
    /**
     * Program execution begins here. DO NOT modify this method
     *      unless you have a very secific reason you must place
     *      code here. Put code in simpleInitApp() instead.
     * @param args 
     */
    public static void main(String[] args)
    {
        MAIN.start();
    }

    @Override
    /**
     * This just inits app States and background game logic. All
     *      Other inits should be done in separate app states!
     */
    public void simpleInitApp()
    {
        stateManager.attach(BULLET_APP_STATE);
        stateManager.attach(GAME_APP_STATE);
    }
    
    /**
     * @return The Main object
     */
    public static Main getMain()
    {
        return MAIN;
    }
    
    /**
     * @return The BulletAppState, used to process physics data
     */
    public BulletAppState getBulletAppState()
    {
        return BULLET_APP_STATE;
    }
}