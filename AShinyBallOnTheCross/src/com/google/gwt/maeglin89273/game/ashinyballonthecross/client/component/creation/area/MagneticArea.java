/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area;



import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Creation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.timer.SchedulingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;
import com.google.gwt.user.client.Window;

/**
 * @author Maeglin Liao
 *
 */
public class MagneticArea extends SensorArea {
	private final double radius;
	private static final float K=80f;
	private Vec2 center;
	
	/**
	 * 
	 * @param creator
	 * @param p
	 * @param radius
	 */
	public MagneticArea(Creator creator,Point p, double radius) {
		this(creator, 0,false, p, radius);
	}
	private MagneticArea(Creator creator, int contentPower ,Point p, double radius) {
		this(creator, contentPower, true, p, radius);
	}
	/**
	 * @param creator
	 * @param contentPower TODO
	 * @param p
	 * @param w
	 * @param h
	 */
	private MagneticArea(Creator creator, int contentPower,boolean beControlled ,Point p, double radius) {
		super(creator,contentPower,beControlled ,p, 2*radius, 2*radius, 0);
		if(isVerified()){
			body.setType(BodyType.STATIC);
			
			FixtureDef fixtureDef=new FixtureDef();
			CircleShape shape=new CircleShape();
			
			this.radius = radius;
			this.center=body.getPosition();
			
			shape.m_radius=CoordinateConverter.scalerPixelsToWorld(radius);
			fixtureDef.isSensor=true;
			fixtureDef.shape=shape;
			fixtureDef.userData=this;
			fixtures=new Fixture[]{body.createFixture(fixtureDef)};
			aabb=fixtures[0].getAABB();
			
		}else{
			this.radius=0;
		}
	}
	@Override
	public void destroy(){
		
		center=null;
		super.destroy();
	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		Vec2 v;
		for(PhysicalShape ps:contentShapes){
			v=center.sub(ps.getBody().getPosition());
			float lengthS=v.lengthSquared();
			if(lengthS>=Settings.EPSILON*Settings.EPSILON){
				v.normalize();
				v.mulLocal(K*ps.getBody().getMass());
				ps.getBody().applyForce(v, ps.getBody().getWorldCenter());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setFillStyle(GameColors.TRANSLUCENT_DARK_GRAY);
		context.setStrokeStyle(GameColors.DARK_GRAY);
		context.setLineWidth(2);
		context.beginPath();
		context.arc(getX(), getY(), radius, 2*Math.PI, 0);
		context.closePath();
		context.fill();
		context.stroke();
	}
	
	
	public static class MagneticAreaDefiner extends CircleKindAreaDefiner{

		public MagneticAreaDefiner(Creator creator) {
			super(creator,150,new Point(ICON_BOUNDS_PLUS_SPACING,0),85,30);
		}

		@Override
		public void sketch(Context2d context) {
			if(center!=null){
				context.setFillStyle(GameColors.TRANSLUCENT_DARK_GRAY);
				context.setStrokeStyle(GameColors.DARK_GRAY);
				context.setLineWidth(2);
				context.beginPath();
				context.arc(center.getX(),center.getY(), radius, 2*Math.PI, 0);
				context.closePath();
				context.fill();
				context.stroke();
			}
		}
		
		@Override
		protected MainCreation create(int requiredPower) {
			return new MagneticArea(creator,requiredPower,center, radius);
		}

	}
}
