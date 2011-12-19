/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;


import org.jbox2d.callbacks.ContactListener;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class ContactStaticLine extends StaticLine implements
		ContactListener {

	/**
	 * @param creator
	 * @param contentPower
	 * @param beControlled
	 * @param p1
	 * @param p2
	 * @param color
	 */
	public ContactStaticLine(Creator creator, int contentPower,
			boolean beControlled, Point p1, Point p2, CssColor color) {
		super(creator, contentPower, beControlled, p1, p2, color);
		if(isVerified()){
			this.creator.getWorld().addContactListener(this);
		}
	}

	@Override 
	public void destroy(){
		creator.getWorld().removeContactListener(this);
		super.destroy();
	}
}
