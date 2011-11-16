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
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.maeglin89273.game.mengine.game.Game;
import com.google.gwt.maeglin89273.game.mengine.timer.ControlledTimer;

/**
 * @author Maeglin Liao
 *
 */
public class GameExecutor {
	private final Canvas canvas;
	private final Context2d context;
	
	private final CanvasElement bufferCanvasElement;
	private final Context2d bufferContext;
	
	private final Game game;
	private final int gameWidth;
	private final int gameHeight;
	
	private CssColor redrawColor=CssColor.make("hsla(0,100%,100%,0.6)");
	
	private AnimationScheduler animationScheduler=AnimationScheduler.get();
	private AnimationCallback animationCallback;
	private static final int MAXIMUM_INTERVAL=75;
	
	private boolean pause;
	private HashSet<ControlledTimer> timers=new HashSet<ControlledTimer>();
	GameExecutor(Game game,Canvas canvas){
		this.game=game;
		this.gameWidth=game.getGameInfo().getWidth();
		this.gameHeight=game.getGameInfo().getHeight();
		this.canvas=canvas;
		this.context=this.canvas.getContext2d();
		
		Canvas bufferCanvas=Canvas.createIfSupported();
		bufferCanvas.setCoordinateSpaceWidth(this.gameWidth);
		bufferCanvas.setCoordinateSpaceHeight(this.gameHeight);
		
		bufferCanvasElement=bufferCanvas.getCanvasElement();
		bufferContext=bufferCanvas.getContext2d();
	}
	void setRedrawAlpha(float alpha){
		redrawColor=CssColor.make("hsla(0,100%,100%,"+alpha+")");
	}
	void start(){
		animationCallback=new AnimationCallback(){
			double now;
			double last=Duration.currentTimeMillis();
			double delta;
			@Override
			public void execute(double timestamp) {
				
				now=timestamp;
				delta=now-last;
				last=now;
				
				bufferContext.setFillStyle(redrawColor);
				bufferContext.fillRect(0, 0,GameExecutor.this.gameWidth,GameExecutor.this.gameHeight);
				
				synchronized(this){
					do{
						game.update();
						delta-=MAXIMUM_INTERVAL;
					}while(delta>0);
					game.draw(bufferContext);
					context.drawImage(bufferCanvasElement, 0, 0);
					if(pause){
						return;
					}
				}
				
				animationScheduler.requestAnimationFrame(this,canvas.getCanvasElement());
				
			}
			
		};
		if(game.getGameInfo().hasLoadingDataPage()){
			play();
		}else{
			if(MEngine.getAssetsManager().isDataLoaded()){
				play();
			}else{
				MEngine.getAssetsManager().addDataLoadedListener(new AssetsManager.DataLoadedListener(){
					@Override
					public void done() {
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
			timer.start();
		}
	}
}
