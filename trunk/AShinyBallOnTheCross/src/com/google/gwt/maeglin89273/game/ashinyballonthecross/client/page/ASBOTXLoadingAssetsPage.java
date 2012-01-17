/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.Glass;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.page.LoadingAssetsPage;
import com.google.gwt.maeglin89273.game.mengine.page.Page;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXLoadingAssetsPage extends LoadingAssetsPage{
	private boolean triggerA=false;
	private boolean triggerB=false;
	private GroupLayer layers;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.LoadingPage#update()
	 */
	public ASBOTXLoadingAssetsPage(Page nextPage){
		super(nextPage);
	}
	@Override
	public void update() {
		if(triggerB){
			((ASBOTXGame)getGame()).initPlayer();
			layers=null;
			toNextPage();
		}else{
			layers.update();
			
			if(triggerA){
				triggerB=true;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.LoadingPage#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		layers.draw(context);
	}
	@Override
	public void onScreen() {
		layers=new GroupLayer();
		layers.addComponentOnLayer(new GameLabel(new Point(getGameWidth()/2,getGameHeight()/2),
				TextAlign.CENTER, TextBaseline.MIDDLE, "Loading...", ASBOTXConfigs.Color.WHITE,
				ASBOTXConfigs.getGameFont(26)));
		layers.addComponentOnLayer(new LoadingBar(35));
		layers.addComponentOnLayer(new Glass(getGameWidth(), getGameHeight()));
		
	}
	@Override
	public void done() {
		triggerA=true;
		
	}
	private class LoadingBar extends GeneralComponent{
		private int percentage;
		
		protected LoadingBar(int yOffset){
			super(new Point(getGameWidth()/2,getGameHeight()/2+yOffset), 400, 10);
		}

		@Override
		public void update() {
			percentage=MEngine.getAssetManager().getLoadedPercentage();
		}

		@Override
		public void draw(Context2d context) {
			context.setStrokeStyle(ASBOTXConfigs.Color.WHITE);
			context.setLineWidth(getHeight());
			context.beginPath();
			context.moveTo(getLeftX(),getY());
			context.lineTo(getLeftX()+(getWidth()/100)*percentage,getY());
			context.stroke();
			
		}
		
	}

	
	
}
