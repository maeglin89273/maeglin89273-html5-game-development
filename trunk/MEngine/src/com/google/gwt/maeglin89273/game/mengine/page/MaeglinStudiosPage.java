/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;


import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;


/**
 * @author Maeglin Liao
 *
 */
public class MaeglinStudiosPage extends FadingPage {

	public MaeglinStudiosPage(GeneralGame game, Page nextPage) {
		super(game, MEngine.getAssetManager().getSpriteSheet("MStudios_landscape.png"),
				Math.min(game.getWidth(), game.getHeight()),
				Math.min(game.getWidth(), game.getHeight()), nextPage);
		// TODO Auto-generated constructor stub
	}
}
