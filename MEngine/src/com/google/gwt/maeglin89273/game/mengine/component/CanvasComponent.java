package com.google.gwt.maeglin89273.game.mengine.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author  Liao
 */
public interface CanvasComponent extends Component {
	public enum PositionType{NORTHWEST,NORTH,NORTHEAST,WEST,CENTER,EAST,SOUTHWEST,SOUTH,SOUTHEAST}
	
	
	public abstract double getX();

	public abstract void setX(double x);

	public abstract double getY();
	
	public abstract void setY(double y);
	
	public abstract void setWidth(double width);

	public abstract Point getPositionAt(PositionType type);

	public abstract void setPositionAt(PositionType type,Point p);
	
	public abstract void setHeight(double height);

	public abstract void setSize(double w, double h);

	public abstract void update();

	public abstract void draw(Context2d context);
	
}