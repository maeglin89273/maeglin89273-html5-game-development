/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.area;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.WorldContactListener;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public abstract class SensorArea extends PhysicalArea implements WorldContactListener{
	protected List<Physical> contentCreations=new ArrayList<Physical>();
	
	/**
	 * @param creator
	 * @param contentPower TODO
	 * @param p
	 * @param w
	 * @param h
	 * @param angle
	 */
	protected SensorArea(int contentPower,boolean beControlled, Point p, double w,double h, double angle) {
		super(contentPower,beControlled, p, w, h, angle);
		if(isVerified()){
			creator.getWorld().addContactListener(this);
		}
	}
	
	@Override
	public void destroy(){
		creator.getWorld().removeContactListener(this);
		super.destroy();
		contentCreations.clear();
		contentCreations=null;
	}
	
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#beginContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void beginContact(Contact contact,Fixture thisFixture, Fixture thatFixture) {
		if(thatFixture.getBody().getType()==BodyType.DYNAMIC&&!contentCreations.contains(thatFixture.getBody().getUserData())){
			contentCreations.add((Physical)thatFixture.getBody().getUserData());
		}
		

	}

	
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#preSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.collision.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold,Fixture thisFixture, Fixture thatFixture) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#postSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.callbacks.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse,Fixture thisFixture, Fixture thatFixture) {
		// TODO Auto-generated method stub

	}
	
	//for solving the overlap problems, we should use a checker to solve it.
	public static class Checker{
		private Fixture[] fixtures;
		public Checker(Fixture[] fixtures){
			this.fixtures=fixtures;
		}
		public boolean checkPointIsOut(Fixture notThisFixture,Vec2 p){
			for(Fixture fix:fixtures){
				if(notThisFixture.equals(fix)){
					continue;
				}else if(fix.testPoint(p)){
					return false;
				}
			}
			return true;
		}
	}
}
