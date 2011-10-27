package com.google.gwt.maeglin89273.game.physicsbattle.client;



import com.google.gwt.maeglin89273.game.physicsbattle.client.page.PhysicsBattleLoadingResourcesPage;
import com.google.gwt.maeglin89273.mengine.core.GeneralGame;
import com.google.gwt.maeglin89273.mengine.sprite.SpriteSheet;


/**
 * @author Maeglin Liao
 */
public class PhysicsBattleGame extends GeneralGame {
	
	@Override
	public void init() {
		setPage(new PhysicsBattleLoadingResourcesPage(this));
	}
	
	

	@Override
	public SpriteSheet[] getGameSpriteSheets() {
		return new SpriteSheet[]{new SpriteSheet("gravity_clock.png"),new SpriteSheet("star.png"),new SpriteSheet("buttons.png")};
	}
	@Override
	public boolean hasLoadingResourcesPage() {
		return true;
	}



	@Override
	public int getWidth() {
		return 1000;
	}



	@Override
	public int getHeight() {
		return 750;
	}


	
	
}
