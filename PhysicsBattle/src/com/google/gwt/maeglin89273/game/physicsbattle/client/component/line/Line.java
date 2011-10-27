/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.line;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.Creation;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.Sketcher;
import com.google.gwt.maeglin89273.mengine.component.Physical;
import com.google.gwt.maeglin89273.mengine.component.Spacial;
import com.google.gwt.maeglin89273.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.mengine.physics.Point;
import com.google.gwt.maeglin89273.mengine.physics.Vector;
import com.google.gwt.maeglin89273.mengine.utility.CoordinateConverter;

/**
 * @author Maeglin Liao
 *
 */
public abstract class Line extends Creation implements Physical {
	
	public static final int MAX_LENGTH=300;
	protected final Point pointA=new Point(0,0);
	protected final Point pointB=new Point(0,0);
	
	protected Body body;
	protected PhysicalWorld world;
	protected PixelAABB aabb;
	
	protected Line(Point p1,Point p2,PhysicalWorld world) {
		super(new Point((p1.getX()+p2.getX())/2,(p1.getY()+p2.getY())/2), 0, 0,world);
		
		position.setPosition((p1.getX()+p2.getX())/2,(p1.getY()+p2.getY())/2);
		pointA.setPosition(p1);
		pointB.setPosition(p2);
		
		BodyDef bodyDef=new BodyDef();
		PolygonShape lineP=new PolygonShape();
		lineP.setAsEdge(CoordinateConverter.coordPixelsToWorld(pointA), CoordinateConverter.coordPixelsToWorld(pointB));
		
		this.body=this.world.getWorld().createBody(bodyDef);
		aabb=CoordinateConverter.transformAABB(body.createFixture(lineP, 0f).getAABB());
		setWidth(aabb.getWidth());
		setHeight(aabb.getHeight());
	}
	public Point getPointA(){
		return pointA;
	}
	public Point getPointB(){
		return pointB;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.Physical#getBody()
	 */
	@Override
	public Body getBody() {
		return body;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.Physical#getSpace()
	 */
	@Override
	public Spacial getSpace() {
		return world;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.Physical#destory()
	 */
	@Override
	public void destory() {
		world.removeLine(this);
		body=null;
		world=null;

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.Physical#getAABB()
	 */
	@Override
	public PixelAABB getAABB() {
		return aabb;
	}
	public static abstract class LineSketcher implements Sketcher{
		protected PhysicalWorld world;
		protected Point pointA;
		protected Point pointB;
		protected LineSketcher(PhysicalWorld world){
			this.world=world;
		}
		@Override
		public void reset(){
			pointA=null;
			pointB=null;
		}
		@Override
		public void updatePenPosition(Point p){
			if(pointA!=null){
				Vector v=new Vector(p.getX()-pointA.getX(),p.getY()-pointA.getY());
				if(v.getMagnitude()<Line.MAX_LENGTH){
					pointB=p;
				}else{
					pointB=new Point(Math.atan2(-v.getVectorY(), v.getVectorX()),Line.MAX_LENGTH,true);
					pointB.translate(pointA.getX(), pointA.getY());
				}
				
			}
		}
		@Override
		public void onPenDown(Point p){
			pointA=p;
		}
		@Override
		public void onPenUp(Point p){
			if(pointA!=null){
				Vector v=new Vector(p.getX()-pointA.getX(),p.getY()-pointA.getY());
				if(v.getMagnitude()<Line.MAX_LENGTH){
					pointB=p;
				}else{
					/*pointB=new Point((p.getX()*Line.MAX_LENGTH+pointA.getX()*(v.getMagnitude()-Line.MAX_LENGTH))/v.getMagnitude(),
							(p.getY()*Line.MAX_LENGTH+pointA.getY()*(v.getMagnitude()-Line.MAX_LENGTH))/v.getMagnitude());*/
					pointB=new Point(Math.atan2(-v.getVectorY(), v.getVectorX()),Line.MAX_LENGTH,true);
					pointB.translate(pointA.getX(), pointA.getY());
				}
				sketchFinished();
			}
		}
	}
	
	
}
