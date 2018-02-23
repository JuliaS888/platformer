/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import net.dermetfan.gdx.Multiplexer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

/**
 *
 *  A {@link ContactListener} implementation that sends {@link Contact Contacts} to an {@link Array}
 * of ContactListeners. Allows adding and removing listeners.
 * 
 * @author Julia
 */
public class ContactListenerMultiplexer extends Multiplexer<ContactListener> implements ContactListener {

    public ContactListenerMultiplexer() {
            super();
    }

    public ContactListenerMultiplexer(final ContactListener... receivers) {
            super(receivers);
    }

    public ContactListenerMultiplexer(final Array<ContactListener> receivers) {
            super(receivers);
    }

    @Override
    public void beginContact(final Contact contact) {
            for (ContactListener listener : receivers) {
                    listener.beginContact(contact);
            }
    }

    @Override
    public void preSolve(final Contact contact, final Manifold oldManifold) {
            for (ContactListener listener : receivers) {
                    listener.preSolve(contact, oldManifold);
            }
    }

    @Override
    public void postSolve(final Contact contact, final ContactImpulse impulse) {
            for (ContactListener listener : receivers) {
                    listener.postSolve(contact, impulse);
            }
    }

    @Override
    public void endContact(final Contact contact) {
            for (ContactListener listener : receivers) {
                    listener.endContact(contact);
            }
    }

}
