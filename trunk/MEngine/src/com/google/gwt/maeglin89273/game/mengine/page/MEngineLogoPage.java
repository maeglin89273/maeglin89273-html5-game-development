/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.timer.SchedulingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;

/**
 * @author Maeglin Liao
 *
 */
public class MEngineLogoPage extends FadingPage {
	
	public MEngineLogoPage(GeneralGame game, Page nextPage) {
		
			super(game,MEngine.getAssetsManager().getSpriteSheet("MEngine_logo.png"),
					Math.min(game.getWidth(), game.getHeight()),
					Math.min(game.getWidth(), game.getHeight()),
					nextPage);
	}
	
}
