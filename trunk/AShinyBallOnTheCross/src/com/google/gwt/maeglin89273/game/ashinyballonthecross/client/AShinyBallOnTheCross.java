package com.google.gwt.maeglin89273.game.ashinyballonthecross.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;




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