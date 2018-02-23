/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.fsm.SantaState;
import com.mygdx.platformer.msg.MessageType;

/**
 *
 * @author Julia
 */
public class SantaPhysicsProcessor implements PhysicsProcessor {
    private static final float DEATH_ALTITUDE = -5.0F;
    public static final String FOOT_IDENTIFIER = "foot";
    private static final String GROUND_IDENTIFIER = "ground";
    private static final String PLATFORM_IDENTIFIER = "platform";
    private static final float LINEAR_VELOCITY = 2.0f;
    private static final float JUMP_VELOCITY = /*5.33f;*/3f;
    private static final Vector2 MAX_VELOCITY = new Vector2(3.3f, 2.99f);

    private int groundContacts;
    private int jumpTimeout;
    private float stillTime;

    /*
     * (non-Javadoc)
     * 
     * @see ar.uba.fi.game.entity.PhysicsProcessor#update()
     */
    @Override
    public void update(final Entity character) {
        Vector2 position = character.getBody().getWorldCenter();
        Vector2 velocity = character.getBody().getLinearVelocity();
        //character.getBody().setGravityScale(1);

        if (character.getBody().getPosition().y < DEATH_ALTITUDE) {
            MessageManager.getInstance().dispatchMessage(null, MessageType.DEAD.code(), character);
        } else {
            // Cap maximum velocity on X axis
            if (Math.abs(velocity.x) > MAX_VELOCITY.x) {
                velocity.x = Math.signum(velocity.x) * MAX_VELOCITY.x;
                character.getBody().setLinearVelocity(velocity);
                velocity = character.getBody().getLinearVelocity();
            }

            // Linear velocity dampening
            if (character.isInState(SantaState.IDLE)) {
                velocity = character.getBody().getLinearVelocity();
                stillTime += Gdx.graphics.getDeltaTime();
                character.getBody().setLinearVelocity(velocity.x * 0.9f, velocity.y);
            } else {
                stillTime = 0.0f;
            }

            // Disable friction if character is not on ground
            float newFriction = 0.0f;//0.05f;
            if (groundContacts > 0) {
                if (character.isInState(SantaState.IDLE) && stillTime > 0.2f) {
                    newFriction = 300.0f;
                } else {
                    newFriction = 0.8f;
                }
            }

            for (Fixture f : character.getBody().getFixtureList()) {
                f.setFriction(newFriction);
            }

            // Move character left or right
            if (character.isInState(SantaState.RIGHT) && velocity.x < MAX_VELOCITY.x) {
                character.getBody().applyLinearImpulse(LINEAR_VELOCITY * character.getBody().getMass(), 0.0f, position.x, position.y, true);
            } else if (character.isInState(SantaState.LEFT) && velocity.x > -MAX_VELOCITY.x) {
                character.getBody().applyLinearImpulse(-LINEAR_VELOCITY * character.getBody().getMass(), 0.0f, position.x, position.y, true);
            }

            // Jump
            if (character.isInState(SantaState.JUMP) && groundContacts > 0) {
                position = character.getBody().getPosition();

                // Initial jumping impulse (on ground)
                character.getBody().setLinearVelocity(velocity.x, 0.0f);
                character.getBody().setTransform(position.x, position.y + 0.01f, 0);
                
                float mass = character.getBody().getMass();
                float m = JUMP_VELOCITY * character.getBody().getMass() + Math.abs(velocity.x) * 0.2f;
                character.getBody().applyLinearImpulse(0.0f, JUMP_VELOCITY * character.getBody().getMass() + Math.abs(velocity.x) * 0.001f/*0.2f*/,
                                position.x,
                                position.y, true);
                jumpTimeout = 0;
            } else if (character.isInState(SantaState.JUMP) && velocity.y < MAX_VELOCITY.y && velocity.y > 0) {
                // Incrementally decreased impulse added while mid-air the longer the jump button is
                // held, the higher the character jumps)
                character.getBody().applyLinearImpulse(0.0f,
                                0.13f * JUMP_VELOCITY * character.getBody().getMass() / ++jumpTimeout,
                                position.x,
                                position.y, true);
            }
        }
    }

    @Override
    public void beginContact(final Contact contact) {
        // Foot sensor is touching the ground
        if ((contact.getFixtureA().getBody().getFixtureList().size > 1 && FOOT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData())) ||
                (contact.getFixtureB().getBody().getFixtureList().size > 1 && FOOT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData())) &&
                ((contact.getFixtureA().getBody().getFixtureList().size > 1 && GROUND_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData())) ||
                (contact.getFixtureB().getBody().getFixtureList().size > 1 && GROUND_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData())) ||
                (contact.getFixtureA().getBody().getFixtureList().size > 1 && PLATFORM_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData())) ||
                (contact.getFixtureB().getBody().getFixtureList().size > 1 && PLATFORM_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData())))) {
            groundContacts++;
        }
        
        /* Fixture fd = contact.getFixtureA();
        boolean b1 = FOOT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData());
        boolean b2 = FOOT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData());
        boolean b3 = GROUND_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData());
        boolean b4 = GROUND_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData());
        boolean b5 = PLATFORM_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData());
        boolean b6 = PLATFORM_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData());*/
        
        // Foot sensor is touching the ground
        /*if (FOOT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                FOOT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()) &&
                (GROUND_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                GROUND_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()) ||
                FOOT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                FOOT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()) &&
                PLATFORM_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                PLATFORM_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()))) {
            groundContacts++;
        }*/
        
            // Foot sensor is touching the ground
       /* if (FOOT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                FOOT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()) &&
                PLATFORM_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                PLATFORM_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData())){
            groundContacts++;
        }*/
    }

    @Override
    public void endContact(final Contact contact) {
       /*if (FOOT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                FOOT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()) &&
                (GROUND_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                GROUND_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()) ||
                FOOT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                FOOT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()) &&
                PLATFORM_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()) ||
                PLATFORM_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()))) {
            groundContacts--;
        }*/
       if ((contact.getFixtureA().getBody().getFixtureList().size > 1 && FOOT_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData())) ||
                (contact.getFixtureB().getBody().getFixtureList().size > 1 && FOOT_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData())) &&
                ((contact.getFixtureA().getBody().getFixtureList().size > 1 && GROUND_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData())) ||
                (contact.getFixtureB().getBody().getFixtureList().size > 1 && GROUND_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData())) ||
                (contact.getFixtureA().getBody().getFixtureList().size > 1 && PLATFORM_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData())) ||
                (contact.getFixtureB().getBody().getFixtureList().size > 1 && PLATFORM_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData())))) {
            groundContacts--;
        }
    }

    @Override
    public void postSolve(final Contact contact, final ContactImpulse impulse) {
        // TODO Auto-generated method stub
    }

    @Override
    public void preSolve(final Contact contact, final Manifold oldManifold) {
        // TODO Auto-generated method stub
        /*Fixture f;
        if(contact.getFixtureA().getBody().getFixtureList().size > 11){
            f=contact.getFixtureA().getBody().getFixtureList().get(12);
        }
        else if(contact.getFixtureB().getBody().getFixtureList().size > 11){
            f=contact.getFixtureB().getBody().getFixtureList().get(12);
        }*/
        if((contact.getFixtureA().getBody().getFixtureList().size > 11 && contact.getFixtureB().getBody().getFixtureList().size > 1 && "head".equals(contact.getFixtureA().getBody().getFixtureList().get(12).getUserData())&& "obstruction".equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData()))||
            (contact.getFixtureB().getBody().getFixtureList().size > 11 && contact.getFixtureA().getBody().getFixtureList().size > 1 && "head".equals(contact.getFixtureB().getBody().getFixtureList().get(12).getUserData())&& "obstruction".equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData()))){
            System.out.println("head-obstruction");
            contact.setFriction(0.0f);
            contact.setRestitution(0.1f);
        }
    }

    @Override
    public void dispose() {}    
}
