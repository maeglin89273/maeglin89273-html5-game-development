/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.mengine.core.AssetsManager.DataLoadedListener;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;



/**
 * @author Maeglin Liao
 *
 */
public abstract class LoadingResourcesPage extends SinglePage implements DataLoadedListener{
	protected LoadingResourcesPage(GeneralGame game,Page nextPage) {
		super(game, nextPage);
		MEngine.getAssetsManager().addDataLoadedListener(this);
	}
	@Override
	public void onClick(ClickEvent e){
		return;
	}
	@Override
	public void initHandlers() {
		return;
		
	}
	@Override
	public void onScreen(){
		return;
	}
	@Override
	public void done() {
		this.toNextPage();
		
	}
}
