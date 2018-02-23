/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platformer.PlatformManGame;
import com.mygdx.platformer.entity.Direction;

/**
 *
 * @author Julia
 */
public class PlatformBodyFactory implements BodyFactory {
    private static final float PLATFORM_SCALE = 1f;

    private final BodyEditorLoader loader;
    private final BodyDef bdef;
    private final FixtureDef fdef;

    public PlatformBodyFactory(final BodyEditorLoader loader) {
        if (loader == null) {
                throw new IllegalArgumentException("'loader' cannot be null");
        }
        this.loader = loader;

        bdef = new BodyDef();
        bdef.type = BodyType.KinematicBody;
        bdef.position.set(0, 0);
        bdef.fixedRotation = true;

        fdef = new FixtureDef();
    };
    
    @Override
    public Body create(World world, Direction direction) {
        return create(world, bdef, direction);
    }

    @Override
    public Body create(World world, BodyDef definition) {
        return create(world, definition, null);
    }

    @Override
    public Body create(World world, BodyDef definition, Direction direction) {
        Body platformBody = world.createBody(definition);
        loader.attachFixture(platformBody, "platform", fdef, PLATFORM_SCALE);
        platformBody.getFixtureList().get(0).setUserData("platform");
        platformBody.getFixtureList().get(1).setUserData("platform");
        //platformBody.getFixtureList().get(2).setUserData("platform");
        
        return platformBody;
    }
    
    public BodyDef getBodyDef(){
        return bdef;
    }
    
}
