/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.user.client.Random;

/**
 * @author Liao
 *
 */
public class Circle extends PhysicalShape{
	
	private double radius;
	/**
	 * 
	 * @param craetor
	 * @param p
	 */
	public Circle(Creator craetor,Point p){
		this(craetor,0,p,4+Random.nextInt(7),GameColors.getRandomShapeBorderColor());
	}
	/**
	 * 
	 * @param creator
	 * @param p
	 * @param radius
	 * @param color
	 */
	public Circle(Creator creator,Point p,double radius,CssColor color){
		this(creator, (int)radius, p, radius, color);
	}
	/**
	 * 
	 * @param creator
	 * @param contentPower
	 * @param p
	 * @param radius
	 * @param color
	 */
	private Circle(Creator creator,int contentPower,Point p,double radius,CssColor color){
		super(creator,contentPower, p, radius*2, radius*2, 0, color);
		if(this.isVerified()){
			this.radius=radius;
			body.setBullet(true);
			CircleShape shape=new CircleShape();
			FixtureDef fixtureDef=new FixtureDef();
			
			shape.m_radius=CoordinateConverter.scalerPixelsToWorld(this.radius);
			fixtureDef.shape=shape;
			fixtureDef.density=1f;
			fixtureDef.restitution=0.8f;
			fixtureDef.friction=0.2f;
			fixtureDef.userData=this;
			fixture=body.createFixture(fixtureDef);
			aabb=fixture.getAABB();
		}
	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
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
