/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.physics;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.platformer.entity.Collectible;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.fsm.GiftState;
import com.mygdx.platformer.msg.MessageType;

/**
 *
 * @author Julia
 */
public class GiftPhysicsProcessor implements PhysicsProcessor{
    private static final float MAX_DISTANCE = 0.1f;
    private static final float VERTICAL_VELOCITY = 0.21f;
    public static final String GIFT_IDENTIFIER = "gift";

    private float origin;
    
    @Override
    public void beginContact(final Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();
        //if (GIFT_IDENTIFIER.equals(contact.getFixtureA().getUserData())) {
        if (GIFT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(0).getUserData()) ||
                (contact.getFixtureA().getBody().getFixtureList().size > 1 && GIFT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) )
                ) {
            collectGift(contact.getFixtureA());
        } else if (GIFT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(0).getUserData()) ||
                (contact.getFixtureA().getBody().getFixtureList().size > 1 && GIFT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()))) {
            collectGift(contact.getFixtureB());
        }
    }

    /**
     * @param contact
     */
    private void collectGift(final Fixture fixture) {
        Collectible gift = (Collectible) fixture.getBody().getUserData();
        if (!gift.isCollected()) {
            gift.setCollected(true);
            //MessageManager.getInstance().dispatchMessage(null, MessageType.COLLECTED.code(), gift);
            MessageManager.getInstance().dispatchMessage(null,MessageType.COLLECTED.code(), gift);
        }
    }

    @Override
    public void endContact(final Contact contact) {

    }

    @Override
    public void preSolve(final Contact contact, final Manifold oldManifold) {

    }

    @Override
    public void postSolve(final Contact contact, final ContactImpulse impulse) {

    }

    @Override
    public void update(final Entity character) {
        Body body = character.getBody();

        if (((Collectible) character).isCollected() && ((Collectible) character).isDel()==false) {
            ((Collectible) character).del();
            body.getWorld().destroyBody(body);
        }
    }

    @Override
    public void dispose() {

    }
}
