/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public abstract class SensorArea extends PhysicalArea implements ContactListener{
	protected Set<PhysicalShape> contentShapes=new HashSet<PhysicalShape>();
	
	/**
	 * @param creator
	 * @param contentPower TODO
	 * @param p
	 * @param w
	 * @param h
	 * @param angle
	 */
	protected SensorArea(Creator creator, int contentPower,boolean beControlled, Point p, double w,double h, double angle) {
		super(creator, contentPower,beControlled, p, w, h, angle);
		if(isVerified()){
			creator.getWorld().addContactListener(this);
		}
	}
	
	@Override
	public void destroy(){
		creator.getWorld().removeContactListener(this);
		contentShapes.clear();
		contentShapes=null;
		super.destroy();
	}
	
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#beginContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		Fixture fixA=contact.getFixtureA();
		Fixture fixB=contact.getFixtureB();
		
		for(Fixture fix:fixtures){
			if(fixA.equals(fix)&&(fixB.getUserData() instanceof PhysicalShape)){
				contentShapes.add((PhysicalShape)fixB.getUserData());
				return;
			}else if(fixB.equals(fix)&&(fixA.getUserData() instanceof PhysicalShape)){
				contentShapes.add((PhysicalShape)fixA.getUserData());
				return;
			}
		}
		

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#endContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		Fixture fixA=contact.getFixtureA();
		Fixture fixB=contact.getFixtureB();
		for(Fixture fix:fixtures){
			if(fixA.equals(fix)&&(fixB.getUserData() instanceof PhysicalShape)){
				contentShapes.remove((PhysicalShape)fixB.getUserData());
				return;
			}else if(fixB.equals(fix)&&(fixA.getUserData() instanceof PhysicalShape)){
				contentShapes.remove((PhysicalShape)fixA.getUserData());
				return;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#preSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.collision.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#postSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.callbacks.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}
	
	
}
