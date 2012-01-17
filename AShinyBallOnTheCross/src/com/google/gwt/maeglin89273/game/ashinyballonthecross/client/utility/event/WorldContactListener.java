/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event;

import java.util.EventListener;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.maeglin89273.game.mengine.component.Physical;

/**
 * @author Maeglin Liao
 *
 */
public interface WorldContactListener extends EventListener,Physical {
	
	public void beginContact(Contact contact,Fixture thisFixture,Fixture thatFixture);
		
	public void endContact(Contact contact,Fixture thisFixture,Fixture thatFixture);
	
	public void preSolve(Contact contact, Manifold oldManifold,Fixture thisFixture,Fixture thatFixture);
	
	public void postSolve(Contact contact, ContactImpulse impulse,Fixture thisFixture,Fixture thatFixture);
	
}