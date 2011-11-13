package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop;

public abstract class Page implements HasGameLoop{
	protected GeneralGame game;
	
	protected Page(GeneralGame game){
		this.game=game;
	}
	public abstract void initHandlers();
	public abstract void onScreen();
	public int getGameWidth(){
		return getGame().getWidth();
		
	}
	
	public int getGameHeight(){
		return getGame().getHeight();
	}
	
	
	public GeneralGame getGame(){
		return game;
	}

	
}