/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;


import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Cross;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.*;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Circle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.DefaultLevel;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.WorldLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GamePage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.timer.SchedulingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;
import com.google.gwt.user.client.Window;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTCTestingPage extends GamePage implements MouseDownHandler,MouseWheelHandler {
	private Creator creator;
	private Cross cross;
	private WorldLayer worldLayer;
	private int hits=0;
	/**
	 * @param game
	 */
	public ASBOTCTestingPage(GeneralGame game) {
		super(game);
		
		/*creator=new Creator(new DefaultLevel(game.getWidth(),game.getHeight()));
		worldLayer=new WorldLayer(creator.getWorld(), 2.5f);*/
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.MouseDownHandler#onMouseDown(com.google.gwt.event.dom.client.MouseDownEvent)
	 */
	@Override
	public void onMouseDown(MouseDownEvent event) {
		//MEngine.play();
		MEngine.getLocalStorage().setItem("testSuccessfully!"+(++hits), "nice!"+hits);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#initHandlers()
	 */
	@Override
	public void regHandlers() {
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseWheelHandler(this);

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#update()
	 */
	@Override
	public void update() {
		//worldLayer.update();
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		//worldLayer.draw(context);
		
	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		/*if(event.isNorth())
			worldLayer.getCamera().zoomIn();
		if(event.isSouth())
			worldLayer.getCamera().zoomOut();
		*/
	}

	@Override
	public void onScreen() {
		/*new ShinyBall(creator, new Point(125,0));
		
		new WoodLine(creator,new Point(100,150),new Point(200,100));*/
		
	}
	
}
