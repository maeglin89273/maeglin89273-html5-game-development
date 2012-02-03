/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.ImageLayer;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.timer.SchedulingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;

/**
 * @author Maeglin Liao
 *
 */
public class FadingPage extends SinglePage {

	private static final float PER_ALPHA_VALUE=0.04f;
	private int count=25;
	
	private boolean fadeIn=true,fadeOut=false;
	private SchedulingTimer timer;
	
	private ImageLayer layer;
	
	public FadingPage(ImageLayer img,Page nextPage){
		super(nextPage);
		this.layer=img;
	}
	public FadingPage(String sheetPath,int imgWidthInGame,int imgHeightInGame,Page nextPage) {
		this(new ImageLayer(MEngine.getAssetManager().getSpriteSheet(sheetPath),
			new Point((getGame().getGameInfo().getWidth()-imgWidthInGame)/2,
					  (getGame().getGameInfo().getHeight()-imgHeightInGame)/2),
					   imgWidthInGame,imgHeightInGame),nextPage);
				
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		
	}
	@Override
	public void regHandlers(){
		return;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#update()
	 */
	@Override
	public void update() {
		
		if(fadeIn){
			layer.setAlpha(layer.getAlpha()+PER_ALPHA_VALUE);
			count--;
			if(count==1){
				fadeIn=false;
				timer=new SchedulingTimer(new TimerTask(){

					@Override
					public void doTask() {
						fadeOut=true;
					}
					
				}, 1750);
				timer.start();
				
			}
		}
		if(fadeOut){
			layer.setAlpha(layer.getAlpha()-PER_ALPHA_VALUE);
			count++;
			if(count==25){
				this.toNextPage();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		layer.draw(context);
	}

	@Override
	public void onScreen() {
		
		this.layer.setAlpha(0);
		
	}
}
