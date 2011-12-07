package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;

class FragmentalLine extends PhysicalLine{
	/**
	 * 
	 */
	private final Vector rpA;
	private final Vector rpB;
	
	
	public FragmentalLine(Creator creator,int contentPower,boolean beControlled,Point p1, Point p2) {
		super(creator,contentPower,beControlled, p1,p2, GameColors.GRAY);
		
		if(isVerified()){	
			body.setType(BodyType.DYNAMIC);
			body.setBullet(true);
			FixtureDef fixD=new FixtureDef();
				
			PolygonShape lineP=new PolygonShape();
			Vector delta=Point.delta(pointA, pointB);
			rpA=position.delta(pointA);
			rpB=position.delta(pointB);
				
			lineP.setAsBox(CoordinateConverter.scalerPixelsToWorld(delta.getMagnitude())/2
					,CoordinateConverter.scalerPixelsToWorld(1),new Vec2(),(float)-delta.getAngle());
						
			fixD.density=0.5f;
			fixD.shape=lineP;
			fixD.restitution=0.75f;
			fixD.friction=0.7f;
			aabb=body.createFixture(fixD).getAABB();
		}else{
			rpA=null;
			rpB=null;
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
		context.moveTo(rpA.getVectorX(),rpA.getVectorY());
		context.lineTo(rpB.getVectorX(), rpB.getVectorY());
		context.stroke();
		context.restore();
	}
	
	
}