/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.GameComponent;

/**
 * @author Maeglin Liao
 *
 */
public class ComponentLayer extends Layer {
	private final GameComponent component;
	public ComponentLayer(GameComponent component){
		this.component = component;
	}
	public GameComponent getComponent(){
		return component;
	}
	
	@Override
	public void update() {
		component.update();
		
	}

	@Override
	public void draw(Context2d context) {
		component.draw(context);
		
	}
}
