/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.core.AssetManager;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.utility.CoordinateConverter;

/**
 * @author Liao
 *
 */
public class Star extends GeneralComponent implements Physical {
	private static final float STAR_RADIUS=7.5f;
	private float rotation;
	
	private final PixelAABB aabb;
	
	private Body body;
	private PhysicalWorld world;
	
	private SpriteBlock spriteBlock=new SpriteBlock(150,138,500,500);
	private SpriteSheet spriteSheet;
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Physical#getBody()
	 */
	public Star(PhysicalWorld world,Point p){
		super(p,2*STAR_RADIUS,2*STAR_RADIUS);
		
		this.world=world;
		
		
		aabb=new PixelAABB(this.position,width,height);
		
		this.spriteSheet=MEngine.getAssetManager().getSpriteSheet("star.png");
		BodyDef bodyDef=new BodyDef();
		CircleShape shape=new CircleShape();
		FixtureDef fixtureDef=new FixtureDef();
		
		bodyDef.type=BodyType.DYNAMIC;
		bodyDef.position.set(CoordinateConverter.coordPixelsToWorld(position));
		
		
		body=world.getWorld().createBody(bodyDef);
		//body.applyLinearImpulse(CoordinateConverter.vectorPixelsToWorld(new Vector(-100+Random.nextDouble()*200,-100+Random.nextDouble()*200)),body.getWorldCenter());
		
		shape.m_radius=CoordinateConverter.scalerPixelsToWorld(STAR_RADIUS);
		fixtureDef.shape=shape;
		fixtureDef.density=1f;
		fixtureDef.restitution=0.6f;
		fixtureDef.friction=0.6f;
		
		body.createFixture(fixtureDef);
	}
	@Override
	public Body getBody() {
		
		return body;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Physical#getSpace()
	 */
	@Override
	public Spacial getSpace() {
		
		return world;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Physical#destory()
	 */
	@Override
	public void destory() {
		world.remove(this);
		body=null;
		world=null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		position.setPosition(CoordinateConverter.coordWorldToPixels(body.getPosition()));
		aabb.setPosition(position);
		rotation=body.getAngle();
		if(world.isOutOfBounds(aabb)){
			destory();
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		double w=spriteBlock.getWidth()*0.2;
		double h=spriteBlock.getHeight()*0.2;
		context.save();
		context.translate(getX(), getY());
		context.rotate(-rotation);
		context.drawImage(spriteSheet.getImage(), spriteBlock.getX(), spriteBlock.getY(), spriteBlock.getWidth(), spriteBlock.getHeight(),
						-w/2,-h/2,w,h);
		context.restore();
	}
	@Override
	public PixelAABB getAABB() {
		// TODO Auto-generated method stub
		return aabb;
	}

}
