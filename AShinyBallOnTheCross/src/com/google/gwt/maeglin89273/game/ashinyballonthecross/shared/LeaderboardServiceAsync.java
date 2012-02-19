/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Maeglin Liao
 *
 */
public interface LeaderboardServiceAsync {

	void getLeaderboard(AsyncCallback<Leader[]> callback);

	void queryPosition(String encodedKey, AsyncCallback<Integer> callback);
}
