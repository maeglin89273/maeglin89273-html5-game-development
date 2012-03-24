/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Maeglin Liao
 *
 */
public interface PlayerServiceAsync {

	void checkLogin(CheckLoginRequest reuqest,
			AsyncCallback<CheckLoginResponse> callback);

	void createNewPlayer(Player player,
			AsyncCallback<PlayerCreatedResponse> callback);

	void saveAchievements(Player player, AsyncCallback<Date> callback);

}
