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
public class SimpleStaticLine extends StaticLine {
	public static final int LIMITED_TIME=30;
	
	/**
	 * 
	 * @param creator
	 * @param p1
	 * @param p2
	 */
	public SimpleStaticLine(Creator creator, Point p1, Point p2) {
		this(creator,0,false, p1, p2);
		
	}
	private SimpleStaticLine(Creator creator, int contentPower, Point p1, Point p2) {
		this(creator, contentPower, true, p1, p2);
	}
	/**
	 * @param contentPower TODO
	 * @param p1
	 * @param p2
	 * @param creator
	 */
	private SimpleStaticLine(Creator creator, int contentPower,boolean beControlled, Point p1, Point p2) {
		super(creator, contentPower,beControlled, p1, p2, ASBOTXConfigs.Color.BLACK);
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	public static class BlackStaticLineDefiner extends StaticLineDefiner{

		public BlackStaticLineDefiner(Creator creator){
			super(creator,ASBOTXConfigs.CreationPowerComsumption.SIMPLE_STATIC_LINE,new Point(0,ICON_BOUNDS_PLUS_SPACING),ASBOTXConfigs.Color.BLACK);
			
		}
		@Override
		protected MainCreation create(int requiredPower){
			return new SimpleStaticLine(creator,requiredPower,pointA, pointB);
		}
	}
	
}
