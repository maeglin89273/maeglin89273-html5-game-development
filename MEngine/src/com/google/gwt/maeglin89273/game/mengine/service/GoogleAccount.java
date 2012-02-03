/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.service;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Maeglin Liao
 *
 */
public class GoogleAccount implements IsSerializable{
	private String email;
	private String authDomain;
	private String nickname;
	private String userId;
	public GoogleAccount(String email,String nickname){
		this(email, null, nickname, null);
	}
	public GoogleAccount(String email,String authDomain,String nickname,String userId){
		this.email = email;
		this.authDomain = authDomain;
		this.userId = userId;
		this.nickname = nickname;
	}
	/**
	 * for RPC
	 */
	private GoogleAccount(){
		
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return the authDomain
	 */
	public String getAuthDomain() {
		return authDomain;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
}
