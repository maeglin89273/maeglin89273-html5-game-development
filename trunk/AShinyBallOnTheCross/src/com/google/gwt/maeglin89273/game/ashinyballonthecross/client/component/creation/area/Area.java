/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area;

import org.jbox2d.common.Settings;

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
			updatePenPosition(p);
			if(center!=null){
				this.defineFinished();
			}
			reset();
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
	public static abstract class TriangleKindAreaDefiner extends AreaDefiner{
		private static final int MAX_R=150;
		protected Point[] vertices=new Point[3];
		private double r;
		protected Point g;
		protected Vector[] relativeVertices;
		protected TriangleKindAreaDefiner(Creator creator,int requiredFullPower, Point iconCorner) {
			super(creator, requiredFullPower, iconCorner);
			
		}
		@Override
		public void onPenDown(Point p){
			if(vertices[0]==null){
				vertices[0]=p;
				vertices[1]=p;
			}
		}
		@Override
		public void updatePenPosition(Point p){
			if(vertices[0]!=null){
				if(vertices[2]==null){
					vertices[1]=p;
				}else{
					Vector v10=vertices[1].delta(vertices[0]);
					Vector v20=p.delta(vertices[0]);
					Vector v21=p.delta(vertices[1]);
					this.r=Math.sqrt(v10.getSquare()*v20.getSquare()*v21.getSquare())/Math.abs(2*v20.crossProduct(v21));
					
					vertices[2]=p;
					
				}
			}
		}
		@Override 
		public void onPenUp(Point p){
			if(vertices[2]==null){
				vertices[1]=p;
				vertices[2]=p;
			}else{
				Vector v0=vertices[2].delta2(vertices[1]);
				Vector v1=vertices[0].delta2(vertices[2]);
				double cross=v0.crossProduct(v1);
				if(cross*cross<=ASBOTXConfigs.E_SQUARE*v0.getSquare()*v1.getSquare()){
					reset();
					return;
				}
				if(cross<0){
					Point tmp=vertices[0];
					vertices[0]=vertices[1];
					vertices[1]=tmp;
				}
				
				relativeVertices=new Vector[3];
				g=new Point((vertices[0].getX()+vertices[1].getX()+vertices[1].getX())/3,
							(vertices[0].getY()+vertices[1].getY()+vertices[1].getY())/3);
				for(int i=0;i<3;i++){
					relativeVertices[i]=g.delta(vertices[i]);
				}
				defineFinished();
				reset();
			}
			
		}
		@Override
		public void reset(){
			vertices=new Point[3];
		}
		@Override 
		public int getCreationRequiredPower(){
			return vertices[2]!=null?Math.max(3,Math.round((float)(r*this.requiredFullPower/MAX_R))):0;
		}
	}
}
