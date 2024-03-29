/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class ElasticLine extends ContactStaticLine {
	
	private static final float  IMPULSE_MAGNITUDE=0.3f;
	private Set<Physical> contactShapes=new HashSet<Physical>();
	
	
	/**
	 * 
	 * @param creator
	 * @param p1
	 * @param p2
	 */
	public ElasticLine(Point p1, Point p2) {
		this(0,false, p1, p2);
	}
	private ElasticLine(int cotentPower, Point p1, Point p2) {
		this( cotentPower, true, p1, p2);
	}
	/**
	 * @param creator
	 * @param cotentPower TODO
	 * @param p1
	 * @param p2
	 * @param color
	 */
	private ElasticLine(int cotentPower,boolean beControlled, Point p1, Point p2) {
		super( cotentPower,beControlled, p1, p2, ASBOTXConfigs.Color.LIGHT_BLUE);
	}
	
	@Override
	public void update(){
		
		if(!contactShapes.isEmpty()){
			Body body;
			Vec2 imp;
			for(Physical ps:contactShapes){
				if(!ps.isDestroyed()){
					body=ps.getBody();
					imp=body.getLinearVelocity().clone();
					
					imp.mulLocal(IMPULSE_MAGNITUDE);
					body.applyLinearImpulse(imp, body.getWorldCenter());
				}
			}
			contactShapes.clear();
			
		}
	}
	@Override
	public void beginContact(Contact contact, Fixture thisFixture, Fixture thatFixture) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void endContact(Contact contact, Fixture thisFixture, Fixture thatFixture) {
		if(thatFixture.getBody().getType()==BodyType.DYNAMIC&&thatFixture.getBody().getUserData()!=null)//null happens when the creation is destroying
			contactShapes.add((Physical)thatFixture.getBody().getUserData());
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold, Fixture thisFixture, Fixture thatFixture) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse, Fixture thisFixture, Fixture thatFixture) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static class ElasticLineDefiner extends StaticLineDefiner{

		public ElasticLineDefiner() {
			super(ASBOTXConfigs.CreationPowerComsumption.ELASTIC_LINE,new Point(2*ICON_BOUNDS_PLUS_SPACING,ICON_BOUNDS_PLUS_SPACING),ASBOTXConfigs.Color.LIGHT_BLUE);
		}

		@Override
		protected MainCreation create(int requiredPower) {
			return new ElasticLine(requiredPower, pointA, pointB);
		}
		
	}
	
}
