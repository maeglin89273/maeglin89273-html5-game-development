/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.physics;

import com.google.gwt.maeglin89273.game.mengine.component.Component;

/**
 * @author Liao
 *
 */
public class PixelAABB implements Component{
	private final Point position=new Point(0,0);
	private double width;
	private double height;
	public PixelAABB(Point lowerVertex,Point upperVertex){
		this.position.setPosition(new Point((lowerVertex.getX()+upperVertex.getX())/2,(lowerVertex.getY()+upperVertex.getY())/2));
		this.width=upperVertex.getX()-lowerVertex.getX();
		this.height=lowerVertex.getY()-upperVertex.getY();
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
	public Point getLowerVertex(){
		return new Point(getLeftX(),getBottomY());
	}
	public Point getUpperVertex(){
		return new Point(getRightX(),getTopY());
	}
	public void set(Point lowerVertex,Point upperVertex){
		this.position.setPosition(new Point((lowerVertex.getX()+upperVertex.getX())/2,(lowerVertex.getY()+upperVertex.getY())/2));
		this.width=upperVertex.getX()-lowerVertex.getX();
		this.height=lowerVertex.getY()-upperVertex.getY();
	}
	
}
