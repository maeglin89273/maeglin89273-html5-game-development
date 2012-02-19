/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Maeglin Liao
 *
 */
public enum WorldType implements IsSerializable{
	INTRO("Introduction");
	private String title;
	private WorldType(){
		
	}
	private WorldType(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}
	
}
