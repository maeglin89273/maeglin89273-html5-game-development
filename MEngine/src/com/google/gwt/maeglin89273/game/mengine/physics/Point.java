package com.google.gwt.maeglin89273.game.mengine.physics;

public class Point {
	/**
	 * @uml.property  name="x"
	 */
	private double x;
	/**
	 * @uml.property  name="y"
	 */
	private double y;
	
	
	public Point(double x,double y){
		this.x=x;
		this.y=y;
	}
	public Point(Point p){
		this(p.getX(), p.getY());
	}
	public Point(double radius,double radian,boolean anticlockwise){
		this(radius*Math.cos(radian),radius*Math.sin((anticlockwise?-1:1)*radian));
	}
	public void setInPolarCoordinate(double r,double radian){
		this.setPosition(r*Math.cos(radian), r*Math.sin(radian));
	}
	/**
	 * @param x
	 * @uml.property  name="x"
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return
	 * @uml.property  name="x"
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param y
	 * @uml.property  name="y"
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return
	 * @uml.property  name="y"
	 */
	public double getY() {
		return y;
	}
	public void setPosition(double x,double y){
		this.x=x;
		this.y=y;
	}
	public void setPosition(Point p){
		this.setPosition(p.getX(),p.getY());
	}
	public void translate(Vector v){
		this.translate(v.getVectorX(), v.getVectorY());
	}
	public void translate(double dX,double dY){
		this.x+=dX;
		this.y+=dY;
	}
	public Vector delta(Point p){
		return new Vector(p.getX()-getX(),p.getY()-getY());
	}
	public Vector delta(double x,double y){
		return new Vector(x-getX(),y-getY());
	}
	public static Vector delta(Point p1,Point p2){
		return new Vector(p2.getX()-p1.getX(),p2.getY()-p1.getY());
	}
	public Point clone(){
		return new Point(this);
	}
}
