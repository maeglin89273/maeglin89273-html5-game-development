/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.LevelMenuButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.ImageLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXEndingPage extends GeneralPage {
	
	private static final float PER_ALPHA_VALUE=0.02f;
	private float alpha=0;
	private GroupLayer root;
	private ImageLayer img;
	private ImageLayer background; 
	private LevelMenuButton button;
	/**
	 * 
	 */
	public ASBOTXEndingPage() {
		root=new GroupLayer();
		img=new ImageLayer(MEngine.getAssetManager().getSpriteSheet("images/ending.png"), getGameWidth(), getGameHeight());
		background=new ImageLayer(MEngine.getAssetManager().getSpriteSheet("images/blue_bg.png"),getGameWidth(),getGameHeight());
		button=new LevelMenuButton(new Point(50,getGameHeight()-50), 100);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		if(alpha>=1){
			button.onClick(MEngine.getMousePosition());
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		if(alpha<1){
			alpha+=PER_ALPHA_VALUE;
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		
		context.save();
		context.setGlobalAlpha(alpha);
		root.draw(context);
		context.restore();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#onScreen()
	 */
	@Override
	public void onScreen() {
		root.addComponentOnLayer(button);
		root.addLayer(img);
		root.addLayer(background);
	}

}
