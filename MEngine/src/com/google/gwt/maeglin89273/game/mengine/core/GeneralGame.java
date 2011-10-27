/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.core;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.page.*;


/**
 * @author Maeglin Liao
 *
 */
public abstract class GeneralGame implements Game {
	protected Page page;
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
		MEngine.getEventsDeliverer().clearHandlers();
		this.page=page;
		this.page.initHandlers();
	}

}
