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
    
    private int treeNum = 0;
    private int weatherIndex = 0;
    private String [] weathers = {"Sunny", "Rain", "Snow"};
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
        Rectangle incTreeButton = new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2);
        //Rectangle createTerrainBox = new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2);
        //HUDButton incTree = new HUDButton(new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2));
        HUDButton decTree = new HUDButton(new Rectangle(30, 30, 20, 20));
        HUDButton weatherUp = new HUDButton(new Rectangle(40, 40, 20, 20));
        HUDButton weatherDown = new HUDButton(new Rectangle(50, 50, 20, 20));
        //HUDButton createTerrainButton = new HUDButton(new Rectangle(20, 20, 20, 20));
        HUDButton createTerrainButton = new HUDButton(incTreeButton);
        //HUD_INPUT_MANAGER.addButton(incTree);
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
         /*
        treeNumText = new BitmapText(guiFont, false);
        treeNumText.setText(trees);
        treeNumText.setSize(guiFont.getCharSet().getRenderedSize());
        treeNumText.setColor(ColorRGBA.Red);
        treeNumText.setSize(screenSize.width / 4);
        treeNumText.setLocalTranslation(screenSize.width / 2, screenSize.height / 2, 0);
        guiNode.attachChild(treeNumText);
        
       
        weatherText = new BitmapText(guiFont, false);
        weatherText.setText(trees);
        weatherText.setSize(guiFont.getCharSet().getRenderedSize());
        weatherText.setColor(ColorRGBA.Red);
        weatherText.setSize(size);
        weatherText.setLocalTranslation(posX, posY, 0);
        guiNode.attachChild(weatherText);
        
        */
        createTerrainText = new BitmapText(guiFont, false);
        createTerrainText.setText("Make Terrain!");
        createTerrainText.setSize(guiFont.getCharSet().getRenderedSize());
        createTerrainText.setColor(ColorRGBA.Red);
        createTerrainText.setSize(screenSize.width / 4);
        createTerrainText.setLocalTranslation(screenSize.width / 2, screenSize.height / 2, 0);
        guiNode.attachChild(createTerrainText);
        
        System.out.println("After instantiating treeNumText; there would be a problem if it is still null pointer at this point");
        /*
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
        /*
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
        //TODO: Be able to find a way to access the time duration needed to run the program.
        //This will allow you to 
        weatherUp.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    public void onAction() 
                    {
                        weatherIndex++;
                        createText(weathers[weatherIndex]);
                        System.out.println("Before setListener part");
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
                        createText(weathers[weatherIndex]);
                    }
                });
        
        weatherDown.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    public void onAction() 
                    {
                        weatherIndex--;
                        createText(weathers[weatherIndex]);
                    }
                });
        */
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
                    }
                });
        
        //Mistake: You placed HUD_INPUT_MANAGER.addButton down here rather than up there; that 
        //caused the problem of ExceptionInInitializer in the first place when running. It is a
        //run time error.
        
        AssetManager a = main.getAssetManager();
        Picture guiPict = new Picture("guiBackground");
        guiPict.setImage(a, "GUIComponent/guiBG.png", true);
        int imageCenterPosX = screenSize.width/4 + screenSize.width / 8;
        int imageCenterPosY = screenSize.height/2 - screenSize.height / 4;
        guiPict.setWidth(screenSize.width/4);
        guiPict.setHeight(screenSize.height/2);
        //guiPict.setPosition(screenSize.width/4, imageCenterPosY);
        guiPict.setPosition(imageCenterPosX, imageCenterPosY);
        guiNode.attachChild(guiPict);
        
        
        //IMPORTANT TO NOTE: AN IMAGE IS CENTERED ON THE BOTTOM LEFT PART, WHERE IT IS CONSIDERED TO BE (0,0).
        //Must give diagonal correction to solve.
        
        
        
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
        /*
        BitmapText hpText = new BitmapText(guiFont, false);
        hpText.setText("YAY! TEXT DISPLAY WORKS");
        hpText.setSize(guiFont.getCharSet().getRenderedSize());
        hpText.setColor(ColorRGBA.Red);
        hpText.setSize(50);
        hpText.setLocalTranslation(screenSize.width / 2, screenSize.height / 2, 0);
        guiNode.attachChild(hpText);
        */
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

