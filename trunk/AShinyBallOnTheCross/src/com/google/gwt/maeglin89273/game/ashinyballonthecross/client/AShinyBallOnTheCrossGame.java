package com.google.gwt.maeglin89273.game.ashinyballonthecross.client;



import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTCWelcomePage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTCGamePage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTCLoadingDataPage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.TestCollisionGamePage;
import com.google.gwt.maeglin89273.game.mengine.game.GameInfo;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.page.MEngineLogoPage;
import com.google.gwt.maeglin89273.game.mengine.page.MaeglinStudiosPage;



/**
 * @author Maeglin Liao
 */
public class AShinyBallOnTheCrossGame extends GeneralGame {
	public AShinyBallOnTheCrossGame(){
		super(new GameInfo(720,540,true,"creations.png","gravity_indicator.png","shinyball.png","definers_icons.png","buttons.png","MEngine_logo.png","MStudios_landscape.png"));
	}
	
	@Override
	public void init() {
		setPage(new ASBOTCLoadingDataPage(this,new ASBOTCWelcomePage(this)));
		//setPage(new ASBOTCLoadingDataPage(this,new TestCollisionGamePage(this)));
		//setPage(new ASBOTCLoadingDataPage(this,new ASBOTCGamePage(this)));
	}
	
}
