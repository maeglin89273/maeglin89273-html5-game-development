/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.dot;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.Rectangle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class RectangleBombDot extends BombDot {

	/**
	 * @param p
	 * @param creator
	 * @param color
	 * @param count
	 * @param impulseMag
	 * @param spoutDistance
	 */
	public RectangleBombDot( Point p) {
		super(p, ASBOTXConfigs.Color.YELLOW_BORDER, 3f, 45f);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.dot.BombDot#generateShape(com.google.gwt.maeglin89273.game.mengine.physics.Point, double)
	 */
	@Override
	protected PhysicalShape generateShape(Point position, double angle) {
		return new Rectangle(this.controller, position , angle, 5+Random.nextInt(16), 5+Random.nextInt(16), this.dotColor);
	}
	public static class RectangleBombDotDefiner extends BombDotDefiner{

		public RectangleBombDotDefiner() {
			super(new Point(ICON_BOUNDS_PLUS_SPACING,2*ICON_BOUNDS_PLUS_SPACING),ASBOTXConfigs.Color.YELLOW_BORDER);
			
		}

		@Override
		protected MainCreation create(int requiredPower) {
			return new RectangleBombDot(position);
		}
		
	}
}
