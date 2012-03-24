/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Maeglin Liao
 *
 */
public class TransportablePlayer implements IsSerializable,Player {
	public static final String TOTAL_PROPERTY="total";
	public static final String ID_PROPERTY="id";
	public static final String ACHIEVEMENT_PROPERTY="achievements";
	public static final String USER_PROPERTY="user";
	public static final String DATE_PROPERTY="date";
	
	private String id;
	private String key;
	private long total;
	private String encryptedAchievements;
	private Date timestamp;
	
	public TransportablePlayer(String key,String id,long total,String achievements,Date timestamp){
		this.key=key;
		this.id = id;
		this.total = total;
		this.encryptedAchievements = achievements;
		this.timestamp = timestamp;
		
	}
	private TransportablePlayer(){
		
	}
	@Override
	public void setKey(String encodedKey){
		this.key=encodedKey;
	}
	@Override
	public String getKey(){
		return key;
	}
	@Override
	public void setID(String id){
		this.id=id;
	}
	@Override
	public String getID(){
		return id;
	}
	@Override
	public long getTotal(){
		return total;
	}
	@Override
	public void setTotal(long total) {
		this.total=total;		
	}
	@Override
	public String getEncryptedAchievements(){
		return encryptedAchievements;
	}
	
	@Override
	public void setEncryptedAchievements(String encryptedAchvs) {
		this.encryptedAchievements=encryptedAchvs;
	}
	@Override
	public Date getTimestamp() {
		return timestamp;
	}
	@Override
	public void setTimestamp(Date timestamp) {
		this.timestamp=timestamp;
	}
}
