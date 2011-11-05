/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * @author Maeglin Liao
 *
 */
public interface Layer {
	public void update();
	public void draw(Context2d context);
}
