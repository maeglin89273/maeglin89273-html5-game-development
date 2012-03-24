/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
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
	public WoodLine(Point p1, Point p2) {
		this(0,false, p1, p2);
		
	}
	/**
	 * 
	 * @param creator
	 * @param contentPower
	 * @param p1
	 * @param p2
	 */
	private WoodLine(int contentPower, Point p1, Point p2) {
		this(contentPower, true, p1, p2);
	}
	/**
	 * @param creator
	 * @param contentPower
	 * @param beControlled
	 * @param p1
	 * @param p2
	 * @param color
	 */
	private WoodLine(int contentPower, boolean beControlled,Point p1, Point p2) {
		super(contentPower, beControlled, p1, p2,ASBOTXConfigs.Color.WOOD);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean doesLineBreak(float normalImpulse) {
		return (woodStrength-=normalImpulse)<=0;
	}
	
	public static class WoodLineDefiner extends StaticLineDefiner{

		public WoodLineDefiner(){
			super(ASBOTXConfigs.CreationPowerComsumption.WOOD_LINE,
			new Point(ICON_BOUNDS_PLUS_SPACING,ICON_BOUNDS_PLUS_SPACING),ASBOTXConfigs.Color.WOOD);
		}
		@Override
		protected MainCreation create(int requiredPower){
			return new WoodLine(requiredPower,pointA,pointB);
		}
	}
}
