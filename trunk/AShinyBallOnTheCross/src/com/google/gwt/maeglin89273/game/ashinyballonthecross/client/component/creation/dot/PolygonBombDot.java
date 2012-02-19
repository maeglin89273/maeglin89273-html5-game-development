/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Polygon;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Random;

/**
 * @author Liao
 *
 */
public class PolygonBombDot extends BombDot {

	/**
	 * @param p
	 * @param creator
	 * @param color
	 * @param count
	 * @param impulseMag
	 * @param spoutDistance
	 */
	public PolygonBombDot(Creator creator,Point p ) {
		super(creator,p, ASBOTXConfigs.Color.RED, 8f, 36);
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.dot.BombDot#generateShape(com.google.gwt.maeglin89273.game.mengine.physics.Point, double)
	 */
	@Override
	protected PhysicalShape generateShape(Point position, double angle) {
		return new Polygon(this.creator,this.controller, position, angle ,Polygon.generateRandomInscribedPolygonVertices(3+Random.nextInt(6), 13+Random.nextInt(3)), dotColor);
	}
	public static class PolygonBombDotDefiner extends BombDotDefiner{

		public PolygonBombDotDefiner(Creator creator) {
			super(creator,new Point(2*ICON_BOUNDS_PLUS_SPACING,2*ICON_BOUNDS_PLUS_SPACING), ASBOTXConfigs.Color.RED);
			
		}

		@Override
		protected MainCreation create(int requiredPower) {
			return new PolygonBombDot(creator,position);
		}
		
	}
}
