/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.area;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;

/**
 * @author Maeglin Liao
 *
 */
public class TriangleArea extends PhysicalArea{
	private Vector[] vertices;
	
	/**
	 * @param creator
	 * @param contentPower
	 * @param beControlled
	 * @param p
	 * @param w
	 * @param h
	 * @param angle
	 */
	public TriangleArea(int contentPower, boolean beControlled, Point p, Vector[] vertices) {
			
		super(contentPower, beControlled, p, 0, 0,0);
		if(isVerified()){
			this.vertices=vertices;
			
			this.body.setType(BodyType.DYNAMIC);
			this.body.setUserData(this);
			PolygonShape shape=new PolygonShape();
			FixtureDef fixtureDef=new FixtureDef();
			
			Vec2[] vs=new Vec2[vertices.length];
			for(int i=0;i<3;i++){
				vs[i]=CoordinateConverter.vectorPixelToWorld(vertices[i]);
			}
			
			shape.set(vs, 3);
			
			fixtureDef.shape=shape;
			fixtureDef.friction=0.8f;
			fixtureDef.density=3f;
			fixtureDef.restitution=0.3f;
			
			aabb=body.createFixture(fixtureDef).getAABB();
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
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.save();
		context.setStrokeStyle(ASBOTXConfigs.Color.GREEN_BORDER);
		context.setFillStyle(ASBOTXConfigs.Color.TRANSPARENT_GREEN);
		context.setLineWidth(2);
		context.translate(getX(),getY());
		context.rotate(getAngle());
		
		context.beginPath();
		context.moveTo(vertices[0].getVectorX(), vertices[0].getVectorY());
		for(int i=1;i<vertices.length;i++){
			context.lineTo(vertices[i].getVectorX(), vertices[i].getVectorY());
		}
		context.closePath();
		context.fill();
		context.stroke();
		context.restore();
	}
	
	@Override
	public void destroy(){
		body.setUserData(null);
		super.destroy();
	}
	public static class TriangleAreaDefiner extends TriangleKindAreaDefiner{

		public TriangleAreaDefiner() {
				
			super(ASBOTXConfigs.CreationPowerComsumption.TRIANGLE_AREA, new Point(2*ICON_BOUNDS_PLUS_SPACING,0));
			
		}

		@Override
		public void sketch(Context2d context) {
			if(vertices[0]!=null){
				context.save();
				context.setStrokeStyle(ASBOTXConfigs.Color.GREEN_BORDER);
				context.setFillStyle(ASBOTXConfigs.Color.TRANSPARENT_GREEN);
				context.setLineWidth(2);
				
				context.beginPath();
				context.moveTo(vertices[0].getX(), vertices[0].getY());
				for(int i=1;i<vertices.length;i++){
					if(vertices[i]!=null){
						context.lineTo(vertices[i].getX(), vertices[i].getY());
					}
				}
				context.closePath();
				if(vertices[2]!=null){
					context.fill();
				}
				context.stroke();
				context.restore();
			}
		}

		@Override
		protected MainCreation create(int requiredPower) {
			return new TriangleArea( requiredPower, true, g, relativeVertices);
		}
		
	}
}
