package generation;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * This class control I / O and display for the HUD (GUI).
 * @author jeffr
 */
public class HUDAppState extends BaseAppState
{
    private Main main;
    private Node guiNode;
    private BitmapFont guiFont;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

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
        createMainMenu();
    }
    
    private void createMainMenu()
    {
        BitmapText hpText = new BitmapText(guiFont, false);
        hpText.setText("YAY! TEXT DISPLAY WORKS");
        hpText.setSize(guiFont.getCharSet().getRenderedSize());
        hpText.setColor(ColorRGBA.Red);
        hpText.setSize(50);
        hpText.setLocalTranslation(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2, 0);
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
        
    }
}