/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.mengine.component.Physical;
import com.google.gwt.maeglin89273.mengine.component.Spacial;
import com.google.gwt.maeglin89273.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.mengine.physics.Point;
import com.google.gwt.maeglin89273.mengine.physics.Vector;
import com.google.gwt.maeglin89273.mengine.utility.CoordinateConverter;
import com.google.gwt.user.client.Random;

/**
 * @author Liao
 *
 */
public class Circle extends PhysicalShape{

	private double radius;
	public Circle(PhysicalWorld space,Point p,double radius){
		this(space, p, radius, GameColors.getRandomShapeBorderColor());
	}
	public Circle(PhysicalWorld world,Point p,double radius,CssColor color){
		super(p, radius*2, radius*2, world, 0, color);
		
		CircleShape shape=new CircleShape();
		FixtureDef fixtureDef=new FixtureDef();
		
		shape.m_radius=CoordinateConverter.scalerPixelsToWorld(this.radius);
		fixtureDef.shape=shape;
		fixtureDef.density=0.6f;
		fixtureDef.restitution=0.8f;
		fixtureDef.friction=0.2f;
		
		fixture=body.createFixture(fixtureDef);
		aabb=CoordinateConverter.transformAABB(fixture.getAABB());
	}
	public Circle(PhysicalWorld space,Point p){
		this(space,p,4+Random.nextInt(7));
	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		
		context.setStrokeStyle(borderColor);
		context.setLineWidth(1.5);
		context.beginPath();
		context.arc(position.getX(), position.getY(), radius, 0, 2*Math.PI,true);
		context.closePath();
		context.stroke();
		
	}
	

}
