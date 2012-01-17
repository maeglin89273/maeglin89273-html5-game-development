/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level;

/**
 * @author Maeglin Liao
 *
 */
public enum WorldType {
	INTRO("Introduction");
	private String title;
	private WorldType(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}
	
}
