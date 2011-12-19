/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.NativeEvent;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.*;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.SimpleStaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.CreatorPanel;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.CreatorPropertiesBar;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.key.CreativeKey;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.GamePage;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;
import com.google.gwt.maeglin89273.game.mengine.layer.*;
import com.google.gwt.user.client.Window;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTCGamePage extends GamePage implements MouseDownHandler,MouseUpHandler,KeyDownHandler,KeyUpHandler,MouseOutHandler,MouseWheelHandler{
	
	private final Creator creator;
	private final LevelContext level;
	
	private final GravityIndicator gravityIndicator;
	private final CreatorPanel creatorPanel;
	
	private GroupLayer rootLayer;
	private WorldLayer worldLayer;
	private Camera camera;
	
	private final ASBOTCPausePage pausePage;
	
	private Point grabPos=null;
	public ASBOTCGamePage(GeneralGame game,LevelContext level){
		super(game);
		
		this.pausePage=new ASBOTCPausePage(this.game,this);
		this.level=level;
		this.creator=new Creator(level);
		
		//initialize UIs
		gravityIndicator=new GravityIndicator(creator.getWorld().getPosition(),250,30,level.getGravityAngleInDegrees());
		
		CreativeKey.setSketchersFactory(new DefinersFactory(creator));
		CreatorPropertiesBar creatorPropertiesBar=new CreatorPropertiesBar(getGameWidth(),getGameHeight(),creator.getMaxPower());
				
		creatorPanel=new CreatorPanel(creator,getGameWidth(), getGameHeight());
		
		//add listeners
		gravityIndicator.addGravityChangeListener(creator.getWorld());
		creator.addPropertiesChangeListener(creatorPropertiesBar);
		creatorPanel.setDefiningListener(creatorPropertiesBar);
		//initialize layers
		worldLayer=new WorldLayer(creator.getWorld(),level.getCameraViewPoint(),2,0.45f);
		camera=worldLayer.getCamera();
		worldLayer.addLayer(0,new Layer(){

			@Override
			public void update() {
				creatorPanel.updatePenPosition(camera.ConvertToWorldPosition(MEngine.getMousePosition()));
			}

			@Override
			public void draw(Context2d context) {
				creatorPanel.sketch(context);
				
			}
			
		});
		worldLayer.addLayer(new ComponentLayer(gravityIndicator));	
		
		
		rootLayer=new GroupLayer();
		rootLayer.addLayer(new ComponentLayer(creatorPanel));
		rootLayer.addLayer(new ComponentLayer(creatorPropertiesBar));
		rootLayer.addLayer(worldLayer);
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
			case ASBOTCConfigurations.KeysConfiguration.AREA:
				creatorPanel.onKeyChange(CreatorPanel.AREA_KEY_INDEX,false);
				break;
			case ASBOTCConfigurations.KeysConfiguration.LINE:
				creatorPanel.onKeyChange(CreatorPanel.LINE_KEY_INDEX,false);
				break;
			case ASBOTCConfigurations.KeysConfiguration.DOT:
				creatorPanel.onKeyChange(CreatorPanel.DOT_KEY_INDEX,false);
				break;
		}
		
	}

	
	@Override
	public void onKeyDown(KeyDownEvent event) {
		switch(event.getNativeKeyCode()){
		case ASBOTCConfigurations.KeysConfiguration.AREA:
			creatorPanel.onKeyChange(CreatorPanel.AREA_KEY_INDEX,true);
			break;
		case ASBOTCConfigurations.KeysConfiguration.LINE:
			creatorPanel.onKeyChange(CreatorPanel.LINE_KEY_INDEX,true);
			break;
		case ASBOTCConfigurations.KeysConfiguration.DOT:
			creatorPanel.onKeyChange(CreatorPanel.DOT_KEY_INDEX,true);
			break;
		case ASBOTCConfigurations.KeysConfiguration.REMOVE_CREATION:
			if(event.isControlKeyDown()){
				creator.removeLastCreation();
			}
			if(event.isAltKeyDown()){
				creator.removeFirstCreation();
			}
			break;
		case ASBOTCConfigurations.KeysConfiguration.RESET_LEVEL:
			game.setPage(new ASBOTCGamePage(game,level));
			break;
		case ASBOTCConfigurations.KeysConfiguration.RETURN:
			game.setPage(new ASBOTCLevelSelectPage(game));
			
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
		game.setPage(pausePage);
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
		rootLayer.update();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		rootLayer.draw(context);
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
		creator.build(new GameOverCallback(){

			@Override
			public void showScore(Creator c) {
				Window.alert("Score="+c.getScore());
				game.setPage(new ASBOTCLevelSelectPage(game));
			}
			
		});
	}
}
