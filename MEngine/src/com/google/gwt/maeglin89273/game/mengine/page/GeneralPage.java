/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;

/**
 * @author Maeglin Liao
 *
 */
public abstract class GeneralPage extends Page implements ClickHandler{
	
	protected GeneralPage(GeneralGame game) {
		super(game);
		
	}
	
	@Override
	public void regHandlers() {
		MEngine.addClickHandler(this);
	}
}
