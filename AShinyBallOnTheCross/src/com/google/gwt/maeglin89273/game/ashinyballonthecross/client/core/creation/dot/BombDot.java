/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.dot;

import org.jbox2d.common.Vec2;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.ShapesController;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;

/**
 * @author Maeglin Liao
 *
 */
public abstract class BombDot extends Dot {
	
	protected int lastShapesCount=15;
	protected static final double PORTION_ANGLE=2*Math.PI/15;
	protected final float impulseMag;
	protected final float spoutDistance;
	protected ShapesController controller;
	protected BombDot(Point p,CssColor color,float impulseMag, float spoutDistance){
		super(0,false,p, color);
		this.impulseMag=impulseMag;
		this.spoutDistance=spoutDistance;
		this.controller=new ShapesController();
	}
	@Override
	public void update() {
		if(lastShapesCount>0){
			double angle=lastShapesCount*PORTION_ANGLE;
			Point pos=this.position.clone();
			pos.translate(new Vector(spoutDistance,angle,true));
			Vec2 impulse=new Vec2((float)(this.impulseMag*Math.cos(angle)),(float)(this.impulseMag*Math.sin(angle)));
			
			PhysicalShape shape=generateShape(pos,angle);
			if(shape.isVerified()){
				shape.getBody().applyLinearImpulse(impulse,shape.getBody().getWorldCenter());
				lastShapesCount--;
			}else{
				if(lastShapesCount==15){
					controller.destroy();
				}
				lastShapesCount=0;
			}
		}else{
			destroy();
		}
	}
	@Override
	public void destroy(){
		super.destroy();
		this.controller=null;
	}
	protected abstract PhysicalShape generateShape(Point position, double angle);
	
	
	public static abstract class BombDotDefiner extends DotDefiner{

		protected BombDotDefiner(Point iconCorner, CssColor dotColor) {
			super(0, iconCorner, dotColor);
		}
		
	}
}
