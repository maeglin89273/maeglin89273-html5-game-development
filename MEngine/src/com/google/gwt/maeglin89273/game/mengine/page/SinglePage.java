/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;

/**
 * @author Maeglin Liao
 *
 */
public abstract class SinglePage extends GeneralPage {
	protected Page nextPage;
	/**
	 * @param game
	 * @param nextPage
	 */
	public SinglePage(GeneralGame game, Page nextPage) {
		super(game);
		this.nextPage=nextPage;
	}
	protected void toNextPage(){
		game.setPage(nextPage);
		this.game=null;
		this.nextPage=null;
	}
}
