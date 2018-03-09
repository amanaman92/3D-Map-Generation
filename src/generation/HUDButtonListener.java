package generation;

/**
 * This class serves to provide an extendable abstract
 *      for use in HUDButtons. It should be extended in-line
 *      (anonymous-class) to pass as a parameter to
 *      a HUDButton.
 * @author jeffr
 */
public abstract class HUDButtonListener 
{
    public abstract void onAction();
    
}
