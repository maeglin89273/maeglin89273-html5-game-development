/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.WorldContactListener;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public class Cross extends Creation implements WorldContactListener{
	private static final double SQRT_2=Math.sqrt(2);
	
	private GameOverCallback callback;
	
	private ShinyBall ball;
	Point[] points;
	private Body body;
	private Fixture[] fixtures=new Fixture[2];
	private boolean[] contacts=new boolean[]{false,false};
	private CssColor color=ASBOTXConfigs.Color.DARK_GRAY;
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
		this.creator.getWorld().addContactListener(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		
		if(ball!=null&&!ball.getBody().isAwake()){
			callback.showScore(this.creator.getScore());
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
	private int indexOf(Fixture fix){
		
		for(int i=0;i<fixtures.length;i++){
			if(fix.equals(fixtures[i])){
				return i;
			}
		}
		return -1;
	}
	@Override
	public void beginContact(Contact contact,Fixture thisFixture,Fixture thatFixture) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void endContact(Contact contact,Fixture thisFixture,Fixture thatFixture) {
		if(thatFixture.getBody().getUserData() instanceof ShinyBall){
			contacts[indexOf(thisFixture)]=false;
			if(!(contacts[0]||contacts[1]))
				color=ASBOTXConfigs.Color.DARK_GRAY;
					
			ball=null;
		}		
			
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold,Fixture thisFixture,Fixture thatFixture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse,Fixture thisFixture,Fixture thatFixture) {
		
		if(thatFixture.getBody().getUserData() instanceof ShinyBall){
				contacts[indexOf(thisFixture)]=true;
				if(contacts[0]&&contacts[1])
					ball=(ShinyBall)thatFixture.getBody().getUserData();
				
				this.color=ASBOTXConfigs.Color.LIGHT_BLUE;
				return;
		}
	}

	
}
