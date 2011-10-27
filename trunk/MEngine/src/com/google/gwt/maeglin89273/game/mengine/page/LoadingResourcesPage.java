/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.maeglin89273.game.mengine.core.GeneralGame;


/**
 * @author Maeglin Liao
 *
 */
public abstract class LoadingResourcesPage extends Page {
	protected LoadingResourcesPage(GeneralGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
	public abstract boolean isLoaded();
	@Override
	public void initHandlers() {
		return;
		
	}
}
