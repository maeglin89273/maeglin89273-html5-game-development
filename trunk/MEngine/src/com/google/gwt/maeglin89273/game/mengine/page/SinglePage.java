/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.maeglin89273.game.mengine.core.MEngine;

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
	public SinglePage(Page nextPage) {
		this.nextPage=nextPage;
	}
	protected void toNextPage(){
		getGame().setPage(nextPage);
		this.nextPage=null;
	}
}
