/**
 * 
 */
package com.google.gwt.maeglin89273.shared.test.volcanogame.component;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.utility.CoordinateConverter;

/**
 * @author Maeglin Liao
 *
 */
public class Volcano extends GeneralComponent implements Physical {
	public static final int CRATER_WIDTH=70;
	private final PixelAABB aabb;
	private Body body;
	private Spacial space;
	private SpriteSheet image;
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Physical#getBody()
	 */
	public Volcano(Spacial space,Point p){
		super(p,500,150);
		
		this.space=space;
		this.image=MEngine.getAssetManager().getSpriteSheet("volcano.png");
		
		BodyDef bodyDef=new BodyDef();
		bodyDef.position.set(CoordinateConverter.coordPixelsToWorld(p));
		PolygonShape shape=new PolygonShape();
		Vec2[] vertices={
				CoordinateConverter.vectorPixelsToWorld(new Vector(-250,75)),
				CoordinateConverter.vectorPixelsToWorld(new Vector(250,75)),
				CoordinateConverter.vectorPixelsToWorld(new Vector(CRATER_WIDTH/2,-75)),
				CoordinateConverter.vectorPixelsToWorld(new Vector(-CRATER_WIDTH/2,-75)),
				
				
		};
		
		
		body=space.getWorld().createBody(bodyDef);
		
		shape.set(vertices, vertices.length);
		
		aabb=CoordinateConverter.transformAABB(body.createFixture(shape, 0f).getAABB());
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
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	public Point getLeftCraterPoint(){
		return new Point(position.getX()-CRATER_WIDTH/2,getTopY());
	}
	public Point getRightCraterPoint(){
		return new Point(position.getX()+CRATER_WIDTH/2,getTopY());
	}
	
	@Override
	public void draw(Context2d context) {
		Point p=getPositionAt(PositionType.NORTHWEST);
		
		context.save();
		/*context.setShadowOffsetY(1);
		context.setShadowOffsetX(4);
		context.setShadowBlur(25);
		context.setShadowColor("hsla(22.5,50%,5%,0.3)");
		context.setShadowColor("rgba(0,0,0,0.2)");*/
		context.drawImage(image.getImage(),p.getX(),p.getY()-17, width, height+17);
		context.restore();
		
	}
	@Override
	public Spacial getSpace() {
		
		return space;
	}
	@Override
	public void destory() {
		space.remove(this);
		body=null;
		
	}
	@Override
	public PixelAABB getAABB() {
		
		return aabb;
	}

}
