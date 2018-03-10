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
    
    public static int treeNum = 100;
    public static int weatherIndex = 0;
    private static String [] weathers = {"Clear", "Rain"};
    private final HUDInputManager HUD_INPUT_MANAGER = new HUDInputManager();
    private BitmapText treeNumText, weatherText, createTerrainText;
    //TODO: Test out to see if the launchGame does it job when GUI is added. 
    //I predict that if true, it will run both the text and gui. What about when false? Will it run?
    //Or will it be blank with the gui displayed? Will be changed by a method called launchGame.
    private boolean launchGame = false; //change to false when GUI added; it will bring you to the screen.
    
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
    
    //Problem: You are creating the text but you are not setting it; instead, you are making it over and over again.
    //Solution: Have the text be set to new value. Only obstacle is, 
    /*
    public void createText(String s, int size, int posX, int posY){
        //Poor strategy down there; you passed by value, preventing global variable from being set.
        BitmapText hpText = new BitmapText(guiFont, false);
        hpText.setText(s);
        hpText.setSize(guiFont.getCharSet().getRenderedSize());
        hpText.setColor(ColorRGBA.Red);
        hpText.setSize(size);
        hpText.setLocalTranslation(posX, posY, 0);
        guiNode.attachChild(hpText);
    }
    
    public void setTreeText(String s){
        
        
    }
    
    */

    public String getWeather(int index){
        return weathers[index];
    }
    /*
    public void printTest(){
            System.out.println("Hi! Can you see me? If you can, that means that you are able to go inside the .setHUDButton Listener class!");
    }
    */
    public void setSizeAtCenter(){
        
    
    }
    private void createMainMenu()
    {
        /** Will need to specifically create buttons to name in order to avoid confusion.
         * 
         *
         *
         */
        
        //2 button for tree count(up and down); two(up and down) for weather, one for creating the terrain itself.
        
        //TODO: set each coords to be less hardcoded and more for the screen size.
        
        //createText("Sigh", screenSize.width / 4, screenSize.width / 2, screenSize.height / 2);
        Rectangle incTreeButtonBox = new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2);
        Rectangle decTreeButtonBox = new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2);
        Rectangle weatherUpButtonBox = new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2);
        Rectangle weatherDownButtonBox = new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2);
        Rectangle createTerrainButtonBox = new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2);

        //Rectangle createTerrainBox = new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2);
        HUDButton incTree = new HUDButton(new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2));
        HUDButton decTree = new HUDButton(new Rectangle(30, 30, 20, 20));
        HUDButton weatherUp = new HUDButton(new Rectangle(40, 40, 20, 20));
        HUDButton weatherDown = new HUDButton(new Rectangle(50, 50, 20, 20));
        HUDButton createTerrainButton = new HUDButton(incTreeButtonBox);
        HUD_INPUT_MANAGER.addButton(decTree);
        HUD_INPUT_MANAGER.addButton(weatherUp);
        HUD_INPUT_MANAGER.addButton(weatherDown);
        HUD_INPUT_MANAGER.addButton(createTerrainButton);
        
        /* Very interesting... It turns out that if you eliminate the incTreeButton that had the button at what is
           supposed to be the same exact position as the createTerrainButton, it would actually work.
        
           Thus, when the incTree HUDButton WAS there, and at the same spot as createTerrainButton, even with the onAction
           and setListener part commented out, it would still give null pointer exception.
        
            Possible solution is to place the two buttons at different positions.
        */
        
        //TODO: Make a setter method for increasing the private treeNum Var by 1, as public method.
        
        HUD_INPUT_MANAGER.uncapMouse(); 
        System.out.println("Before setListener part");
        String trees = "" + treeNum;
        System.out.println("Before instantiating treeNumText");
        
        //createText(trees, screenSize.width / 4, screenSize.width / 2, screenSize.height / 2);
         
        treeNumText = new BitmapText(guiFont, false);
        treeNumText.setText(trees);
        treeNumText.setSize(guiFont.getCharSet().getRenderedSize());
        treeNumText.setColor(ColorRGBA.Red);
        treeNumText.setSize(screenSize.width / 64);
        treeNumText.setLocalTranslation(screenSize.width / 2, screenSize.height / 4, 1);
        guiNode.attachChild(treeNumText);
        
       
        weatherText = new BitmapText(guiFont, false);
        weatherText.setText(trees);
        weatherText.setSize(guiFont.getCharSet().getRenderedSize());
        weatherText.setColor(ColorRGBA.Red);
        weatherText.setSize(screenSize.width / 64);
        weatherText.setLocalTranslation(screenSize.width / 2, screenSize.height / 2, 1);
        guiNode.attachChild(weatherText);
        
        
        createTerrainText = new BitmapText(guiFont, false);
        createTerrainText.setText("Make Terrain!");
        createTerrainText.setSize(guiFont.getCharSet().getRenderedSize());
        createTerrainText.setColor(ColorRGBA.Red);
        createTerrainText.setSize(screenSize.width / 64);
        createTerrainText.setLocalTranslation(screenSize.width / 2, screenSize.height / 4, 1);
        guiNode.attachChild(createTerrainText);
        
        System.out.println("After instantiating treeNumText; there would be a problem if it is still null pointer at this point");
        
        incTree.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    public void onAction() 
                    {
                        if(treeNum == 10){
                            treeNum = 0;
                            System.out.println("Printing treeNum after this statement");
                            String s = "" + treeNum;
                            System.out.println("setting treeNumText to new treeNum after this");
                            //Problem located right below you.
                            treeNumText.setText(s);
                            System.out.println("Inc tree");
                        } else{
                        incTree();
                        System.out.println("Printing treeNum after this statement");
                        String s = "" + treeNum;
                        System.out.println("setting treeNumText to new treeNum after this");
                        //Problem located right below you.
                        treeNumText.setText(s);
                        System.out.println("Inc tree");
                        }
                        
                    }
                });
        
        decTree.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    public void onAction() 
                    {
                        if(treeNum == 0){
                            
                        } else {
                            decTree();
                            System.out.println("Printing treeNum after this statement");
                            String s = "" + treeNum;
                            System.out.println("setting treeNumText to new treeNum after this");
                            treeNumText.setText(s);
                            System.out.println("dec tree");
                        
                        }
                    }
                });

        weatherUp.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    public void onAction() 
                    {
                        weatherIndex++;
                        System.out.println("weatherIndex++ done");
                        String s = weathers[weatherIndex];
                        System.out.println("String is 's' now weather");
                        
                        //Note to self: Why can't you just directly increment weatherIndex++ and instead use a getter method for it?
                        //That is, why would you have to be in a position of saying to make it final?
                        
                    }
                });
        
        weatherDown.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    public void onAction() 
                    {
                        weatherIndex--;
                        System.out.println("weatherIndex-- done");
                        String s = weathers[weatherIndex];
                        System.out.println("String is 's' now weather");
                    }
                });
        
        createTerrainButton.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    /* It seems that no matter if the game is already activated or not, the mere act 
                       of pressing on the button alone causes null pointer exception.
                       It won't even print the statements down there, it will just get clicked and
                       get a null pointer exception.
                       
                    */
                    public void onAction() 
                    {
                        System.out.println("You just clicked on the createTerrain Button");
                        launchGame = true;
                        System.out.println("Shouldn't you launch game at this point?");
                        guiNode.detachAllChildren();
                        HUD_INPUT_MANAGER.capMouse();
                    }
                });
        
        //Mistake: You placed HUD_INPUT_MANAGER.addButton down here rather than up there; that 
        //caused the problem of ExceptionInInitializer in the first place when running. It is a
        //run time error.
        
        AssetManager a = main.getAssetManager();
        Picture guiPict = new Picture("guiBackground");
        guiPict.setImage(a, "GUIComponent/guiBG.png", true);
       
        /*
            Larger version. It appears to be okay. Factor is that for every 2x of ratio,
            you must decrease the posX by screenSize.width /8 more and decrease by screenSize.height / 4 more.
        */
        
        int imageCenterPosX = screenSize.width/4;
        int imageCenterPosY = screenSize.height/2 - screenSize.height / 2;
        guiPict.setWidth(screenSize.width/2);
        guiPict.setHeight(screenSize.height/1);
        
        /*
        int imageCenterPosX = screenSize.width/4 + screenSize.width / 8;
        int imageCenterPosY = screenSize.height/2 - screenSize.height / 4;
        guiPict.setWidth(screenSize.width/4);
        guiPict.setHeight(screenSize.height/2);
        */
        //guiPict.setPosition(screenSize.width/4, imageCenterPosY);
        guiPict.setPosition(imageCenterPosX, imageCenterPosY);
        guiNode.attachChild(guiPict);
        
    }
                
   public void incTree(){
        treeNum++;
    }
    public void decTree(){
        treeNum--;
    }
    public void incWeatherChoice(){
        weatherIndex++;
    }
    public void decWeatherChoice(){
        weatherIndex--;
    }
    public void launchGame(){
        launchGame = true;   
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

