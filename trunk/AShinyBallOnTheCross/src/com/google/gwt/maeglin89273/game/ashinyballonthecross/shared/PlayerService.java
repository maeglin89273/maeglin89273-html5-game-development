/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Maeglin Liao
 *
 */
@RemoteServiceRelativePath("player")
public interface PlayerService extends RemoteService {
	public CheckLoginResponse checkLogin(CheckLoginRequest reuqest);
	public Date saveAchievements(Player player);
	public PlayerCreatedResponse createNewPlayer(Player player);
	
}
