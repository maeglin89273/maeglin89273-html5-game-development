/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area;


import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Dynamic;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;



/**
 * @author Maeglin Liao
 *
 */
public class ArrowArea extends SensorArea{
	private static final float FORCE_MAGNITUDE_FACTOR=2f;
	private final Vec2 forceVec;
	private final double radius;
	private Checker checker;
	private final SpriteBlock block=new SpriteBlock(0,0,250,250,MEngine.getAssetManager().getSpriteSheet("images/areas.png"));
	
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
			
			Fixture[] fixtures=new Fixture[]{body.createFixture(triFixD),body.createFixture(recFixD)};
			
			this.checker=new Checker(fixtures);
			
			body.setTransform(body.getPosition(),(float)-angle);
			aabb=new AABB();
			aabb.combine(fixtures[0].getAABB(), fixtures[1].getAABB());
			forceVec=new Vec2((float)(radius*FORCE_MAGNITUDE_FACTOR*Math.cos(-angle)),(float)(radius*FORCE_MAGNITUDE_FACTOR*Math.sin(-angle)));
			
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
		
		Dynamic dc;
		for(int i=contentCreations.size()-1;i>=0;i--){
			dc=contentCreations.get(i);
			if(dc.isDestroyed()){
				contentCreations.remove(i);
			}else{
				dc.getBody().applyForce(forceVec, dc.getBody().getWorldCenter());
			}
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
	
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#endContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void endContact(Contact contact, Fixture thisFixture, Fixture thatFixture) {
		if(thatFixture.getBody().getUserData() instanceof Dynamic&&
				checker.checkPointIsOut(thisFixture,thatFixture.getBody().getWorldCenter())){
			contentCreations.remove((Dynamic)thatFixture.getBody().getUserData());
				
		}
		
	}
	
	
	public static class ArrowAreaDefiner extends CircleKindAreaDefiner{
		private final SpriteBlock block=new SpriteBlock(0,0,250,250,MEngine.getAssetManager().getSpriteSheet("images/areas.png"));
		public ArrowAreaDefiner(Creator creator) {
			super(creator,ASBOTXConfigs.CreationPowerComsumption.ARROW_AREA,new Point(0,0),60,20);
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
