/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Creation;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;


/**
 * @author Maeglin Liao
 *
 */
public abstract class PhysicalShape extends Creation implements
		Physical {
	
	protected Body body;
	protected  Fixture fixture;
	protected  AABB aabb;
	
	protected CssColor borderColor;
	
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
	protected PhysicalShape(Creator creator,int contentPower, Point p, double w,double h,double angle, CssColor color) {
		super(creator,contentPower,p, w, h, angle);
		if(this.isVerified()){
			this.borderColor=color;
			
			BodyDef bodyDef=new BodyDef();
			bodyDef.type=BodyType.DYNAMIC;
			bodyDef.position.set(CoordinateConverter.coordPixelToWorld(position));
			bodyDef.angle=(float)-angle;
			
			body=creator.getWorld().getWorld().createBody(bodyDef);
			
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
	public Spacial getSpace() {
		return creator.getWorld();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#destory()
	 */
	@Override
	public void destroy() {
		super.destroy();
		if(fixture!=null){
			fixture.setUserData(null);
			fixture=null;
		}
		
		body=null;
		aabb=null;
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
