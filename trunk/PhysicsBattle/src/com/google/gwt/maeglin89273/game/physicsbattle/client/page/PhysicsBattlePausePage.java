/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.mengine.core.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;


/**
 * @author Maeglin Liao
 *
 */
public class PhysicsBattlePausePage extends GeneralPage {
	private static final CssColor backgroundColor=CssColor.make("hsla(0,0%,0%,0.2)");
	private static final CssColor textColor=CssColor.make("rgb(255,255,255)");
	private static final String pauseTextFont="26pt Century Gothic";
	private static final String clickTextFont="12pt Century Gothic";
	
	private PhysicsBattleGamePage gamePage;
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.GeneralPage#onClick(com.google.gwt.maeglin89273.game.mengine.utility.physics.Point)
	 */
	public PhysicsBattlePausePage(GeneralGame game,PhysicsBattleGamePage gamePage){
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
		MEngine.pause();

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setTextAlign(TextAlign.CENTER);
		context.setTextBaseline(TextBaseline.MIDDLE);
		
		context.setFillStyle(backgroundColor);
		context.fillRect(0, 0,getGameWidth(),getGameHeight());
		
		context.setFillStyle(textColor);
		context.setFont(pauseTextFont);
		context.fillText("Pause",getGameWidth()/2,getGameHeight()/2);
		context.setFont(clickTextFont);
		context.fillText("click here to continue", getGameWidth()/2, getGameHeight()/2+30);
	}
}
