/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation;

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
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.WorldContactListener;
import com.google.gwt.maeglin89273.game.mengine.component.Spatial;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class Goblet extends Creation implements WorldContactListener {
	private static final double SQRT_2=Math.sqrt(2);
	
	protected final CssColor defaultColor;
	protected final CssColor contactColor;
	private CssColor color;
	private Point[] points;
	protected ShinyBall ball;
	
	private boolean[] contacts=new boolean[]{false,false};
	private Body body;
	private Fixture[] fixtures=new Fixture[2];
	private boolean destroyed=false;
	/**
	 * @param creator
	 * @param cotentPower
	 * @param p
	 * @param w
	 * @param h
	 * @param angle
	 */
	public Goblet(Point p,double angle,CssColor defaultColor,CssColor contactColor) {
		super(0, p, 15*(1+SQRT_2), 15*(1+SQRT_2/2), angle);
		
		this.defaultColor = this.color = defaultColor;
		this.contactColor = contactColor;
		
		Point tlP=this.getPositionAt(PositionType.NORTHWEST);
		Point trP=this.getPositionAt(PositionType.NORTHEAST);
		Point crossP=new Point(getX(),getTopY()+getWidth()/2);
		Point bmP=this.getPositionAt(PositionType.SOUTH);
		Point blP=bmP.clone();
		Point brP=bmP.clone();
		blP.translate(-7.5f, 0);
		brP.translate(7.5f, 0);
		points=Point.getRotatedPoint(getPosition(), getAngle(),tlP,trP,crossP,bmP,blP,brP);
		
		Vec2 crossV=CoordinateConverter.vectorPixelToWorld(position.delta(crossP));
		
		BodyDef bodyD=new BodyDef();
		PolygonShape[] edges=new PolygonShape[]{new PolygonShape(),new PolygonShape(),new PolygonShape(),new PolygonShape()};
		
		bodyD.type=BodyType.STATIC;
		bodyD.angle=(float)-getAngle();
		bodyD.position.set(CoordinateConverter.coordPixelToWorld(getPosition()));
		this.body=creator.getWorld().getWorld().createBody(bodyD);
		
		edges[0].setAsEdge(CoordinateConverter.vectorPixelToWorld(position.delta(tlP)),crossV);
		edges[1].setAsEdge(CoordinateConverter.vectorPixelToWorld(position.delta(trP)),crossV);
		edges[2].setAsEdge(crossV, CoordinateConverter.vectorPixelToWorld(position.delta(bmP)));
		edges[3].setAsEdge(CoordinateConverter.vectorPixelToWorld(position.delta(blP)),
						   CoordinateConverter.vectorPixelToWorld(position.delta(brP)));
		
		for(int i=0;i<fixtures.length;i++){
			fixtures[i]=body.createFixture(edges[i], 0);
		}
		body.createFixture(edges[2], 0);
		body.createFixture(edges[3], 0);
		creator.getWorld().addContactListener(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getBody()
	 */
	@Override
	public Body getBody() {
		return body;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getSpace()
	 */
	@Override
	public Spatial getSpace() {
		return creator.getWorld();
	}
	@Override
	public void destroy(){
		creator.getWorld().removeContactListener(this);
		super.destroy();
		fixtures=null;
		body=null;
		
		destroyed=true;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#isDestroyed()
	 */
	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.Physical#getAABB()
	 */
	@Override
	public AABB getAABB() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.WorldContactListener#beginContact(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.dynamics.Fixture, org.jbox2d.dynamics.Fixture)
	 */
	@Override
	public void beginContact(Contact contact, Fixture thisFixture,
			Fixture thatFixture) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.WorldContactListener#endContact(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.dynamics.Fixture, org.jbox2d.dynamics.Fixture)
	 */
	@Override
	public void endContact(Contact contact, Fixture thisFixture,
			Fixture thatFixture) {
		if(thatFixture.getBody().getUserData() instanceof ShinyBall){
			contacts[indexOf(thisFixture)]=false;
			if(!(contacts[0]||contacts[1]))
				color=this.defaultColor;
					
			ball=null;
		}

	}
	private int indexOf(Fixture fix){
		
		for(int i=0;i<fixtures.length;i++){
			if(fix.equals(fixtures[i])){
				return i;
			}
		}
		return -1;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.WorldContactListener#preSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.collision.Manifold, org.jbox2d.dynamics.Fixture, org.jbox2d.dynamics.Fixture)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold,
			Fixture thisFixture, Fixture thatFixture) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.WorldContactListener#postSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.callbacks.ContactImpulse, org.jbox2d.dynamics.Fixture, org.jbox2d.dynamics.Fixture)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse,
			Fixture thisFixture, Fixture thatFixture) {
		if(thatFixture.getBody().getUserData() instanceof ShinyBall){
			contacts[indexOf(thisFixture)]=true;
			if(contacts[0]&&contacts[1])
				ball=(ShinyBall)thatFixture.getBody().getUserData();
			
			this.color=this.contactColor;
			return;
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		if(ball!=null&&!ball.getBody().isAwake()){
			doEffect();
			ball=null;
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
		moveTo(context,points[0]);
		lineTo(context,points[2]);
		
		moveTo(context,points[1]);
		lineTo(context,points[2]);
		lineTo(context,points[3]);
		
		moveTo(context,points[4]);
		lineTo(context,points[5]);
		context.stroke();
	}
	private void moveTo(Context2d context,Point p){
		context.moveTo(p.getX(), p.getY());
	}
	private void lineTo(Context2d context,Point p){
		context.lineTo(p.getX(), p.getY());
	}
	protected abstract void doEffect();
}
