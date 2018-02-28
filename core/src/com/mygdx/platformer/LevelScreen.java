/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.entity.EntityFactory;
import com.mygdx.platformer.graphics.BoundedCamera;
import com.mygdx.platformer.graphics.StatusBar;
import com.mygdx.platformer.graphics.WindBar;
import com.mygdx.platformer.map.LevelFactory;
import com.mygdx.platformer.map.LevelRenderer;
import com.mygdx.platformer.physics.BodyEditorLoader;
import com.mygdx.platformer.player.PlayerStatusObserver;
import com.mygdx.platformer.wind.Wind;
import com.mygdx.platformer.wind.WindStatusProcessor;

/**
 *
 * @author Julia
 */
public class LevelScreen extends AbstractScreen{
    private static final float GRAVITY = -9.8f;
    private static final String BODIES_DEFINITION_FILE = "bodies.json";

    private static final float TIME_STEP = 1 / 300f;
    private static final int POSITION_ITERATIONS = 3;
    private static final int VELOCITY_ITERATIONS = 8;

    private final World world;
    private final ScreenViewport viewport;
    private final Entity santa;
    private final Entity environment;
    private final StatusBar hud;
    private final WindBar windHud;
    private float accumulator;
    private Wind wind;
    private WindStatusProcessor windProcessor;
        
    public LevelScreen(PlatformManGame game) {
        super(game);
        world = new World(new Vector2(0.0f, GRAVITY), true);
        hud = new StatusBar(game.getBatch(), game.getAssetsManager());
        windHud = new WindBar(game.getBatch(), game.getAssetsManager());
        BodyEditorLoader bodyLoader = new BodyEditorLoader(Gdx.files.internal(BODIES_DEFINITION_FILE));
        LevelRenderer mapRenderer = LevelFactory.create(world, bodyLoader, game.getBatch(), game.getAssetsManager(), 1 / PlatformManGame.PPM);
        
        wind = new Wind();
        windProcessor = new WindStatusProcessor(wind,5);
        windProcessor.addObserver(windHud);
        
	santa = EntityFactory.createSanta(world, bodyLoader, game.getAssetsManager(), game.getPlayerStatus(), hud);
		
	environment = EntityFactory.createEnvironment(world, game.getBatch(), mapRenderer, game.getAssetsManager(),game.getPlayerStatus(), (PlayerStatusObserver[]) null);
        
        viewport = new ScreenViewport();
        viewport.setUnitsPerPixel(1 / PlatformManGame.PPM);
        viewport.setCamera(new BoundedCamera(0.0f,800f,0.0f,400f));
        
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        accumulator += Math.min(delta, 0.25f);
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }

        santa.update(viewport.getCamera());
        viewport.getCamera().update();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        environment.update(viewport.getCamera());

        windProcessor.update();
        game.getBatch().begin();
        santa.step(game.getBatch(),wind);
        environment.step(game.getBatch(),null);
        game.getBatch().end();

        MessageManager.getInstance().update();
                
        hud.render();
        windHud.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hud.resize(width, height);
        santa.resize(width, height);
        environment.resize(width, height);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        santa.dispose();
        environment.dispose();
        world.dispose();
        hud.dispose();
    }
    
}
