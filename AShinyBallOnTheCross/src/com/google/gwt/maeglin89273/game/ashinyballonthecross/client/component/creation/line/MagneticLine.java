/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;

import java.util.ArrayList;
import java.util.List;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.canvas.dom.client.Context2d;

import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Dynamic;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;

import static com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.SensorArea.Checker;
import static com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations.Color;
/**
 * @author Maeglin Liao
 *
 */
public class MagneticLine extends ContactStaticLine {
	private final int MAGNETIC_FIELD_BOUNDS=50;
	private final float FORCE_MAGNITUDE=20f;
	
	private List<Dynamic> creationsInField=new ArrayList<Dynamic>();
	private  Fixture fixtures[];
	
	private Vec2 vecA;
	private Vec2 vecB;
	private Vec2 vecAToB;
	private Vec2 vecBToA;
	
	private Vec2[] forces;
	public MagneticLine(Creator creator, Point p1, Point p2) {
		this(creator, 0, false, p1, p2);
	}
	public MagneticLine(Creator creator, int contentPower, Point p1, Point p2) {
		this(creator, contentPower, true, p1, p2);
		
	}
	/**
	 * @param creator
	 * @param contentPower
	 * @param beControlled
	 * @param p1
	 * @param p2
	 * @param color
	 */
	private MagneticLine(Creator creator, int contentPower,boolean beControlled, Point p1, Point p2) {
		super(creator, contentPower, beControlled, p1, p2, null);
		if(isVerified()){
			
			vecA=CoordinateConverter.coordPixelToWorld(pointA);
			vecB=CoordinateConverter.coordPixelToWorld(pointB);
			vecAToB=vecB.sub(vecA);
			vecBToA=vecAToB.negate();
			forces=new Vec2[]{new Vec2(-vecAToB.y,vecAToB.x),new Vec2(-vecBToA.y,vecBToA.x)};
			for(Vec2 force:forces){
				force.normalize();
				force.mulLocal(FORCE_MAGNITUDE);//force length
			}
			
			float radius=CoordinateConverter.scalerPixelsToWorld(MAGNETIC_FIELD_BOUNDS);
			
			FixtureDef recFixD=new FixtureDef(); 
			FixtureDef cirFixDA=new FixtureDef(); 
			FixtureDef cirFixDB=new FixtureDef(); 
			
			CircleShape cirShapeA=new CircleShape();
			CircleShape cirShapeB=new CircleShape();
			PolygonShape recShape=new PolygonShape();
			
			cirShapeA.m_radius=radius;
			cirShapeB.m_radius=radius;
			cirShapeA.m_p.set(CoordinateConverter.vectorPixelToWorld(position.delta(pointA)));
			cirShapeB.m_p.set(CoordinateConverter.vectorPixelToWorld(position.delta(pointB)));
			recShape.setAsBox(CoordinateConverter.scalerPixelsToWorld(getWidth()/2),radius, new Vec2(),(float)-getAngle());
			
			recFixD.isSensor=true;
			cirFixDA.isSensor=true;
			cirFixDB.isSensor=true;
			
			recFixD.shape=recShape;
			cirFixDA.shape=cirShapeA;
			cirFixDB.shape=cirShapeB;
			
			
			fixtures=new Fixture[]{body.createFixture(recFixD),body.createFixture(cirFixDA),body.createFixture(cirFixDB)};
			fixtures[0].setUserData(new Checker(fixtures[1],fixtures[2]));
			fixtures[1].setUserData(new Checker(fixtures[0],fixtures[2]));
			fixtures[2].setUserData(new Checker(fixtures[0],fixtures[1]));
			fixture.setFriction(0.3f);
			fixture.setRestitution(0.2f);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update(){
		Vec2 shapePos;
		Vec2 pAToShape;
		Vec2 pBToShape;
		Dynamic dc;
		for(int i=creationsInField.size()-1;i>=0;i--){
			dc=creationsInField.get(i);
			if(dc.isDestroyed()){
				creationsInField.remove(i);
			}else{
				shapePos=dc.getBody().getWorldCenter();
				pAToShape=shapePos.sub(vecA);
				pBToShape=shapePos.sub(vecB);
				if(Vec2.dot(pAToShape, vecAToB)<0){
					if(pAToShape.lengthSquared()>Settings.EPSILON*Settings.EPSILON){
						pAToShape.negateLocal();
						pAToShape.normalize();
						pAToShape.mulLocal(FORCE_MAGNITUDE);
						dc.getBody().applyForce(pAToShape,shapePos);
					}
				}else if(Vec2.dot(pBToShape, vecBToA)<0){
					if(pBToShape.lengthSquared()>Settings.EPSILON*Settings.EPSILON){
						pBToShape.negateLocal();
						pBToShape.normalize();
						pBToShape.mulLocal(FORCE_MAGNITUDE);
						dc.getBody().applyForce(pBToShape,shapePos);
					}
				}else{
					dc.getBody().applyForce(pAToShape.y*vecAToB.x-pAToShape.x*vecAToB.y<0?forces[0]:forces[1],shapePos);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		
		context.setLineWidth(2);
		context.setStrokeStyle(Color.RED);
		context.beginPath();
		context.moveTo(pointA.getX(),pointA.getY());
		context.lineTo(getX(),getY());
		context.stroke();
		
		context.setStrokeStyle(Color.BLUE);
		context.beginPath();
		context.moveTo(getX(),getY());
		context.lineTo(pointB.getX(), pointB.getY());
		context.stroke();
		
	}
	
	@Override
	public void destroy(){
		super.destroy();
		
		for(int i=0;i<fixtures.length;i++){
			fixtures[i].setUserData(null);
		}
		fixtures=null;
		creationsInField.clear();
		creationsInField=null;
	}
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#beginContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		Fixture fixA=contact.getFixtureA();
		Fixture fixB=contact.getFixtureB();
		
		for(Fixture fix:fixtures){
			if(fixA.equals(fix)&&(fixB.getUserData() instanceof Dynamic)
					&&!creationsInField.contains(fixB.getUserData())){
				creationsInField.add((Dynamic)fixB.getUserData());
				return;
			}else if(fixB.equals(fix)&&(fixA.getUserData() instanceof Dynamic)
					&&!creationsInField.contains(fixA.getUserData())){
				creationsInField.add((Dynamic)fixA.getUserData());
				return;
			}
		}
		

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#endContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		Fixture fixA=contact.getFixtureA();
		Fixture fixB=contact.getFixtureB();
		for(Fixture fix:fixtures){
			if(fixA.equals(fix)&&(fixB.getUserData() instanceof Dynamic)&&
					((Checker)fix.getUserData()).checkPointIsOut(fixB.getBody().getWorldCenter())){
				
				creationsInField.remove((Dynamic)fixB.getUserData());
				return;
			}else if(fixB.equals(fix)&&(fixA.getUserData() instanceof Dynamic)&&
					((Checker)fix.getUserData()).checkPointIsOut(fixA.getBody().getWorldCenter())){
				
				creationsInField.remove((Dynamic)fixA.getUserData());
				return;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#preSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.collision.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#postSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.callbacks.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}
	
	
	public static class MagneticLineDefiner extends StaticLineDefiner{

		public MagneticLineDefiner(Creator creator) {
				
			super(creator, ASBOTCConfigurations.CreationPowerComsumption.MAGNETIC_LINE,
					new Point(3*ICON_BOUNDS_PLUS_SPACING,ICON_BOUNDS_PLUS_SPACING), null);
			
		}
		@Override
		public void sketch(Context2d context){
			if(pointA!=null&&pointB!=null){
				Point center=Point.getCenter(pointA, pointB);
				context.setLineWidth(2);
				context.setStrokeStyle(Color.RED);
				context.beginPath();
				context.moveTo(pointA.getX(),pointA.getY());
				context.lineTo(center.getX(),center.getY());
				context.stroke();
				
				context.setStrokeStyle(Color.BLUE);
				context.beginPath();
				context.moveTo(center.getX(),center.getY());
				context.lineTo(pointB.getX(), pointB.getY());
				context.stroke();
			}
		}
		@Override
		protected MainCreation create(int requiredPower) {
			
			return new MagneticLine(creator,requiredPower,pointA,pointB);
		}
		
	}
}