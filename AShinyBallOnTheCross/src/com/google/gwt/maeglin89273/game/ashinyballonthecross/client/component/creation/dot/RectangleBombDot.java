/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Creation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Rectangle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
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
	public RectangleBombDot(Creator creator, Point p) {
		super(creator, p, ASBOTCConfigurations.Color.YELLOW_BORDER, 3f, 45f);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.dot.BombDot#generateShape(com.google.gwt.maeglin89273.game.mengine.physics.Point, double)
	 */
	@Override
	protected PhysicalShape generateShape(Point position, double angle) {
		return new Rectangle(creator, position , angle, 5+Random.nextInt(16), 5+Random.nextInt(16), this.dotColor);
	}
	public static class RectangleBombDotDefiner extends BombDotDefiner{

		public RectangleBombDotDefiner(Creator creator) {
			super(creator,null,ASBOTCConfigurations.Color.YELLOW_BORDER);
			
		}

		@Override
		protected MainCreation create(int requiredPower) {
			return new RectangleBombDot(creator,position);
		}
		
	}
}