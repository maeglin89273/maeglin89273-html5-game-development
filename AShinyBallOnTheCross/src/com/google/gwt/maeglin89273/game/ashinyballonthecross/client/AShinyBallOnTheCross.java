package com.google.gwt.maeglin89273.game.ashinyballonthecross.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.LeaderboardService;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.LeaderboardServiceAsync;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.WorldType;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;




/**
 * Entry position classes define <code>onModuleLoad()</code>.
 */
public class AShinyBallOnTheCross implements EntryPoint {
	
	/**
	 * This is the entry position method.
	 */
	public void onModuleLoad() {
		MEngine.init(new ASBOTXGame(),"resources/");
		MEngine.start();
		
	}
		
}