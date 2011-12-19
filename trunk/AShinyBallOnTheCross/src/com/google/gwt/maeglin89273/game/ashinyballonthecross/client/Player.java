/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.WorldType;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.storage.client.Storage;


/**
 * @author Maeglin Liao
 *
 */
public class Player {
	private static final String SCORES_KEY="SCORES";
	private Storage storage;
	
	public Player(){
		storage=MEngine.getLocalStorage();
		String encrptedScores=storage.getItem(SCORES_KEY);
		if(encrptedScores==null){
			storage.setItem(SCORES_KEY,getInitialEncryptedScores());
		}else{
			MEngine.getCipher().decrypt(encrptedScores);
		}
	}
	public boolean isLevelUnlocked(WorldType type,int level){
		return false;
	}
	private static String getInitialEncryptedScores(){
		return null;
	}
	
}
