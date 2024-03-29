/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.dot;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.Circle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class CircleBombDot extends BombDot {

	/**
	 * @param p
	 * @param creator
	 * @param color
	 * @param count
	 * @param impulseMag
	 */
	public CircleBombDot(Point p) {
		super(p, ASBOTXConfigs.Color.BLUE,5f, 20f);
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.dot.BombDot#spoutShape()
	 */
	@Override
	protected PhysicalShape generateShape(Point position, double angle) {
		return new Circle(this.controller,position,10, this.dotColor);
	}
	public static class CircleBombDotDefiner extends BombDotDefiner{

		public CircleBombDotDefiner() {
			super(new Point(0,2*ICON_BOUNDS_PLUS_SPACING),ASBOTXConfigs.Color.BLUE);
			
		}

		@Override
		protected MainCreation create(int requiredPower) {
			return new CircleBombDot(position);
		}
	}
}
