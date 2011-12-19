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
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Dynamic;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class GravitationalArea extends SensorArea {
	private final double radius;
	private Vec2 center;
	
	/**
	 * 
	 * @param creator
	 * @param p
	 * @param radius
	 */
	public GravitationalArea(Creator creator,Point p, double radius) {
		this(creator, 0,false, p, radius);
	}
	private GravitationalArea(Creator creator, int contentPower ,Point p, double radius) {
		this(creator, contentPower, true, p, radius);
	}
	/**
	 * @param creator
	 * @param contentPower TODO
	 * @param p
	 * @param w
	 * @param h
	 */
	private GravitationalArea(Creator creator, int contentPower,boolean beControlled ,Point p, double radius) {
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
		Dynamic dc;
		for(int i=contentCreations.size()-1;i>=0;i--){
			dc=contentCreations.get(i);
			if(dc.isDestroyed()){
				contentCreations.remove(i);
			}else{
				v=center.sub(dc.getBody().getPosition());
				float lengthS=v.lengthSquared();
				if(lengthS>Settings.EPSILON*Settings.EPSILON){
					v.normalize();
					v.mulLocal((float)radius*dc.getBody().getMass());
					dc.getBody().applyForce(v, dc.getBody().getWorldCenter());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setFillStyle(ASBOTCConfigurations.Color.TRANSLUCENT_DARK_GRAY);
		context.setStrokeStyle(ASBOTCConfigurations.Color.DARK_GRAY);
		context.setLineWidth(2);
		context.beginPath();
		context.arc(getX(), getY(), radius, 2*Math.PI, 0);
		context.closePath();
		context.fill();
		context.stroke();
	}
	
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#endContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		Fixture fixA=contact.getFixtureA();
		Fixture fixB=contact.getFixtureB();
		for(Fixture fix:fixtures){
			if(fixA.equals(fix)&&(fixB.getUserData() instanceof Dynamic)){
				contentCreations.remove((Dynamic)fixB.getUserData());
				return;
			}else if(fixB.equals(fix)&&(fixA.getUserData() instanceof Dynamic)){
				contentCreations.remove((Dynamic)fixA.getUserData());
				return;
			}
		}
	}
	public static class GravitationalAreaDefiner extends CircleKindAreaDefiner{

		public GravitationalAreaDefiner(Creator creator) {
			super(creator,ASBOTCConfigurations.CreationPowerComsumption.GRAVITATIONAL_AREA,new Point(ICON_BOUNDS_PLUS_SPACING,0),85,20);
		}

		@Override
		public void sketch(Context2d context) {
			if(center!=null){
				context.setFillStyle(ASBOTCConfigurations.Color.TRANSLUCENT_DARK_GRAY);
				context.setStrokeStyle(ASBOTCConfigurations.Color.DARK_GRAY);
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
			return new GravitationalArea(creator,requiredPower,center, radius);
		}

	}
}
