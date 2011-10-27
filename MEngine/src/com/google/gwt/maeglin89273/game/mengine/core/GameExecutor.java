/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.core;

import java.util.HashSet;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.Duration;
import com.google.gwt.maeglin89273.game.mengine.timer.ControlledTimer;

/**
 * @author Maeglin Liao
 *
 */
public class GameExecutor {
	private final Canvas canvas;
	private final Context2d context;
	private final Game game;
	private static final CssColor redrawColor=CssColor.make("hsla(0,100%,100%,0.6)");
	
	private AnimationScheduler animationScheduler=AnimationScheduler.get();
	private AnimationCallback animationCallback;
	private static final int MAXIMUM_INTERVAL=75;
	
	private boolean pause;
	private HashSet<ControlledTimer> timers=new HashSet<ControlledTimer>();
	GameExecutor(Game game,Canvas canvas,Context2d context){
		this.game=game;
		this.canvas=canvas;
		this.context=context;
	}
	public void start(){
		animationCallback=new AnimationCallback(){
			double now;
			double last=Duration.currentTimeMillis();
			double delta;
			@Override
			public void execute(double timestamp) {
				
				now=timestamp;
				delta=now-last;
				last=now;
				
				context.setFillStyle(redrawColor);
				context.fillRect(0, 0, game.getWidth(),game.getHeight());
				
				synchronized(MEngine.class){
					do{
						game.update();
						delta-=MAXIMUM_INTERVAL;
					}while(delta>0);
					game.draw(context);
					if(pause){
						return;
					}
				}
				
				animationScheduler.requestAnimationFrame(this,canvas.getCanvasElement());
				
			}
			
		};
		if(game.hasLoadingResourcesPage()){
			play();
		}else{
			if(MEngine.getAssetManager().isDataLoaded()){
				play();
			}else{
				MEngine.getAssetManager().waitDataLoaded(new AssetManager.Done(){
					@Override
					public void execute() {
						play();
					}
					
				});
			}
		}
	}
	
	public void addControlledTimer(ControlledTimer timer){
		timers.add(timer);
	}
	public void removeControlledTimer(ControlledTimer timer){
		timers.remove(timer);
	}
	public void pause(){
		pause=true;
		for(ControlledTimer timer:timers){
			timer.stop();
		}
	}
	public void play(){
		animationScheduler.requestAnimationFrame(animationCallback, canvas.getCanvasElement());
		pause=false;
		for(ControlledTimer timer:timers){
			if(!timer.isLastStateStop())
				timer.start();
		}
	}
}
