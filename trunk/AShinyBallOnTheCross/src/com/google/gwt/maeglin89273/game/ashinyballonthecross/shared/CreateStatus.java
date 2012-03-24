/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Maeglin Liao
 *
 */
public enum CreateStatus implements IsSerializable {
	DUPLICATED("The ID has been used. Please choose another ID."),
	SUCCESS("Created a player successfully."),
	NOT_LOGGED_IN("You haven't logged in yet. Please login first.");
	private String message;
	private CreateStatus(String message){
		this.message=message;
	}
	private CreateStatus(){
		
	}
	public String getMessage(){
		return message;
	}
}
