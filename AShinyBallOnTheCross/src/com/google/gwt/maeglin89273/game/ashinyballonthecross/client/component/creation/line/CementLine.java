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
public class CementLine extends BreakableStaticLine{
	private static final float STRUCTURE_STRENGTH=17f; 
	
	/**
	 * @param creator
	 * @param p1
	 * @param p2
	 */
	public CementLine(Creator creator, Point p1, Point p2) {
		this(creator, 0,false, p1, p2);
		
	}
	/**
	 * 
	 * @param creator
	 * @param contentPower
	 * @param p1
	 * @param p2
	 */
	private CementLine(Creator creator,int contentPower, Point p1, Point p2) {
		this(creator, contentPower, true, p1, p2);
	}
	/**
	 * 
	 * @param creator
	 * @param contentPower
	 * @param p1
	 * @param p2
	 */
	private CementLine(Creator creator,int contentPower,boolean beControlled, Point p1, Point p2) {
		super(creator,contentPower,beControlled, p1, p2, ASBOTCConfigurations.Color.GRAY);
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
			
		if(contact.getFixtureA().equals(fixture)&&(contact.getFixtureB().getUserData() instanceof Dynamic)||
				contact.getFixtureB().equals(fixture)&&(contact.getFixtureA().getUserData() instanceof Dynamic)){
					
			for(int i=0;i<impulse.tangentImpulses.length;i++){
				if(impulse.normalImpulses[i]!=0){
					if((STRUCTURE_STRENGTH<impulse.normalImpulses[i])){
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
	

	public static class CementLineDefiner extends StaticLineDefiner{

		public CementLineDefiner(Creator creator){
			super(creator,ASBOTCConfigurations.CreationPowerComsumption.CEMENT_LINE,
			new Point(ICON_BOUNDS_PLUS_SPACING,ICON_BOUNDS_PLUS_SPACING),ASBOTCConfigurations.Color.GRAY);
		}
		@Override
		protected MainCreation create(int requiredPower){
			return new CementLine(creator,requiredPower,pointA,pointB);
		}
	}

}
