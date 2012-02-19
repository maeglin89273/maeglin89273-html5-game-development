/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Maeglin Liao
 *
 */
public class Player implements IsSerializable {
	public static final String TOTAL_PROPERTY="total";
	public static final String ID_PROPERTY="id";
	public static final String ACHIEVEMENT_PROPERTY="achievements";
	public static final String USER_PROPERTY="user";
	public static final String DATE_PROPERTY="date";
	
	private String id;
	private String key;
	private long total;
	private String encryptedAchievements;
	
	public Player(String key,String id,long total,String achievements){
		this.key=key;
		this.id = id;
		this.total = total;
		encryptedAchievements = achievements;
		
	}
	private Player(){
		
	}
	public void setKey(String encodedKey){
		this.key=encodedKey;
	}
	public String getKey(){
		return key;
	}
	public void setID(String id){
		this.id=id;
	}
	public String getID(){
		return id;
	}
	
	public long getTotal(){
		return total;
	}
	
	public String getEncryptedAchievements(){
		return encryptedAchievements;
	}
}
