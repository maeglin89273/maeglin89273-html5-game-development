/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.key;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;

/**
 * @author Liao
 *
 */
public class AreaKey extends CreativeKey {

	/**
	 * @param p
	 * @param w
	 * @param h
	 * @param block
	 * @param definers
	 */
	public AreaKey(Point p, double w, double h,AreaDefinerType... types) {
		super(p, w, h, new Point(0,0),definersFactory.getAreaDefiners(types));
	}
}
