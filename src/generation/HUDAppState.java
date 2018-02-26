package generation;
import java.awt.*;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import java.awt.Dimension;
import com.jme3.ui.Picture;
import com.jme3.asset.AssetManager;
/**
 * This class control I / O and display for the HUD (GUI).
 * @author Cary Wang
 */
public class HUDAppState extends BaseAppState{
    private Main main;
    private Node guiNode;
    private BitmapFont guiFont;
    private Dimension screenSize;
    private final HUDInputManager HUD_INPUT_MANAGER = new HUDInputManager();

    private boolean launchGame = true; //change to false when GUI added
    
    /**
     * This function does basic initialiation of the AppState.
     *      Not called directly from user code.
     * @param app The application object that controls the program
     */
    @Override
    protected void initialize(Application app)
    {
        main = Main.getMain();
        guiNode = main.getGuiNode();
        guiFont = main.getGuiFont();
        screenSize = main.getScreenSize();
        createMainMenu();
    }
    
    /**
     * Creates the main menu, where most or all UI elements should be placed
     * 
     * Will need to be able to create much of
     */
    private void createMainMenu()
    {
        HUDButton b = new HUDButton(new Rectangle(20, 20, 20, 20));
        b.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    public void onAction() 
                    {
                        //do something with the buton
                    }
                });
        HUD_INPUT_MANAGER.addButton(b);
 
                
        BitmapText hpText = new BitmapText(guiFont, false);
        hpText.setText("YAY! TEXT DISPLAY WORKS");
        
        //Line 50 - Line 56 is mine.
        AssetManager a = main.getAssetManager();
        Picture guiPict = new Picture("guiBackground");
        guiPict.setImage(a, "GUIComponent/guiBG.png", true);
        guiPict.setWidth(screenSize.width/2);
        guiPict.setHeight(screenSize.height);
        guiPict.setPosition(screenSize.width/4, screenSize.height/4);
        guiNode.attachChild(guiPict);
        
        
        /** TODO: Add some integer variable as a string to be placed on the screen, and then be able to
         *  have it increase by 1 per single press, or decrease by 1 per single press.
         * 
         * To do that(Very high level, may overlook the low level stuff); I will need to
         * 
         * 1. Make a button.
         * 
         * 2. Set that button to increase variable number by 1 or - 1.
         * 
         * 3. Position two buttons together to serve as up/down counters
         * 
         * 4. Have the text update the number. That is, if number is 1, set it to be 1. 
         * If nothing changes from 0, keep it at 0. 
         * 
         **/ 

        hpText.setSize(guiFont.getCharSet().getRenderedSize());
        hpText.setColor(ColorRGBA.Red);
        hpText.setSize(50);
        hpText.setLocalTranslation(screenSize.width / 2, screenSize.height / 2, 0);
        guiNode.attachChild(hpText);
    }

    /**
     * This cleans up the AppState for removal. Not called
     *      directly from user code.
     * @param app The application object that controls the program
     */
    @Override
    protected void cleanup(Application app)
    {
        
    }

    /**
     * This code runs when the appstate is attached to the
     *      stateManager.
     */
    @Override
    protected void onEnable()
    {
        
    }

    /**
     * This code runs when the appstate is dettached from the
     *      stateManager.
     */
    @Override
    protected void onDisable() 
    {
        
    }
    
    /**
     * This is the AppState Update loop. It runs every frame and
     *      should be responsible for the majority of in-game
     *      logic.
     * @param tpf The time taken to run by the last frame.
     */
    @Override
    public void update(float tpf)
    {
        if(launchGame)
        {
            launchGame = false;
            main.beginGame();
        }
    }
}