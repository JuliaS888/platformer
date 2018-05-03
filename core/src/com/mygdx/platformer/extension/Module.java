/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.extension;

import com.mygdx.platformer.LevelScreen;
import com.mygdx.platformer.PlatformManGame;

/**
 *
 * @author Julia
 */
public interface Module {
    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_FAILURE = 1;

    public void load(LevelScreen ls);
    public int run(PlatformManGame game);
    public void unload(PlatformManGame game);
}
