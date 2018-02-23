/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer;

import com.badlogic.gdx.Screen;

/**
 *
 * @author Julia
 */
public abstract class AbstractScreen implements Screen{
    protected final PlatformManGame game;
    
    public AbstractScreen(final PlatformManGame game){
        this.game = game;
    }

    @Override
    public void hide() {
        dispose();
    }
    
}
