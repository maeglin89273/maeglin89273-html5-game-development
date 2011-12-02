package com.google.gwt.maeglin89273.game.mengine.core;



import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;

import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.maeglin89273.game.mengine.game.Game;
import com.google.gwt.maeglin89273.game.mengine.game.GameInfo;
import com.google.gwt.maeglin89273.game.mengine.layer.Camera;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Maeglin Liao
 *
 */
public class MEngine {
	
	private static Canvas canvas;
	
	private static GameInfo gameInfo;
	
	private static GameExecutor gameExecutor;
	private static AssetManager assetManager;
	private static MousePosition mousePosition;
	private static HandlersManager handlersManager;
	public static void init(Game game,String assetsPrefix){
		if(!Canvas.isSupported()){
			RootPanel.get().add(new Label("Sorry,your browser doesn't support HTML5.Go get Chrome!"));
			return;
		}
		canvas=Canvas.createIfSupported();
		
		gameInfo=game.getGameInfo();
		
		gameExecutor=new GameExecutor(game,canvas);
		assetManager=new AssetManager(assetsPrefix);
		mousePosition=new MousePosition(canvas);
		handlersManager=new HandlersManager(canvas);
		
		assetManager.loadSpriteSheets(gameInfo.getSpriteSheets());
		Camera.setCameraSize(gameInfo.getWidth(), gameInfo.getHeight());
		CoordinateConverter.init(gameInfo.getWidth(), gameInfo.getHeight());
		
		setupCanvas();
		
		game.init();
	}
	public static void setRedrawAlpha(float alpha){
		gameExecutor.setRedrawAlpha(alpha);
	}
	private static void setupCanvas(){
		canvas.setPixelSize(gameInfo.getWidth(),gameInfo.getHeight());
		canvas.setCoordinateSpaceWidth(gameInfo.getWidth());
		canvas.setCoordinateSpaceHeight(gameInfo.getHeight());
		canvas.addStyleName("canvas");
		
		canvas.addMouseMoveHandler(mousePosition);//do not use handlersManager to add MouseMoveEventHandler,because the deliverer may remove it.
		canvas.addDomHandler(new ContextMenuHandler(){

			@Override
			public void onContextMenu(ContextMenuEvent event) {
				event.preventDefault();
				event.stopPropagation();
			}
		}, ContextMenuEvent.getType());
		canvas.addMouseWheelHandler(new MouseWheelHandler(){

			@Override
			public void onMouseWheel(MouseWheelEvent event) {
				event.preventDefault();
				event.stopPropagation();
			}
		});
		hideDraggingCursor(canvas.getCanvasElement());
		
		VerticalPanel vp=new VerticalPanel();
		vp.addStyleName("center");
		vp.add(canvas);
		RootPanel.get("content").add(vp);
		Window.scrollTo(canvas.getAbsoluteLeft()+(canvas.getOffsetWidth()-Window.getClientWidth())/2,
						canvas.getAbsoluteTop()+(canvas.getOffsetHeight()-Window.getClientHeight())/2);
		
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
	public static HandlersManager getHandlersManager(){
		return handlersManager;
	}
	public static Point getMousePosition(){
		return mousePosition.getPosition();
	}
	public static void addKeyDownHandler(KeyDownHandler h){
		handlersManager.addKeyDownHandler(h);
	}
	public static void addKeyUpHandler(KeyUpHandler h){
		handlersManager.addKeyUpHandler(h);
	}
	public static void addMouseMoveHandler(MouseMoveHandler h){
		handlersManager.addMouseMoveHandler(h);
	}
	public static void addMouseDownHandler(MouseDownHandler h){
		handlersManager.addMouseDownHandler(h);
	}
	public static void addMouseUpHandler(MouseUpHandler h){
		handlersManager.addMouseUpHandler(h);
	}
	public static void addMouseWheelHandler(MouseWheelHandler h){
		handlersManager.addMouseWheelHandler(h);
	}
	public static void addKeyPressHandler(KeyPressHandler h){
		canvas.addKeyPressHandler(h);
	}
	public static void addClickHandler(ClickHandler h){
		handlersManager.addClickHandler(h);
	}
	public static void addMouseOutHandler(MouseOutHandler h){
		handlersManager.addMouseOutHandler(h);
	}
	public static Canvas getCanvas(){
		return canvas;
	}
	
	private static native void hideDraggingCursor(Element e) /*-{ 
		e.onselectstart = function() { 
		return false; 
		};
		e = null; 
	}-*/;
	
}
