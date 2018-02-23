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
public class GiftBodyFactory implements BodyFactory {
    private static final float GIFT_SCALE = 61 / PlatformManGame.PPM;

    private final BodyEditorLoader loader;
    private final BodyDef bdef;
    private final FixtureDef fdef;

    public GiftBodyFactory(final BodyEditorLoader loader) {
        if (loader == null) {
                throw new IllegalArgumentException("'loader' cannot be null");
        }
        this.loader = loader;

        bdef = new BodyDef();
        bdef.type = /*BodyType.DynamicBody*/BodyType.KinematicBody;
        bdef.position.set(0, 0);
        bdef.fixedRotation = true;

        fdef = new FixtureDef();
        fdef.isSensor = true;
    };

    /*
     * (non-Javadoc)
     *
     * @see ar.uba.fi.game.physics.BodyFactory#create(com.badlogic.gdx.physics.box2d.World,
     * ar.uba.fi.game.entity.Direction)
     */
    @Override
    public Body create(final World world, final Direction direction) {
        return create(world, bdef, direction);
    }

    @Override
    public Body create(final World world, final BodyDef definition) {
        return create(world, definition, null);
    }

    @Override
    public Body create(final World world, final BodyDef definition, final Direction direction) {
        Body giftBody = world.createBody(definition);
        loader.attachFixture(giftBody, GiftPhysicsProcessor.GIFT_IDENTIFIER, fdef, GIFT_SCALE);
        //giftBody.getFixtureList().first().setUserData(GiftPhysicsProcessor.GIFT_IDENTIFIER);
        //giftBody.getFixtureList().
        giftBody.getFixtureList().get(0).setUserData(GiftPhysicsProcessor.GIFT_IDENTIFIER);
        giftBody.getFixtureList().get(1).setUserData(GiftPhysicsProcessor.GIFT_IDENTIFIER);
        giftBody.getFixtureList().get(2).setUserData(GiftPhysicsProcessor.GIFT_IDENTIFIER);
        giftBody.getFixtureList().get(3).setUserData(GiftPhysicsProcessor.GIFT_IDENTIFIER);
        giftBody.getFixtureList().get(4).setUserData(GiftPhysicsProcessor.GIFT_IDENTIFIER);
        giftBody.getFixtureList().get(5).setUserData(GiftPhysicsProcessor.GIFT_IDENTIFIER);
        return giftBody;
    }
}
