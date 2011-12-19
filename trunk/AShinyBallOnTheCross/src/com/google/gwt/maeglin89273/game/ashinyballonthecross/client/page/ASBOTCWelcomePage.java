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
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.StartButton;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.ComponentLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTCWelcomePage extends GeneralPage implements MouseDownHandler,MouseUpHandler{
	
	private boolean mousePressed=false;
	
	private Creator creator;
	private GroupLayer layers;
	StartButton startButton;
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.GeneralPage#onClick(com.google.gwt.maeglin89273.game.mengine.utility.physics.Point)
	 */
	
	public ASBOTCWelcomePage(GeneralGame game) {
		super(game);
		
	}
	
	@Override
	public void onClick(ClickEvent e) {
		if(startButton.contain(MEngine.getMousePosition())){
			startButton.doTask();
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
	public void regHandlers(){
		super.regHandlers();
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseUpHandler(this);
	}
	@Override
	public void onScreen() {
		this.startButton=new StartButton(game,new Point(getGameWidth()/2.0,getGameHeight()/2.0));
		this.creator=new Creator(getGameWidth(),getGameHeight());
		layers=new GroupLayer();
		layers.addLayer(new ComponentLayer(startButton));
		layers.addLayer(new ComponentLayer(creator.getWorld()));
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		this.mousePressed=false;
		
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		this.mousePressed=true;
		
	}
	
}
