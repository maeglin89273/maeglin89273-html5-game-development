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
@RemoteServiceRelativePath("player")
public interface PlayerService extends RemoteService {
	public CheckLoginResponse checkLogin(CheckLoginRequest reuqest);
	public void saveAchievements(Player player);
	public CreateStatus createNewPlayer(Player player);
}
