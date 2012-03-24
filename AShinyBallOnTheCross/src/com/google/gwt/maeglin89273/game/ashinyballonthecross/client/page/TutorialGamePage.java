/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;


import java.util.Date;

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
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.LocalPlayer;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.area.ArrowArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.area.GravitationalArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.SimpleStaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.CreatorPanel;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.CreatorPropertiesBar;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.GravityIndicator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.TaskHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.TutorialManager;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component.TasksList;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.WorldType;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.Camera;
import com.google.gwt.maeglin89273.game.mengine.layer.ComponentLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.Layer;
import com.google.gwt.maeglin89273.game.mengine.layer.WorldLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GamePage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Maeglin Liao
 *
 */
public class TutorialGamePage extends GamePage implements MouseDownHandler,
MouseUpHandler,KeyDownHandler,KeyUpHandler,MouseWheelHandler {
	private TutorialManager manager;
	
	private Creator creator;
	private Level level;
	
	private GravityIndicator gravityIndicator;
	private CreatorPanel creatorPanel;
	
	private WorldLayer worldLayer;
	private Camera camera;
	
	private Point grabPos=null;
	private boolean inited=false;
	
	private static final TaskHandler[] TASK_HANDLERS=new TaskHandler[8];
	
	static{
		TaskHandler doNothingHandler=new TaskHandler(){

			@Override
			public boolean handleTask(Object o,TutorialManager manager) {
				return true;
			}
			
		};
		TASK_HANDLERS[0]=doNothingHandler;
		TASK_HANDLERS[1]=new TaskHandler(){
		
			private boolean zoomIn=false;
			private boolean zoomOut=false;
			@Override
			public boolean handleTask(Object o,TutorialManager manager) {
				TasksList list=manager.getTasksList();
				if(o instanceof String&&!list.isChecked(1)){
					if("zoom in".equals(o)){
						zoomIn=true;
					}else{
						zoomOut=true;
					}
					if(zoomIn&&zoomOut){
						list.check(1);
					}
				}else if(o instanceof Vector&&((Vector)o).getSquare()>100){
					list.check(0);
				}
				return list.isChecked(0)&&list.isChecked(1);
			}
			
		};
		TASK_HANDLERS[2]=doNothingHandler;
		TASK_HANDLERS[3]=new TaskHandler(){
			
			@Override
			public boolean handleTask(Object o, TutorialManager manager) {
				TasksList list=manager.getTasksList();
				if(o instanceof SimpleStaticLine){
					list.check(0);
				}else if(o instanceof ArrowArea){
					list.check(1);
				}else if(o instanceof GravitationalArea){
					list.check(2);
				}
				return list.isChecked(0)&&list.isChecked(1)&&list.isChecked(2);
			}
			
		};
		TASK_HANDLERS[4]=doNothingHandler;
		TASK_HANDLERS[5]=new TaskHandler(){
			
			@Override
			public boolean handleTask(Object o, TutorialManager manager) {
				TasksList list=manager.getTasksList();
				if("remove last".equals(o)){
					list.check(0);
				}else{
					list.check(1);
				}
				return list.isChecked(0)&&list.isChecked(1);
			}
			
		};
		TASK_HANDLERS[6]=new TaskHandler(){
			
			@Override
			public boolean handleTask(Object o, TutorialManager manager) {
				manager.getTasksList().check(0);
				return true;
			}
			
		};
		TASK_HANDLERS[7]=new TaskHandler(){
			
			@Override
			public boolean handleTask(Object o, TutorialManager manager) {
				ASBOTXGame game=(ASBOTXGame)getGame();
				final LocalPlayer player=game.getLocalPlayer();
				if(!player.isLevelUnlocked(WorldType.INTRO, 1)){
					player.setScoreAt(WorldType.INTRO, 1, 0);
					player.save();
					
					//update the online achievement if the localPlayer has logged in
					if(game.getLoginInfo().getStatus()==LoginInfo.Status.LOGGED_IN){
						game.getPlayerService().saveAchievements(player.getPlayer(),
						new AsyncCallback<Date>(){
			
							@Override
							public void onFailure(Throwable caught) {
								ASBOTXConfigs.Utility.alertError(caught);
							}
			
							@Override
							public void onSuccess(Date result) {
								player.setTimestamp(result);
							}
							
						});
					}
				}
				return true;
			}
			
		};
	}
	
	
	@Override
	public void onMouseDown(MouseDownEvent event) {
		switch(event.getNativeButton()){
			case NativeEvent.BUTTON_LEFT:
				Point mP=MEngine.getMousePosition();
				
				//return because the next step button is clicked
				if(manager.getButton().onClick(mP)){
					return;
				}
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
			//detect step 3
			manager.getDetector().detect(3, creatorPanel.onPenUp(camera.ConvertToWorldPosition(mP)));
			if(!creatorPanel.isAnyKeyPressed()&&grabPos!=null){
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
				//detect step 5
				if(creator.removeLastCreation()!=null){
					manager.getDetector().detect(5, "remove last");
				}
			}
			if(event.isAltKeyDown()){
				//detect step 5
				if(creator.removeFirstCreation()!=null){
					manager.getDetector().detect(5,"remove first");
				}
			}
			break;
		case ASBOTXConfigs.KeysConfiguration.RESET_LEVEL:
			manager.getDetector().detect(7,null);
			getGame().setPage(new TutorialGamePage());
			break;
		case ASBOTXConfigs.KeysConfiguration.RETURN:
			//detect step 7
			manager.getDetector().detect(7,null);
			getGame().setPage(new LevelSelectPage(WorldType.INTRO));
			
		}
	}
	
	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		//check whether it is switching or not
		if(event.isNorth()){
			creatorPanel.previous();
			if(!creatorPanel.isAnyKeyPressed()){
				worldLayer.getCamera().zoomIn();
				
				//detect step 1
				manager.getDetector().detect(1, "zoom in");
			}
		}else{
			creatorPanel.next();
			if(!creatorPanel.isAnyKeyPressed()){
				worldLayer.getCamera().zoomOut();
				
				//detect step 1
				manager.getDetector().detect(1, "zoom out");
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#update()
	 */
	@Override
	public void update() {
		if(grabPos!=null){
			Point mP=MEngine.getMousePosition();
			Vector v=mP.delta(grabPos);
			worldLayer.getCamera().move(v, true);
			grabPos.setPosition(mP);
			
			//detect step 1
			manager.getDetector().detect(1, v);
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
	}
	@Override
	public void onScreen(){
		if(inited){
			return;
		}
		
		//init here
		manager=new TutorialManager(MEngine.getAssetManager().getJson("configs/tutorial_configs.json"),
				getGameWidth(),getGameHeight(),2,TASK_HANDLERS);
		this.level=new Level(MEngine.getAssetManager().getJson("levels/tutorial_level.json"),
		 new Point(getGameWidth()/2.0,getGameHeight()/2.0));
		this.creator=new Creator(level);
		
		creator.build(new GameOverCallback(){

			@Override
			public void showScore(int score) {
				//detect step 6
				manager.getDetector().detect(6, null);
			}
			
		});
		//initialize UIs
		gravityIndicator=new GravityIndicator(creator.getWorld().getPosition(),250,creator.getGravityController());
				
		CreatorPropertiesBar creatorPropertiesBar=new CreatorPropertiesBar(getGameWidth(),
																			getGameHeight(),
																			creator.getMaxPower(),
																			((ASBOTXGame)getGame()).getLocalPlayer().getScoreAt(level),
																			level.getRequiredScore());
																			
						
		creatorPanel=new CreatorPanel(creator,getGameWidth(), getGameHeight());
				
		//add listeners
		creator.addPropertiesChangeListener(creatorPropertiesBar);
		creatorPanel.addDefiningListener(creatorPropertiesBar);
		
		//initialize layers
		worldLayer=new WorldLayer(creator.getWorld(),level.getCameraViewPoint(),2,0.45f);
		camera=worldLayer.getCamera();
		worldLayer.insertLayer(0, new ComponentLayer(manager.getBlueMark(0)));
		worldLayer.insertLayer(1,new Layer(){

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
		
		GroupLayer tutorialRoot=new GroupLayer();
		tutorialRoot.addComponentOnLayer(manager.getStepBoard());
		tutorialRoot.addComponentOnLayer(manager.getBlueMark(1));
		tutorialRoot.addComponentOnLayer(manager.getTasksList());
		tutorialRoot.addComponentOnLayer(manager.getButton());
		
		rootLayer.addLayer(tutorialRoot);
		
		rootLayer.addComponentOnLayer(creatorPanel);
		rootLayer.addComponentOnLayer(creatorPropertiesBar);
		rootLayer.addLayer(worldLayer);
		
		manager.startTutorial();
		inited=true;
	}
	
}
