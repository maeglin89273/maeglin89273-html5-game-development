/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button;




import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTCLevelSelectPage;
import com.google.gwt.maeglin89273.game.mengine.component.GameButton;

import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;

/**
 * @author Maeglin Liao
 *
 */
public class StartButton extends GameButton {
	
	public StartButton(GeneralGame game,Point p){
		super(game,p,250,100,new SpriteBlock(0,400+2*SpriteBlock.SPACING,500,200,MEngine.getAssetManager().getSpriteSheet("buttons.png")));
		
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.gamecomponent.CanvasButton#doTask()
	 */
	@Override
	public void doTask() {
		game.setPage(new ASBOTCLevelSelectPage(game));
		this.game=null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.gamecomponent.CanvasButton#contain(com.google.gwt.maeglin89273.game.mengine.utility.physics.Point)
	 */
	@Override
	public boolean contain(Point p) {
		if(p.getX()>=getLeftX()&&p.getX()<=getRightX()&&p.getY()>=getTopY()&&p.getY()<=getBottomY())
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
