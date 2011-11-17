package com.google.gwt.maeglin89273.game.mengine.developmenttools;

import com.google.gwt.core.client.Duration;

public class FPSMeasurer {
	private int maxFrame;
	private int fps=0;
	private int frameCount;
	private double now;
	private double lastTime;
	
	public FPSMeasurer(int timerInterval){
		this.maxFrame=1000/timerInterval;
	}
	public int getFPS(){
		return fps;
	}
	public int getTimerInterval(){
		return 1000/maxFrame;
	}
	public void setTimerInterval(int timerInterval){
		this.maxFrame=1000/timerInterval;
	}
	public double getFPSRate(){
		return fps/(double)maxFrame*100;
	}
	public void measure(){
		now=Duration.currentTimeMillis();
		if(Math.ceil(now-lastTime)>=1000){
			fps=frameCount;
			
			frameCount=0;
			lastTime=now;
		}
		frameCount++;
	}
	@Override
	public String toString(){
		return "fps:"+fps+"("+(int)(fps/(double)maxFrame*100)+"%)";
	}
}
