/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedListener;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
/**
 * @author Liao
 *
 */
public class ShinyBall extends PhysicalShape implements CreatorPropertiesChangedListener{
	public static final float CORE_RADIUS=7.5f;
	
	
	private final SpriteBlock spriteBlock=new SpriteBlock(SHEET_OFFSET,138,500,500,MEngine.getAssetManager().getSpriteSheet("star.png"));
	private final float radiance=spriteBlock.getWidth()/5f;
	private static final int SHEET_OFFSET=150;
	
	private final int portion;
	private final Point initPos=new Point(0,0);
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Physical#getBody()
	 */
	public ShinyBall(Creator creator,Point p){
		super(creator,0,p,2*CORE_RADIUS,2*CORE_RADIUS,0, GameColors.STAR_BOUND_COLOR);
		
		if(this.isVerified()){
			initPos.setPosition(p);
			portion=creator.getMaxPower()/4;
			creator.addPropertiesChangeListener(this);
			
			CircleShape shape=new CircleShape();
			FixtureDef fixtureDef=new FixtureDef();
			
			shape.m_radius=CoordinateConverter.scalerPixelsToWorld(CORE_RADIUS);
			fixtureDef.shape=shape;
			fixtureDef.density=1f;
			fixtureDef.restitution=0.6f;
			fixtureDef.friction=0.8f;
			fixtureDef.userData=this;
			fixture=body.createFixture(fixtureDef);
			aabb=fixture.getAABB();
		}else{
			portion=0;
		}
	}
	
	
	@Override
	public void draw(Context2d context) {
		
		context.save();
		context.translate(getX(), getY());
		context.rotate(getAngle());
		context.drawImage(spriteBlock.getSheetImage(), spriteBlock.getX(), spriteBlock.getY(), spriteBlock.getWidth(), spriteBlock.getHeight(),
						-radiance/2,-radiance/2,radiance,radiance);
		context.restore();
		
	}
	@Override
	public void destroy(){
		new ShinyBall(creator,initPos);
		super.destroy();
	}

	@Override
	public void powerChanged(CreatorPropertiesChangedEvent event) {
		int r=event.getPower()/portion;
		if(r>3)r=3;
		spriteBlock.setX(SHEET_OFFSET+r*(spriteBlock.getWidth()+SpriteBlock.SPACING));
	}

	@Override
	public void scoreChanged(CreatorPropertiesChangedEvent event) {
		
	}

}
