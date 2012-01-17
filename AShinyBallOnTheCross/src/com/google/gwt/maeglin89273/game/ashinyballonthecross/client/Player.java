/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.*;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.WorldType;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.storage.client.Storage;


/**
 * @author Maeglin Liao
 *
 */
public class Player {
	private static final String SCORES_KEY="ACHVS";
	private Storage storage;
	
	private Map<WorldType,int[]> playerAchievements=new HashMap<WorldType,int[]>(); 
	public Player(){
		storage=MEngine.getLocalStorage();
		String encrptedScores=storage.getItem(SCORES_KEY);
		if(encrptedScores==null){
			resetPlayerAchievements();
			storage.setItem(SCORES_KEY,getEncrypedJson());
		}else{
			setPlayerAchievements(MEngine.getCipher().decrypt(encrptedScores));
		}
	}
	public void save(){
		storage.setItem(SCORES_KEY, getEncrypedJson());
	}
	public boolean isLevelUnlocked(WorldType type,int level){
		return this.getScoreAt(type, level)>=0;
	}
	public int getScoreAt(WorldType type,int level){
		if(level<=0||level>Level.LEVEL_COUNT){
			return -1;
		}else{
			
			return playerAchievements.get(type)[level];
			
		}
	}
	public int getScoreAt(Level level){
		return this.getScoreAt(level.getWorldType(), level.getLevelNumber());
	}
	public int getTotalInWorld(WorldType world){
		return playerAchievements.get(world)[0];
	}
	public void setScoreAt(WorldType type,int level,int score){
		if(level<=0||level>Level.LEVEL_COUNT){
			return;
		}
		int oldScore=playerAchievements.get(type)[level];
		playerAchievements.get(type)[0]+=oldScore<0?0:(score-oldScore);
		playerAchievements.get(type)[level]=score;
			
		
	}
	public void setScoreAt(Level level,int score){
		this.setScoreAt(level.getWorldType(), level.getLevelNumber(), score);
	}
	/**
	 * reset the player progess and also return the encrypted json text
	 * @return encrypted json text
	 */
	private void resetPlayerAchievements(){
		
		int[] scores;
		for(WorldType type:WorldType.values()){
			scores=new int[Level.LEVEL_COUNT+1];//index 0 for total per world
			for(int i=1;i<scores.length;i++){
				//the required score depends on level,may use queryLevelRequiedScore(WorldTye type,level)
				scores[i]=-1;
			}
			playerAchievements.put(type, scores);
		}
		playerAchievements.get(WorldType.INTRO)[1]=0;
		
	}
	private void setPlayerAchievements(String jsonString){
		JSONObject object=JSONParser.parseStrict(jsonString).isObject();
		try{
			WorldType type;
			int[] scores;
			JSONArray worldScores;
			for(String typeString:object.keySet()){
				type=WorldType.valueOf(typeString);
				worldScores=object.get(typeString).isArray();
				scores=new int[worldScores.size()];
				for(int i=0;i<worldScores.size();i++){
					//the required score depends on level,may use queryLevelRequiedScore(WorldTye type,level)
					scores[i]=(int)worldScores.get(i).isNumber().doubleValue();
				}
				playerAchievements.put(type, scores);
			}
		}catch(RuntimeException e){
			resetPlayerAchievements();
		}
	}
	private String getEncrypedJson(){
		JSONObject object=new JSONObject();
		JSONArray worldScores;
		int[] scores;
		for(WorldType type:playerAchievements.keySet()){
			worldScores=new JSONArray();
			scores=playerAchievements.get(type);
			for(int i=0;i<scores.length;i++){
				worldScores.set(i, new JSONNumber(scores[i]));
			}
			object.put(type.toString(), worldScores);
		}
		return MEngine.getCipher().encrypt(object.toString());
	}
	
	
}
