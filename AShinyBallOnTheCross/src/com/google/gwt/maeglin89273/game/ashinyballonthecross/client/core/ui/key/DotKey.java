/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.key;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.DefinersFactory;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;


/**
 * @author Liao
 *
 */
public class DotKey extends CreativeKey {
	
	/**
	 * @param p
	 * @param w
	 * @param h
	 * @param block
	 */
	public DotKey(Point p, double w, double h,String[] kinds) {
		super(p, w, h,new Point(400+2*SpriteBlock.SPACING,0),DefinersFactory.getDefiners(kinds));
		
	}
}
