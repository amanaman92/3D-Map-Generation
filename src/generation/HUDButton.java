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
    private HUDButtonListener hudButtonListener;
    
    /**
     * Creates a new HUDButton with the given bounds
     * @param buttonBounds THe Rectangle defining button bounds
     */
    public HUDButton(Rectangle buttonBounds)
    {
        BUTTON_BOUNDS = buttonBounds;
    }
    
    /**
     * Creates a new HUDButton with the given bounds
     * @param buttonBounds The Rectangle defining button bounds
     * @param hudButtonListener The Listener object for button clicks
     */
    public HUDButton(Rectangle buttonBounds, HUDButtonListener hudButtonListener)
    {
        BUTTON_BOUNDS = buttonBounds;
        setHUDButtonListener(hudButtonListener);
    }
    
    /**
     * Sets the Listener object for button clicks.
     *      This object's onAction will be called when the button is clicked.
     * @param hudButtonListener The Listener object for button clicks
     */
    public final void setHUDButtonListener(HUDButtonListener hudButtonListener)
    {
        this.hudButtonListener = hudButtonListener;
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
        hudButtonListener.onAction();
    }
}
