/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.key;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.DefinersFactory;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

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
	public AreaKey(Point p, double w, double h,String[] kinds) {
		super(p, w, h, new Point(0,0),DefinersFactory.getDefiners(kinds));
	}
}
