/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.button.StartButton;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTCWelcomePage extends GeneralPage {
	private static final String textFont="40pt Century Gothic";
	private static final CssColor textColor=CssColor.make(0,0,0);
	private static final String shadowColor="rgba(100,100,100,0.6)";
	StartButton button;
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.GeneralPage#onClick(com.google.gwt.maeglin89273.game.mengine.utility.physics.Point)
	 */
	
	public ASBOTCWelcomePage(GeneralGame game) {
		super(game);
		button=new StartButton(game,new Point(getGameWidth()/2.0,getGameHeight()/2.0));
	}
	
	@Override
	public void onClick(ClickEvent e) {
		if(button.contain(MEngine.getMousePosition())){
			button.doTask();
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.save();
		context.setTextAlign(TextAlign.CENTER);
		context.setTextBaseline(TextBaseline.MIDDLE);
		context.setFillStyle(textColor);
		context.setShadowColor(shadowColor);
		context.setShadowBlur(2);
		context.setShadowOffsetX(2);
		context.setShadowOffsetY(2);
		context.setFont(textFont);
		context.fillText("A Shiny Ball On The Cross",getGameWidth()/2,125);
		
		button.draw(context);
		context.restore();
	}

	@Override
	public void onScreen() {
		// TODO Auto-generated method stub
		
	}
	

}
