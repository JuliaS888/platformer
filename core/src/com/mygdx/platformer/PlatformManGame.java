package com.mygdx.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.platformer.msg.MessageType;
import com.mygdx.platformer.player.CurrentPlayerStatus;

public class PlatformManGame extends Game implements Telegraph {
    public static final float PPM = 150.0f;
    public static final String GAME_TITLE = "Santa's Gifts";
    
    private Batch batch;
    private AssetManager assetManager;
    
    private final CurrentPlayerStatus status;
    
    public PlatformManGame(){
        status = new CurrentPlayerStatus();
    }
    
    @Override
    public void create () {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load(Assets.SANTA_LOGO);
        assetManager.load(Assets.GIFT_LOGO);
        assetManager.load(Assets.SANTA_ATLAS);
        assetManager.load(Assets.DEFAULT_BACKGROUND);
        assetManager.load(Assets.NIGHT_BACKGROUND);
        assetManager.load(Assets.PLATFORM);
        assetManager.finishLoading();
        setScreen(new TitleScreen(this));
        addListeners();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle(GAME_TITLE);
        super.render();
    }

    @Override
    public void dispose () {
        super.dispose();
        batch.dispose();
        assetManager.dispose();
    }
    
    public void reset(){
        status.reset();
        addListeners();
        setScreen(new TitleScreen(this));
    }

    private void addListeners() {
        MessageManager manager = MessageManager.getInstance();
        manager.clear();
        manager.addListeners(this, MessageType.GAME_OVER.code(), MessageType.RESET.code(), MessageType.FINISH_LEVEL.code());
    }
    
            
    @Override
    public boolean handleMessage(final Telegram msg) {
        if (msg.message == MessageType.RESET.code()) {
            reset();
        } else if (msg.message == MessageType.GAME_OVER.code()) {
            setScreen(new EndScreen(this,0));
        } else if (msg.message == MessageType.FINISH_LEVEL.code()) {
            setScreen(new EndScreen(this,1));
        } 
        return true;
    }    
    
    public AssetManager getAssetsManager() {
        return assetManager;
    }

    public Batch getBatch() {
        return batch;
    }
    
    public CurrentPlayerStatus getPlayerStatus() {
        return status;
    }
}
