/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Maeglin Liao
 *
 */
public class CheckLoginRequest implements IsSerializable {
	private String reqUrl;
	private TransportablePlayer localPlayer;
	
	
	public CheckLoginRequest(String requestUrl,TransportablePlayer localPlayer){
		reqUrl = requestUrl;
		this.localPlayer=localPlayer;
		
		
	}
	private CheckLoginRequest(){
		// TODO Auto-generated constructor stub
	}
	
	public String getRquestUrl(){
		return reqUrl;
	}
	public TransportablePlayer getLocalPlayer(){
		return localPlayer;
	}
	
}
