/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.KeyCodes;
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
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.Sketcher;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.SketchersFactory;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.VariableGravityClock;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.SketchersFactory.LineSketcherType;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.button.key.CreativeKey;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.button.key.LineKey;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.mengine.core.GeneralGame;
import com.google.gwt.maeglin89273.mengine.core.MEngine;
import com.google.gwt.maeglin89273.mengine.page.GamePage;
import com.google.gwt.maeglin89273.mengine.physics.Point;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class PhysicsBattleGamePage extends GamePage implements MouseDownHandler,MouseUpHandler,KeyDownHandler,KeyUpHandler,MouseOutHandler{
	private static final int DOT_KEY_INDEX=2;
	private static final int LINE_KEY_INDEX=1;
	private static final int AREA_KEY_INDEX=0;
	
	private CreativeKey[] keys=new CreativeKey[1];
	private PhysicalWorld world;
	private VariableGravityClock gravityClock;
	
	private PhysicsBattlePausePage pausePage;
	public PhysicsBattleGamePage(GeneralGame game){
		super(game);
		
		this.pausePage=new PhysicsBattlePausePage(this.game,this);
		
		gravityClock=new VariableGravityClock(new Point(getGameWidth()/2,getGameHeight()/2),400,10);
		world=new PhysicalWorld(getGameWidth(),getGameHeight(),gravityClock.getGravity());
		CreativeKey.setSketchersFactory(new SketchersFactory(world));
		keys[0]=new LineKey(new Point(125,game.getHeight()-160),LineSketcherType.SHAPES_LINE,LineSketcherType.GRAY_STATIC_LINE,LineSketcherType.BLACK_STATIC_LINE);
		
		gravityClock.addGravityChangeListener(world);
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.utility.page.GamePage#onMouseDown(com.google.gwt.event.dom.client.MouseDownEvent)
	 */
	@Override
	public void onMouseDown(MouseDownEvent event) {
		Point mP=MEngine.getMousePosition();
		for(CreativeKey key:keys){
			if(key.isPressed()){
				key.getSketcher().onPenDown(mP);
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.utility.page.GamePage#onMouseUp(com.google.gwt.event.dom.client.MouseUpEvent)
	 */
	@Override
	public void onMouseUp(MouseUpEvent event) {
		Point mP=MEngine.getMousePosition();
		for(CreativeKey key:keys){
			if(key.isPressed()){
				key.getSketcher().onPenUp(mP);
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.utility.page.GamePage#onKeyUp(com.google.gwt.event.dom.client.KeyUpEvent)
	 */
	@Override
	public void onKeyUp(KeyUpEvent event) {
		keys[0].setPressed(event.isShiftKeyDown());
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.utility.page.GamePage#onKeyDown(com.google.gwt.event.dom.client.KeyDownEvent)
	 */
	@Override
	public void onKeyDown(KeyDownEvent event) {
		keys[0].setPressed(event.isShiftKeyDown());
		if(event.getNativeKeyCode()=='Q'){
			for(CreativeKey key:keys){
				if(key.isPressed()){
					key.prevoius();
				}
			}
		}else if(event.getNativeKeyCode()=='E'){
			for(CreativeKey key:keys){
				if(key.isPressed()){
					key.next();
				}
			}
		}

	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.utility.page.GamePage#onMouseOut(com.google.gwt.event.dom.client.MouseOutEvent)
	 */
	@Override
	public void onMouseOut(MouseOutEvent event){
		game.setPage(pausePage);
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.utility.page.Page#update()
	 */
	@Override
	public void update() {
		Point mP=MEngine.getMousePosition();
		gravityClock.update();
		
		world.update();
		
		for(CreativeKey key:keys){
			key.update();
			key.getSketcher().updatePenPosition(mP);
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.utility.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		gravityClock.draw(context);
		
		world.draw(context);
		
		for(CreativeKey key:keys){
			key.getSketcher().sketch(context);
		}
		for(CreativeKey key:keys){
			key.draw(context);
		}
	}
	
	@Override
	public void initHandlers() {
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseUpHandler(this);
		MEngine.addKeyDownHandler(this);
		MEngine.addKeyUpHandler(this);
		MEngine.addMouseOutHandler(this);
	}
	

}
