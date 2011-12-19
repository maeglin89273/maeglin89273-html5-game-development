/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.levelequipment;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.levelequipment.LevelEquipment;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class Shelf extends LevelEquipment {
	public static final float STANDARD_WIDTH=175; 
	
	protected Point commonPoint;
	protected Point longerLinePoint;
	protected Point shorterLinePoint;
	/**
	 * @param creator
	 * @param position
	 * @param angle
	 */
	protected Shelf(Creator creator, Point commonPoint,double width, double angle,boolean reverse) {
		super(creator, commonPoint, angle);
		this.commonPoint=commonPoint;
		if(reverse){
			longerLinePoint=new Point(this.commonPoint.getX()+width*2/3,this.commonPoint.getY()-width*1/2);
			shorterLinePoint=new Point(this.commonPoint.getX()-width*1/3,this.commonPoint.getY()-width*4/9);
		}else{
			longerLinePoint=new Point(this.commonPoint.getX()-width*2/3,this.commonPoint.getY()-width*1/2);
			shorterLinePoint=new Point(this.commonPoint.getX()+width*1/3,this.commonPoint.getY()-width*4/9);
		}
		Point[] ps=Point.getRotatedPoint(commonPoint, this.angle, longerLinePoint,shorterLinePoint);
		longerLinePoint=ps[0];
		shorterLinePoint=ps[1];
	}

}
