/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.button;


import com.google.gwt.maeglin89273.game.physicsbattle.client.page.PhysicsBattleGamePage;
import com.google.gwt.maeglin89273.game.physicsbattle.client.page.PhysicsBattleGameBoardPage;
import com.google.gwt.maeglin89273.game.mengine.component.CanvasButton;

import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.page.Page;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;

/**
 * @author Maeglin Liao
 *
 */
public class StartButton extends CanvasButton {
	private Page gameBoardPage;
	
	public StartButton(PhysicsBattleGameBoardPage page,Point p){
		super(p,250,100,new SpriteBlock(500+SpriteBlock.SPACITNG,0,500,200,MEngine.getAssetManager().getSpriteSheet("buttons.png")));
		
		this.gameBoardPage=page;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.gamecomponent.CanvasButton#doTask()
	 */
	@Override
	public void doTask() {
		gameBoardPage.getGame().setPage(new PhysicsBattleGamePage(gameBoardPage.getGame()));

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