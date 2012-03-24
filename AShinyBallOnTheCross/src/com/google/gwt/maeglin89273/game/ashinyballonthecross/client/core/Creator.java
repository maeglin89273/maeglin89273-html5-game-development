/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core;


import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.Creation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedListener;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GravityChangedEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GravityChangedListener;






/**
 * @author Maeglin Liao
 *
 */
public class Creator {
	private int power;
	private int score;
	private int bufferRestorePower=0;
	private final int maxPower;
	
	private final List<MainCreation> creations=new ArrayList<MainCreation>();
	private final List<CreatorPropertiesChangedListener> listeners=new ArrayList<CreatorPropertiesChangedListener>();
	
	private PhysicalWorld world;
	private final Level level;
	private final GravityController gController;
	private boolean built=false;
	private GameOverCallback callback;
	public Creator(Level level){
		
		this.level = level;
		this.gController=new GravityController(level.getGravityAngleInDegrees());
		maxPower=power=score=level.getFullPower();
		
		
	}
	public Level getLevel(){
		return level;
	}
	public void build(GameOverCallback callback){
		if(!built){
			Creation.setCreator(this);
			this.world=new PhysicalWorld(level.getScreenCenter(),level.getLevelWidth(),level.getLevelHeight()
					,gController.getGravity());
			gController.addGravityChangeListener(this.world);
			this.callback = callback;
			level.buildLevel();
		}
		built=true;
	}
	public PhysicalWorld getWorld(){
		return world;
	}
	public GravityController getGravityController(){
		return gController;
	}
	public int getScore(){
		return score;
	}
	public int getPower(){
		return power;
	}
	public int getMaxPower(){
		return maxPower;
	}
	
	public void addPropertiesChangeListener(CreatorPropertiesChangedListener l){
		listeners.add(l);
	}
	public void removePropertiesChangeListener(CreatorPropertiesChangedListener l){
		int i=listeners.lastIndexOf(l);
		if(i>=0)
			listeners.remove(i);
	}
	private void fireScoreChangedEvents(CreatorPropertiesChangedEvent event){
		
		for(int i=listeners.size()-1;i>=0;i--){
			listeners.get(i).scoreChanged(event);
		}
		
	}
	private void firePowerChangedEvents(CreatorPropertiesChangedEvent event){
		
		for(int i=listeners.size()-1;i>=0;i--){
			listeners.get(i).powerChanged(event);
		}
		
	}
	/**
	 * verify the creation.It doesn't add the creation into the creator,but cost the remaining power.
	 * @param c
	 * @return
	 */
	public boolean verify(Creation c){
		if(power<c.getContentPower()){
			return false;
		}
		if(c.getContentPower()!=0){
			power-=c.getContentPower();
			firePowerChangedEvents(new CreatorPropertiesChangedEvent(this));
			updateScore();
		}
		return true;
	}
	/**
	 * recycle a creation.It restore the power of the creation.
	 * @param c
	 */
	public void recycle(Creation c){
		bufferRestorePower+=c.getContentPower();
	}
	private void updateScore(){
		if(power<score){
			score=power;
			fireScoreChangedEvents(new CreatorPropertiesChangedEvent(this));
		}
		
	}
	public void bonus(int bonus){
		if(score!=power){
			if(score+bonus<power){
				score+=bonus;
			}else{
				score=power;
			}
			fireScoreChangedEvents(new CreatorPropertiesChangedEvent(this));
		}
	}
	public void restore(){
		if(power<maxPower&&bufferRestorePower>0){
			bufferRestorePower--;
			power++;
			firePowerChangedEvents(new CreatorPropertiesChangedEvent(this));
		}
	}
	
	/**
	 * add the creation into the creations list if the creation is verified.
	 * @param c
	 */
	public void add(MainCreation c){
		if(c.isVerified())
			this.creations.add(c);
	}
	public void  remove(MainCreation c){
		int ri=creations.lastIndexOf(c);
		if(ri>=0){
			creations.remove(ri);
		}
	}
	/**
	 * remove the first main creation from the creations list, and also call the destroy() method. 
	 */
	public MainCreation removeFirstCreation(){
		if(!creations.isEmpty()){
			MainCreation c=this.creations.get(0);
			c.destroy();
			return c;
		}
		return null;
	}
	/**
	 * remove the last main creation from the creations list, and also call the destroy() method.
	 */
	public MainCreation removeLastCreation(){
		if(!creations.isEmpty()){
			MainCreation c=this.creations.get(creations.size()-1);
			c.destroy();
			return c;
		}
		return null;
	}
	/**
	 * @return the callback
	 */
	public GameOverCallback getGameOverCallback() {
		return callback;
	}
	public static class GravityController{
		public static final float DEFAULT_VELOCITY_THRESHOLD=Settings.velocityThreshold;
		public static final float GRAVITY_MAGNITUDE=9.8f;
		
		private int RotationUnit=0;
		private int gravityAngle;
		private int nextGravityAngle;
		
		private final Vec2 gravity;
		
		private ArrayList<GravityChangedListener> listenerList=new ArrayList<GravityChangedListener>();
		private GravityController(int angleInDegrees){
			this.gravityAngle=this.nextGravityAngle=-angleInDegrees;
			gravity=getGravityByDegrees(angleInDegrees);
		}
		public double getAngle(){
			return Math.toRadians(-gravityAngle);
		}
		public void updateRotation(){
			if(isRotating()){
				gravityAngle+=RotationUnit;
				if(gravityAngle>=360){
					gravityAngle-=360;
				}else if(gravityAngle<0){
					gravityAngle+=360;
				}
				
				rotateGravity();
				fireGravityChangedEvents();
			}
		}
		private void rotateGravity(){
			double radians=-getAngle();
			
			gravity.set((float)(GRAVITY_MAGNITUDE*Math.cos(radians)),
						(float)(GRAVITY_MAGNITUDE*Math.sin(radians)));
		}
		public boolean isRotating(){
			return gravityAngle!=nextGravityAngle;
		}
		
		private void fireGravityChangedEvents(){
			GravityChangedEvent event=new GravityChangedEvent(this,new Vec2(gravity));
			for(int i=listenerList.size()-1;i>=0;i--){
				listenerList.get(i).gravityChanged(event);
			}
			
		}
		public void clearGravity(){
			
			Settings.velocityThreshold=0;
		}
		public void setAngle(int angleInDegree){
			int delta;
			
			nextGravityAngle=-angleInDegree;
			delta=gravityAngle-(nextGravityAngle+(nextGravityAngle>180?-180:180));
			
			if(delta==0){
				return;
			}
			RotationUnit=delta/Math.abs(delta);
			Settings.velocityThreshold=DEFAULT_VELOCITY_THRESHOLD;
		}
		private static Vec2 getGravityByDegrees(int angleInDegree){
			return new Vec2((float)(GRAVITY_MAGNITUDE*Math.cos(Math.toRadians(angleInDegree))),
					 		(float)(GRAVITY_MAGNITUDE*Math.sin(Math.toRadians(-angleInDegree))));
		}
		public void addGravityChangeListener(GravityChangedListener listener){
			listenerList.add(listener);
		}
		public void removeGravityChangeListener(GravityChangedListener listener){
			listenerList.remove(listener);
		}
		public Vec2 getGravity(){
			return gravity;
		}
	}
}
