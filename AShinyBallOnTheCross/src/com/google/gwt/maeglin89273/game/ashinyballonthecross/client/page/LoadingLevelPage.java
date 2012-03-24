/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.Glass;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.page.Page;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class LoadingLevelPage extends GeneralPage {
	private static final String loadingTextFont=ASBOTXConfigs.getCGFont(26);
	
	private boolean firstUpdate=true;
	private String path;
	private Page specialPage=null;
	
	private Glass glass=new Glass(getGameWidth(),getGameHeight());
	
	/**
	 * @param game
	 */
	public LoadingLevelPage(String levelPath) {
		this.path=levelPath;
	}
	public LoadingLevelPage(Page specialPage){
		this.specialPage = specialPage;
		
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
			try{
				if(specialPage!=null){
					getGame().setPage(specialPage);
				}else{
					getGame().setPage(new ASBOTXGamePage(
							new Level(MEngine.getAssetManager().getJson(path),
							new Point(getGameWidth()/2.0,getGameHeight()/2.0))));
				}	  
			}catch(IllegalArgumentException e){//the last level
				getGame().setPage(new EndingPage());
			}
			
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setTextAlign(TextAlign.CENTER);
		context.setTextBaseline(TextBaseline.MIDDLE);
		
		glass.draw(context);
		
		context.setFillStyle(ASBOTXConfigs.Color.WHITE);
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
