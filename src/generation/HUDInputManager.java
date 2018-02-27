package generation;

import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class allows HUDButtons to be mapped to input to allow
 *      event-driven actions
 * @author jeffr
 */
public class HUDInputManager implements ActionListener
{
    private final ArrayList<HUDButton> HUD_BUTTONS = new ArrayList<>();
    private final InputManager INPUT_MANAGER;
    private final String LEFT_CLICK = "LeftClick";
    private final Dimension SCREEN_SIZE;
    
    /**
     * Creats a new HUDInputManager
     */
    public HUDInputManager()
    {
        System.out.println("1");
        Main main = Main.getMain();
        System.out.println("2");
        if (main == null) {
            System.out.println("NULL!");
        }
        //So when main tries to get input manager, it is preventing the 
        INPUT_MANAGER = main.getInputManager();
        //INPUT_MANAGER = main.getInputManager();
        System.out.println("3");
        SCREEN_SIZE = main.getScreenSize();
        initInputMapping();
    }
    
    /**
     * Set up input responiveness for clicks
     */
    private void initInputMapping()
    {
        INPUT_MANAGER.addMapping(LEFT_CLICK, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        INPUT_MANAGER.addListener(this, LEFT_CLICK);
    }

    /**
     * This method is called when input is received, and maps that input
     *      to the correct button for handling.
     * @param name The name of the action performed
     * @param isPressed If the tiggering button is currently held down
     * @param tpf The time per frame of the last frame
     */
    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {
        Point clickLocation = vector2fToPoint(INPUT_MANAGER.getCursorPosition());
        if(name.equals(LEFT_CLICK) && isPressed)
        {
            System.out.println("LEFT CLICK Xpos: " + clickLocation.x);
            System.out.println("LEFT CLICK Ypos: " + clickLocation.y);
            for(HUDButton HUD_Button : HUD_BUTTONS)
            {
                if(HUD_Button.getButtonBounds().contains(clickLocation))
                {
                    HUD_Button.onClick();
                    //onClicked goes into onAction, which is in turn referring to the HUDButtonListener Abs method.
                }
            }
        }
    }
    
    /**
     * Adds a button to receive input.
     * @param toAdd the HUDButton to add
     */
    public void addButton(HUDButton toAdd)
    {
        HUD_BUTTONS.add(toAdd);
    }
    
    /**
     * Removes a button from the manager, so it is no longer
     *      responds to clicks. THe button will not disapear from
     *      the UI, however.
     * @param toRemove the HUDButton to remove
     */
    public void removeButton(HUDButton toRemove)
    {
        Iterator it = HUD_BUTTONS.iterator();
        while(it.hasNext())
        {
            HUDButton hudbutton = (HUDButton) it.next();
            if(hudbutton == toRemove)
            {
                it.remove();
            }
        }
    }
    
    /**
     * @param v, a vector with screen coordinates having (0,0) at the bottom left
     * @return a Point, with the coordiantes having (0,0) at the top left
     */
    private Point vector2fToPoint(Vector2f v)
    {
        return new Point((int) v.x, SCREEN_SIZE.height - (int) v.y);
    }
}