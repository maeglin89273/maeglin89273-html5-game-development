/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component;

import java.util.ArrayList;


import org.jbox2d.common.Vec2;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.event.GravityChangedEvent;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.event.GravityChangedListener;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.timer.RepeatingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;
import com.google.gwt.user.client.Random;


/**
 * @author Maeglin Liao
 *
 */
public class VariableGravityClock extends GeneralComponent {
	
	private SpriteSheet img;
	private int directionFactor;
	private int rotateUnit=1;
	private int gravityAngle=Random.nextInt(360);
	private int nextGravityAngle=gravityAngle;
	private float gravityMagnitude=5f;
	private Vec2 gravity=new Vec2((float)(gravityMagnitude*Math.cos(Math.toRadians(gravityAngle))),
									(float)(gravityMagnitude*Math.sin(Math.toRadians(gravityAngle))));
	private CountdownTimer timer;
	
	private ArrayList<GravityChangedListener> listenerList=new ArrayList<GravityChangedListener>();
	
	public VariableGravityClock(Point p,double diameter,int sec){
		super(p,diameter,diameter);
		if(gravityAngle<180){
			directionFactor=-1;
		}else{
			directionFactor=0;
		}
		
		img=MEngine.getAssetManager().getSpriteSheet("gravity_clock.png");
		timer=new CountdownTimer(p,diameter*0.55,diameter*0.55,sec);
		timer.run();
	}
	
	public void addGravityChangeListener(GravityChangedListener listener){
		listenerList.add(0, listener);
	}
	public void removeGravityChangeListener(GravityChangedListener listener){
		listenerList.remove(listener);
	}
	public Vec2 getGravity(){
		return gravity;
	}
	@Override
	public void update() {
		if((!timer.isStop())&&timer.getCount()==0){
			reverse();
		}
		if(gravityAngle!=nextGravityAngle){
			gravityAngle+=rotateUnit;
			if(gravityAngle>=360){
				gravityAngle-=360;
			}else if(gravityAngle<0){
				gravityAngle+=360;
			}
			rotateGravity(gravityAngle);
			fireChangeGravity();
			if(gravityAngle==nextGravityAngle){
				timer.run();
			}
		}
	}
	public void reverse(){
		int delta;
		timer.stop();
		directionFactor=~directionFactor;
		nextGravityAngle=180*(directionFactor+1)+Random.nextInt(180);
		if(nextGravityAngle>180){
			delta=gravityAngle-(nextGravityAngle-180);
		}else{
			delta=gravityAngle-(nextGravityAngle+180);
		}
		
		if(delta==0){
			return;
		}
		rotateUnit=delta/Math.abs(delta);
	}
	private void rotateGravity(int angle){
		gravity.set((float)(gravityMagnitude*Math.cos(Math.toRadians(angle))),
					(float)(gravityMagnitude*Math.sin(Math.toRadians(angle))));
	}
	private void fireChangeGravity(){
		GravityChangedEvent event=new GravityChangedEvent(this,new Vec2(gravity));
		for(int i=listenerList.size()-1;i>=0;i--){
			listenerList.get(i).gravityChange(event);
		}
		
	}
	public void setGravityMagnitude(float m){
		gravity.mulLocal(m/gravityMagnitude);
		this.gravityMagnitude=m;
		
	}
	public float getGravityMagnitude(){
		return gravityMagnitude;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.save();
		context.translate(getX(), getY());
		context.rotate(Math.toRadians(-gravityAngle));
		context.drawImage(img.getImage(), -width/2, -height/2, width, height);
		context.restore();
		timer.draw(context);
	}
	
	
	private class CountdownTimer extends GeneralComponent{
		private final RepeatingTimer timer;
		
		private final int startValue;
		private int count;
		
		private final String textFont;
		private CountdownTimer(Point p,double w,double h,int sec){
			super(p,w,h);
			textFont=((int)width*0.8)+"pt Century Gothic";
			
			this.startValue = sec;
			this.count = sec;
			
			timer=new RepeatingTimer(new TimerTask(){

				@Override
				public void doTask() {
					if(count==0){
						count=startValue;
						return;
					}
					count--;
					
				}
				
			},1000);
			
			
			
		}
		
		private void run(){
			reset();
			timer.start();
		}
		private void stop(){
			timer.stop();
			
		}
		private boolean isStop(){
			return timer.isStop();
		}
		private void reset(){
			this.count=startValue;
		}
		private int getCount(){
			return count;
		}
		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void draw(Context2d context) {
			context.setTextAlign(TextAlign.CENTER);
			context.setTextBaseline(TextBaseline.MIDDLE);
			context.setFillStyle(GameColors.TEXT_COLOR);
			context.setFont(textFont);
			context.fillText(Integer.toString(count),getX(),getY(),width);
		}
		
	}
}
