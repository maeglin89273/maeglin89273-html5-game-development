/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.physics;

import com.google.gwt.maeglin89273.game.mengine.component.Component;

/**
 * @author Liao
 *
 */
public class PixelAABB{
	private final Point position=new Point(0,0);
	private double width;
	private double height;
	public PixelAABB(Point p,double width,double height){
		this.position.setPosition(p);
		this.width=width;
		this.height=height;
	}
	
	public double getTopY() {
		
		return position.getY()-height/2;
	}

	
	public double getLeftX() {
		
		return position.getX()-width/2;
	}

	
	public double getBottomY() {
		
		return position.getY()+height/2;
	}

	
	public double getRightX() {
		return position.getX()+width/2;
	}

	
	public double getWidth() {
		
		return width;
	}

	
	public Point getPosition() {
		
		return new Point(position);
	}

	
	public double getHeight() {
		
		return height;
	}
	
	public void setWidth(double width) {
		this.width=width;
	}
	
	public void setPosition(Point p) {
		position.setPosition(p);
	}
	
	public void setHeight(double height) {
		this.height=height;
	}

}
