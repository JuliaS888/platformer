/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platformer.physics.BodyEditorLoader;

/**
 *
 * @author Julia
 */
public class LevelFactory {
    private static final String LEVEL_MAP_FILE = "map/map.tmx";
    private static final String COLLECTIBLES_LAYER = "collectibles";
    private static final String PLATFORMS_LAYER = "platforms";

    private LevelFactory() {

    };
    
    public static LevelRenderer create(final World world, final BodyEditorLoader loader, final Batch batch, final AssetManager assets, final float unitScale) {
        TiledMap tiledMap = new TmxMapLoader().load(LEVEL_MAP_FILE);
        LevelRenderer renderer = new LevelRenderer(tiledMap, assets, batch, unitScale);

        for (MapLayer ml : tiledMap.getLayers()) {
            if (ml.getName().toLowerCase().startsWith(COLLECTIBLES_LAYER)) {
                CollectibleRenderer gifts = new CollectibleRenderer(unitScale);
                gifts.load(world, loader, assets, ml);
                renderer.addCollectibleRenderer(gifts);
            }
            
            if (ml.getName().toLowerCase().startsWith(PLATFORMS_LAYER)) {
                PlatformRenderer platforms = new PlatformRenderer();
                platforms.load(world, loader, assets, ml);
                renderer.addPlatformRenderer(platforms);
            }
        }
        return renderer;
    }
}
