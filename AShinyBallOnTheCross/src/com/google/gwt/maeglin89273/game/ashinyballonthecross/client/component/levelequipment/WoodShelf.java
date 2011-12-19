/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.levelequipment;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.WoodLine;
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
		new WoodLine(creator, commonPoint, longerLinePoint);
		new WoodLine(creator,commonPoint,shorterLinePoint);
	}

}
