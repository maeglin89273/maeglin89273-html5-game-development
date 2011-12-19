/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Circle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Random;

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
	public CircleBombDot(Creator creator, Point p) {
		super(creator,p, ASBOTCConfigurations.Color.BLUE,5f, 20f);
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.dot.BombDot#spoutShape()
	 */
	@Override
	protected PhysicalShape generateShape(Point position, double angle) {
		return new Circle(this.creator,position,10, this.dotColor);
	}
	public static class CircleBombDotDefiner extends BombDotDefiner{

		public CircleBombDotDefiner(Creator creator) {
			super(creator,null,ASBOTCConfigurations.Color.BLUE);
			
		}

		@Override
		protected MainCreation create(int requiredPower) {
			return new CircleBombDot(creator,position);
		}
	}
}
