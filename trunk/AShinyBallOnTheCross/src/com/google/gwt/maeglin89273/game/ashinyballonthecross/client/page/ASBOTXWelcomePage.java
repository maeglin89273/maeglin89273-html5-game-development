/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.LocalPlayer;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Circle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Polygon;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Rectangle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.Glass;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.IDEnteredBoard;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.IDEnteredBoard.CloseTask;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.LoginButtonAndIDDisplay;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.LoginButtonAndIDDisplay.AppendProgress;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginResponse;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.CircleButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.ComponentLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.ImageLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXWelcomePage extends GeneralPage implements MouseDownHandler,MouseUpHandler, KeyPressHandler,KeyDownHandler{
	
	
	private boolean mousePressed=false;
	
	private Creator creator;
	private GroupLayer layers;
	
	private GameButton[] buttons=new GameButton[3];
	
	private LoginButtonAndIDDisplay loginButton=null;
	
	private IDEnteredBoard board;
	private boolean blocked;
	@Override
	public void onClick(ClickEvent e) {
		Point p=MEngine.getMousePosition();
		if(blocked){
				board.getButton().onClick(p);
		}else{
			if(loginButton==null||!loginButton.onClick(p)){
				for(int i=0;i<buttons.length&&!buttons[i].onClick(p);i++);
			}	
		}
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#update()
	 */
	@Override
	public void update() {
		if(mousePressed){
			switch(Random.nextInt(3)){
				case 0:
					new Circle(creator,MEngine.getMousePosition());
					break;
				case 1:
					new Rectangle(creator,MEngine.getMousePosition());
					break;
				case 2:
					new Polygon(creator,MEngine.getMousePosition());
					
			}
		}
		layers.update();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		layers.draw(context);
		
	}
	@Override
	public void onMouseUp(MouseUpEvent event) {
		this.mousePressed=false;
		
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		if(blocked){
			return ;
		}
		Point p=MEngine.getMousePosition();
		boolean result=true;
		for(GameButton button:buttons){
			result&=!button.contains(p);
		}
		if(result){
			this.mousePressed=true;
		}
	}
	@Override
	public void onKeyPress(KeyPressEvent event) {
		if(blocked){
			board.onKeyPress(event);
		}
	}
	@Override
	public void onKeyDown(KeyDownEvent event) {
		if(blocked){
			board.onKeyDown(event);
		}
		
	}
	@Override
	public void regHandlers(){
		super.regHandlers();
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseUpHandler(this);
		MEngine.addKeyPressHandler(this);
		MEngine.addKeyDownHandler(this);
	}
	@Override
	public void onScreen() {
		
		final ASBOTXGame game=(ASBOTXGame)getGame();
		
		this.buttons[0]=new StartButton(new Point(getGameWidth()/2.0,getGameHeight()/2.0+25));
		this.buttons[1]=new InformationButton(new Point(55,getGameHeight()-55));
		Point tP;
		GameLabel tL=null;
		if(game.getLocalPlayer().isTutorialReaded()){
			tP=new Point(55,getGameHeight()-160);
		}else{
			tP=new Point(getGameWidth()/2.0,getGameHeight()/2-85);
			tL=new GameLabel(new Point(getGameWidth()/2.0,getGameHeight()/2-150), TextAlign.CENTER, TextBaseline.MIDDLE, "First Play? Read The Tutorial First", ASBOTXConfigs.Color.RED,ASBOTXConfigs.getCGBoldFont(20));
		}
		this.buttons[2]=new TutorialButton(tP);
		this.creator=new Creator(new Level(new Point(getGameWidth()/2,getGameHeight()/2),MEngine.getAssetManager().getJson("levels/welcome_level.json")));
		creator.build(new GameOverCallback(){

			@Override
			public void showScore(int score) {
				ASBOTXWelcomePage.this.buttons[0].doTask();				
			}
			
		});
		
		LoginInfo info=game.getLoginInfo();
		
		layers=new GroupLayer();
		if(tL!=null){
			layers.addComponentOnLayer(tL);
		}
		for(GameButton button:buttons){
			layers.addComponentOnLayer(button);
		}
		
		
		if(info.isConnectionSuccess()){
			
			
			loginButton=new LoginButtonAndIDDisplay(new Point(getGameWidth()-50,getGameHeight()-50),
					new Point(getGameWidth()/2,getGameHeight()-12),TextAlign.CENTER,100,ASBOTXConfigs.Color.GRAY,
					info.getUrl(),info.getStatus()==LoginInfo.Status.LOGGED_IN?game.getLocalPlayer().getID():"",
					ASBOTXConfigs.Utility.switchStatus(info.getStatus()),new AppendProgress(){
						@Override
						public void execute(CheckLoginResponse response) {
							if(response.getStatus()==CheckLoginResponse.Status.NEW_PLAYER){
								showBoard(game);
							}
							
						}
					});
					
			layers.addComponentOnLayer(loginButton);
			
			if(info.getStatus()==LoginInfo.Status.LOGGED_IN&&game.getLocalPlayer().getID()==null){
				showBoard(game);
			}
		}
		
		layers.addLayer(new ImageLayer(MEngine.getAssetManager().getSpriteSheet("images/welcome_bg.png"), new Point(0,0), getGameWidth(), getGameHeight()));
		layers.addComponentOnLayer(new GameLabel(new Point(getGameWidth()-5,3), TextAlign.RIGHT, TextBaseline.TOP, ASBOTXConfigs.VERSION, ASBOTXConfigs.Color.TEXT, ASBOTXConfigs.getCGFont(12)));
		layers.addComponentOnLayer(creator.getWorld());
	}
	
	private void showBoard(final ASBOTXGame game){
		board=new IDEnteredBoard(new Point(getGameWidth()/2,getGameHeight()/2),
				new CloseTask(){

					@Override
					public void doTask() {
						layers.removeLayer(1);
						layers.removeLayer(0);
						blocked=false;
						loginButton.setID(game.getLocalPlayer().getID());
						board=null;
					}
			
		});
		layers.insertLayer(0, new ComponentLayer(new Glass(getGameWidth(),getGameHeight())));
		layers.insertLayer(0,new ComponentLayer(board));
		blocked=true;
	}
	private class StartButton extends BoxButton{
		
		public StartButton(Point p){
			super(p,250,100,new SpriteBlock(0,2*(200+SpriteBlock.SPACING),500,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
			
		}
		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.gamecomponent.CanvasButton#doTask()
		 */
		@Override
		public void doTask() {
			getGame().setPage(new ASBOTXLevelSelectPage());
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#update()
		 */
		@Override
		public void update() {
			// TODO Auto-generated method stub
		}
	}
	private class TutorialButton extends CircleButton{

		public TutorialButton(Point p) {
			super(p, 50,new SpriteBlock(5*(200+SpriteBlock.SPACING),200+SpriteBlock.SPACING,200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		}

		@Override
		public void doTask() {
			LocalPlayer localPlayer=((ASBOTXGame)getGame()).getLocalPlayer();
			if(!localPlayer.isTutorialReaded()){
				localPlayer.setTutorialReaded(true);
			}
			getGame().setPage(new ASBOTXTutorialPage());
			
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class InformationButton extends CircleButton{

		public InformationButton(Point p) {
			super(p, 50,new SpriteBlock(4*(200+SpriteBlock.SPACING),200+SpriteBlock.SPACING,
					200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		}

		@Override
		public void doTask() {
			getGame().setPage(new ASBOTXInformationPage());
			
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}
