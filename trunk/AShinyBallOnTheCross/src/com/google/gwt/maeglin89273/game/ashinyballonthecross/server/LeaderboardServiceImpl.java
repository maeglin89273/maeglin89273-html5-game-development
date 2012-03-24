/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.server;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Leader;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.LeaderboardService;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Player;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.TransportablePlayer;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Maeglin Liao
 *
 */
public class LeaderboardServiceImpl extends RemoteServiceServlet implements LeaderboardService {
	
	private static final int LIMITED_LEADER_COUNT=10;
	
	@Override
	public Leader[] getLeaderboard() {
		Query query=new Query(Player.class.getSimpleName())
					.addSort(TransportablePlayer.TOTAL_PROPERTY,SortDirection.DESCENDING)
					.addSort(TransportablePlayer.DATE_PROPERTY, SortDirection.DESCENDING);
		List<Entity> leaderList=getDatastoreService().prepare(query).asList(FetchOptions.Builder.withLimit(LIMITED_LEADER_COUNT));
		Leader[] toReturn=new Leader[leaderList.size()];
		int offset=1;//a variable for leader's position
		for(int i=0;i<toReturn.length;i++){
			Entity entity=leaderList.get(i);
			toReturn[i]=new Leader(offset++,
					((String)entity.getProperty(TransportablePlayer.ID_PROPERTY)),
					(Date)entity.getProperty(TransportablePlayer.DATE_PROPERTY),
					((Long)entity.getProperty(TransportablePlayer.TOTAL_PROPERTY)));
		}
		return toReturn;
	}


	@Override
	public int queryPosition(String encodedKey) {
		DatastoreService datastore=getDatastoreService();
		Entity player;
		try {
			player = datastore.get(KeyFactory.stringToKey(encodedKey));
			Query query=new Query(Player.class.getSimpleName());
			query.addFilter(TransportablePlayer.TOTAL_PROPERTY, Query.FilterOperator.GREATER_THAN,player.getProperty(TransportablePlayer.TOTAL_PROPERTY));
			return 1+datastore.prepare(query).countEntities(FetchOptions.Builder.withDefaults());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			PlayerServiceImpl.LOG.log(Level.WARNING,"cannot find the player who want to query his position.");
			return -1;
		}
		
		
	}
	
	private DatastoreService getDatastoreService(){
		return DatastoreServiceFactory.getDatastoreService();
	}

}
