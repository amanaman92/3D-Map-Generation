A few notes so far:
    The UI still is not in the right place in execution. In reality, the UI AppState should run before the MapAppState.
    When it is done collecting data, it should be removed from the stateManager, and only then should the MapAppState
    be attached.

The map rendering is freaking out on my computer. Please tell me if your GPU has trouble handling the generated map.
Procedural Generation (PG) is still limited. Texutring is just a placeholder.

I made a RandomUtils class. If you need to do anything with Randomness, go through this class. Do not create any more
    Random objects, nor call Math.random!! We want to have one seed for all randomness, so it can be reproduced.
