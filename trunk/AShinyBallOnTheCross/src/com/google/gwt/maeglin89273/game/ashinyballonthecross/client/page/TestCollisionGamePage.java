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
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.WorldLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GamePage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.timer.SchedulingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;

/**
 * @author Maeglin Liao
 *
 */
public class TestCollisionGamePage extends GamePage implements MouseDownHandler,MouseWheelHandler {
	private Creator creator;
	private Cross cross;
	private WorldLayer worldLayer;
	/**
	 * @param game
	 */
	public TestCollisionGamePage(GeneralGame game) {
		super(game);
		
		//creator=new Creator();
		worldLayer=new WorldLayer(creator.getWorld(), 2.5f);
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.MouseDownHandler#onMouseDown(com.google.gwt.event.dom.client.MouseDownEvent)
	 */
	@Override
	public void onMouseDown(MouseDownEvent event) {
		
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
		worldLayer.update();
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		worldLayer.draw(context);
		
	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		if(event.isNorth())
			worldLayer.getCamera().zoomIn();
		if(event.isSouth())
			worldLayer.getCamera().zoomOut();
		
	}

	@Override
	public void onScreen() {
		int widthCenter=getGameWidth()/2;
		/*new BreakableLine(creator,new Point(widthCenter-100,125), new Point(widthCenter+100,125));
		final ElasticLine line=new ElasticLine(creator, new Point(widthCenter-100,150), new Point(widthCenter+100,150));*/
		new ShinyBall(creator, new Point(widthCenter,175));
		//new ElasticLine(creator, new Point(widthCenter-100,400), new Point(widthCenter+100,400));
		//new SimpleStaticLine(creator, new Point(0,450), new Point(getGameWidth(),450));
		/*SchedulingTimer timer=new SchedulingTimer(new TimerTask(){

			@Override
			public void doTask() {
				line.destroy();
				
			}
			
		},13000);
		 timer.start();*/
		//new BreakableLine(creator,new Point(widthCenter-100,400), new Point(widthCenter+100,400));
		
	}
	
}
