/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape;

import org.jbox2d.collision.AABB;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.Creation;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spatial;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;


/**
 * @author Maeglin Liao
 *
 */
public abstract class PhysicalShape extends Creation implements Physical{
		
	protected Body body;
	
	protected  AABB aabb;
	
	protected CssColor borderColor;
	
	private boolean destroyed=false;
	
	private ShapesController controller;
	/**
	 * 
	 * @param creator
	 * @param contentPower
	 * @param p
	 * @param w
	 * @param h
	 * @param angle
	 * @param color
	 */
	protected PhysicalShape( int contentPower,ShapesController controller, Point p, double w,double h,double angle, CssColor color) {
		super(contentPower,p, w, h, angle);
		if(this.isVerified()){
			this.borderColor=color;
			this.controller=controller;
			
			BodyDef bodyDef=new BodyDef();
			bodyDef.type=BodyType.DYNAMIC;
			bodyDef.userData=this;
			bodyDef.position.set(CoordinateConverter.coordPixelToWorld(position));
			bodyDef.angle=(float)-angle;
			
			body=creator.getWorld().getWorld().createBody(bodyDef);
			if(controller!=null){
				controller.addShape(this);
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getBody()
	 */
	public CssColor getColor(){
		return borderColor;
	}
	
	
	@Override
	public Body getBody() {
		
		return body;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getSpace()
	 */
	@Override
	public Spatial getSpace() {
		return creator.getWorld();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#destory()
	 */
	@Override
	public void destroy() {
		super.destroy();
		if(controller!=null){
			controller.removeShape(this);
			controller=null;
		}
		body.setUserData(null);
		
		body=null;
		aabb=null;
		destroyed=true;
	}
	@Override
	public boolean isDestroyed(){
		return destroyed;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getAABB()
	 */
	@Override
	public AABB getAABB() {
		
		return aabb;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		if(creator.getWorld().isOutOfBounds(aabb)){
			destroy();
			return;
		}
		
		position.setPosition(CoordinateConverter.coordWorldToPixels(body.getPosition()));
		setAngle(-body.getAngle());
	}
}
