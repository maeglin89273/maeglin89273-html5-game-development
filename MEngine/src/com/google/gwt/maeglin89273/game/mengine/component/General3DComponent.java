package com.google.gwt.maeglin89273.game.mengine.component;


import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Point3D;


public abstract class General3DComponent implements Is3Dimension{
		
	
	protected final Point3D position=new Point3D(0,0,0);
	
	protected double width;
	
	protected double height;
	
	protected double scale=1;
	protected General3DComponent(Point3D p,double w,double h){
		this.position.setPosition(p);
		this.setWidth(w);
		this.setHeight(h);
		
	}
	@Override
	public double getX(){
		return position.getX();
	}
	@Override
	public void setX(double x){
		position.setX(x);
	}
	@Override
	public double getY(){
		return position.getY();
	}
	@Override
	public void setY(double y){
		position.setY(y);
	}
	@Override
	public double getZ() {
		return position.getZ();
	}
	@Override
	public void setZ(double z) {
		position.setZ(z);
	}
	
	@Override
	public Point getPositionAt(PositionType type){
		switch(type){
		case NORTHWEST:
			return new Point(position.getX()-width/2,position.getY()-height/2);
		case NORTH:
			return new Point(position.getX(),position.getY()-height/2);
		case NORTHEAST:
			return new Point(position.getX()+width/2,position.getY()-height/2);
		case WEST:
			return new Point(position.getX()-width/2,position.getY());
		case CENTER:
			return new Point(position);
		case EAST:
			return new Point(position.getX()+width/2,position.getY());
		case SOUTHWEST:
			return new Point(position.getX()-width/2,position.getY()+height/2);
		case SOUTH:
			return new Point(position.getX(),position.getY()+height/2);
		case SOUTHEAST:
			return new Point(position.getX()+width/2,position.getY()+height/2);
		default:
			throw new IllegalArgumentException("Wrong dirction argument.");
		}
	}
	@Override
	public void setPositionAt(PositionType type,Point p){
		switch(type){
			case NORTHWEST:
				p.translate(width/2,height/2);
				this.position.setPosition(p);
				break;
			case NORTH:
				p.translate(0,height/2);
				this.position.setPosition(p);
				break;
			case NORTHEAST:
				p.translate(-width/2,height/2);
				this.position.setPosition(p);
				break;
			case WEST:
				p.translate(width/2,0);
				this.position.setPosition(p);
				break;
			case CENTER:
				this.position.setPosition(p);
				break;
			case EAST:
				p.translate(-width/2,0);
				this.position.setPosition(p);
				break;
			case SOUTHWEST:
				p.translate(width/2,-height/2);
				this.position.setPosition(p);
				break;
			case SOUTH:
				p.translate(0,-height/2);
				this.position.setPosition(p);
				break;
			case SOUTHEAST:
				p.translate(-width/2,-height/2);
				this.position.setPosition(p);
				break;
			default:
				throw new IllegalArgumentException("Wrong dirction argument.");
		}
	}
	@Override
	public void setPosition(Point p){
		position.setPosition(p);
	}
	@Override
	public double getTopY(){
		return getY()-height/2;
	}
	@Override
	public double getLeftX(){
		return getX()-width/2;
	}
	@Override
	public double getBottomY(){
		return getY()+height/2;
	}
	@Override
	public double getRightX(){
		return getX()+width/2;
	}
	
	@Override
	public void set3DPosition(Point3D p) {
		position.setPosition(p);

	}
	@Override
	public Point3D get3DPosition() {
		return position;
	}
	@Override
	public Point getPosition(){
		return new Point(position);
	}
	
	@Override
	public void setWidth(double width) {
		this.width = width;
	}
	
	@Override
	public double getWidth() {
		return width;
	}
	
	@Override
	public void setHeight(double height) {
		this.height = height;
	}
	
	@Override
	public double getHeight() {
		return height;
	}
	@Override
	public void setSize(double w,double h){
		this.width=w;
		this.height=h;
	}
	@Override
	public void setRenderingScale(double s){
		this.scale=s;
	}
}
