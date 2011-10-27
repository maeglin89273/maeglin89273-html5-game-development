/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.component;

import com.google.gwt.maeglin89273.game.mengine.physics.Point3D;

/**
 * @author  Maeglin Liao
 */
public interface Is3Dimension extends CanvasComponent {
	
	public abstract double getZ();

	public abstract void setZ(double z);
	
	public abstract void set3DPosition(Point3D p);
	
	public abstract Point3D get3DPosition();
	
	public abstract void setRenderingScale(double s);
}
