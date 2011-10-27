package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.core.Game;
import com.google.gwt.maeglin89273.game.mengine.core.GeneralGame;

public abstract class Page {
	protected GeneralGame game;
	
	protected Page(GeneralGame game){
		this.game=game;
	}
	public abstract void initHandlers();
	public int getGameWidth(){
		return getGame().getWidth();
		
	}
	
	public int getGameHeight(){
		return getGame().getHeight();
	}
	
	
	public GeneralGame getGame(){
		return game;
	}

	public abstract void update();

	public abstract void draw(Context2d context);
}