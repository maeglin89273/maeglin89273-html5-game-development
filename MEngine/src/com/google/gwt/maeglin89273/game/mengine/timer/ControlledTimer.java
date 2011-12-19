/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.timer;

import com.google.gwt.core.client.Duration;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.user.client.Timer;

/**
 * @author Maeglin Liao
 *
 */
public abstract class ControlledTimer extends Timer {
	protected int interval;
	protected int delta=0;
	protected double last;
	protected boolean stop=true;
	
	protected TimerTask task;
	
	protected ControlledTimer(int interval,TimerTask task){
		this.interval=interval;
		this.task=task;
		MEngine.getGameExecutor().addControlledTimer(this);
	}
	public abstract void start();
	public void stop(){
		if(!stop){
			delta=(int)Math.ceil(Duration.currentTimeMillis()-last);
			this.cancel();
			stop=true;
		}
		
	}
	
	public boolean isStop(){
		return stop;
	}
	public void finish(){
		this.cancel();
		MEngine.getGameExecutor().removeControlledTimer(this);
		task=null;
	}
	public int getInterval(){
		return interval;
	}
	@Override
	public int hashCode(){
		return interval;
	}
}
