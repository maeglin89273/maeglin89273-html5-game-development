/**
 * 
 */
package com.google.gwt.maeglin89273.shared.test.volcanogame;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;

import com.google.gwt.event.dom.client.*;




import com.google.gwt.maeglin89273.game.mengine.core.Game;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.WorldLayer;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.shared.test.volcanogame.component.VolcanoWorld;


/**
 * @author Maegin Liao
 *
 */
public class ElectronicsVolcanoGame implements Game {

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.core.Game#init()
	 */
	private VolcanoWorld volcanoWorld;

	private WorldLayer layer;

	private Point grabPoint;
	@Override
	public void init() {
		volcanoWorld=new VolcanoWorld(getWidth(),getHeight());
		layer=new WorldLayer(volcanoWorld.getBounds(), 2.5f){

			@Override
			public void updateComponents() {
				volcanoWorld.update();
				
			}

			@Override
			public void drawComponents(Context2d context) {
				volcanoWorld.draw(context);
				
			}
			
		};
		MEngine.addMouseDownHandler(new MouseDownHandler(){

			@Override
			public void onMouseDown(MouseDownEvent event) {
				grabPoint=MEngine.getMousePosition();
				
			}
		});
		
		MEngine.addMouseUpHandler(new MouseUpHandler(){

			@Override
			public void onMouseUp(MouseUpEvent event) {
				layer.getCamera().move(MEngine.getMousePosition().delta(grabPoint), false);
				grabPoint=null;
			}
			
		});
		MEngine.addMouseWheelHandler(new MouseWheelHandler(){

			@Override
			public void onMouseWheel(MouseWheelEvent event) {
				if(event.isNorth())
					layer.getCamera().zoomIn();
				if(event.isSouth())
					layer.getCamera().zoomOut();
			}
			
		});
		MEngine.addKeyDownHandler(new KeyDownHandler(){

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode()==' ')
					volcanoWorld.erupt();
				
			}
			
		});
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.core.Game#update(float)
	 */
	@Override
	public void update() {
		if(grabPoint!=null){
			Point mP=MEngine.getMousePosition();
			layer.getCamera().move(mP.delta(grabPoint), true);
			grabPoint.setPosition(mP);
		}
		layer.update();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.core.Game#draw(com.google.gwt.canvas.dom.client.Context2d, double)
	 */
	@Override
	public void draw(Context2d context) {
		layer.draw(context);
	}

	

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.core.Game#getWidth()
	 */
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 500;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.core.Game#getHeight()
	 */
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 500;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.core.Game#getSpriteSheets()
	 */
	@Override
	public SpriteSheet[] getGameSpriteSheets() {
		// TODO Auto-generated method stub
		return new SpriteSheet[]{
				new SpriteSheet("volcano.png"),
				new SpriteSheet("volcano_background.png"),
				new SpriteSheet("clouds.png"),
		};
	}

	@Override
	public boolean hasLoadingResourcesPage() {
		
		return false;
	}


}
