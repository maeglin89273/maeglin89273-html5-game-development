/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.StaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public class Cross extends Creation implements Physical,ContactListener{
	private static final double SQRT_2=Math.sqrt(2);
	
	private GameOverCallback callback;
	
	private ShinyBall ball;
	Point[] points;
	private Body body;
	private Fixture[] fixtures=new Fixture[2];
	private boolean[] contacts=new boolean[]{false,false};
	private CssColor color=ASBOTCConfigurations.Color.DARK_GRAY;
	/**
	 * @param creator
	 * @param cotentPower
	 * @param p
	 * @param w
	 * @param h
	 * @param gravityInDegrees
	 */
	public Cross(Creator creator, Point p,int gravityInDegrees,GameOverCallback callback) {
		super(creator,0, p, 15*(1+SQRT_2), 15*(1+SQRT_2/2),Math.toRadians(gravityInDegrees-90));
		this.creator.getWorld().addContactListener(this);
		this.callback=callback;
		
		Vec2 crossV=CoordinateConverter.vectorPixelToWorld(position.delta(getX(),getTopY()+getWidth()/2));
		Point nwP=this.getPositionAt(PositionType.NORTHWEST);
		Point neP=this.getPositionAt(PositionType.NORTHEAST);
		Point seP=nwP.clone();
		Point swP=neP.clone();
		seP.translate(getHeight(), getHeight());
		swP.translate(-getHeight(), getHeight());
		this.points=Point.getRotatedPoint(getPosition(),getAngle(),nwP,neP,seP,swP);
		
		BodyDef bodyD=new BodyDef();
		PolygonShape[] edges=new PolygonShape[]{new PolygonShape(),new PolygonShape(),new PolygonShape(),new PolygonShape()};
		
		bodyD.type=BodyType.STATIC;
		bodyD.angle=(float)-getAngle();
		bodyD.position.set(CoordinateConverter.coordPixelToWorld(getPosition()));
		this.body=creator.getWorld().getWorld().createBody(bodyD);
		
		edges[0].setAsEdge(CoordinateConverter.vectorPixelToWorld(position.delta(nwP)),crossV);
		edges[1].setAsEdge(CoordinateConverter.vectorPixelToWorld(position.delta(neP)),crossV);
		edges[2].setAsEdge(crossV, CoordinateConverter.vectorPixelToWorld(position.delta(swP)));
		edges[3].setAsEdge(crossV, CoordinateConverter.vectorPixelToWorld(position.delta(seP)));
		for(int i=0;i<fixtures.length;i++){
			fixtures[i]=body.createFixture(edges[i], 0);
		}
		body.createFixture(edges[2], 0);
		body.createFixture(edges[3], 0);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		
		if(ball!=null&&!ball.getBody().isAwake()){
			callback.showScore(this.creator);
			this.destroy();
		}
			
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setLineWidth(2);
		context.setStrokeStyle(color);
		context.beginPath();
		context.moveTo(points[0].getX(), points[0].getY());
		context.lineTo(points[2].getX(), points[2].getY());
		context.moveTo(points[1].getX(), points[1].getY());
		context.lineTo(points[3].getX(), points[3].getY());
		context.stroke();
	}
	@Override
	public void destroy(){
		creator.getWorld().removeContactListener(this);
		super.destroy();
		fixtures=null;
		body=null;
		callback=null;
	}
	
	@Override
	public Body getBody() {
		// TODO Auto-generated method stub
		return body;
	}

	@Override
	public Spacial getSpace() {
		
		return creator.getWorld();
	}

	@Override
	public AABB getAABB() {
		
		return null;
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixA=contact.getFixtureA();
		Fixture fixB=contact.getFixtureB();
		for(int i=0;i<fixtures.length;i++){
			if(fixA.equals(fixtures[i])&&(fixB.getUserData() instanceof ShinyBall)||
				fixB.equals(fixtures[i])&&(fixA.getUserData() instanceof ShinyBall)){
				contacts[i]=false;
				if(!(contacts[0]||contacts[1]))
					color=ASBOTCConfigurations.Color.DARK_GRAY;
				
				ball=null;
				return;
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Fixture fixA=contact.getFixtureA();
		Fixture fixB=contact.getFixtureB();
		
		for(int i=0;i<fixtures.length;i++){
			
			if(fixA.equals(fixtures[i])&&(fixB.getUserData() instanceof ShinyBall)){
				contacts[i]=true;
				if(contacts[0]&&contacts[1])
					ball=(ShinyBall)fixB.getUserData();
				
				
				this.color=ASBOTCConfigurations.Color.LIGHT_BLUE;
				return;
			}else if(fixB.equals(fixtures[i])&&(fixA.getUserData() instanceof ShinyBall)){
				contacts[i]=true;
				if(contacts[0]&&contacts[1])
					ball=(ShinyBall)fixB.getUserData();
				
				this.color=ASBOTCConfigurations.Color.LIGHT_BLUE;
				return;
			}
		}
	}
}
