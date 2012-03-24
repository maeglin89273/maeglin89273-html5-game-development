package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


class FragmentalLine extends PhysicalLine{
	/**
	 * 
	 */
	
	private final double hL;
	
	public FragmentalLine(int contentPower,boolean beControlled,Point p1, Point p2, CssColor color) {
		super(contentPower,beControlled, p1,p2,color);
		
		if(isVerified()){	
			body.setType(BodyType.DYNAMIC);
			this.body.setUserData(this);
			body.setBullet(true);
			
			FixtureDef fixD=new FixtureDef();
				
			PolygonShape lineP=new PolygonShape();
			
			this.hL=getWidth()/2;
				
			lineP.setAsBox(CoordinateConverter.scalerPixelsToWorld(getWidth())/2
					,CoordinateConverter.scalerPixelsToWorld(1),new Vec2(),0);
						
			fixD.density=1.2f;
			fixD.shape=lineP;
			fixD.restitution=0.75f;
			fixD.friction=0.4f;
			
			aabb=body.createFixture(fixD).getAABB();
			body.setTransform(body.getPosition(), (float) -getAngle());
		}else{
			this.hL=0;
		}
	}

	@Override
	public void update() {
		if(creator.getWorld().isOutOfBounds(aabb)){
			destroy();
			return;
		}
		position.setPosition(CoordinateConverter.coordWorldToPixels(body.getPosition()));
		setAngle(-body.getAngle());
		
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.save();
		context.translate(getX(), getY());
		context.rotate(getAngle());
		context.setLineWidth(2);
		context.setStrokeStyle(lineColor);
		context.beginPath();
		context.moveTo(hL,0);
		context.lineTo(-hL,0);
		context.stroke();
		context.restore();
	}
	@Override
	public void destroy(){
		body.setUserData(null);
		super.destroy();
		
	}
}