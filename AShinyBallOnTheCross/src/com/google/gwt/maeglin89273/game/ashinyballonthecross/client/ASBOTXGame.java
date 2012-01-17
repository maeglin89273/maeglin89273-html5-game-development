package com.google.gwt.maeglin89273.game.ashinyballonthecross.client;



import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTXWelcomePage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTXLoadingAssetsPage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.resources.ASBOTXAssetsBundleWithLookup;

import com.google.gwt.maeglin89273.game.mengine.game.GameInfo;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.page.MEngineLogoPage;
import com.google.gwt.maeglin89273.game.mengine.page.MaeglinStudiosPage;




/**
 * @author Maeglin Liao
 */
public class ASBOTXGame extends GeneralGame {
	private Player player;
	public ASBOTXGame(){
		super(new GameInfo(720,540,ASBOTXAssetsBundleWithLookup.INSTANCE));
	}
	
	@Override
	public void init() {
		setPage(new ASBOTXLoadingAssetsPage(new MEngineLogoPage(new MaeglinStudiosPage(new ASBOTXWelcomePage(),"images/"),"images/")));
		//setPage(new ASBOTXLoadingAssetsPage(this,new ASBOTXTestingPage()));
		//setPage(new ASBOTXLoadingAssetsPage(this,new ASBOTXGamePage(new Level5(new Point(getWidth()/2,getHeight()/2)))));
		//setPage(new ASBOTXLoadingAssetsPage(this,new ASBOTXWelcomePage()));
	}
	public Player getPlayer(){
		return player;
	}
	public void initPlayer(){
		player=new Player();
	}
}
