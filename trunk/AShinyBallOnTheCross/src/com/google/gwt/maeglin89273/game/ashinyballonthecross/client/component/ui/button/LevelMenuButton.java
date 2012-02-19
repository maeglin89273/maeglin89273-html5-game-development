/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTXLevelSelectPage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class LevelMenuButton extends BoxButton{
	//the other constructor of LevelSelectPage require WorldType constant as a parameter
	public LevelMenuButton(Point p, double bounds){
			
		super(p, bounds,bounds,new SpriteBlock(0,3*(200+SpriteBlock.SPACING),200,200,
				ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		
	}

	@Override
	public void doTask() {
		
		MEngine.getGeneralGame().setPage(new ASBOTXLevelSelectPage());
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
