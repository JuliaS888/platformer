/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.graphics;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.platformer.Assets;
import com.mygdx.platformer.PlatformManGame;
import com.mygdx.platformer.entity.Direction;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.fsm.SantaState;
import com.mygdx.platformer.msg.MessageType;
import java.util.ArrayList;
import java.util.Collection;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

/**
 *
 * @author Julia
 */
public class SantaGraphicsProcessor implements GraphicsProcessor, Telegraph {
    private static final String IDLE_REGION = "idle";
    private static final String RUN_REGION = "run";
    private static final String JUMP_REGION = "jump";
    //private static final Vector2 RESPAWN_POSITION = new Vector2(0.6f, 3.2f); //место появления игрока
    private Vector2 RESPAWN_POSITION = new Vector2(175.5f, 9f);/*new Vector2(0.6f, 10f)*/; //место появления игрока //179.5
    private  ArrayList<Vector2> array_respawn = new ArrayList<Vector2>();

    private final TextureAtlas textureAtlas;
    private final Box2DSprite standingSprite;
    private final AnimatedBox2DSprite runRightSprite;
    private final AnimatedBox2DSprite runLeftSprite;
    private final AnimatedBox2DSprite jumpSprite;
    private final AnimatedBox2DSprite idleRightSprite;
    private final AnimatedBox2DSprite idleLeftSprite;

    // private final AnimatedBox2DSprite duckSprite;
    private boolean isFinish;
    public SantaGraphicsProcessor(final AssetManager assets) {
        //Добавить массив точек воскрешения
        isFinish = false;
        array_respawn.add(new Vector2(5.5f, 1f));
        
        textureAtlas = assets.get(Assets.SANTA_ATLAS);

        Array<Sprite> walkingSprites = textureAtlas.createSprites(RUN_REGION);
        standingSprite = new Box2DSprite(walkingSprites.first());
        standingSprite.setAdjustSize(false);
        standingSprite.setSize(standingSprite.getWidth() / PlatformManGame.PPM, standingSprite.getHeight() / PlatformManGame.PPM);
        standingSprite.setScale(0.2f, 0.2f);
        
        Animation animation = new Animation(1 / 11.0f, walkingSprites, PlayMode.LOOP);
        runRightSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
        runRightSprite.setAdjustSize(false);
        runRightSprite.setSize(runRightSprite.getWidth() / PlatformManGame.PPM, runRightSprite.getHeight() / PlatformManGame.PPM);
        runRightSprite.setScale(0.2f, 0.2f);
        
        animation = new Animation(1 / 11.0f, textureAtlas.createSprites(RUN_REGION), PlayMode.LOOP);
        runLeftSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
        runLeftSprite.flipFrames(true, false, true);
        runLeftSprite.setAdjustSize(false);
        runLeftSprite.setSize(runLeftSprite.getWidth() / PlatformManGame.PPM, runLeftSprite.getHeight() / PlatformManGame.PPM);
        runLeftSprite.setScale(0.2f, 0.2f);
        
        animation = new Animation(1 / 16.0f, textureAtlas.createSprites(JUMP_REGION),PlayMode.LOOP);
        jumpSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
        jumpSprite.setAdjustSize(false);
        jumpSprite.setSize(jumpSprite.getWidth() / PlatformManGame.PPM, jumpSprite.getHeight() / PlatformManGame.PPM);
        jumpSprite.setScale(0.2f, 0.2f);
        
        animation = new Animation(1 / 16.0f, textureAtlas.createSprites(IDLE_REGION), PlayMode.LOOP);
        idleRightSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
        idleRightSprite.setAdjustSize(false);
        idleRightSprite.setSize(idleRightSprite.getWidth() / PlatformManGame.PPM, idleRightSprite.getHeight() / PlatformManGame.PPM);
        idleRightSprite.setScale(0.2f, 0.2f);
        
        animation = new Animation(1 / 16.0f, textureAtlas.createSprites(IDLE_REGION), PlayMode.LOOP);
        idleLeftSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
        idleLeftSprite.flipFrames(true, false, true);
        idleLeftSprite.setAdjustSize(false);
        idleLeftSprite.setSize(idleLeftSprite.getWidth() / PlatformManGame.PPM, idleLeftSprite.getHeight() / PlatformManGame.PPM);
        idleLeftSprite.setScale(0.2f, 0.2f);
        
        MessageManager.getInstance().addListeners(this, MessageType.DEAD.code(),MessageType.FINISH_LEVEL.code());
    }

    /*
     * (non-Javadoc)
     * 
     * @see ar.uba.fi.game.graphics.GraphicsProcessor#update(ar.uba.fi.game.entity.Entity,
     * com.badlogic.gdx.graphics.Camera)
     */
    @Override
    public void update(final Entity character, final Camera camera) {
        if(character.getBody() == null){
            camera.position.x = 0.0f;
        }
        else{
            camera.position.x = character.getBody().getPosition().x + camera.viewportWidth * 0.25f;
            camera.position.y = character.getBody().getPosition().y + camera.viewportHeight * 0.05f;
            
            //контрольные точки сохранения
            Vector2 p = character.getBody().getPosition();
            for (int i = 0;i<array_respawn.size();i++){
                if(Math.abs(p.x-array_respawn.get(i).x)<=0.5 && Math.abs(p.y-array_respawn.get(i).y)<=0.5 && array_respawn.get(i).x > RESPAWN_POSITION.x){
                    RESPAWN_POSITION = array_respawn.get(i);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see ar.uba.fi.game.entity.GraphicsProcessor#draw(com.badlogic.gdx.graphics.g2d.Batch)
     */
    @Override
    public void draw(final Entity character, final Batch batch) {
        Box2DSprite frame = null;

        if (character.isInState(SantaState.JUMP)) {
            //jumpSprite.flipFrames(!(Direction.RIGHT.equals(character.getDirection()) ^ jumpSprite.isFlipX()), false, false);
            //frame = jumpSprite;
            if (!(Direction.RIGHT.equals(character.getDirection())))
                frame = runLeftSprite;
            else
                frame = runRightSprite;
        } else if (character.isInState(SantaState.RIGHT)) {
            frame = runRightSprite;
            character.setDirection(Direction.RIGHT);
        } else if (character.isInState(SantaState.LEFT)) {
            frame = runLeftSprite;
            character.setDirection(Direction.LEFT);
        } else {
            boolean b = !(Direction.RIGHT.equals(character.getDirection()));
            b = !(Direction.RIGHT.equals(character.getDirection()) ^ jumpSprite.isFlipX());
            if (!(Direction.RIGHT.equals(character.getDirection())))
                frame = idleLeftSprite;
            else
                frame = idleRightSprite;
            jumpSprite.setTime(0.0f);
        }

        // Following numbers came from voodoo
        frame.setPosition(
            -frame.getWidth() * 0.12f +
                            Box2DUtils.width(character.getBody()) / (Direction.RIGHT.equals(character.getDirection())
                                            ? 2.8f : 1.55f),
            -frame.getHeight() * 0.12f + Box2DUtils.width(character.getBody()) + 0.36f);
        if(!isFinish)
            frame.draw(batch, character.getBody());
    }

    @Override
    public boolean handleMessage(final Telegram msg) {
        if (msg.message == MessageType.DEAD.code()){ 
            Entity character = (Entity) msg.extraInfo;
            character.getBody().setTransform(RESPAWN_POSITION, character.getBody().getAngle());
            character.changeState(SantaState.IDLE);
            character.setDirection(Direction.RIGHT);
        }
        else if (msg.message == MessageType.FINISH_LEVEL.code()) {
            isFinish = true; //для того, чтобы не отрисовывалось при финише
        }
        return true;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(final int width, final int height) {
    }    
}
