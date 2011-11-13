package com.google.gwt.maeglin89273.game.mengine.physics;


public class Point3D extends Point {
	/**
	 * @uml.property  name="z"
	 */
	private double z;
	public Point3D(double x, double y,double z) {
		super(x, y);
		this.z=z;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param z
	 * @uml.property  name="z"
	 */
	public void setZ(double z) {
		this.z = z;
	}
	/**
	 * @return
	 * @uml.property  name="z"
	 */
	public double getZ() {
		return z;
	}
	public void setPosition(double x,double y,double z){
		super.setPosition(x, y);
		this.z=z;
	}
	public void setPosition(Point3D p){
		this.setPosition(p.getX(),p.getY(),p.getZ());
	}
	public void translate(double dX,double dY,double dZ){
		super.translate(dX, dY);
		z+=dZ;
	}
}
