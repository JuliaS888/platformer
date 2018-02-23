/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.platformer.PlatformManGame;
import com.mygdx.platformer.entity.Direction;

/**
 *
 * @author Julia
 */
public class SantaBodyFactory implements BodyFactory {
    private static final float SANTA_SCALE = 101 / PlatformManGame.PPM;
    private static final int FOOT_FIXTURE_INDEX = 1;
    private static final String SANTA_IDENTIFIER = "santa";
    private static final Vector2 INITIAL_POSITION = new Vector2(1.2f, 2.2f);

    private final BodyEditorLoader loader;
    private final BodyDef bdef;
    private final FixtureDef fdef;

    public SantaBodyFactory(final BodyEditorLoader loader) {
        if (loader == null) {
                throw new IllegalArgumentException("'loader' cannot be null");
        }
        this.loader = loader;

        bdef = new BodyDef();
        bdef.type = BodyType.DynamicBody;
        bdef.position.set(INITIAL_POSITION);
        bdef.fixedRotation = true;
        bdef.gravityScale = 2.0f;
        // bdef.bullet = true;

        fdef = new FixtureDef();
        fdef.density = 1.0f;
        fdef.restitution = 0.0f;
        fdef.friction = 0.8f;
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

    /*
     * (non-Javadoc)
     *
     * @see ar.uba.fi.game.physics.BodyFactory#create(com.badlogic.gdx.physics.box2d.World,
     * com.badlogic.gdx.physics.box2d.BodyDef)
     */
    @Override
    public Body create(final World world, final BodyDef definition) {
        return create(world, definition, Direction.RIGHT);
    }

    /*
     * (non-Javadoc)
     *
     * @see ar.uba.fi.game.physics.BodyFactory#create(com.badlogic.gdx.physics.box2d.World,
     * com.badlogic.gdx.physics.box2d.BodyDef, ar.uba.fi.game.entity.Direction)
     */
    @Override
    public Body create(final World world, final BodyDef definition, final Direction direction) {
        Body santaBody = world.createBody(definition);
        //loader.attachFixture(santaBody, SANTA_IDENTIFIER + "-" + direction.direction(), fdef, SANTA_SCALE);
        loader.attachFixture(santaBody, SANTA_IDENTIFIER + "-" + direction.direction(), fdef, 0.41f);

        Array<Fixture> fx = santaBody.getFixtureList();
        Fixture footSensor = santaBody.getFixtureList().get(FOOT_FIXTURE_INDEX);
        footSensor.setUserData(SantaPhysicsProcessor.FOOT_IDENTIFIER);
        footSensor.setDensity(0.0f);
        footSensor.setSensor(true);
        
        santaBody.getFixtureList().get(12).setUserData("head");
        //santaBody.getFixtureList().get(12).setRestitution(0.1f);
        santaBody.getFixtureList().get(12).setDensity(0.0f);
        santaBody.getFixtureList().get(12).setSensor(true);
        
        santaBody.resetMassData();

        return santaBody;
    }
}
