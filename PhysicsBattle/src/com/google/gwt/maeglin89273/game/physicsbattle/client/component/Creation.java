/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component;


import com.google.gwt.maeglin89273.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class Creation extends GeneralComponent {
	protected PhysicalWorld world;
	/**
	 * @param p
	 * @param w
	 * @param h
	 */
	public Creation(Point p, double w, double h,PhysicalWorld world) {
		super(p, w, h);
		this.world=world;
	}

}
