/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;






import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;


/**
 * @author Maeglin Liao
 *
 */
public abstract class Line extends MainCreation{
	
	public static final int MAX_LENGTH=250;
	public static final int MAX_LENGTH_SQUARE=MAX_LENGTH*MAX_LENGTH;
	public static final int MIN_LENGTH_SQUARE = 13*13;
	protected final Point pointA=new Point(0,0);
	protected final Point pointB=new Point(0,0);
	
	protected Line(Creator creator,int contentPower,boolean beControlled,Point p1, Point p2) {
		super(creator,contentPower, beControlled, Point.getCenter(p1, p2), Point.getDistance(p1, p2),0,p1.delta(p2).getAngle());
		if(this.isVerified()){
			if(!p1.equals(p2)){
				pointA.setPosition(p1);
				pointB.setPosition(p2);
			}
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
			if(pointA!=null&&!pointA.equals(p)){
				Vector v=pointA.delta(p);
				if(v.getSquare()>Line.MAX_LENGTH_SQUARE){
					v.setMagnitude(MAX_LENGTH);
					p=pointA.clone();
					p.translate(v);
				}
				pointB=p;
			}
		}
		@Override
		public void onPenDown(Point p){
			pointA=p;
		}
		@Override
		public void onPenUp(Point p){
			
			updatePenPosition(p);
			if(pointA!=null&&pointB!=null){
				if(pointA.delta(pointB).getSquare()<MIN_LENGTH_SQUARE){
					new FragmentalLine(creator, getCreationRequiredPower(), true, pointA, pointB, ASBOTXConfigs.Color.DARK_GRAY);
				}else{
					defineFinished();
				}
			}
			reset();
			
		}
		@Override 
		public int getCreationRequiredPower(){
			return pointA!=null&&pointB!=null?Math.max(1,Math.round((float)(requiredFullPower*pointA.delta(pointB).getMagnitude()/Line.MAX_LENGTH))):0;
		}
	}
}
