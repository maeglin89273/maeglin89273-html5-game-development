/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.LocalPlayer;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Maeglin Liao
 *
 */
public class CheckLoginResponse implements IsSerializable {
	public enum Status implements IsSerializable{NOT_LOGGED_IN,NEW_PLAYER,DOWNLOAD,UPLOAD}
	private Status status;
	private LoginInfo info;
	private Player player;
	
	public CheckLoginResponse(Status status,LoginInfo info,Player player){
		this.status = status;
		this.info = info;
		this.player=player;
		
	}
	
	/**
	 * 
	 */
	private CheckLoginResponse() {
		// TODO Auto-generated constructor stub
	}
	public Status getStatus(){
		return status;
	}
	public LoginInfo getLoginInfo(){
		return info;
	}
	public Player getPlayer(){
		return player;
	}
	
	public void handleThis(LocalPlayer localPlayer){
		switch(getStatus()){
		case NEW_PLAYER:
		case DOWNLOAD:
			localPlayer.overwite(player);
			break;
		case UPLOAD:
			localPlayer.setKey(player.getKey());
			break;
		case NOT_LOGGED_IN:
			localPlayer.clearKey();
			break;
	}
	}
}
