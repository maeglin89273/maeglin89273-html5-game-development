/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.game;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * @author Maeglin Liao
 *
 */
public interface HasGameLoop {
	public abstract void update();
	public abstract void draw(Context2d context);
}
