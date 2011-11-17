/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.timer;

import com.google.gwt.core.client.Duration;



/**
 * @author Maeglin Liao
 *
 */
public class RepeatingTimer extends ControlledTimer {
	private boolean isBuffered=false;
	public RepeatingTimer(TimerTask task,int interval){
		super(interval, task);
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.mengine.timer.ControlledTimer#start()
	 */
	@Override
	public void start() {
		if(stop){
			if(delta<=0){
				isBuffered=false;
				this.scheduleRepeating(interval);
				
			}else{
				isBuffered=true;
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
		if(isBuffered){
			isBuffered=false;
			RepeatingTimer.this.scheduleRepeating(interval);
		}
	}
}
