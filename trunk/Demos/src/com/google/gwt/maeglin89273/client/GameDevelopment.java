package com.google.gwt.maeglin89273.client;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.shared.test.volcanogame.ElectronicsVolcanoGame;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GameDevelopment implements EntryPoint {
	
	public void onModuleLoad() {
		MEngine.init(new ElectronicsVolcanoGame(),"images/");
		MEngine.start();
		
	}
}
