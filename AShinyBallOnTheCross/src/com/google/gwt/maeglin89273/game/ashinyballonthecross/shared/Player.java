/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import java.util.Date;

/**
 * @author Maeglin Liao
 *
 */
public interface Player {
	public void setKey(String encodedKey);
	public String getKey();
	public void setID(String id);
	public String getID();
	public long getTotal();
	public void setTotal(long total);
	public String getEncryptedAchievements();
	public void setEncryptedAchievements(String encryptedAchvs);
	public Date getTimestamp();
	public void setTimestamp(Date timestamp);
}
