/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class Rectangle extends PhysicalShape{
	/**
	 * 
	 * @param creator
	 * @param p
	 */
	public Rectangle(Creator creator,Point p){
		this(creator,0,null, p,Random.nextDouble()*Math.PI, 5+Random.nextInt(21), 5+Random.nextInt(21),ASBOTXConfigs.Color.getRandomShapeBorderColor());
	}
	/**
	 * 
	 * @param creator
	 * @param p
	 * @param angle
	 * @param width
	 * @param height
	 * @param color
	 */
	public Rectangle(Creator creator,ShapesController controller, Point p,double angle,double width,double height,CssColor color){
		this(creator, (int)Math.floor(((width+height)/2)),controller, p, angle, width, height, color);
		
	}
	/**
	 * 
	 * @param creator
	 * @param contentPower
	 * @param p
	 * @param angle
	 * @param width
	 * @param height
	 * @param color
	 */
	private Rectangle(Creator creator,int contentPower,ShapesController controller, Point p,double angle,double width,double height,CssColor color){
		super(creator,contentPower,controller, p, width, height, angle, color);
		if(this.isVerified()){
			PolygonShape shape=new PolygonShape();
			FixtureDef fixtureDef=new FixtureDef();
			
			shape.setAsBox(CoordinateConverter.scalerPixelsToWorld(width/2), CoordinateConverter.scalerPixelsToWorld(height/2));
			
			fixtureDef.shape=shape;
			fixtureDef.friction=0.6f;
			fixtureDef.density=1.2f;
			fixtureDef.restitution=0.6f;
			
			aabb=body.createFixture(fixtureDef).getAABB();
		}
	}
	@Override
	public int hashCode(){
		return 1;
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
		context.rotate(getAngle());
		
		context.strokeRect(-width/2, -height/2, width, height);
		
		context.restore();
	}
}
