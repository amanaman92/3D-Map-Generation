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
 * @author jeffr
 */
public class HUDAppState extends BaseAppState{
    private Main main;
    private Node guiNode;
    private BitmapFont guiFont;
    private Dimension screenSize;

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