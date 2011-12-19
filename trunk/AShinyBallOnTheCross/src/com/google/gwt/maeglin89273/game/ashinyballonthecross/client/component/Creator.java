/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Creation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.DefaultLevel;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedListener;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


import java.util.ArrayList;
import java.util.List;






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
	
	private final PhysicalWorld world;
	private final LevelContext level;
	
	private boolean built=false;
	public Creator(int w,int h){
		this(new DefaultLevel(w,h));
	}
	public Creator(LevelContext level){
		
		/*use LevelContext as the argument for initializing this creator
		 * the properties below depends on LevelContext
		*/
		this.level = level;
		
		maxPower=power=score=level.getFullPower();
		
		this.world=new PhysicalWorld(level.getScreenCenter(),level.getLevelWidth(),level.getLevelHeight()
				,GravityIndicator.getGravityByDegrees(level.getGravityAngleInDegrees()));
	}
	public LevelContext getLevel(){
		return level;
	}
	public void build(GameOverCallback callback){
		if(!built)
			level.buildLevel(this,callback);
		built=true;
	}
	public PhysicalWorld getWorld(){
		return world;
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
	public void removeFirstCreation(){
		if(!creations.isEmpty()){
			this.creations.get(0).destroy();
		}
		
	}
	/**
	 * remove the last main creation from the creations list, and also call the destroy() method.
	 */
	public void removeLastCreation(){
		if(!creations.isEmpty()){
			this.creations.get(creations.size()-1).destroy();
		}
	}
}
