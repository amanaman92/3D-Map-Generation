package generation;

import java.*;
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
    
    private static int treeNum = 0;
    private static int weatherIndex = 0;
    private static String [] weathers = {"Clear", "Rain"};
    private final HUDInputManager HUD_INPUT_MANAGER = new HUDInputManager();
    private BitmapText treeNumText, weatherText, createTerrainText;
    private final int NUM_OF_ARROWS = 3; 

    //TODO: Test out to see if the launchGame does it job when GUI is added. 
    //I predict that if true, it will run both the text and gui. What about when false? Will it run?
    //Or will it be blank with the gui displayed? Will be changed by a method called launchGame.
    private static boolean launchGame = false; //change to false when GUI added; it will bring you to the screen.
    
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
    
    public BitmapText setTextSettings(BitmapText toMake, String s, int posX, int posY){
        toMake.setText(s);
        toMake.setSize(guiFont.getCharSet().getRenderedSize());
        toMake.setColor(ColorRGBA.Red);
        toMake.setSize(screenSize.width / 64);
        toMake.setLocalTranslation(posX, posY, 1);
        return toMake;
        
    }
    
    

    public static String getWeather(){
        return weathers[weatherIndex];
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
        
        /** New rectangle parameters are (in order) the x and y positions, then the width and height.
         * 
         * 
         * 
         * 
         * private final int ARROW_DOWN_POS_Y = screenSize.height/4 + screenSize.height/8;
           private final int ARROW_UP_POS_Y = screenSize.height/4;
           private final int ARROW_ALL_POS_X = screenSize.width/4;
           private final int ARROW_WIDTH = screenSize.width/32;
           private final int ARROW_HEIGHT = screenSize.height / 16;  
         */ 
        int arrowAllPosX = screenSize.width/4;
        int arrowWidth = screenSize.width/32;
        int arrowHeight = screenSize.height / 16;
        int arrowUpPosY = screenSize.height/4 + screenSize.height/8;
        int arrowDownPosY = screenSize.height/4;
        
        int buttonAllPosX = arrowAllPosX;
        int buttonWidth = screenSize.width/32;
        int buttonHeight = screenSize.height / 16;
        int buttonUpPosY = screenSize.height - arrowUpPosY;
        int buttonDownPosY = screenSize.height - arrowDownPosY;
        
        int numOfArrowButtons = 4;
        int spacingPerOwnArrow = screenSize.height / 4;
        /*
        Rectangle incTreeButtonBox = new Rectangle(screenSize.width, screenSize.height, arrowWidth, arrowHeight);
        Rectangle decTreeButtonBox = new Rectangle(screenSize.width, screenSize.height, arrowWidth, arrowHeight);
        Rectangle weatherUpButtonBox = new Rectangle(screenSize.width, screenSize.height, arrowWidth, arrowHeight);
        Rectangle weatherDownButtonBox = new Rectangle(screenSize.width, screenSize.height + (3*spacingPerOwnArrow), arrowWidth, arrowHeight);
        
        Rectangle incTreeButtonBox = new Rectangle(0,0, arrowWidth, arrowHeight);
        Rectangle decTreeButtonBox = new Rectangle(0,0, arrowWidth, arrowHeight);
        Rectangle weatherUpButtonBox = new Rectangle(0, 0, arrowWidth, arrowHeight);
        Rectangle weatherDownButtonBox = new Rectangle(0,0 + (3*spacingPerOwnArrow), arrowWidth, arrowHeight);
        /**
         * Important note: THE RECTANGLE OBJECTS'S (0,0) IS ON TOP LEFT CORNER. 
         * In addition, it is also shown that the rectangle is actually placed on its own top left corner. 
         * Thus, when you test it with the code you have now(with this 1 - position thing), the button functions
           at its gaps.
        
            I should shift them by screenSize/16 upwards to correct the problem.
         */
        
        
        /* N
        
        
        */
        Rectangle incTreeButtonBox = new Rectangle(arrowAllPosX, buttonUpPosY - (spacingPerOwnArrow), arrowWidth, arrowHeight);
        Rectangle decTreeButtonBox = new Rectangle(arrowAllPosX, buttonDownPosY - spacingPerOwnArrow, arrowWidth, arrowHeight);
        Rectangle weatherUpButtonBox = new Rectangle(arrowAllPosX, buttonUpPosY - (2*spacingPerOwnArrow), arrowWidth, arrowHeight);
        Rectangle weatherDownButtonBox = new Rectangle(arrowAllPosX, buttonDownPosY - (2*spacingPerOwnArrow), arrowWidth, arrowHeight);
        
        //Rectangle createTerrainButtonBox = new Rectangle(screenSize.width / 4, screenSize.width / 4, screenSize.width/2, screenSize.height / 2);
        
        HUDButton incTree = new HUDButton(incTreeButtonBox);
        HUDButton decTree = new HUDButton(decTreeButtonBox);
        HUDButton weatherUp = new HUDButton(weatherUpButtonBox);
        HUDButton weatherDown = new HUDButton(weatherDownButtonBox);
        //HUDButton createTerrainButton = new HUDButton(incTreeButtonBox);
        HUD_INPUT_MANAGER.addButton(incTree);
        HUD_INPUT_MANAGER.addButton(decTree);
        HUD_INPUT_MANAGER.addButton(weatherUp);
        HUD_INPUT_MANAGER.addButton(weatherDown);
        //HUD_INPUT_MANAGER.addButton(createTerrainButton);
        
        /* Very interesting... It turns out that if you eliminate the incTreeButton that had the button at what is
           supposed to be the same exact position as the createTerrainButton, it would actually work.
        
           Thus, when the incTree HUDButton WAS there, and at the same spot as createTerrainButton, even with the onAction
           and setListener part commented out, it would still give null pointer exception.
        
            Possible solution is to place the two buttons at different positions.
        */
        
        //TODO: Make a setter method for increasing the private treeNum Var by 1, as public method.
        
        HUD_INPUT_MANAGER.uncapMouse(); 
        System.out.println("Before setListener part");
        String trees = "Number of trees: " + treeNum;
        System.out.println("Before instantiating treeNumText");
        
        treeNumText = new BitmapText(guiFont, false);
        treeNumText = setTextSettings(treeNumText, trees, 0, 0);
        guiNode.attachChild(treeNumText);
        
        treeNumText = new BitmapText(guiFont, false);
        treeNumText = setTextSettings(treeNumText, trees, 60, 60);
        guiNode.attachChild(treeNumText);

        weatherText = new BitmapText(guiFont, false);
        weatherText.setText(weathers[0]);
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
        
        //Very funny; even with the size of the testText set to the entire screen's length, you cannot see the actual text anywhere on screen
        //Especially when setting it to the size.
        BitmapText testText = new BitmapText(guiFont, false);
        testText.setText("Hola! You should be seen somewhere!");
        testText.setSize(guiFont.getCharSet().getRenderedSize());
        testText.setColor(ColorRGBA.Red);
        testText.setSize(screenSize.height);
        testText.setLocalTranslation(screenSize.width, screenSize.height, 2);
        guiNode.attachChild(testText);
        
        //System.out.println("After instantiating treeNumText; there would be a problem if it is still null pointer at this point");
        //Problem was that making method would cause pass by value problem, preventing the actual variable from being changed outside the method.
        
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
                        if(weatherIndex == weathers.length - 1){
                            weatherIndex = 0;
                            System.out.println("weatherIndex++ done");
                            String s = "Weather: " + weathers[weatherIndex];
                            System.out.println("String is 's' now weather");
                            weatherText.setText(s);
                            System.out.println("inc weatherIndex");
                        } else{
                            weatherIndex++;
                            System.out.println("weatherIndex++ done");
                            String s = "Weather: " + weathers[weatherIndex];
                            System.out.println("String is 's' now weather");
                            weatherText.setText(s);
                            System.out.println("inc weatherIndex");
                        }
                        
                    }
                });
        
        weatherDown.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    public void onAction() 
                    {
                        if(weatherIndex == 0){
                            System.out.println("weatherIndex-- done; weather Index is now at last index.");
                            weatherIndex = weathers.length - 1;
                            System.out.println("weatherIndex-- done; weather Index is now at last index.");
                            String s = "Weather: " + weathers[weatherIndex];                            
                            weatherText.setText(s);
                        } else{
                            System.out.println("weatherIndex-- done");
                            weatherIndex--;
                            String s = "Weather: " + weathers[weatherIndex];
                            weatherText.setText(s);
                        }
                        

                    }
                });
        /*
        createTerrainButton.setHUDButtonListener(new HUDButtonListener()
                {
                    @Override
                    /* It seems that no matter if the game is already activated or not, the mere act 
                       of pressing on the button alone causes null pointer exception.
                       It won't even print the statements down there, it will just get clicked and
                       get a null pointer exception.
                       
                    SOLVED: Problem was the fact that the button existed and has same size, which was in turn not
                        added in input manager as needed.
                    
                    Actually, not really. Something must be up.
                    
                    
                    public void onAction() 
                    {
                        System.out.println("You just clicked on the createTerrain Button");
                        launchGame = true;
                        System.out.println("Shouldn't you launch game at this point?");
                        //Bottom part deals with detaching all GUI components; good to know, it is indeed very possible to do this in one function.
                        guiNode.detachAllChildren();
                        HUD_INPUT_MANAGER.capMouse();
                    }
                });
        
        //Mistake: You placed HUD_INPUT_MANAGER.addButton down here rather than up there; that 
        //caused the problem of ExceptionInInitializer in the first place when running. It is a
        //run time error.
        /* Methodologically speaking: You cannot have a button function without being placed inside, right?
           It is like telling a sensor to 
        
        */
        
        
        /** Actual area of the pictures that we need to deal with.
         * 
         */
        
        /** GUI BG component.
         * 
         */
        AssetManager a = main.getAssetManager();
        Picture guiPictBG = new Picture("PNG GUI Background Box");
        guiPictBG.setImage(a, "GUIComponent/PNG GUI Background Box.png", true);
        int imageCenterPosX = screenSize.width/4;
        int imageCenterPosY = 0;
        guiPictBG.setWidth(screenSize.width/2);
        guiPictBG.setHeight(screenSize.height/1);
        guiPictBG.setPosition(imageCenterPosX, imageCenterPosY);
        guiNode.attachChild(guiPictBG);
        
        /** GUI BG component for arrow up.
         * 
         */
        
        for(int i = 0; i < NUM_OF_ARROWS; i++){
            Picture guiArrowUp = new Picture("ArrowUpGUIComponent");
            
            guiArrowUp.setImage(a, "GUIComponent/ArrowUpGUIComponent.png", true);
            
            int arrowUpCenterPosX = screenSize.width/4;
            
            int arrowUpCenterPosY = screenSize.height/4 + screenSize.height/8;
            
            guiArrowUp.setWidth(screenSize.width/32);
            
            guiArrowUp.setHeight(screenSize.height/16);
            
            guiArrowUp.setPosition(arrowUpCenterPosX, arrowUpCenterPosY + (i * spacingPerOwnArrow));
            
            guiNode.attachChild(guiArrowUp);
        }
        
        /** GUI BG component for arrow down.
         * 
         */
        for(int i = 0; i < NUM_OF_ARROWS; i++){
            
            Picture guiArrowDown = new Picture("ArrowDownGUIComponent");

            guiArrowDown.setImage(a, "GUIComponent/ArrowDownGUIComponent.png", true);
            
            int arrowDownCenterPosX = screenSize.width/4;
            
            int arrowDownCenterPosY = screenSize.height/4;
            
            guiArrowDown.setWidth(screenSize.width/32);
            
            guiArrowDown.setHeight(screenSize.height/16);
            
            guiArrowDown.setPosition(arrowDownCenterPosX, arrowDownCenterPosY + (i * spacingPerOwnArrow));
            
            guiNode.attachChild(guiArrowDown);
        }

        
        
        
        /**  Larger version of both the arrow and screen components clumped together in one
         **  picture; it appears to be okay. Factor is that for every 2x of ratio,
         **  you must decrease the posX by screenSize.width /8 more and decrease by screenSize.height / 4 more.

        Picture guiPict = new Picture("guiBackground");
        guiPict.setImage(a, "GUIComponent/guiBG.png", true);
        
        
        int imageCenterPosX = screenSize.width/4;
        int imageCenterPosY = screenSize.height/2 - screenSize.height / 2;
        guiPict.setWidth(screenSize.width/2);
        guiPict.setHeight(screenSize.height/1);
        
        int imageCenterPosX = screenSize.width/4;
        int imageCenterPosY = screenSize.height/2 - screenSize.height / 2;
        guiPict.setWidth(screenSize.width/2);
        guiPict.setHeight(screenSize.height/1);
        guiPict.setPosition(imageCenterPosX, imageCenterPosY);
        guiNode.attachChild(guiPict);
        */
        
        /*
        int imageCenterPosX = screenSize.width/4 + screenSize.width / 8;
        int imageCenterPosY = screenSize.height/2 - screenSize.height / 4;
        guiPict.setWidth(screenSize.width/4);
        guiPict.setHeight(screenSize.height/2);
        */
        //guiPict.setPosition(screenSize.width/4, imageCenterPosY);
        
        
    }
                
   public static void incTree(){
        treeNum++;
    }
    public static void decTree(){
        treeNum--;
    }
    public static void incWeatherChoice(){
        weatherIndex++;
    }
    public static void decWeatherChoice(){
        weatherIndex--;
    }
    public static void launchGame(){
        launchGame = true;   
    }
    public static int getTreeNum(){
        return treeNum;
    }
    public static int getWeatherIndex(){
        return weatherIndex;
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

