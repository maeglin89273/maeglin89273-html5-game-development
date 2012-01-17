/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot;

import org.jbox2d.common.Vec2;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
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
	protected BombDot(Creator creator,Point p,CssColor color,float impulseMag, float spoutDistance){
		super(creator,0,false,p, color);
		this.impulseMag=impulseMag;
		this.spoutDistance=spoutDistance;
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
				lastShapesCount=0;
			}
		}else{
			destroy();
		}
	}
	protected abstract PhysicalShape generateShape(Point position, double angle);
	
	
	public static abstract class BombDotDefiner extends DotDefiner{

		protected BombDotDefiner(Creator creator,Point iconCorner, CssColor dotColor) {
			super(creator,0, iconCorner, dotColor);
		}
		
	}
}
