/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.platformer.GameOverOverlay;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.map.LevelRenderer;
import com.mygdx.platformer.msg.MessageType;

/**
 *
 * @author Julia
 */
public class LevelGraphicsProcessor implements GraphicsProcessor {
    private final LevelRenderer mapRenderer;
    private boolean renderGameOver;

    public LevelGraphicsProcessor(final AssetManager assets, final Batch batch, final LevelRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
    }

    @Override
    public void update(final Entity character, final Camera camera) {
        mapRenderer.render((OrthographicCamera) camera);
    }

    /*
     * (non-Javadoc)
     *
     * @see ar.uba.fi.game.graphics.GraphicsProcessor#draw(ar.uba.fi.game.entity.Entity,
     * com.badlogic.gdx.graphics.g2d.Batch)
     */
    @Override
    public void draw(final Entity entity, final Batch batch) {
        mapRenderer.update();
    }


    @Override
    public void resize(final int width, final int height) {
    }

    @Override
    public void dispose() {
    }    
}
