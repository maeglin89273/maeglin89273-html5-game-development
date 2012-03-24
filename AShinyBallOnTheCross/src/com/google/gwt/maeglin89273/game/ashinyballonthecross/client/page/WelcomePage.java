/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.Circle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.Polygon;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.Rectangle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.CircleButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.ImageLayer;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class WelcomePage extends HasLoginButtonPage implements MouseDownHandler,MouseUpHandler, KeyPressHandler,KeyDownHandler{
	
	
	
	private boolean mousePressed=false;
	
	private Creator creator;
	
	private GameButton[] buttons=new GameButton[3];
	
	public WelcomePage() {
		super(new Point(getGameWidth()-50,getGameHeight()-50),
			  new Point(getGameWidth()/2,getGameHeight()-12),100,TextAlign.CENTER,ASBOTXConfigs.Color.GRAY);
	}
	@Override
	protected void progressFinished() {
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#update()
	 */
	@Override
	public void update() {
		if(mousePressed){
			switch(Random.nextInt(3)){
				case 0:
					new Circle(MEngine.getMousePosition());
					break;
				case 1:
					new Rectangle(MEngine.getMousePosition());
					break;
				case 2:
					new Polygon(MEngine.getMousePosition());
					
			}
		}
		super.update();
	}
	@Override
	public void onClick(Point p) {
		for(int i=0;i<buttons.length&&!buttons[i].onClick(p);i++);
	}
	
	@Override
	public void onMouseUp(MouseUpEvent event) {
		this.mousePressed=false;
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		if(isBlocked()){
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
	public void regHandlers(){
		super.regHandlers();
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseUpHandler(this);
		
	}
	@Override
	public void onScreen() {
		this.buttons[0]=new StartButton(new Point(getGameWidth()/2.0,getGameHeight()/2.0));
		this.buttons[1]=new InformationButton(new Point(55,getGameHeight()-55));
		this.buttons[2]=new GuideButton(new Point(55,getGameHeight()-160));
		
		this.creator=new Creator(new Level(MEngine.getAssetManager().getJson("levels/welcome_level.json"),new Point(getGameWidth()/2,getGameHeight()/2)));
		creator.build(new GameOverCallback(){

			@Override
			public void showScore(int score) {
				WelcomePage.this.buttons[0].doTask();				
			}
			
		});
		
		for(GameButton button:buttons){
			root.addComponentOnLayer(button);
		}
			
		root.addLayer(new ImageLayer(MEngine.getAssetManager().getSpriteSheet("images/welcome_bg.png"), new Point(0,0), getGameWidth(), getGameHeight()));
		root.addComponentOnLayer(new GameLabel(new Point(getGameWidth()-5,3), TextAlign.RIGHT, TextBaseline.TOP, ASBOTXConfigs.VERSION, ASBOTXConfigs.Color.TEXT, ASBOTXConfigs.getCGFont(12)));
		root.addComponentOnLayer(creator.getWorld());
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
			getGame().setPage(new WorldSelectPage());
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#update()
		 */
		@Override
		public void update() {
			// TODO Auto-generated method stub
		}
	}
	private class GuideButton extends CircleButton{

		public GuideButton(Point p) {
			super(p, 50,new SpriteBlock(5*(200+SpriteBlock.SPACING),200+SpriteBlock.SPACING,200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		}

		@Override
		public void doTask() {
			getGame().setPage(new GuidePage());
			
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
			getGame().setPage(new InformationPage());
			
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
