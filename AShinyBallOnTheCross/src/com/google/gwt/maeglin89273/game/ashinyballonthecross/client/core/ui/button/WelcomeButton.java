/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.button;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.WelcomePage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class WelcomeButton extends BoxButton {

	/**
	 * @param p
	 * @param w
	 * @param h
	 * @param block
	 */
	public WelcomeButton(Point p,double bounds) {
		super(p,bounds,bounds, new SpriteBlock(5*(200+SpriteBlock.SPACING),0,200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.button.GameButton#doTask()
	 */
	@Override
	public void doTask() {
		MEngine.getGeneralGame().setPage(new WelcomePage());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
