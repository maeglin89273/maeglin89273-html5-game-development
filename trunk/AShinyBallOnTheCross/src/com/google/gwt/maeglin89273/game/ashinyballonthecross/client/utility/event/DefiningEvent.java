/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event;

import java.util.EventObject;

/**
 * @author Maeglin Liao
 *
 */
public class DefiningEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int expectativeRemainingPower;
	/**
	 * 
	 * @param source
	 * @param estimateRequiredgPower
	 */
	public DefiningEvent(Object source,int expectiveRemainingPower) {
		super(source);
		this.expectativeRemainingPower=expectiveRemainingPower;
	}
	public int getExpectativeRemainingPower(){
		return expectativeRemainingPower;
	}
}
