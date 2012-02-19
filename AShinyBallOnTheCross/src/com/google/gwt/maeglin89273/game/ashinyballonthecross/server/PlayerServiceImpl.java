/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.server;

import java.util.Date;
import java.util.logging.Level;
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
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CreateStatus;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Player;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.PlayerService;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginResponse.Status;
import com.google.gwt.maeglin89273.game.mengine.service.GoogleAccount;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Maeglin Liao
 *
 */
public class PlayerServiceImpl extends RemoteServiceServlet implements PlayerService {
		
	public static final Logger LOG=Logger.getLogger(LeaderboardServiceImpl.class.getCanonicalName());
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
			Player localPlayer=request.getLocalPlayer();
			Key key=KeyFactory.createKey(Player.class.getSimpleName(), user.getEmail());
			String keyString=KeyFactory.keyToString(key);
			try{
				Entity player=datastore.get(key);
				
				if(localPlayer.getID().equals(player.getProperty(Player.ID_PROPERTY))){
					//the achievement has changed
					if(!localPlayer.getEncryptedAchievements().equals(player.getProperty(Player.ACHIEVEMENT_PROPERTY))){
						//do upload
						player.setProperty(Player.DATE_PROPERTY, new Date());
						player.setProperty(Player.ACHIEVEMENT_PROPERTY, localPlayer.getEncryptedAchievements());
						player.setProperty(Player.TOTAL_PROPERTY, localPlayer.getTotal());
						
						datastore.put(player);
					}
					localPlayer.setKey(keyString);
					response=new CheckLoginResponse(Status.UPLOAD,loginInfo,localPlayer);
				}else{
					//do download
					String id=(String)player.getProperty(Player.ID_PROPERTY);
					long total=((Long)player.getProperty(Player.TOTAL_PROPERTY)).longValue();
					String achv=(String)player.getProperty(Player.ACHIEVEMENT_PROPERTY);
					
					response=new CheckLoginResponse(Status.DOWNLOAD,loginInfo,new Player(keyString,id,total,achv));
				}
			}catch(EntityNotFoundException e){
				localPlayer.setKey(keyString);
				localPlayer.setID(null);
				response=new CheckLoginResponse(Status.NEW_PLAYER,loginInfo,localPlayer);
			}
			
		}else{
			loginInfo=new LoginInfo(LoginInfo.Status.LOGGED_OUT, svc.createLoginURL(request.getRquestUrl()));
			response=new CheckLoginResponse(Status.NOT_LOGGED_IN,loginInfo,null);
		}
		return response;
	}
	
	@Override
	public CreateStatus createNewPlayer(Player player) {
		User user=getUser();
		CreateStatus status;
		try{
			checkUser(user);
		}catch(IllegalStateException e){
			status=CreateStatus.NOT_LOGGED_IN;
		}
		
		DatastoreService datastore=getDatastoreService();
		Query query=new Query(Player.class.getSimpleName()).addFilter(Player.ID_PROPERTY, Query.FilterOperator.EQUAL,player.getID());
		if(datastore.prepare(query).asSingleEntity()==null){
			// Player id is not duplicated. Create a new player
			Entity newPlayer =new Entity(KeyFactory.stringToKey(player.getKey()));
			newPlayer.setProperty(Player.USER_PROPERTY, user);
			newPlayer.setProperty(Player.ID_PROPERTY, player.getID());
			newPlayer.setProperty(Player.DATE_PROPERTY, new Date());
			newPlayer.setProperty(Player.TOTAL_PROPERTY, player.getTotal());
			newPlayer.setProperty(Player.ACHIEVEMENT_PROPERTY, player.getEncryptedAchievements());
			
			datastore.put(newPlayer);
			LOG.log(Level.INFO,"new player is created.");
			status=CreateStatus.SUCCESS;
		}else{
			// Player id is duplicated. Send the status back
			status=CreateStatus.DUPLICATED;
		}
		
		return status;
	}
	
	@Override
	public void saveAchievements(Player player) {
		DatastoreService datastore=getDatastoreService();
		try{
			Entity playerE=datastore.get(KeyFactory.stringToKey(player.getKey()));
			playerE.setProperty(Player.ACHIEVEMENT_PROPERTY, player.getEncryptedAchievements());
			playerE.setProperty(Player.TOTAL_PROPERTY, player.getTotal());
			playerE.setProperty(Player.DATE_PROPERTY, new Date());
			datastore.put(playerE);
		}catch(EntityNotFoundException e){
			e.printStackTrace();
			LOG.log(Level.WARNING,"cannot find the player who want to query his position.");
		}
	}
	private User getUser(){
		return getUserService().getCurrentUser();
	}
	private void checkUser(User user)throws IllegalStateException{
		if(user==null){
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
