/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;


/**
 * @author Maeglin Liao
 *
 */
public class ASBOTCPausePage extends GeneralPage {
	
	private static final String pauseTextFont=ASBOTCConfigurations.getGameFont(26);
	private static final String clickTextFont=ASBOTCConfigurations.getGameFont(12);
	
	private ASBOTCGamePage gamePage;
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.GeneralPage#onClick(com.google.gwt.maeglin89273.game.mengine.utility.physics.Point)
	 */
	public ASBOTCPausePage(GeneralGame game,ASBOTCGamePage gamePage){
		super(game);
		this.gamePage=gamePage;
	}
	@Override
	public void onClick(ClickEvent event){
		this.game.setPage(gamePage);
		MEngine.play();

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#update()
	 */
	@Override
	public void update() {
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setTextAlign(TextAlign.CENTER);
		context.setTextBaseline(TextBaseline.MIDDLE);
		
		context.setFillStyle(ASBOTCConfigurations.Color.GLASS);
		context.fillRect(0, 0,getGameWidth(),getGameHeight());
		
		context.setFillStyle(ASBOTCConfigurations.Color.WHITE);
		context.setFont(pauseTextFont);
		context.fillText("Pause",getGameWidth()/2,getGameHeight()/2);
		context.setFont(clickTextFont);
		context.fillText("click here to continue", getGameWidth()/2, getGameHeight()/2+30);
	}
	@Override
	public void onScreen() {
		MEngine.pause();
		
	}
}
