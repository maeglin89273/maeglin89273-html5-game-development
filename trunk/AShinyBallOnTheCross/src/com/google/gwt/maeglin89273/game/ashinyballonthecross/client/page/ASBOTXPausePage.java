/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.Glass;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.SinglePage;


/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXPausePage extends SinglePage{
	
	private static final String pauseTextFont=ASBOTXConfigs.getCGFont(26);
	private static final String clickTextFont=ASBOTXConfigs.getCGFont(12);
	private Glass glass;
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.GeneralPage#onClick(com.google.gwt.maeglin89273.game.mengine.utility.physics.Point)
	 */
	public ASBOTXPausePage(ASBOTXGamePage gamePage){
		super(gamePage);
		this.glass=new Glass(getGameWidth(),getGameHeight());
	}
	@Override
	public void onClick(ClickEvent event){
		this.toNextPage();
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
		
		glass.draw(context);
		
		context.setFillStyle(ASBOTXConfigs.Color.WHITE);
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
