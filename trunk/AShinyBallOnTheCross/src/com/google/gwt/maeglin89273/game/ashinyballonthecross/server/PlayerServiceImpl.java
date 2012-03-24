/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.server;

import java.util.Date;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginRequest;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginResponse;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.PlayerCreatedResponse;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CreateStatus;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Player;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.PlayerService;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginResponse.Status;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.TransportablePlayer;
import com.google.gwt.maeglin89273.game.mengine.service.GoogleAccount;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Maeglin Liao
 *
 */
public class PlayerServiceImpl extends RemoteServiceServlet implements PlayerService {
		
	
	/**
	 * 
	 */
	public static final Logger LOG=Logger.getLogger(PlayerServiceImpl.class.getCanonicalName());
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.PlayerService#getLoginInfo(java.lang.String)
	 */
	@Override
	public CheckLoginResponse checkLogin(CheckLoginRequest request) {
		UserService svc=getUserService();
		User user=svc.getCurrentUser();
		LoginInfo loginInfo;
		CheckLoginResponse response;
		
		if(user!=null){
			loginInfo=new LoginInfo(LoginInfo.Status.LOGGED_IN,svc.createLogoutURL(request.getRquestUrl()),new GoogleAccount(user.getEmail(),user.getNickname()));
			DatastoreService datastore=getDatastoreService();
			TransportablePlayer localPlayer=request.getLocalPlayer();
			Key key=KeyFactory.createKey(Player.class.getSimpleName(), user.getEmail());
			String keyString=KeyFactory.keyToString(key);
			
			try{
				Entity player=datastore.get(key);
				
				if(localPlayer.getID().equals(player.getProperty(TransportablePlayer.ID_PROPERTY))){
					//the achievement has changed
					if(!player.getProperty(TransportablePlayer.ACHIEVEMENT_PROPERTY).equals(localPlayer.getEncryptedAchievements())){
						//if the date is greater than client timestamp, do download. Else, do upload 
						if(localPlayer.getTimestamp()!=null&&((Date)player.getProperty(TransportablePlayer.DATE_PROPERTY)).compareTo(localPlayer.getTimestamp())>0){
							//do download
							response=doDownload(player,loginInfo,keyString);
						}else{
							//do upload
							response=doUpload(datastore, player, loginInfo, keyString, localPlayer);
						}
					}else{
						//local player patch
						if(localPlayer.getTimestamp()==null){
							localPlayer.setTimestamp((Date)player.getProperty(TransportablePlayer.DATE_PROPERTY));
						}
						localPlayer.setKey(keyString);
						response=new CheckLoginResponse(Status.UPLOAD,loginInfo,localPlayer);
					}
				}else{
					//do download
					response=doDownload(player,loginInfo,keyString);
				}
			}catch(EntityNotFoundException e){
				//request to create a new player
				response=requestNewPlayer(keyString, loginInfo, localPlayer);
			}
			
		}else{
			loginInfo=new LoginInfo(LoginInfo.Status.LOGGED_OUT, svc.createLoginURL(request.getRquestUrl()));
			response=new CheckLoginResponse(Status.NOT_LOGGED_IN,loginInfo,null);
		}
		return response;
	}
	
	private CheckLoginResponse doDownload(Entity player,LoginInfo loginInfo,String keyString){
		String id=(String)player.getProperty(TransportablePlayer.ID_PROPERTY);
		long total=((Long)player.getProperty(TransportablePlayer.TOTAL_PROPERTY)).longValue();
		String achv=(String)player.getProperty(TransportablePlayer.ACHIEVEMENT_PROPERTY);
		Date timestamp=(Date)player.getProperty(TransportablePlayer.DATE_PROPERTY);
		
		return new CheckLoginResponse(Status.DOWNLOAD,loginInfo,new TransportablePlayer(keyString,id,total,achv, timestamp));
	}
	private CheckLoginResponse doUpload(DatastoreService datastore, Entity player,LoginInfo loginInfo,String keyString, TransportablePlayer localPlayer){
		Date timestamp=new Date();
		player.setProperty(TransportablePlayer.DATE_PROPERTY, timestamp);
		player.setProperty(TransportablePlayer.ACHIEVEMENT_PROPERTY, localPlayer.getEncryptedAchievements());
		player.setProperty(TransportablePlayer.TOTAL_PROPERTY, localPlayer.getTotal());
		
		datastore.put(player);
		
		localPlayer.setKey(keyString);
		localPlayer.setTimestamp(timestamp);
		return new CheckLoginResponse(Status.UPLOAD,loginInfo,localPlayer);
	}
	private CheckLoginResponse requestNewPlayer(String keyString, LoginInfo loginInfo, TransportablePlayer localPlayer){
		localPlayer.setKey(keyString);
		localPlayer.setID(null);
		return new CheckLoginResponse(Status.NEW_PLAYER,loginInfo,localPlayer);
	}
	
	@Override
	public PlayerCreatedResponse createNewPlayer(Player player) {
		User user=getUser();
		PlayerCreatedResponse response;
		try{
			checkLoggedIn(user);
		}catch(IllegalStateException e){
			response=new PlayerCreatedResponse(CreateStatus.NOT_LOGGED_IN);
		}
		
		DatastoreService datastore=getDatastoreService();
		Query query=new Query(Player.class.getSimpleName()).addFilter(TransportablePlayer.ID_PROPERTY, Query.FilterOperator.EQUAL,player.getID());
		if(datastore.prepare(query).asSingleEntity()==null){
			// TransportablePlayer id is not duplicated. Create a new player
			Entity newPlayer =new Entity(KeyFactory.stringToKey(player.getKey()));
			Date createTime =new Date();
			newPlayer.setProperty(TransportablePlayer.USER_PROPERTY, user);
			newPlayer.setProperty(TransportablePlayer.ID_PROPERTY, player.getID());
			newPlayer.setProperty(TransportablePlayer.DATE_PROPERTY, createTime);
			newPlayer.setProperty(TransportablePlayer.TOTAL_PROPERTY, player.getTotal());
			newPlayer.setProperty(TransportablePlayer.ACHIEVEMENT_PROPERTY, player.getEncryptedAchievements());
			
			datastore.put(newPlayer);
			LOG.info("new player \""+player.getID()+"\" is created.");
			response=new PlayerCreatedResponse(createTime);
		}else{
			// TransportablePlayer id is duplicated. Send the status back
			response=new PlayerCreatedResponse(CreateStatus.DUPLICATED);
		}
		
		return response;
	}
	
	@Override
	public Date saveAchievements(Player player) {
		checkLoggedIn();
		DatastoreService datastore=getDatastoreService();
		try{
			Date timestamp=new Date();
			Entity playerE=datastore.get(KeyFactory.stringToKey(player.getKey()));
			playerE.setProperty(TransportablePlayer.ACHIEVEMENT_PROPERTY, player.getEncryptedAchievements());
			playerE.setProperty(TransportablePlayer.TOTAL_PROPERTY, player.getTotal());
			playerE.setProperty(TransportablePlayer.DATE_PROPERTY, timestamp);
			datastore.put(playerE);
			
			LOG.info("The achievements have been saved.");
			
			return timestamp;
		}catch(EntityNotFoundException e){
			e.printStackTrace();
			LOG.warning("Cannot save this player's achievements.");
			throw new IllegalArgumentException("cannot save this player's achievements at the server.");
		}
	}
	
	
	private User getUser(){
		return getUserService().getCurrentUser();
	}
	private void checkLoggedIn(User user)throws IllegalStateException{
		if(user==null){
			throw new IllegalStateException("There is no user logging in currently.");
		}
	}
	private void checkLoggedIn()throws IllegalStateException{
		if(getUser()==null){
			throw new IllegalStateException("There is no user logging in currently.");
		}
	}
	private UserService getUserService(){
		return  UserServiceFactory.getUserService();
	}
	private DatastoreService getDatastoreService(){
		return DatastoreServiceFactory.getDatastoreService();
	}
}
