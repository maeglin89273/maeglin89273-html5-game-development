package com.google.gwt.maeglin89273.game.physicsbattle.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PhysicsBattle implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		MEngine.init(new PhysicsBattleGame(),"images/");
		MEngine.start();	
		
	}
}
