package com.google.gwt.maeglin89273.game.mengine.core;


import java.util.HashSet;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.core.client.Duration;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;

import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.maeglin89273.game.mengine.page.LoadingResourcesPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.timer.ControlledTimer;
import com.google.gwt.maeglin89273.game.mengine.utility.CoordinateConverter;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Maeglin Liao
 *
 */
public class MEngine {
	
	private static Canvas canvas;
	private static Context2d context;
	
	private static Game game;
	
	private static GameExecutor gameExecutor;
	private static AssetManager assetManager;
	private static MousePosition mousePosition;
	private static EventsDeliverer eventsDeliverer;
	public static void init(Game g,String assetsPrefix){
		if(!Canvas.isSupported()){
			RootPanel.get().add(new Label("Sorry,your browser doesn't support HTML5."));
			return;
		}
		canvas=Canvas.createIfSupported();
		context=canvas.getContext2d();
		game=g;
		
		gameExecutor=new GameExecutor(game,canvas,context);
		assetManager=new AssetManager(assetsPrefix);
		mousePosition=new MousePosition(canvas);
		eventsDeliverer=new EventsDeliverer(canvas);
		
		assetManager.loadSpriteSheets(game.getGameSpriteSheets());
		canvas.addMouseMoveHandler(mousePosition);//do not use eventsDeliverer to add MouseMoveEventHandler,because the deliverer may remove it.
		CoordinateConverter.init(game.getWidth(), game.getHeight());
		
		setupCanvas();
		
		game.init();
	}
	public static void start(){
		gameExecutor.start();
	}
	public static void pause(){
		gameExecutor.pause();
	}
	public static void play(){
		gameExecutor.play();
	}
	public static AssetManager getAssetManager(){
		return assetManager;
	}
	public static GameExecutor getGameExecutor(){
		return gameExecutor;
	}
	public static EventsDeliverer getEventsDeliverer(){
		return eventsDeliverer;
	}
	public static Point getMousePosition(){
		return mousePosition.getPosition();
	}
	public static void addKeyDownHandler(KeyDownHandler h){
		eventsDeliverer.addKeyDownHandler(h);
	}
	public static void addKeyUpHandler(KeyUpHandler h){
		eventsDeliverer.addKeyUpHandler(h);
	}
	public static void addMouseMoveHandler(MouseMoveHandler h){
		eventsDeliverer.addMouseMoveHandler(h);
	}
	public static void addMouseDownHandler(MouseDownHandler h){
		eventsDeliverer.addMouseDownHandler(h);
	}
	public static void addMouseUpHandler(MouseUpHandler h){
		eventsDeliverer.addMouseUpHandler(h);
	}
	public static void addKeyPressHandler(KeyPressHandler h){
		canvas.addKeyPressHandler(h);
	}
	public static void addClickHandler(ClickHandler h){
		eventsDeliverer.addClickHandler(h);
	}
	public static void addMouseOutHandler(MouseOutHandler h){
		eventsDeliverer.addMouseOutHandler(h);
	}
	public static Canvas getCanvas(){
		return canvas;
	}
	private static void setupCanvas(){
		canvas.setPixelSize(game.getWidth(),game.getHeight());
		canvas.setCoordinateSpaceWidth(game.getWidth());
		canvas.setCoordinateSpaceHeight(game.getHeight());
		canvas.addStyleName("canvas");
		
		VerticalPanel vp=new VerticalPanel();
		vp.addStyleName("center");
		vp.add(canvas);
		
		RootPanel.get("content").add(vp);
		
		hideDraggingCursor(canvas.getCanvasElement());
	}
	private static native void hideDraggingCursor(Element e) /*-{ 
		e.onselectstart = function() { 
    		return false; 
		} 
		e = null; 
	}-*/;
	
}
