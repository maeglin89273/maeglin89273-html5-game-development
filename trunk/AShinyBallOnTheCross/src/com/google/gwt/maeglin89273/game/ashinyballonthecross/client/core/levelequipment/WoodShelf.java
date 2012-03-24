/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.levelequipment;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.WoodLine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class WoodShelf extends Shelf {

	/**
	 * @param creator
	 * @param commonPoint
	 * @param width
	 * @param angle
	 */
	public WoodShelf(Creator creator, Point commonPoint, double width,double angle,boolean reverse) {
		super(creator, commonPoint, width, angle,reverse);
		new WoodLine(commonPoint, longerLinePoint);
		new WoodLine(commonPoint,shorterLinePoint);
	}

}
