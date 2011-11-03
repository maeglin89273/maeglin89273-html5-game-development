/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.line;




import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.Creation;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.CreationSketcher;

import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;


/**
 * @author Maeglin Liao
 *
 */
public abstract class Line extends Creation{
	
	public static final int MAX_LENGTH=300;
	protected final Point pointA=new Point(0,0);
	protected final Point pointB=new Point(0,0);
	
	protected Line(Point p1,Point p2,PhysicalWorld world) {
		super(new Point((p1.getX()+p2.getX())/2,(p1.getY()+p2.getY())/2), 0, 0,world);
		
		position.setPosition((p1.getX()+p2.getX())/2,(p1.getY()+p2.getY())/2);
		pointA.setPosition(p1);
		pointB.setPosition(p2);
		
	}
	public Point getPointA(){
		return pointA;
	}
	public Point getPointB(){
		return pointB;
	}
	
	public static abstract class LineSketcher implements CreationSketcher{
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
