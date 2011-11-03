/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.line;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.utility.CoordinateConverter;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;

/**
 * @author Maeglin Liao
 *
 */
public abstract class PhysicalLine extends Line implements Physical {
	protected Body body;
	protected PixelAABB aabb;
	/**
	 * @param p1
	 * @param p2
	 * @param world
	 */
	public PhysicalLine(Point p1, Point p2, PhysicalWorld world) {
		super(p1, p2, world);
		BodyDef bodyDef=new BodyDef();
		PolygonShape lineP=new PolygonShape();
		lineP.setAsEdge(CoordinateConverter.coordPixelsToWorld(pointA), CoordinateConverter.coordPixelsToWorld(pointB));
		
		this.body=this.world.getWorld().createBody(bodyDef);
		aabb=CoordinateConverter.transformAABB(body.createFixture(lineP, 0f).getAABB());
		setWidth(aabb.getWidth());
		setHeight(aabb.getHeight());
		this.world.addLine(this);
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
		return world;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#destory()
	 */
	@Override
	public void destory() {
		world.removePhysicalLine(this);
		body=null;
		aabb=null;
		world=null;

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getAABB()
	 */
	@Override
	public PixelAABB getAABB() {
		return aabb;
	}

	

}
