/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
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
		super(creator,contentPower,beControlled, p1, p2, ASBOTXConfigs.Color.GRAY);
		
	}
	@Override
	public boolean doesLineBreak(float normalImpulse) {
		return STRUCTURE_STRENGTH<normalImpulse;
	}
	
	public static class CementLineDefiner extends StaticLineDefiner{

		public CementLineDefiner(Creator creator){
			super(creator,ASBOTXConfigs.CreationPowerComsumption.CEMENT_LINE,
			new Point(ICON_BOUNDS_PLUS_SPACING,ICON_BOUNDS_PLUS_SPACING),ASBOTXConfigs.Color.GRAY);
		}
		@Override
		protected MainCreation create(int requiredPower){
			return new CementLine(creator,requiredPower,pointA,pointB);
		}
	}

}
