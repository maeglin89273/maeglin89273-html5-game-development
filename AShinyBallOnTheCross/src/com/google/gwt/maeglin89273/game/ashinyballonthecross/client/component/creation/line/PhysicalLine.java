/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;


import org.jbox2d.collision.AABB;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public abstract class PhysicalLine extends Line implements Physical {
	protected Body body;
	protected Fixture fixture; 
	protected AABB aabb;
	
	protected CssColor lineColor;
	/**
	 * @param creator
	 * @param contentPower TODO
	 * @param p1
	 * @param p2
	 */
	protected PhysicalLine(Creator creator,int contentPower,boolean beControlled, Point p1,Point p2, CssColor color) {
		super(creator, contentPower, beControlled, p1, p2);
		if(this.isVerified()){
			this.lineColor=color;
			
			BodyDef bodyDef=new BodyDef();
			bodyDef.position.set(CoordinateConverter.coordPixelToWorld(position));
			this.body=this.creator.getWorld().getWorld().createBody(bodyDef);
		}
	}
	public CssColor getColor(){
		return lineColor;
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
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#destory()
	 */
	@Override
	public void destroy() {
		super.destroy();

		body=null;
		aabb=null;
		lineColor=null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getAABB()
	 */
	@Override
	public AABB getAABB() {
		return aabb;
	}
}
