package com.google.gwt.maeglin89273.game.mengine.component;

import com.google.gwt.maeglin89273.game.mengine.physics.Point;

public interface Component {

	public abstract double getTopY();

	public abstract double getLeftX();

	public abstract double getBottomY();

	public abstract double getRightX();

	public abstract double getWidth();
	
	public abstract void setWidth(double width);
	
	public abstract double getHeight();
	
	public abstract void setHeight(double height);
	
	public abstract Point getPosition();
	
	public abstract void setPosition(Point p);
	
	public abstract double getAngle();
	
	public abstract void setAngle(double angle);
}