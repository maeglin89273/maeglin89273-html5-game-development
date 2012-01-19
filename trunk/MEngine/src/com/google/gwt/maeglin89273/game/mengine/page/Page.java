package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop;

public abstract class Page implements HasGameLoop{
	
	public abstract void regHandlers();
	public abstract void onScreen();
	protected static int getGameWidth(){
		return MEngine.getGeneralGame().getWidth();
		
	}
	
	protected static int getGameHeight(){
		return MEngine.getGeneralGame().getHeight();
	}
	
	
	protected static GeneralGame getGame(){
		return MEngine.getGeneralGame();
	}
}