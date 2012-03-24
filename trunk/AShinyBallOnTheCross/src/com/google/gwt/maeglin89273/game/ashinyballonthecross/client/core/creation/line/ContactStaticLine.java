/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.WorldContactListener;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class ContactStaticLine extends StaticLine implements
		WorldContactListener {
	
	/**
	 * @param creator
	 * @param contentPower
	 * @param beControlled
	 * @param p1
	 * @param p2
	 * @param color
	 */
	public ContactStaticLine(int contentPower,
			boolean beControlled, Point p1, Point p2, CssColor color) {
		super(contentPower, beControlled, p1, p2, color);
		if(isVerified()){
			creator.getWorld().addContactListener(this);
		}
	}

	@Override 
	public void destroy(){
		creator.getWorld().removeContactListener(this);
		super.destroy();
	}
	
}
