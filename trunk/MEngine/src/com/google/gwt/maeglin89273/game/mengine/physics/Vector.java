/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.physics;

/**
 * @author Maeglin Liao
 *
 */
public class Vector {
	private double x;
	private double y;
	private double magnitude;
	
	public Vector(double x,double y){
		this.x=x;
		this.y=y;
		magnitude=Math.sqrt(this.x*this.x+this.y*this.y);
	}
	public Vector(Vector v){
		this(v.getVectorX(), v.getVectorY());
	}
	public Vector(double radian,double radius,boolean anticlockwise){
		this(radius*Math.cos(radian),radius*Math.sin((anticlockwise?-1:1)*radian));
	}
	public void setInPolarCoordinate(double radian,double r){
		this.setVector(r*Math.cos(radian), r*Math.sin(radian));
	}
	/**
	 * @param x the x to set
	 */
	
	public void setVectorX(double x) {
		this.x = x;
		magnitude=Math.sqrt(this.x*this.x+this.y*this.y);
	}
	/**
	 * @return the x
	 */
	public double getVectorX() {
		return x;
	}
	/**
	 * @param y the y to set
	 */
	public void setVectorY(double y) {
		this.y = y;
		magnitude=Math.sqrt(this.x*this.x+this.y*this.y);
	}
	/**
	 * @return the y
	 */
	public double getVectorY() {
		return y;
	}
	public void add(Vector v){
		add(v.getVectorX(),v.getVectorY());
	}
	public void add(double x,double y){
		this.x+=x;
		this.y+=y;
		this.magnitude=Math.sqrt(this.x*this.x+this.y*this.y);
		
		
	}
	public static Vector add(Vector a,Vector b){
		return new Vector(a.getVectorX()+b.getVectorX(),a.getVectorY()+b.getVectorY());
	}
	public void setVector(Vector v){
		this.x=v.getVectorX();
		this.y=v.getVectorY();
		this.magnitude=v.getMagnitude();
	}
	public void setVector(double x,double y){
		this.x=x;
		this.y=y;
		magnitude=Math.sqrt(this.x*this.x+this.y*this.y);
	}
	
	public double getMagnitude(){
		return magnitude;
	}
	public void setMagnitude(double mag){
		x*=mag/magnitude;
		y*=mag/magnitude;
		magnitude=mag;
	}
	
	public double dotProduct(Vector v){
		return x*v.getVectorX()+y*v.getVectorY();
	}
	public static double dotProduct(Vector a ,Vector b){
		return a.getVectorX()*b.getVectorX()+a.getVectorY()*b.getVectorY();
		
	}
	public void mutiply(double m){
		x*=x;
		y*=y;
		magnitude*=m;
	}
	public static Vector mutiply(Vector a,double m){
		return new Vector(a.getVectorX()*m,a.getVectorY()*m);
	}
	
	public void minus(Vector v){
		minus(v.getVectorX(),v.getVectorY());
	}
	public void minus(double x,double y){
		this.x-=x;
		this.y-=y;
		this.magnitude=Math.sqrt(this.x*this.x+this.y*this.y);
	}
	public static Vector minus(Vector a,Vector b){
		return new Vector(a.getVectorX()-b.getVectorX(),a.getVectorY()-b.getVectorY());
	}
	public void reverseX(){
		x=-x;
	}
	public void reverseY(){
		y=-y;
	}
}
