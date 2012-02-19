/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.*;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Player;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.WorldType;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.storage.client.Storage;


/**
 * @author Maeglin Liao
 *
 */
public class LocalPlayer {
	private static final String ACHIEVEMENTS_KEY="ACHVS";
	private static final String ID_KEY="ID_CACHED";
	private static final String TOTAL_KEY="TS";
	private static final String TUTORIAL_READED_KEY="TR";
	private Storage storage;
	
	private Map<WorldType,int[]> playerAchievements=new HashMap<WorldType,int[]>();
	private long total;
	private String id;
	private boolean tutorialReaded;
	
	private String encryptedAchvs;
	private String encodedKey;
	public LocalPlayer(){
		storage=MEngine.getLocalStorage();
		String encrptedAchievements=storage.getItem(ACHIEVEMENTS_KEY);
		String id=storage.getItem(ID_KEY);
		String eT=storage.getItem(TOTAL_KEY);
		String tR=storage.getItem(TUTORIAL_READED_KEY);
		
		if(encrptedAchievements==null){
			resetPlayerAchievements();
			
		}else{
			loadPlayerAchievements(encrptedAchievements);
		}
		
		if(tR==null){
			resetTutorialReaded();
			
		}else{
			loadTutorialReaded(tR);
		}
		
		if(id==null){
			resetID();
			
		}else{
			loadID(id);
		}
		
		if(eT==null){
			resetTotal();
		}else{
			loadTotal(eT);
		}
	}
	public void setKey(String encodedKey){
		this.encodedKey=encodedKey;
	}
	public String getKey(){
		return encodedKey;
	}
	public void clearKey(){
		this.encodedKey=null;
	}
	
	public void save(){
		encryptAchievements();
		storage.setItem(ACHIEVEMENTS_KEY,this.getEncryptedAchievement());
		
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
		int delta=oldScore<0?0:(score-oldScore);
		setTotal(getTotal()+delta);
		playerAchievements.get(type)[0]+=delta;
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
		save();
	}
	
	private void loadPlayerAchievements(String value){
		try{
			JSONObject object=JSONParser.parseStrict(MEngine.getCipher().decrypt(value)).isObject();
			this.encryptedAchvs=value;
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
	public String getEncryptedAchievement(){
		return this.encryptedAchvs;
	}
	private void encryptAchievements(){
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
		this.encryptedAchvs=MEngine.getCipher().encrypt(object.toString());
	}
	

	public boolean isTutorialReaded(){
		return this.tutorialReaded;
	}
	public void setTutorialReaded(boolean readed){
		this.tutorialReaded=readed;
		storage.setItem(TUTORIAL_READED_KEY, Boolean.toString(this.tutorialReaded));
	}
	
	private void resetTutorialReaded(){ 
		setTutorialReaded(false);
		
	}
	
	private void loadTutorialReaded(String value){
		this.tutorialReaded=Boolean.valueOf(value);
	}
	
	
	public void setID(String id){
		this.id=id;
		storage.setItem(ID_KEY,getID()==null?"":getID());
	}
	
	public String getID(){
		return id;
	}
	
	private void resetID(){
		setID(null);
	}
	
	private void loadID(String value){
		this.id=value;
	}
	
	
	public void setTotal(long total){
		this.total=total;
		storage.setItem(TOTAL_KEY,MEngine.getCipher().encrypt(Long.toString(total)));
	}
	public long getTotal(){
		return total;
	}
	private void resetTotal(){
		long total=0;
		for(WorldType type:WorldType.values()){
			total+=playerAchievements.get(type)[0];
		}
		setTotal(total);
	}
	private void loadTotal(String value){
		try{
			this.total=Long.valueOf(MEngine.getCipher().decrypt(value));
		}catch(RuntimeException e){
			resetTotal();
		}
	}
	public void overwite(Player player){
		storage.setItem(ACHIEVEMENTS_KEY,player.getEncryptedAchievements());
		loadPlayerAchievements(player.getEncryptedAchievements());
		setID(player.getID());
		setTotal(player.getTotal());
		setKey(player.getKey());
	}
	public Player getPlayer(){
		return new Player(getKey(),getID()==null?"":getID(),getTotal(),getEncryptedAchievement());
	}
}
