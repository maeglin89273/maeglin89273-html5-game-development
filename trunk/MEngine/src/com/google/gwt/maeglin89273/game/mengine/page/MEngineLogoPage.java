/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.maeglin89273.game.mengine.core.MEngine;

/**
 * @author Maeglin Liao
 *
 */
public class MEngineLogoPage extends FadingPage{
	
	public MEngineLogoPage(Page nextPage,String directoryPath) {
		
			super(directoryPath+"MEngine_logo.png",
					Math.min(getGameWidth(), getGameHeight()),
					Math.min(getGameWidth(), getGameHeight()),
					nextPage);
	}
	
}
