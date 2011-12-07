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
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.key.CreativeKey;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.pseudo.LevelContext;
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
	
	private GravityIndicator gravityIndicator;
	private Creator creator;
	private LevelContext level;
	private CreatorPanel creatorPanel;
	
	private GroupLayer rootLayer;
	private WorldLayer worldLayer;
	private static Camera camera;
	
	private ASBOTCPausePage pausePage;
	
	private Point grabPos=null;
	public ASBOTCGamePage(GeneralGame game,LevelContext level){
		super(game);
		
		this.pausePage=new ASBOTCPausePage(this.game,this);
		
		creator=new Creator(level);
		
		//initialize UIs
		
		gravityIndicator=new GravityIndicator(creator.getWorld().getPosition(),250,30,creator.getGravityAngleInDegrees());
		
		CreativeKey.setSketchersFactory(new DefinersFactory(creator));
		PowerVolumeBar pb=new PowerVolumeBar(new Point(getGameWidth()-20,getGameHeight()/2),getGameHeight()-100);
		creatorPanel=new CreatorPanel(level,getGameWidth(), getGameHeight());
		
		gravityIndicator.addGravityChangeListener(creator.getWorld());
		creator.addPropertiesChangeListener(pb);
		//initialize layers
		worldLayer=new WorldLayer(creator.getWorld(),2);
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
		rootLayer.addLayer(new ComponentLayer(pb));
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
				creatorPanel.resetSketchers();
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
			case 'A':
				creatorPanel.onKeyChange(CreatorPanel.AREA_KEY_INDEX,false);
				break;
			case 'S':
				creatorPanel.onKeyChange(CreatorPanel.LINE_KEY_INDEX,false);
				break;
			case 'D':
				creatorPanel.onKeyChange(CreatorPanel.DOT_KEY_INDEX,false);
				break;
		}
		
	}

	
	@Override
	public void onKeyDown(KeyDownEvent event) {
		switch(event.getNativeKeyCode()){
		case 'A':
			creatorPanel.onKeyChange(CreatorPanel.AREA_KEY_INDEX,true);
			break;
		case 'S':
			creatorPanel.onKeyChange(CreatorPanel.LINE_KEY_INDEX,true);
			break;
		case 'D':
			creatorPanel.onKeyChange(CreatorPanel.DOT_KEY_INDEX,true);
			break;
		case 'Z':
			if(event.isControlKeyDown()){
				creator.removeLastCreation();
			}
			if(event.isAltKeyDown()){
				creator.removeFirstCreation();
			}
			break;
		case KeyCodes.KEY_ESCAPE:
			game.setPage(new ASBOTCLevelSelectPage(game));
			game=null;
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
