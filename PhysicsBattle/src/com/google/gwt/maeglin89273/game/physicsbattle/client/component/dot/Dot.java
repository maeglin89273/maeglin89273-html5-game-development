/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.dot;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.Creation;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class Dot extends Creation {
	
	protected CssColor dotColor;
	private static final int DOT_RADIUS=3;
	
	protected Dot(Point p,PhysicalWorld world) {
		super(p, 2*DOT_RADIUS, 2*DOT_RADIUS,world);
	}
	public CssColor getColor(){
		return dotColor;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setFillStyle(dotColor);
		context.beginPath();
		context.arc(position.getX(), position.getY(), DOT_RADIUS, 0, 2*Math.PI, true);
		context.closePath();
		context.fill();
	}

}
