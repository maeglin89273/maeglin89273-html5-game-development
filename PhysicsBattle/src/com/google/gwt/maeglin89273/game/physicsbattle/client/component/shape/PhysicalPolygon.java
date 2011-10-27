/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class PhysicalPolygon extends PhysicalShape{

	protected PhysicalPolygon(Point p, double w, double h, PhysicalWorld world,double angle,
			CssColor color) {
		super(p, w, h, world, angle, color);
	}
}
