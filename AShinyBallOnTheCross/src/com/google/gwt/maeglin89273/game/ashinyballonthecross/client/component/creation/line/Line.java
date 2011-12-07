/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;






import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;


/**
 * @author Maeglin Liao
 *
 */
public abstract class Line extends MainCreation{
	
	public static final int MAX_LENGTH=200;
	protected final Point pointA=new Point(0,0);
	protected final Point pointB=new Point(0,0);
	
	protected Line(Creator creator,int contentPower,boolean beControlled,Point p1, Point p2) {
		super(creator,contentPower, beControlled, new Point((p1.getX()+p2.getX())/2,(p1.getY()+p2.getY())/2), 0,0, 0);
		if(this.isVerified()){
			pointA.setPosition(p1);
			pointB.setPosition(p2);
		}
	}
	public Point getPointA(){
		return pointA;
	}
	public Point getPointB(){
		return pointB;
	}
	
	public static abstract class LineDefiner extends CreationDefiner{
		
		protected Point pointA;
		protected Point pointB;
		protected LineDefiner(Creator creator,int requiredFullPower,Point iconCorner){
			super(creator,requiredFullPower,iconCorner);
			
		}
		@Override
		public void reset(){
			pointA=null;
			pointB=null;
		}
		@Override
		public void updatePenPosition(Point p){
			if(pointA!=null){
				Vector v=pointA.delta(p);
				if(v.getMagnitude()<Line.MAX_LENGTH){
					pointB=p;
				}else{
					pointB=new Point(Line.MAX_LENGTH,Math.atan2(-v.getVectorY(), v.getVectorX()),true);
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
				updatePenPosition(p);
				defineFinished();
				reset();
			}
		}
		@Override 
		public int getCreationRequiredPower(){
			if(pointA!=null&&pointB!=null){
				return Math.round((float)(requiredFullPower*pointA.delta(pointB).getMagnitude()/Line.MAX_LENGTH));
			}
			return 0;
		}
	}
	
	
}
