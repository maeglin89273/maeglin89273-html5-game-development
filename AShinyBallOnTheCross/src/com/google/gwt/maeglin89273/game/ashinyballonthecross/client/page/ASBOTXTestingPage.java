/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;


import org.jbox2d.common.Vec2;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.Circle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.WorldLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GamePage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXTestingPage extends GamePage implements MouseDownHandler,MouseUpHandler,MouseWheelHandler {
	private Creator creator;
	private WorldLayer worldLayer;
	
	private boolean mousePressed=false;

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.MouseDownHandler#onMouseDown(com.google.gwt.event.dom.client.MouseDownEvent)
	 */
	@Override
	public void onMouseDown(MouseDownEvent event) {
		this.mousePressed=true;
		
	}
	@Override
	public void onMouseUp(MouseUpEvent event) {
		this.mousePressed=false;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#initHandlers()
	 */
	@Override
	public void regHandlers() {
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseWheelHandler(this);
		MEngine.addMouseUpHandler(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#update()
	 */
	@Override
	public void update() {
		if(mousePressed){
			PhysicalShape shape=null;
			/*switch(Random.nextInt(3)){
				case 0:*/
					shape=new Circle(MEngine.getMousePosition());
					/*break;
				case 1:
					shape=new Rectangle(MEngine.getMousePosition());
					break;
				case 2:
					shape=new Polygon(MEngine.getMousePosition());
					
			}*/
			shape.getBody().applyLinearImpulse(new Vec2(0,-1), shape.getBody().getWorldCenter());
		}
		
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
		
	}

	@Override
	public void onScreen() {
		Point center=new Point(getGameWidth()/2,getGameHeight()/2);
		this.creator=new Creator(new Level(MEngine.getAssetManager().getJson("levels/testing.json"),center));
		creator.build(new GameOverCallback(){

			@Override
			public void showScore(int score) {
								
			}
			
		});
		worldLayer=new WorldLayer(creator.getWorld(),center,1,1);
	}

	
	
}
