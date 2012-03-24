/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.button;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.WorldSelectPage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.button.CircleButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class BackButton extends CircleButton{
	public BackButton(Point p,double r) {
		super(p,r, new SpriteBlock(5*(200+SpriteBlock.SPACING),2*(200+SpriteBlock.SPACING),200,200, ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
	}

	@Override
	public void doTask() {
		MEngine.getGeneralGame().setPage(new WorldSelectPage());
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
