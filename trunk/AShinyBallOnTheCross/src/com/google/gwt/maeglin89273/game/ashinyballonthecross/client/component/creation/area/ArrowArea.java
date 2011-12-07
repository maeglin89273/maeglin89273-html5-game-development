/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area;


import org.jbox2d.collision.shapes.PolygonShape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Creation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.timer.SchedulingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;
import com.google.gwt.user.client.Window;


/**
 * @author Maeglin Liao
 *
 */
public class ArrowArea extends SensorArea{
	private static final float FORCE_MAGNITUDE=100f;
	private final Vec2 forceVec;
	private final double radius;
	private final SpriteBlock block=new SpriteBlock(0,0,250,250,MEngine.getAssetManager().getSpriteSheet("creations.png"));
	
	/**
	 * 
	 * @param creator
	 * @param p
	 * @param angle
	 * @param radius
	 */
	public ArrowArea(Creator creator,Point p, double angle, double radius) {
		this(creator, 0,false, p, angle, radius);
	}
	private ArrowArea(Creator creator,int contentPower, Point p, double angle, double radius) {
		this(creator, contentPower, true, p, angle, radius);
	}
	/**
	 * @param creator
	 * @param contentPower TODO
	 * @param p
	 * @param w
	 * @param h
	 */
	private ArrowArea(Creator creator,int contentPower,boolean beControlled, Point p, double angle, double radius) {
		super( creator, contentPower, beControlled, p, radius+radius*4/5, 2*radius*Math.cos(Math.PI/12), angle);
		if(isVerified()){
			this.radius = radius;
			body.setType(BodyType.STATIC);
			
			double rSin15=radius*Math.sin(Math.PI/12);
			double rCos15=radius*Math.cos(Math.PI/12);
			double hw=(radius*0.8+rSin15)/2;
			
			FixtureDef triFixD=new FixtureDef();
			FixtureDef recFixD=new FixtureDef();
			PolygonShape triShape=new PolygonShape();
			PolygonShape recShape=new PolygonShape();
			Vector[] verticesP=new Vector[]{new Vector(rSin15,rCos15),new Vector(radius,0),new Vector(rSin15,rCos15)};
			Vec2[] verticesW=new Vec2[3];
			for(int i=0;i<3;i++){
				verticesW[i]=CoordinateConverter.vectorPixelToWorld(verticesP[i]);
			}
			triShape.set(verticesW, verticesW.length);
			recShape.setAsBox(CoordinateConverter.scalerPixelsToWorld(hw),
					CoordinateConverter.scalerPixelsToWorld(radius*0.6) ,
					CoordinateConverter.vectorPixelToWorld(new Vector(Math.sin(Math.PI/12)-hw,0)),0);
			
			triFixD.isSensor=true;
			recFixD.isSensor=true;
			triFixD.shape=triShape;
			recFixD.shape=recShape;
			fixtures=new Fixture[]{body.createFixture(triFixD),body.createFixture(recFixD)};
			
			body.setTransform(body.getPosition(),(float)-angle);
			
			forceVec=new Vec2((float)(FORCE_MAGNITUDE*Math.cos(-angle)),(float)(FORCE_MAGNITUDE*Math.sin(-angle)));
			
		}else{
			this.forceVec=null;
			this.radius=0;
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		for(PhysicalShape ps:contentShapes){
				ps.getBody().applyForce(forceVec, ps.getBody().getWorldCenter());
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.save();
		context.translate(getX(), getY());
		context.rotate(angle);
		context.drawImage(block.getSheetImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(),-radius,-radius, radius*2, radius*2);
		context.restore();
	}
	
	public static class ArrowAreaDefiner extends CircleKindAreaDefiner{
		private final SpriteBlock block=new SpriteBlock(0,0,250,250,MEngine.getAssetManager().getSpriteSheet("creations.png"));
		public ArrowAreaDefiner(Creator creator) {
			super(creator,125,new Point(0,0),60,30);
		}

		@Override
		public void sketch(Context2d context) {
			if(center!=null){
				context.save();
				context.translate(center.getX(), center.getY());
				context.rotate(angle);
				context.drawImage(block.getSheetImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(),-radius,-radius, radius*2, radius*2);
				context.restore();
			}
		}
		
		@Override
		protected MainCreation create(int requiredPower) {
			return new ArrowArea(creator,requiredPower,center,angle, radius);
		}

	}

}
