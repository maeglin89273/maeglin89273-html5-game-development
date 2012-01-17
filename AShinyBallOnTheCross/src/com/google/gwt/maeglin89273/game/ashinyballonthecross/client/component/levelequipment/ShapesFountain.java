/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.levelequipment;

import org.jbox2d.common.Vec2;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Circle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Polygon;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Rectangle;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.timer.RepeatingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class ShapesFountain extends LevelEquipment{
	
	private final Vec2 impulse;
	private RepeatingTimer timer;
	/**
	 * @param creator
	 * @param cotentPower
	 * @param p
	 * @param w
	 * @param h
	 * @param a
	 */
	public ShapesFountain(Creator creator,Point p,double a,float impulseMag) {
		super(creator, p,a);
		impulse=new Vec2((float)(impulseMag*Math.cos(-a)),(float)(impulseMag*Math.sin(-a)));
		timer=new RepeatingTimer(new TimerTask(){

			@Override
			public void doTask() {
				
				PhysicalShape shape;
				switch(Random.nextInt(3)){
				case 0:
					shape=new Circle(ShapesFountain.this.creator, position);
					break;
				case 1:
					shape=new Rectangle(ShapesFountain.this.creator, position);
					break;
				default:
					shape=new Polygon(ShapesFountain.this.creator, position);
				}
				shape.getBody().applyLinearImpulse(impulse, shape.getBody().getWorldCenter());
			}
			
		}, 500);
		timer.start();
	}

	public void stopSpurting(){
		timer.finish();
		timer=null;
	}

}
