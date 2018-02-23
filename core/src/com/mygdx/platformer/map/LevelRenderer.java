/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.platformer.Assets;

/**
 *
 * @author Julia
 */
public class LevelRenderer {
    private static final String BACKGROUND_PROPERTY = "background";
    private final OrthogonalTiledMapRenderer renderer;
    
    private final Array<CollectibleRenderer> collectibles;
    
    private final Array<PlatformRenderer> platforms;
    
    public LevelRenderer(final TiledMap map, final AssetManager assets, final Batch batch, final float unitScale) {
        collectibles = new Array<CollectibleRenderer>(3);
        platforms = new Array<PlatformRenderer>(3);

        Texture background = assets.get(map.getProperties().get(BACKGROUND_PROPERTY, Assets.DEFAULT_BACKGROUND.fileName, String.class), Texture.class);
        
        renderer = new BackgroundTiledMapRenderer(map, unitScale, batch, background);
    }

    public void addCollectibleRenderer(final CollectibleRenderer renderer) {
        collectibles.add(renderer);
    }
    
    public void addPlatformRenderer(final PlatformRenderer renderer) {
        platforms.add(renderer);
    }
    
    /**
     * Renders the tiled map for the current frame. Sets its view as the given
     * {@link OrthographicCamera}.
     *
     * @param camera
     *            The camera used to show the map.
     */
    public void render(final OrthographicCamera camera) {
        float width = 1.12f * camera.viewportWidth * camera.zoom;
        float height = camera.viewportHeight * camera.zoom;
        renderer.setView(camera.combined, camera.position.x - width / 2, camera.position.y - height / 2, width, height);
        renderer.render();
    }

    /**
     * Updates every {@link Collectible} in the level.
     */
    public void update() {
        for (CollectibleRenderer collectible : collectibles) {
            collectible.update(renderer.getBatch(), renderer.getViewBounds());
        }
        
        for (PlatformRenderer platform : platforms) {
            platform.update(renderer.getBatch(), renderer.getViewBounds());
        }
    }

    public TiledMap getTiledMap() {
        return renderer.getMap();
    }

    public float getUnitScale() {
        return renderer.getUnitScale();
    }
}
