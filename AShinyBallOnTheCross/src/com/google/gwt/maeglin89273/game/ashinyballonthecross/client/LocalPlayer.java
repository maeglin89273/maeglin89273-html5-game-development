/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.json.client.*;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Player;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.TransportablePlayer;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.WorldType;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;


/**
 * @author Maeglin Liao
 *
 */
public class LocalPlayer implements Player{
	private static final String STORAGE_VERSION="v1.1.0";
	
	private static final String MENU_INDEX_KEY="MI";
	private static final String ACHIEVEMENTS_KEY="ACHVS";
	private static final String ID_KEY="ID_CACHED";
	private static final String VERSION_KEY="V";
	private static final String TIMESTAMP_KEY="T";
	
	private Storage storage;
	
	private Map<WorldType,int[]> playerAchievements=new HashMap<WorldType,int[]>();
	private long total;
	private int menuIndex;
	private String id;
	private Date timestamp;
	
	private String encryptedAchvs;
	private String encodedKey;
	public LocalPlayer(){
		storage=MEngine.getLocalStorage();
		String version=storage.getItem(VERSION_KEY);
		String index=storage.getItem(MENU_INDEX_KEY);
		String encrptedAchievements=storage.getItem(ACHIEVEMENTS_KEY);
		String id=storage.getItem(ID_KEY);
		String timestamp=storage.getItem(TIMESTAMP_KEY);
		
		if(!version.equals(STORAGE_VERSION)){
			storage.setItem(VERSION_KEY, STORAGE_VERSION);
			cleanNoNeededKeys(storage);
		}
		
		if(encrptedAchievements==null){
			resetPlayerAchievements();
			save();
		}else{
			loadPlayerAchievements(encrptedAchievements);
			WorldType[] fullWorlds=WorldType.values();
			
			//If there are new worlds that haven't been saved, so save the new worlds.
			if(playerAchievements.size()<fullWorlds.length){
				
				int[] scores;
				for(int i=playerAchievements.size();i<fullWorlds.length;i++){
					scores=new int[Level.LEVEL_COUNT+1];//index 0 for total per world
					for(int j=1;j<scores.length;j++){
						scores[j]=-1;
					}
					playerAchievements.put(fullWorlds[i], scores);
				}
				save();
			}
		}
		loadTotal();
		
		if(index==null){
			resetMenuIndex();
		}else{
			loatMenuIndex(index);
		}
		
		if(id==null){
			resetID();
			
		}else{
			loadID(id);
		}
		
		if(timestamp!=null){
			loadTimestamp(timestamp);
		}
		
	}
	public void setMenuIndex(int index){
		this.menuIndex=index;
		storage.setItem(MENU_INDEX_KEY, Integer.toString(this.menuIndex));
	}
	public int getMenuIndex(){
		return menuIndex;
	}
	private void loatMenuIndex(String value) {
		this.menuIndex=Integer.parseInt(value);
	}

	private void resetMenuIndex() {
		setMenuIndex(0);
	}

	@Override
	public void setKey(String encodedKey){
		this.encodedKey=encodedKey;
	}
	@Override
	public String getKey(){
		return encodedKey;
	}
	public void clearKey(){
		this.encodedKey=null;
	}
	
	public void save(){
		setEncryptedAchievements(encryptAchievements());
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
	public int getTotalInWorld(WorldType world){
		return playerAchievements.get(world)[0];
	}
	
	@Override
	public String getEncryptedAchievements(){
		return this.encryptedAchvs;
	}
	@Override
	public void setEncryptedAchievements(String encryptedAchvs){
		this.encryptedAchvs=encryptedAchvs;
		storage.setItem(ACHIEVEMENTS_KEY,this.encryptedAchvs);
	}
	private String encryptAchievements(){
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
	
	/**
	 * reset player's achievements
	 * @return
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
		//playerAchievements.get(WorldType.INTRO)[1]=0;
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
					//the required score depends on level,may use queryLevelRequiedScore(WorldType type,level)
					scores[i]=(int)worldScores.get(i).isNumber().doubleValue();
				}
				playerAchievements.put(type, scores);
			}
		}catch(RuntimeException e){
			resetPlayerAchievements();
			save();
		}
	}
	
	@Override
	public void setID(String id){
		this.id=id;
		storage.setItem(ID_KEY,getID()==null?"":getID());
	}
	@Override
	public String getID(){
		return id;
	}
	
	private void resetID(){
		setID(null);
	}
	
	private void loadID(String value){
		this.id=value;
	}
	
	@Override
	public void setTotal(long total){
		this.total=total;
	}
	@Override
	public long getTotal(){
		return total;
	}
	public void loadTotal(){
		long total=0;
		for(WorldType type:WorldType.values()){
			total+=playerAchievements.get(type)[0];
		}
		setTotal(total);
	}
	@Override
	public Date getTimestamp() {
		return timestamp;
	}
	@Override
	public void setTimestamp(Date timestamp) {
		this.timestamp=timestamp;
		storage.setItem(TIMESTAMP_KEY, Long.toString(this.timestamp.getTime()));
	}
	
	private void loadTimestamp(String value){
		this.timestamp=new Date(Long.valueOf(value));
	}
	public void overwite(Player player){
		setEncryptedAchievements(player.getEncryptedAchievements());
		loadPlayerAchievements(player.getEncryptedAchievements());
		setID(player.getID());
		setTotal(player.getTotal());
		setTimestamp(player.getTimestamp());
		setKey(player.getKey());
	}
	public TransportablePlayer getPlayer(){
		return new TransportablePlayer(getKey(),getID()==null?"":getID(),getTotal(),getEncryptedAchievements(),getTimestamp());
	}
	
	private static void cleanNoNeededKeys(Storage storage) {
		Set<String> keys=new HashSet<String>();
		keys.add(VERSION_KEY);
		keys.add(ACHIEVEMENTS_KEY);
		keys.add(ID_KEY);
		keys.add(TIMESTAMP_KEY);
		
		StorageMap storageMap=new StorageMap(storage);
		for(Map.Entry<String,String> entry:storageMap.entrySet()){
			if(!keys.contains(entry.getKey())){
				storage.removeItem(entry.getKey());
			}
		}
	}
	
}
