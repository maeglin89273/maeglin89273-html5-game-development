/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.core;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;

/**
 * @author Liao
 *
 */
public interface Game {

	public abstract void init();
	public abstract void update();
	public abstract void draw(Context2d context);
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract SpriteSheet[] getGameSpriteSheets();
	public abstract boolean hasLoadingResourcesPage();
	
}
