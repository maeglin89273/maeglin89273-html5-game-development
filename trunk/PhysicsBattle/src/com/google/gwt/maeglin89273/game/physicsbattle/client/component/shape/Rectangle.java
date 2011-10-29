/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.utility.CoordinateConverter;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class Rectangle extends PhysicalPolygon{
	
	
	
	public Rectangle(PhysicalWorld space, Point p,double width,double height){
		this(space, p, width, height, 0, GameColors.getRandomShapeBorderColor());
	}
	public Rectangle(PhysicalWorld world, Point p,double width,double height,double angle,CssColor color){
		super(p, width, height, world, angle, color);
		
		PolygonShape shape=new PolygonShape();
		FixtureDef fixtureDef=new FixtureDef();
		
		shape.setAsBox(CoordinateConverter.scalerPixelsToWorld(width/2), CoordinateConverter.scalerPixelsToWorld(height/2));
		
		fixtureDef.shape=shape;
		fixtureDef.friction=0.45f;
		fixtureDef.density=1f;
		fixtureDef.restitution=0.6f;
		fixture=body.createFixture(fixtureDef);
		aabb=CoordinateConverter.transformAABB(fixture.getAABB());
	}
	public Rectangle(PhysicalWorld space,Point p){
		this(space, p, 5+Random.nextInt(21), 5+Random.nextInt(21));
	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		
		context.save();
		context.setStrokeStyle(borderColor);
		
		context.setLineWidth(1.25);
		context.translate(getX(), getY());
		context.rotate(-body.getAngle());
		
		context.strokeRect(-width/2, -height/2, width, height);
		
		context.restore();
	}
}
