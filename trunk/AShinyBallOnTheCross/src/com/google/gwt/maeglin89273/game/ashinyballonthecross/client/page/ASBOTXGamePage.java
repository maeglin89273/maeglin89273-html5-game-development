/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.NativeEvent;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.*;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.CreatorPanel;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.CreatorPropertiesBar;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.GravityIndicator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.key.CreativeKey;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.GamePage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.layer.*;


/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXGamePage extends GamePage implements MouseDownHandler,MouseUpHandler,KeyDownHandler,KeyUpHandler,MouseOutHandler,MouseWheelHandler{
	
	private final Creator creator;
	private final Level level;
	
	private GravityIndicator gravityIndicator;
	private CreatorPanel creatorPanel;
	
	private WorldLayer worldLayer;
	private Camera camera;
	
	private Point grabPos=null;
	private boolean inited=false;
	public ASBOTXGamePage(Level level){
		
		this.level=level;
		this.creator=new Creator(level);
		
	}
	
	@Override
	public void onMouseDown(MouseDownEvent event) {
		switch(event.getNativeButton()){
			case NativeEvent.BUTTON_LEFT:
				Point mP=MEngine.getMousePosition();
				
				//notify the pen is down
				creatorPanel.onPenDown(camera.ConvertToWorldPosition(mP));
				if(!creatorPanel.isAnyKeyPressed())
					grabPos=mP;
				break;
			
			case NativeEvent.BUTTON_RIGHT://cancel sketching
				creatorPanel.resetDefiners();
		}
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		if(event.getNativeButton()==NativeEvent.BUTTON_LEFT){
			Point mP=MEngine.getMousePosition();
			
			//notify the pen is up 
			creatorPanel.onPenUp(camera.ConvertToWorldPosition(mP));
			if(!creatorPanel.isAnyKeyPressed()){
				worldLayer.getCamera().move(mP.delta(grabPos), false);
				grabPos=null;
			}
		}
	}

	
	@Override
	public void onKeyUp(KeyUpEvent event) {
		switch(event.getNativeKeyCode()){
			case ASBOTXConfigs.KeysConfiguration.AREA:
				creatorPanel.onKeyChange(CreatorPanel.AREA_KEY_INDEX,false);
				break;
			case ASBOTXConfigs.KeysConfiguration.LINE:
				creatorPanel.onKeyChange(CreatorPanel.LINE_KEY_INDEX,false);
				break;
			case ASBOTXConfigs.KeysConfiguration.DOT:
				creatorPanel.onKeyChange(CreatorPanel.DOT_KEY_INDEX,false);
				break;
		}
		
	}

	
	@Override
	public void onKeyDown(KeyDownEvent event) {
		switch(event.getNativeKeyCode()){
		case ASBOTXConfigs.KeysConfiguration.AREA:
			creatorPanel.onKeyChange(CreatorPanel.AREA_KEY_INDEX,true);
			break;
		case ASBOTXConfigs.KeysConfiguration.LINE:
			creatorPanel.onKeyChange(CreatorPanel.LINE_KEY_INDEX,true);
			break;
		case ASBOTXConfigs.KeysConfiguration.DOT:
			creatorPanel.onKeyChange(CreatorPanel.DOT_KEY_INDEX,true);
			break;
		case ASBOTXConfigs.KeysConfiguration.REMOVE_CREATION:
			if(event.isControlKeyDown()){
				creator.removeLastCreation();
			}
			if(event.isAltKeyDown()){
				creator.removeFirstCreation();
			}
			break;
		case ASBOTXConfigs.KeysConfiguration.RESET_LEVEL:
			getGame().setPage(new ASBOTXGamePage(level));
			break;
		case ASBOTXConfigs.KeysConfiguration.RETURN:
			getGame().setPage(new ASBOTXLevelSelectPage());
			
		}
	}
	
	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		//check whether it is switching or not
		if(event.isNorth()){
			creatorPanel.previous();
			if(!creatorPanel.isAnyKeyPressed())
				worldLayer.getCamera().zoomIn();
		}else{
			creatorPanel.next();
			if(!creatorPanel.isAnyKeyPressed())
				worldLayer.getCamera().zoomOut();
		}
		
	}
	@Override
	public void onMouseOut(MouseOutEvent event){
		getGame().setPage(new ASBOTXPausePage(this));
	}
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#update()
	 */
	@Override
	public void update() {
		if(grabPos!=null){
			Point mP=MEngine.getMousePosition();
			worldLayer.getCamera().move(mP.delta(grabPos), true);
			grabPos.setPosition(mP);
		}
		creator.restore();
		super.update();
		
	}

	@Override
	public void regHandlers() {
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseUpHandler(this);
		MEngine.addKeyDownHandler(this);
		MEngine.addKeyUpHandler(this);
		MEngine.addMouseWheelHandler(this);
		MEngine.addMouseOutHandler(this);
		
	}
	@Override
	public void onScreen(){
		if(inited){
			return;
		}
		creator.build(new GameOverCallback(){

			@Override
			public void showScore(int score) {
				getGame().setPage(new ASBOTXScoreShowingPage(rootLayer,level,score));
			}
			
		});
		//initialize UIs
		gravityIndicator=new GravityIndicator(creator.getWorld().getPosition(),250,creator.getGravityController());
				
		CreativeKey.setSketchersFactory(new DefinersFactory(creator));
		CreatorPropertiesBar creatorPropertiesBar=new CreatorPropertiesBar(getGameWidth(),
																			getGameHeight(),
																			creator.getMaxPower(),
																			((ASBOTXGame)getGame()).getLocalPlayer().getScoreAt(level),
																			level.getRequiredScore());
																			
						
		creatorPanel=new CreatorPanel(creator,getGameWidth(), getGameHeight());
				
		//add listeners
		creator.addPropertiesChangeListener(creatorPropertiesBar);
		creatorPanel.setDefiningListener(creatorPropertiesBar);
		//initialize layers
		worldLayer=new WorldLayer(creator.getWorld(),level.getCameraViewPoint(),2,0.45f);
		camera=worldLayer.getCamera();
		worldLayer.insertLayer(0,new Layer(){

			@Override
			public void update() {
				creatorPanel.updatePenPosition(camera.ConvertToWorldPosition(MEngine.getMousePosition()));
			}

			@Override
			public void draw(Context2d context) {
				creatorPanel.sketch(context);
						
			}
					
		});
		worldLayer.addComponentOnLayer(gravityIndicator);	
				
				
		rootLayer=new GroupLayer();
		rootLayer.addComponentOnLayer(creatorPanel);
		rootLayer.addComponentOnLayer(creatorPropertiesBar);
		rootLayer.addLayer(worldLayer);
		
		inited=true;
	}
}
