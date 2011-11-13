/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.game;

/**
 * @author Liao
 *
 */
public interface Game extends HasGameLoop{

	public abstract void init();
	public abstract GameInfo getGameInfo();
	public abstract int getWidth();
	public abstract int getHeight();
	
}
