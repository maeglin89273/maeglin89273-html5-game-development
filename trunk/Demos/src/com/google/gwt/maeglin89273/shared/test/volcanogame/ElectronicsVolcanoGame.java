/**
 * 
 */
package com.google.gwt.maeglin89273.shared.test.volcanogame;

import com.google.gwt.canvas.dom.client.Context2d;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;



import com.google.gwt.maeglin89273.mengine.core.Game;
import com.google.gwt.maeglin89273.mengine.core.MEngine;
import com.google.gwt.maeglin89273.mengine.sprite.SpriteSheet;
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
	@Override
	public void init() {
		volcanoWorld=new VolcanoWorld(getWidth(),getHeight());
		
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
		volcanoWorld.update();

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.core.Game#draw(com.google.gwt.canvas.dom.client.Context2d, double)
	 */
	@Override
	public void draw(Context2d context) {
		volcanoWorld.draw(context);

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
