package com.mygdx.platformer.extension.modules;

import com.mygdx.platformer.LevelScreen;
import com.mygdx.platformer.PlatformManGame;
import com.mygdx.platformer.extension.Module;

public class ModulePrinter implements Module {

    @Override
    public void load(LevelScreen ls) {
        System.out.println("Module " + this.getClass() + " loading ...");
    }

    @Override
    public int run(PlatformManGame game) {
        System.out.println("Module " + this.getClass() + " running ...");
        return Module.EXIT_SUCCESS;
    }

    @Override
    public void unload(PlatformManGame game) {
        System.out.println("Module " + this.getClass() + " inloading ...");
    }
}
