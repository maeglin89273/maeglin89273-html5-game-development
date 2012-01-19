/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.game;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.*;


/**
 * @author Maeglin Liao
 *
 */
public abstract class GeneralGame implements Game {
	protected Page page;
	private final GameInfo gameInfo;
	protected GeneralGame(GameInfo info){
		this.gameInfo = info;
		
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.mengine.utility.Game#update(float)
	 */
	@Override
	public void update() {
		page.update();

	}
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.mengine.utility.Game#draw(com.google.gwt.canvas.dom.client.Context2d, double)
	 */
	@Override
	public void draw(Context2d context) {
		page.draw(context);
	}
	
	public void setPage(Page page){
		MEngine.getHandlersManager().clearHandlers();
		
		this.page=page;
		this.page.regHandlers();
		this.page.onScreen();
	}
	public Page getPage(){
		return this.page;
	}
	@Override
	public GameInfo getGameInfo() {
		return gameInfo;
	}
	@Override
	public int getWidth() {
		return gameInfo.getWidth();
	}
	@Override
	public int getHeight() {
		return gameInfo.getHeight();
	}
	
}
