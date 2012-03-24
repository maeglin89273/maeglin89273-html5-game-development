/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event;

import java.util.EventObject;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.Creator;

/**
 * @author Maeglin Liao
 *
 */
public class CreatorPropertiesChangedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param source
	 */
	public CreatorPropertiesChangedEvent(Object source) {
		super(source);
	}
	public int getPower(){
		return ((Creator)this.source).getPower();
	}
	
	public int getScore(){
		return ((Creator)this.source).getScore();
	}
}
