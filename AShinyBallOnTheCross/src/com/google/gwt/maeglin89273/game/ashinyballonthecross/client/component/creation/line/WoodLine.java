/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;

import org.jbox2d.callbacks.ContactImpulse;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.dynamics.contacts.Contact;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Dynamic;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public class WoodLine extends BreakableStaticLine {
	private float woodStrength=40f;
	
	/**
	 * @param creator
	 * @param p1
	 * @param p2
	 */
	public WoodLine(Creator creator, Point p1, Point p2) {
		this(creator, 0,false, p1, p2);
		
	}
	/**
	 * 
	 * @param creator
	 * @param contentPower
	 * @param p1
	 * @param p2
	 */
	private WoodLine(Creator creator,int contentPower, Point p1, Point p2) {
		this(creator, contentPower, true, p1, p2);
	}
	/**
	 * @param creator
	 * @param contentPower
	 * @param beControlled
	 * @param p1
	 * @param p2
	 * @param color
	 */
	private WoodLine(Creator creator, int contentPower, boolean beControlled,Point p1, Point p2) {
			
		super(creator, contentPower, beControlled, p1, p2,ASBOTCConfigurations.Color.WOOD);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		if(contact.getFixtureA().equals(fixture)&&(contact.getFixtureB().getUserData() instanceof Dynamic)||
				contact.getFixtureB().equals(fixture)&&(contact.getFixtureA().getUserData() instanceof Dynamic)){
					
			for(int i=0;i<impulse.tangentImpulses.length;i++){
				if(impulse.normalImpulses[i]!=0){
					if((woodStrength-=impulse.normalImpulses[i])<=0){
						WorldManifold wm=new WorldManifold();
						contact.getWorldManifold(wm);
								
						breakPoint=CoordinateConverter.coordWorldToPixels(wm.points[0]);
						impOnNormal=wm.normal.clone();
								
						if(contact.getFixtureA().equals(fixture)){
							impOnNormal.negateLocal();
						}
						impOnNormal.mulLocal(impulse.normalImpulses[i]);
								
					}
					return;
				}
			}
		}
		
	}
	public static class WoodLineDefiner extends StaticLineDefiner{

		public WoodLineDefiner(Creator creator){
			super(creator,ASBOTCConfigurations.CreationPowerComsumption.WOOD_LINE,
			new Point(ICON_BOUNDS_PLUS_SPACING,ICON_BOUNDS_PLUS_SPACING),ASBOTCConfigurations.Color.WOOD);
		}
		@Override
		protected MainCreation create(int requiredPower){
			return new WoodLine(creator,requiredPower,pointA,pointB);
		}
	}
}
