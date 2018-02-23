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
public class GiftGraphicsProcessor implements GraphicsProcessor{
    private static final String GIFT_REGION = "gift_big";
    private final Box2DSprite gift;

    public GiftGraphicsProcessor(final AssetManager manager) {
        gift = new Box2DSprite(manager.get(Assets.SANTA_ATLAS).findRegion(GIFT_REGION));
    }

    @Override
    public void update(final Entity character, final Camera camera) {
        // Does nothing for this entity
    }

    @Override
    public void draw(final Entity character, final Batch batch) {
        gift.draw(batch, character.getBody());
    }

    @Override
    public void resize(final int width, final int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {    }
}
