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

import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Maeglin Liao
 *
 */
public class MEngine {
	
	private static Canvas canvas;
	private static Storage storage;
	
	private static GameInfo gameInfo;
	
	private static GameExecutor gameExecutor;
	private static AssetManager assetManager;
	private static MousePosition mousePosition;
	private static HandlersManager handlersManager;
	private static Cipher cipher;
	
	public static void init(Game game,String assetsPrefix){
		
		if(!(Canvas.isSupported()&&Storage.isLocalStorageSupported())){
			RootPanel.get().add(new Anchor("Sorry,your browser doesn't support HTML5.Go get Chrome!",
					"http://chrome.google.com"));
			return;
		}
		canvas=Canvas.createIfSupported();
		storage=Storage.getLocalStorageIfSupported();
		
		gameInfo=game.getGameInfo();
		
		gameExecutor=new GameExecutor(game,canvas);
		assetManager=new AssetManager(assetsPrefix);
		mousePosition=new MousePosition(canvas);
		handlersManager=new HandlersManager(canvas);
		cipher=new Cipher();
		
		assetManager.loadSpriteSheets(gameInfo.getSpriteSheets());
		Camera.setCameraSize(gameInfo.getWidth(), gameInfo.getHeight());
		CoordinateConverter.init(gameInfo.getWidth(), gameInfo.getHeight());
		
		setupCanvas();
		
		game.init();
	}
	public static void setRedrawAlpha(float alpha){
		try{
			gameExecutor.setRedrawAlpha(alpha);
		}catch(NullPointerException e){
			throwsUninitException();
		}
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
		try{
			gameExecutor.start();
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static boolean isPause(){
		try{
			return gameExecutor.isPause();
		}catch(NullPointerException e){
			throwsUninitException();
			return true;
		}
	}
	public static void pause(){
		try{
			gameExecutor.pause();
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static void play(){
		try{
			gameExecutor.play();
		}catch(NullPointerException e){
			throwsUninitException();
		}
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
		try{
			return mousePosition.getPosition();
		}catch(NullPointerException e){
			throwsUninitException();
			return null;
		}
	}
	public static Storage getLocalStorage(){
		return storage;
	}
	public static Cipher getCipher(){
		return cipher;
	}
	
	public static void addKeyDownHandler(KeyDownHandler h){
		try{
			handlersManager.addKeyDownHandler(h);
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static void addKeyUpHandler(KeyUpHandler h){
		try{
			handlersManager.addKeyUpHandler(h);
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static void addMouseMoveHandler(MouseMoveHandler h){
		try{
			handlersManager.addMouseMoveHandler(h);
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static void addMouseDownHandler(MouseDownHandler h){
		try{
			handlersManager.addMouseDownHandler(h);
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static void addMouseUpHandler(MouseUpHandler h){
		try{
			handlersManager.addMouseUpHandler(h);
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static void addMouseWheelHandler(MouseWheelHandler h){
		try{
			handlersManager.addMouseWheelHandler(h);
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static void addKeyPressHandler(KeyPressHandler h){
		try{
			handlersManager.addKeyPressHandler(h);
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static void addClickHandler(ClickHandler h){
		try{
			handlersManager.addClickHandler(h);
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static void addMouseOutHandler(MouseOutHandler h){
		try{
			handlersManager.addMouseOutHandler(h);
		}catch(NullPointerException e){
			throwsUninitException();
		}
	}
	public static Canvas getCanvas(){
		return canvas;
	}
	private static void throwsUninitException(){
		throw new IllegalStateException("The MEngine hasn't been initialized.Please call the init method to initialize it.");
	}
	private static native void hideDraggingCursor(Element e) /*-{ 
		e.onselectstart = function() { 
		return false; 
		};
		e = null; 
	}-*/;
	
}
