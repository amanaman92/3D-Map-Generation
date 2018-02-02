------------------------------------------How to use this Project------------------------------------------
This folder contains all the example codes. To use them in JME#:
    Place them in you JME3 workspace (The place JME3 is configured to save your files)
    In JME3, go to  File -> Open Project and then double click on the project.

To run:
    Open in project contents in the project tree on the left.
        Open Source Packages -> examples -> Main.java
    Click the green arrow on the toolbar or press F6 to run

To modify:
    Edit in JME3 just as you would edit a .java in any IDE.

------------------------------------------Notes on this project------------------------------------------
Read the comments. Documentation is available in the Documentation folder. To view, open the README shortcut.
THIS IS NOT part of our submission. It is only a reference for convenience.
    However, you can copy or reference any parts of this code as you wish.

------------------------------------------General Notes on Developing in JME3------------------------------------------
Put all logic into AppStates that extend BaseAppState
    (see https://jmonkeyengine.github.io/wiki/jme3/advanced/application_states.html)

Default Conrols are:
    WASD
    Q for up
    Z for down
    Mouse to rotate
    Mouse wheel to zoom
    Escape to exit

Only ever have one Random object.
    When we init the final project, we should make one class that contains the only Random Object (as public static final)

------------------------------------------External Resources------------------------------------------
JME3 Documentation
    http://javadoc.jmonkeyengine.org/

JME3 forums
    https://hub.jmonkeyengine.org/
    Be careful not to copy code you see (Our professor would not be OK with that). But use the forums if you need--they are a great resource!

JME3 Tutorials for beginners
    https://jmonkeyengine.github.io/wiki/jme3.html#tutorials-for-beginners

JME3 Install Page
    https://github.com/jMonkeyEngine/sdk/releases
