/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.mengine.component.Spacial;
import com.google.gwt.maeglin89273.mengine.physics.Point;
import com.google.gwt.maeglin89273.mengine.physics.Vector;
import com.google.gwt.maeglin89273.mengine.utility.CoordinateConverter;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class Polygon extends PhysicalPolygon {
	
	private Vector[] vertices;

	public Polygon(PhysicalWorld space,Point p,Vector[] vertices){
		this(space, p, vertices, 0, GameColors.getRandomShapeBorderColor());
	}
	public Polygon (PhysicalWorld space,Point p,Vector[] vertices,double angle,CssColor color){
		super(p, 0, 0, space, angle, color);
		this.vertices=vertices;
		
		
		PolygonShape shape=new PolygonShape();
		FixtureDef fixtureDef=new FixtureDef();
		
		Vec2[] vs=new Vec2[vertices.length];
		for(int i=0;i<vs.length;i++){
			vs[i]=CoordinateConverter.vectorPixelsToWorld(vertices[i]);
		}
		
		shape.set(vs, vs.length);
		
		fixtureDef.shape=shape;
		fixtureDef.friction=0.7f;
		fixtureDef.density=0.8f;
		fixtureDef.restitution=0.5f;
		
		this.fixture=body.createFixture(fixtureDef);
		
		aabb=CoordinateConverter.transformAABB(fixture.getAABB());
	}
	public Polygon(PhysicalWorld space,Point p){
		this(space, p, generateRandomInscribedPolygonVertices(3+Random.nextInt(6),12+Random.nextInt(4)));
	}
	
	
	@Override
	public void draw(Context2d context) {
		context.save();
		context.setStrokeStyle(borderColor);
		
		context.setLineWidth(1.25);
		context.translate(getX(),getY());
		context.rotate(-body.getAngle());
		context.beginPath();
		context.moveTo(vertices[0].getVectorX(), vertices[0].getVectorY());
		
		for(int i=1;i<vertices.length;i++){
			context.lineTo(vertices[i].getVectorX(), vertices[i].getVectorY());
		}
		context.closePath();
		context.stroke();
		context.restore();
		
	}
	public static Vector[] generateRandomInscribedPolygonVertices(int verticesCount,double circumscribedCircleRadius){
		if(verticesCount>8){
			throw new IllegalArgumentException("too many vertices!");
		}else if(verticesCount<3){
			throw new IllegalArgumentException("too few vertices!");
		}
		Point g=new Point(0,0);
		double theta=2*Math.PI/verticesCount;
		double angle=0;
		Point[] verticesP=new Point[verticesCount];
		Vector[] vertices=new Vector[verticesCount];
		for(int c=0;c<vertices.length;c++){
			//angle+=(2*Math.PI-angle)*Random.nextDouble(); the other way to create a vertex
			angle=theta*Random.nextDouble()+c*theta;
			verticesP[c]=new Point(angle, circumscribedCircleRadius,true);
			g.translate(verticesP[c].getX(), verticesP[c].getY());
		}
		g.setPosition(g.getX()/verticesCount, g.getY()/verticesCount);
		
		for(int i=0;i<vertices.length;i++){
			vertices[i]=new Vector(verticesP[i].getX()-g.getX(),verticesP[i].getY()-g.getY());
		}
		return vertices;
	}
}
