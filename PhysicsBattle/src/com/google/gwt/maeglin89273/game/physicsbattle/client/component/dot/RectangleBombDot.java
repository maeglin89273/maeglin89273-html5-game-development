/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.dot;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.Rectangle;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class RectangleBombDot extends BombDot {

	/**
	 * @param p
	 * @param world
	 * @param color
	 * @param count
	 * @param impulseMag
	 * @param spoutDistance
	 */
	public RectangleBombDot(Point p, PhysicalWorld world) {
		super(p, world, GameColors.YELLOW_BORDER_Color, 15, 2.2f, 45f);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.physicsbattle.client.component.dot.BombDot#generateShape(com.google.gwt.maeglin89273.game.mengine.physics.Point, double)
	 */
	@Override
	protected PhysicalShape generateShape(Point position, double angle) {
		return new Rectangle(world, position, 5+Random.nextInt(16), 5+Random.nextInt(16), angle, this.dotColor);
	}

}
