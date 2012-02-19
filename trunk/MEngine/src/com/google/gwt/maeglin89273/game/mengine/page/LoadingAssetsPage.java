/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.mengine.core.AssetManager.DataLoadedListener;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;


/**
 * @author Maeglin Liao
 *
 */
public abstract class LoadingAssetsPage extends SinglePage implements DataLoadedListener{
	protected LoadingAssetsPage(Page nextPage) {
		super(nextPage);
		MEngine.getAssetManager().addDataLoadedListener(this);
	}
	@Override
	public void onClick(ClickEvent e){
		return;
	}
	@Override
	public void regHandlers() {
		return;
		
	}
	@Override
	public void done() {
		toNextPage();
	}
}
