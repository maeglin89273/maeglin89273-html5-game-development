/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.utility;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class GameColors {
	
	public static final CssColor TEXT_COLOR=CssColor.make("rgba(208,210,211,0.5)");;
	public static final CssColor DARK_GRAY_LINE_COLOR=CssColor.make("hsla(0,0%,25%,0.6)");
	public static final CssColor BLACK_LINE_COLOR=CssColor.make("hsla(0,0%,0%,0.9)");
	public static final CssColor YELLOW_BORDER_Color=CssColor.make("hsla(55.5,100%,50%,0.8)");
	public static final CssColor BLUE_BORDER_COLOR=CssColor.make("hsla(210,90%,50%,0.8)");
	public static final CssColor getRandomShapeBorderColor(){
		return CssColor.make("hsla("+Random.nextInt(361)+",95%,50%,0.8)");
	}
}
