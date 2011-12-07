/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.key;




import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;

/**
 * @author Maeglin Liao
 *
 */
public class LineKey extends CreativeKey {
	/**
	 * 
	 * @param p
	 * @param w
	 * @param h
	 * @param types
	 */
	public LineKey(Point p,double w,double h,LineDefinerType...types){
		super(p, w, h, new Point(200+SpriteBlock.SPACING,0),definersFactory.getLineDefiners(types));
	}
	
}
