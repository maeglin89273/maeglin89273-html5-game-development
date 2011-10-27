/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.mengine.component.Physical;
import com.google.gwt.maeglin89273.mengine.component.Spacial;
import com.google.gwt.maeglin89273.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.mengine.physics.Point;
import com.google.gwt.maeglin89273.mengine.utility.CoordinateConverter;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public abstract class PhysicalShape extends GeneralComponent implements
		Physical {
	
	

	protected Body body;
	protected  Fixture fixture;
	protected  PixelAABB aabb;
	protected  PhysicalWorld world;
	
	protected CssColor borderColor;
	
	protected PhysicalShape(Point p, double w, double h,PhysicalWorld world,double angle,CssColor color) {
		super(p, w, h);
		this.world=world;
		this.borderColor=color;
		
		BodyDef bodyDef=new BodyDef();
		bodyDef.type=BodyType.DYNAMIC;
		bodyDef.position.set(CoordinateConverter.coordPixelsToWorld(position));
		bodyDef.angle=(float)angle;
		
		body=world.getWorld().createBody(bodyDef);
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.Physical#getBody()
	 */
	public CssColor getColor(){
		return borderColor;
	}
	
	@Override
	public Body getBody() {
		
		return body;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.Physical#getSpace()
	 */
	@Override
	public Spacial getSpace() {
		return world;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.Physical#destory()
	 */
	@Override
	public void destory() {
		world.removeShape(this);
		body=null;
		fixture=null;
		aabb=null;
		world=null;

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.Physical#getAABB()
	 */
	@Override
	public PixelAABB getAABB() {
		
		return aabb;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		CoordinateConverter.transformAABB(fixture.getAABB(), aabb);
		if(world.isOutOfBounds(aabb)){
			destory();
			return;
		}
		//pb.setPosition(CoordinateConverter.coordWorldToPixels(body.getPosition()));
		position.setPosition(CoordinateConverter.coordWorldToPixels(body.getPosition()));
		
		
		
	}

	

}
