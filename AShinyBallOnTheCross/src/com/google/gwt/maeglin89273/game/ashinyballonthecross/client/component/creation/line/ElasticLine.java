/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.callbacks.ContactImpulse;

import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Dynamic;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class ElasticLine extends ContactStaticLine {
	
	private static final float  IMPULSE_MAGNITUDE=0.3f;
	private Set<Dynamic> contactShapes=new HashSet<Dynamic>();
	
	/**
	 * 
	 * @param creator
	 * @param p1
	 * @param p2
	 */
	public ElasticLine(Creator creator, Point p1, Point p2) {
		this(creator, 0,false, p1, p2);
	}
	private ElasticLine(Creator creator, int cotentPower, Point p1, Point p2) {
		this(creator, cotentPower, true, p1, p2);
	}
	/**
	 * @param creator
	 * @param cotentPower TODO
	 * @param p1
	 * @param p2
	 * @param color
	 */
	private ElasticLine(Creator creator, int cotentPower,boolean beControlled, Point p1, Point p2) {
		super(creator, cotentPower,beControlled, p1, p2, ASBOTCConfigurations.Color.LIGHT_BLUE);
		
	}
	
	@Override
	public void update(){
		
		if(!contactShapes.isEmpty()){
			Body body;
			Vec2 imp;
			for(Dynamic ps:contactShapes){
				body=ps.getBody();
				imp=body.getLinearVelocity().clone();
				
				imp.mulLocal(IMPULSE_MAGNITUDE);
				body.applyLinearImpulse(imp, body.getWorldCenter());
			}
			contactShapes.clear();
			
		}
	}
	
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#beginContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#endContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		Fixture fixA=contact.getFixtureA();
		Fixture fixB=contact.getFixtureB();
		if(fixA.equals(fixture)&&(fixB.getUserData() instanceof Dynamic)){
			contactShapes.add((Dynamic)fixB.getUserData());
		}else if(fixB.equals(fixture)&&(fixA.getUserData() instanceof Dynamic)){
			contactShapes.add((Dynamic)fixA.getUserData());
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
	public static class ElasticLineDefiner extends StaticLineDefiner{

		public ElasticLineDefiner(Creator creator) {
			super(creator,ASBOTCConfigurations.CreationPowerComsumption.ELASTIC_LINE,new Point(2*ICON_BOUNDS_PLUS_SPACING,ICON_BOUNDS_PLUS_SPACING),ASBOTCConfigurations.Color.LIGHT_BLUE);
		}

		@Override
		protected MainCreation create(int requiredPower) {
			return new ElasticLine(creator, requiredPower, pointA, pointB);
		}
		
	}
}
