/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.timer;

import com.google.gwt.core.client.Duration;


/**
 * @author Maeglin Liao
 *
 */
public class SchedulingTimer extends ControlledTimer {

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.mengine.timer.ControlledTimer#start()
	 */
	public SchedulingTimer(TimerTask task,int interval){
		super(interval, task);
	}
	@Override
	public void start() {
		if(stop){
			if(delta<=0){
				this.schedule(interval);
			}else{
				this.schedule(interval-delta);
			}
			this.last=Duration.currentTimeMillis();
			this.stop=false;
		}	
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.Timer#run()
	 */
	@Override
	public void run() {
		last=Duration.currentTimeMillis();
		task.doTask();
		finish();
	}

}
