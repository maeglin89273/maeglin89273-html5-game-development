/**
 * 
 */
package com.google.gwt.maeglin89273.shared.test.volcanogame.component;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;
import com.google.gwt.maeglin89273.game.mengine.utility.CoordinateConverter;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class FireBall extends GeneralComponent implements Physical {

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Physical#getBody()
	 */
	private int radius;
	private final PixelAABB aabb;
	private Body body;
	private CssColor ballColor;
	private CssColor ballShadowColor;
	private Spacial space;
	public FireBall(Spacial space,Point p,int radius){
		super(new Point(p.getX(),p.getY()-radius),radius*2,radius*2);
		
		this.radius=radius;
		double h=5+Random.nextInt(21);
		double s=80+Random.nextInt(21);
		double l=35+Random.nextInt(16);
		ballColor=CssColor.make("hsl("+h+","+s+"%,"+l+"%)");
		ballShadowColor=CssColor.make("hsl("+h+","+s+"%,"+(l+15)+"%)");
		this.space=space;
		
		
		aabb=new PixelAABB(this.position,width,height);
		
		BodyDef bodyDef=new BodyDef();
		CircleShape shape=new CircleShape();
		FixtureDef fixtureDef=new FixtureDef();
		Vec2 impulse=CoordinateConverter.vectorPixelsToWorld(new Vector(-30+60*Random.nextDouble(),-(175+50*Random.nextDouble())));
		
		bodyDef.type=BodyType.DYNAMIC;
		bodyDef.position.set(CoordinateConverter.coordPixelsToWorld(position));
		
		
		body=space.getWorld().createBody(bodyDef);
		body.setLinearVelocity(impulse);
		body.applyLinearImpulse(impulse, body.getPosition());
		shape.m_radius=CoordinateConverter.scalerPixelsToWorld(radius);
		fixtureDef.shape=shape;
		fixtureDef.density=4.3f;
		fixtureDef.restitution=0.2f;
		fixtureDef.friction=0.8f;
		body.createFixture(fixtureDef);
		
	}
	@Override
	public Body getBody() {
		
		return body;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		position.setPosition(CoordinateConverter.coordWorldToPixels(body.getPosition()));
		aabb.setPosition(position);
		if(space.isOutOfBounds(aabb)){
			destory();
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		
		context.setFillStyle(ballColor);
		
		//context.setShadowColor(ballShadowColor.toString());
		context.beginPath();
		context.arc(position.getX(), position.getY(), radius, 0, 2*Math.PI);
		context.closePath();
		context.fill();
		
	}
	@Override
	public Spacial getSpace() {
		
		return space;
	}
	@Override
	public void destory() {
		space.remove(this);
		body=null;
		space=null;
	}
	@Override
	public PixelAABB getAABB() {
		// TODO Auto-generated method stub
		return aabb;
	}

}
