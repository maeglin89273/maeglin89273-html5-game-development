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
	
	
	public Vector(double x,double y){
		this.x=x;
		this.y=y;
		
	}
	public Vector(Vector v){
		this(v.getVectorX(), v.getVectorY());
	}
	public Vector(double radius,double radian,boolean anticlockwise){
		this(radius*Math.cos(radian),radius*Math.sin((anticlockwise?-1:1)*radian));
	}
	public void setInPolarCoordinate(double r,double radian){
		this.setVector(r*Math.cos(radian), r*Math.sin(radian));
	}
	/**
	 * @param x the x to set
	 */
	
	public void setVectorX(double x) {
		this.x = x;
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
	}
	public void addMagnitude(double mag){
		setMagnitude(getMagnitude()+mag);
	}
	public static Vector add(Vector a,Vector b){
		return new Vector(a.getVectorX()+b.getVectorX(),a.getVectorY()+b.getVectorY());
	}
	public void setVector(Vector v){
		this.x=v.getVectorX();
		this.y=v.getVectorY();
		
	}
	public void setVector(double x,double y){
		this.x=x;
		this.y=y;
	}
	public double getSquare(){
		return this.x*this.x+this.y*this.y;
	}
	public double getMagnitude(){
		return Math.sqrt(this.x*this.x+this.y*this.y);
	}
	public void setMagnitude(double mag){
		double magLocal=getMagnitude();
		x*=mag/magLocal;
		y*=mag/magLocal;
	}
	
	public double dotProduct(Vector v){
		return x*v.getVectorX()+y*v.getVectorY();
	}
	public static double dotProduct(Vector a ,Vector b){
		return a.getVectorX()*b.getVectorX()+a.getVectorY()*b.getVectorY();
		
	}
	public double crossProduct(Vector v){
		return y*v.getVectorX()-x*v.getVectorY();
	}
	public static double crossProduct(Vector a,Vector b){
		return a.getVectorY()*b.getVectorX()-a.getVectorX()*b.getVectorY();
	}
	public void divided(double divisor){
		x/=divisor;
		y/=divisor;
	}
	
	public static Vector divided(Vector a,double divisor){
		return new Vector(a.getVectorX()/divisor,a.getVectorY()*divisor);
	}
	public void mutiply(double m){
		x*=m;
		y*=m;
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
		
	}
	public void minusMagnitude(double mag){
		setMagnitude(getMagnitude()-mag);
	}
	public static Vector minus(Vector a,Vector b){
		return new Vector(a.getVectorX()-b.getVectorX(),a.getVectorY()-b.getVectorY());
	}
	
	public void reverse(){
		x=-x;
		y=-y;
	}
	public static Vector reverse(Vector v){
		return new Vector(-v.getVectorX(),-v.getVectorY());
	}
	public void rotate(double deltaInRadians){
		double absAngle=getAngle()+deltaInRadians;
		double r=getMagnitude();
		setVector(r*Math.cos(absAngle),r*Math.sin(absAngle));
	}
	
	public void normalized(){
		x/=getMagnitude();
		y/=getMagnitude();
	}
	public double getAngle(){
		return Math.atan2(y, x);
	}
	public Vector clone(){
		return new Vector(this);
	}
}
