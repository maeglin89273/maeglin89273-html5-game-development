/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.page;

import org.jbox2d.common.Vec2;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.maeglin89273.game.mengine.core.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.GamePage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.utility.CoordinateConverter;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.button.key.CreativeKey;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.dot.CircleBombDot;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.dot.RectangleBombDot;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.Circle;

/**
 * @author Maeglin Liao
 *
 */
public class TestForceGamePage extends GamePage implements MouseDownHandler {
	private PhysicalWorld world;
	/**
	 * @param game
	 */
	public TestForceGamePage(GeneralGame game) {
		super(game);
		world=new PhysicalWorld(game.getWidth(), game.getHeight(), new Vec2(0,-10));
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.MouseDownHandler#onMouseDown(com.google.gwt.event.dom.client.MouseDownEvent)
	 */
	@Override
	public void onMouseDown(MouseDownEvent event) {
		switch(event.getNativeButton()){
			case NativeEvent.BUTTON_LEFT:
				new CircleBombDot(MEngine.getMousePosition(),world);
				break;
			
			case NativeEvent.BUTTON_RIGHT:
				new RectangleBombDot(MEngine.getMousePosition(),world);
		}
		
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#initHandlers()
	 */
	@Override
	public void initHandlers() {
		MEngine.addMouseDownHandler(this);

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#update()
	 */
	@Override
	public void update() {
		world.update();

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		world.draw(context);

	}

}
