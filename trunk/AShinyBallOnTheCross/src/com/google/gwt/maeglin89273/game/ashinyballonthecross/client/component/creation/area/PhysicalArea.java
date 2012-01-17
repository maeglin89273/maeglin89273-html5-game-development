/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area;

import org.jbox2d.collision.AABB;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class PhysicalArea extends Area implements Physical {
	protected Body body;
	protected AABB aabb;
	
	/**
	 * @param creator
	 * @param contentPower TODO
	 * @param p
	 * @param w
	 * @param h
	 */
	protected PhysicalArea(Creator creator,int contentPower,boolean beControlled, Point p, double w, double h, double angle) {
		super(creator,contentPower,beControlled, p, w, h, angle);
		
		if(isVerified()){
			BodyDef bodyDef=new BodyDef();
			bodyDef.position.set(CoordinateConverter.coordPixelToWorld(position));
			this.body=creator.getWorld().getWorld().createBody(bodyDef);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getBody()
	 */
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
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getAABB()
	 */
	@Override
	public AABB getAABB() {
		return aabb;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creation#destory()
	 */
	@Override
	public void destroy() {
		super.destroy();
		
		body=null;
		aabb=null;
	}
}
