/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 *
 * @author Julia
 */
public final class Assets {
    private Assets(){ }
    
    public static final AssetDescriptor<Texture> GIFT_LOGO = new AssetDescriptor<Texture>("gift_logo.png", Texture.class);
    public static final AssetDescriptor<Texture> SANTA_LOGO = new AssetDescriptor<Texture>("santa_logo.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> SANTA_ATLAS = new AssetDescriptor<TextureAtlas>("sprites.txt", TextureAtlas.class);
    
    public static final AssetDescriptor<Texture> DEFAULT_BACKGROUND = new AssetDescriptor<Texture>("bg.png", Texture.class);
    public static final AssetDescriptor<Texture> NIGHT_BACKGROUND = new AssetDescriptor<Texture>("map/night.png", Texture.class);
    
    public static final AssetDescriptor<Texture> PLATFORM = new AssetDescriptor<Texture>("map/platform.png", Texture.class);
    
    public static final AssetDescriptor<Texture> ARROW_RIGHT = new AssetDescriptor<Texture>("arrow_logo.png", Texture.class);
    public static final AssetDescriptor<Texture> ARROW_LEFT = new AssetDescriptor<Texture>("arrow_left_logo.png", Texture.class);
    public static final AssetDescriptor<Texture> ARROW_AROUND_LOGO = new AssetDescriptor<Texture>("arrow_around_logo.png", Texture.class);
}
