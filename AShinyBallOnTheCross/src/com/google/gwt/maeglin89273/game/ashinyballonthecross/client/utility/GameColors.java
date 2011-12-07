/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class GameColors {
	
	public static final CssColor TEXT_COLOR=CssColor.make("rgba(208,210,211,0.5)");
	
	public static final CssColor GRAY=CssColor.make("hsla(0,0%,25%,0.6)");
	public static final CssColor BLACK=CssColor.make("hsla(0,0%,0%,0.9)");
	
	public static final CssColor YELLOW_BORDER_COLOR=CssColor.make("hsla(55.5,95%,50%,0.8)");
	public static final CssColor STAR_BOUND_COLOR=CssColor.make("hsla(57,95%,50%,0.6)");
	
	public static final CssColor BLUE=CssColor.make("hsla(210,90%,50%,0.8)");
	public static final CssColor LIGHT_BLUE=CssColor.make("hsla(190,95%,50%,0.8)");
	public static final CssColor DARK_BLUE=CssColor.make("hsla(220,95%,40%,0.9)");
	
	public static final CssColor RED=CssColor.make("hsla(5,95%,50%,0.8)");
	
	public static final CssColor DARK_GRAY=CssColor.make("hsla(0,0%,15%,0.7)");
	public static final CssColor TRANSLUCENT_DARK_GRAY=CssColor.make("hsla(0,0%,20%,0.4)");
	public static final CssColor getRandomShapeBorderColor(){
		return CssColor.make("hsla("+Random.nextInt(361)+",95%,48%,0.8)");
	}
	public static final String getGameFont(int px){
		return px+"pt Century Gothic";
	}
}
