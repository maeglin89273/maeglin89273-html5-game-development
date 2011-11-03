/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.dot;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.Circle;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;

/**
 * @author Maeglin Liao
 *
 */
public class CircleBombDot extends BombDot {

	/**
	 * @param p
	 * @param world
	 * @param color
	 * @param count
	 * @param impulseMag
	 */
	public CircleBombDot(Point p, PhysicalWorld world) {
		super(p, world, GameColors.BLUE_BORDER_COLOR,15, 2.5f,20f);
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.physicsbattle.client.component.dot.BombDot#spoutShape()
	 */
	@Override
	protected PhysicalShape generateShape(Point position, double angle) {
		return new Circle(this.world,position, 7.5, this.dotColor);
	}

}
