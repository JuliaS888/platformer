/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.platformer.Assets;
import com.mygdx.platformer.entity.Entity;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 *
 * @author Julia
 */
public class PlatformGraphicsProcessor implements GraphicsProcessor{
    private final Box2DSprite platform;
    
    public PlatformGraphicsProcessor(final AssetManager manager) {
        platform = new Box2DSprite(manager.get(Assets.PLATFORM));
    }

    @Override
    public void draw(Entity character, Batch batch) {
        platform.draw(batch, character.getBody());
    }

    @Override
    public void update(Entity character, Camera camera) {
        // Does nothing for this entity
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
    
}
