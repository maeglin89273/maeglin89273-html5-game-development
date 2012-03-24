/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line;


import org.jbox2d.collision.AABB;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spatial;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public abstract class PhysicalLine extends Line implements Physical {
	protected Body body;
	protected Fixture fixture; 
	protected AABB aabb;
	protected boolean destroyed=false;
	
	protected CssColor lineColor;
	/**
	 * @param creator
	 * @param contentPower TODO
	 * @param p1
	 * @param p2
	 */
	protected PhysicalLine(int contentPower,boolean beControlled, Point p1,Point p2, CssColor color) {
		super(contentPower, beControlled, p1, p2);
		if(this.isVerified()){
			this.lineColor=color;
			
			BodyDef bodyDef=new BodyDef();
			bodyDef.position.set(CoordinateConverter.coordPixelToWorld(position));
			this.body=creator.getWorld().getWorld().createBody(bodyDef);
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
	public Spatial getSpace() {
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
		destroyed=true;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getAABB()
	 */
	@Override
	public AABB getAABB() {
		return aabb;
	}
	@Override 
	public boolean isDestroyed(){
		return destroyed;
	}
}
