/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Maeglin Liao
 *
 */
@RemoteServiceRelativePath("leaderboard")
public interface LeaderboardService extends RemoteService {
	public Leader[] getLeaderboard();
	public int queryPosition(String encodedKey);
}
