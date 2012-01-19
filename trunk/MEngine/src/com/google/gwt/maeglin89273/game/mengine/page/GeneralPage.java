/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;


/**
 * @author Maeglin Liao
 *
 */
public abstract class GeneralPage extends Page implements ClickHandler{
	
	
	@Override
	public void regHandlers() {
		MEngine.addClickHandler(this);
	}
}
