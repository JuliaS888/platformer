/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 *
 * @author Julia
 */
public class BackgroundTiledMapRenderer extends OrthogonalTiledMapRenderer{
    private final Texture background;

    public BackgroundTiledMapRenderer(final TiledMap map, final float unitScale, final Batch batch, final Texture background) {
            super(map, unitScale, batch);
            this.background = background;
    }

    @Override
    protected void beginRender() {
        super.beginRender();
        // Draw the background
        getBatch().draw(background, viewBounds.x, viewBounds.y, viewBounds.getWidth(), viewBounds.getHeight());
    }
}
