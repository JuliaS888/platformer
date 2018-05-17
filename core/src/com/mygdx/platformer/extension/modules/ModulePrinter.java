package com.mygdx.platformer.extension.modules;

import com.mygdx.platformer.LevelScreen;
import com.mygdx.platformer.PlatformManGame;
import com.mygdx.platformer.extension.Module;

public class ModulePrinter implements Module {
    private LevelScreen levelScreen = null;
    @Override
    public void load(LevelScreen ls) {
        System.out.println("Module " + this.getClass() + " loading ...");
        levelScreen = ls;
    }

    @Override
    public int run(PlatformManGame game) {
        System.out.println("Module " + this.getClass() + " running ...");        
        levelScreen.runRight();
        System.out.println("go");
        return Module.EXIT_SUCCESS;
    }

    @Override
    public void unload(PlatformManGame game) {
        System.out.println("Module " + this.getClass() + " inloading ...");
    }
}
