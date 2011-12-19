/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTCLoadingLevelPage extends GeneralPage {
	private static final String loadingTextFont=ASBOTCConfigurations.getGameFont(26);
	
	private boolean firstUpdate=true;
	private LevelContext level;
	
	/**
	 * @param game
	 */
	public ASBOTCLoadingLevelPage(GeneralGame game,LevelContext level) {
		super(game);
		this.level=level;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		if(firstUpdate){
			firstUpdate=false;
		}else{
			this.game.setPage(new ASBOTCGamePage(this.game,this.level));
			this.level=null;
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setTextAlign(TextAlign.CENTER);
		context.setTextBaseline(TextBaseline.MIDDLE);
		
		context.setFillStyle(ASBOTCConfigurations.Color.GLASS);
		context.fillRect(0, 0,getGameWidth(),getGameHeight());
		
		context.setFillStyle(ASBOTCConfigurations.Color.WHITE);
		context.setFont(loadingTextFont);
		context.fillText("Loading...",getGameWidth()/2,getGameHeight()/2);
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#onScreen()
	 */
	@Override
	public void onScreen() {
		// TODO Auto-generated method stub

	}

}
