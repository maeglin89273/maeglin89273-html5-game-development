/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.mengine.core.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.LoadingResourcesPage;

/**
 * @author Maeglin Liao
 *
 */
public class PhysicsBattleLoadingResourcesPage extends LoadingResourcesPage {
	private int percentage;
	private static final float BAR_LENGTH=200f;
	private CssColor barColor=CssColor.make("rgba(208,210,211,0.5)");
	
	private int barStartX;
	private int barY;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.LoadingPage#update()
	 */
	public PhysicsBattleLoadingResourcesPage(GeneralGame game){
		super(game);
		
		barStartX=getGameWidth()/2-100;
		barY=getGameHeight()/2;
	}
	@Override
	public void update() {
		percentage=MEngine.getAssetManager().getLoadedPercentage();
		if(isLoaded()){
			game.setPage(new PhysicsBattleGameBoardPage(this.game));
		}
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

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.LoadingPage#isLoaded()
	 */
	@Override
	public boolean isLoaded() {
		return percentage==100;
	}
	
}
