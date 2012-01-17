/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Circle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Polygon;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Rectangle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.ImageLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXWelcomePage extends GeneralPage implements MouseDownHandler,MouseUpHandler{
	
	private boolean mousePressed=false;
	
	private Creator creator;
	private GroupLayer layers;
	StartButton startButton;
	
	@Override
	public void onClick(ClickEvent e) {
		startButton.onClick(MEngine.getMousePosition());
		
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
	public void regHandlers(){
		super.regHandlers();
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseUpHandler(this);
	}
	@Override
	public void onScreen() {
		this.startButton=new StartButton(new Point(getGameWidth()/2.0,getGameHeight()/2.0));
		
		this.creator=new Creator(new Level(new Point(getGameWidth()/2,getGameHeight()/2),MEngine.getAssetManager().getJson("levels/welcome_level.json")));
		creator.build(new GameOverCallback(){

			@Override
			public void showScore(int score) {
				ASBOTXWelcomePage.this.startButton.doTask();				
			}
			
		});
		layers=new GroupLayer();
		layers.addComponentOnLayer(startButton);
		layers.addComponentOnLayer(creator.getWorld());
		layers.addLayer(new ImageLayer(MEngine.getAssetManager().getSpriteSheet("images/welcome_bg.png"), new Point(0,0), getGameWidth(), getGameHeight()));
		
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		this.mousePressed=false;
		
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		if(!startButton.contains(MEngine.getMousePosition()));
			this.mousePressed=true;
		
	}
	private class StartButton extends BoxButton{
		
		public StartButton(Point p){
			super(p,250,100,new SpriteBlock(0,400+2*SpriteBlock.SPACING,500,200,ASBOTXConfigs.getButtonSpriteSheet()));
			
		}
		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.gamecomponent.CanvasButton#doTask()
		 */
		@Override
		public void doTask() {
			MEngine.getGeneralGame().setPage(new ASBOTXLevelSelectPage());
		}

		

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#update()
		 */
		@Override
		public void update() {
			// TODO Auto-generated method stub

		}
	}
}
