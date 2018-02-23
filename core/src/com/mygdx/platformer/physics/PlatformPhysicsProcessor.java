/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.entity.Path;

/**
 *
 * @author Julia
 */
public class PlatformPhysicsProcessor implements PhysicsProcessor{
    private Path path;
    private Vector2 velocity;
    
    public PlatformPhysicsProcessor(){
        path = new Path();
    }

    public PlatformPhysicsProcessor(Path path) {
        this.path = path;
    }
    @Override
    public void update(Entity character) {
        Vector2 vel1 = character.getBody().getLinearVelocity();
         Vector2 v1 = character.getBody().getPosition();
         if(path.updatePath(character.getBody().getPosition())){
            velocity = path.getVelocity();
            character.getBody().setLinearVelocity(velocity);
       }
         //character.getBody().setLinearVelocity(velocity);
         //character.getBody().setTransform(character.getBody().getPosition().add(velocity), 0);
         //Vector2 vel = character.getBody().getLinearVelocity();
         //character.getBody(). = v1.add(vel);
         Vector2 v = character.getBody().getPosition();
         
    }

    @Override
    public void beginContact(Contact contact) {
        
        String PLATFORM_IDENTIFIER = "platform";
        if (contact.getFixtureA().getBody().getFixtureList().size > 1 && PLATFORM_IDENTIFIER.equals(contact.getFixtureA().getBody().getFixtureList().get(1).getUserData())){
            contact.setFriction(0.8f);
        }
        else if(contact.getFixtureB().getBody().getFixtureList().size > 1 && PLATFORM_IDENTIFIER.equals(contact.getFixtureB().getBody().getFixtureList().get(1).getUserData())){
            contact.setFriction(0.8f);
        }
    }

    @Override
    public void endContact(Contact cntct) {
        //
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
        //
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
        //
    }

    @Override
    public void dispose() {
        //
    }
    
}
