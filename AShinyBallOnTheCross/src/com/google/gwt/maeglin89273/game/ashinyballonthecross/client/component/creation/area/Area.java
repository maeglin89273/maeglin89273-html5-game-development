/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Creation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;

/**
 * @author Maeglin Liao
 *
 */
public abstract class Area extends MainCreation {

	/**
	 * @param creator
	 * @param contentPower TODO
	 * @param p
	 * @param w
	 * @param h
	 */
	protected Area(Creator creator,int contentPower,boolean beControlled, Point p, double w, double h, double a) {
		super(creator, contentPower,beControlled, p, w, h, a);
	}
	
	//some implements of definers
	public static abstract class AreaDefiner extends CreationDefiner{
		
		protected AreaDefiner(Creator creator,int requiredFullPower,Point iconCorner){
			super(creator,requiredFullPower, iconCorner);
		}
	}
	public static abstract class CircleKindAreaDefiner extends AreaDefiner{
		private final int maxRadi;
		private final int minRadi;
		protected Point center;
		protected double radius;
		protected double angle;
		protected CircleKindAreaDefiner(Creator creator,int requiredFullPower,Point iconCorner,int maxR, int minR) {
			super(creator,requiredFullPower, iconCorner);
			maxRadi = maxR;
			minRadi = minR;
			radius=minRadi;
		}
		@Override
		public void onPenDown(Point p){
			center=p;
		}
		@Override
		public void updatePenPosition(Point p){
			if(center!=null){
				Vector distanceV=center.delta(p);
				double distanceS=distanceV.getSquare();
				this.angle=distanceV.getAngle();
				if(distanceS<minRadi*minRadi){
					radius=minRadi;
				}else if(distanceS>maxRadi*maxRadi){
					radius=maxRadi;
				}else{
					radius=distanceV.getMagnitude();
				}
				
			}
		}
		@Override 
		public void onPenUp(Point p){
			if(center!=null){
				updatePenPosition(p);
				this.defineFinished();
				reset();
			}
		}
		@Override
		public void reset(){
			center=null;
			radius=minRadi;
		}
		@Override 
		public int getCreationRequiredPower(){
			if(center!=null){
				return Math.round((float)(this.requiredFullPower*radius/maxRadi));
			}
			return 0;
		}
	}
}
