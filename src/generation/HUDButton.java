package generation;

import java.awt.Rectangle;

/**
 * This class provides a UI element that can be used to register
 *      and handle input in conjunction with the HUDInputManager.
 * @author jeffr
 */
public class HUDButton 
{
    private final Rectangle BUTTON_BOUNDS;
    
    /**
     * Creates a new HUDButton with the given bounds
     * @param buttonBounds THe Rectangle defining button bounds
     */
    public HUDButton(Rectangle buttonBounds)
    {
        BUTTON_BOUNDS = buttonBounds;
    }
    
    /**
     * @return The Rectangle defining the bounds of this button 
     */
    public Rectangle getButtonBounds()
    {
        return BUTTON_BOUNDS;
    }
    
    /**
     * This code is called when the button is clicked.
     */
    public void onClick()
    {
        
    }
}
