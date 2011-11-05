/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * @author Maeglin Liao
 *
 */
public interface HasComponentsLayer extends Layer {
	public void updateComponents();
	public void drawComponents(Context2d context);
	
}
