/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.LoadingDataPage;
import com.google.gwt.maeglin89273.game.mengine.page.Page;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTCLoadingDataPage extends LoadingDataPage {
	private int percentage;
	private static final float BAR_LENGTH=400f;
	private CssColor barColor=CssColor.make("rgba(208,210,211,0.5)");
	
	private int barStartX;
	private int barY;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.LoadingPage#update()
	 */
	public ASBOTCLoadingDataPage(GeneralGame game,Page nextPage){
		super(game,nextPage);
		
		barStartX=getGameWidth()/2-200;
		barY=getGameHeight()/2;
	}
	@Override
	public void update() {
		percentage=MEngine.getAssetManager().getLoadedPercentage();
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.LoadingPage#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setStrokeStyle(barColor);
		context.setLineWidth(15);
		context.beginPath();
		context.moveTo(barStartX,barY);
		context.lineTo(barStartX+(BAR_LENGTH/100)*percentage,barY);
		context.stroke();
	}

}
