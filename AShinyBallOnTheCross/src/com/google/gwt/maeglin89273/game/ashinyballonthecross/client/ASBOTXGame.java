package com.google.gwt.maeglin89273.game.ashinyballonthecross.client;



import com.google.gwt.core.client.GWT;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTXLoadingAssetsPage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.resources.ASBOTXAssetsBundleWithLookup;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.LeaderboardService;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.PlayerService;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.PlayerServiceAsync;

import com.google.gwt.maeglin89273.game.mengine.game.GameInfo;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;




/**
 * @author Maeglin Liao
 */
public class ASBOTXGame extends GeneralGame {
	private LocalPlayer localPlayer;
	private LoginInfo loginInfo;
	
	private PlayerServiceAsync playerSvc=GWT.create(PlayerService.class);
	public ASBOTXGame(){
		super(new GameInfo(720,540,ASBOTXAssetsBundleWithLookup.INSTANCE));
	}
	
	@Override
	public void init() {
		setPage(new ASBOTXLoadingAssetsPage());
	}
	public LocalPlayer getLocalPlayer(){
		return localPlayer;
	}
	public void initLocalPlayer(){
		localPlayer=new LocalPlayer();
	}
	public PlayerServiceAsync getPlayerService(){
		if(playerSvc==null){
			playerSvc=GWT.create(LeaderboardService.class); 
		}
		return playerSvc;
	}
	public void setLoginInfo(LoginInfo loginInfo){
		this.loginInfo=loginInfo;
	}
	public LoginInfo getLoginInfo(){
		return this.loginInfo;
	}
}
