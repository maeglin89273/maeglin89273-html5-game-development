/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.dot;

import org.jbox2d.common.Vec2;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;

/**
 * @author Maeglin Liao
 *
 */
public abstract class BombDot extends Dot {
	
	protected double portionAngle;
	protected int lastShapesCount;
	protected float impulseMag;
	protected float spoutDistance;
	protected BombDot(Point p,PhysicalWorld world,CssColor color,int count,float impulseMag,float spoutDistance){
		super(p, world,color);
		this.impulseMag=impulseMag;
		this.spoutDistance=spoutDistance;
		this.lastShapesCount=count;
		this.portionAngle=2*Math.PI/count;
		
	}
	@Override
	public void update() {
		if(lastShapesCount>0){
			double angle=-lastShapesCount*portionAngle;
			Point pos=this.position.clone();
			pos.translate(new Vector(spoutDistance,angle,true));
			Vec2 impulse=new Vec2((float)(this.impulseMag*Math.cos(angle)),(float)(this.impulseMag*Math.sin(angle)));
			
			PhysicalShape shape=generateShape(pos,angle);
			shape.applyImpulse(impulse,shape.getBody().getWorldCenter());
			lastShapesCount--;
		}else{
			destory();
		}
	}
	protected abstract PhysicalShape generateShape(Point position, double angle);
	
}
