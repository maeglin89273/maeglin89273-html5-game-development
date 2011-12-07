/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.pseudo;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.DotDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class LevelContext {
	protected final Point screenCenter;
	protected double width;
	protected double height;
	
	/**
	 * 
	 */
	public LevelContext(Point screenCenter) {
		this.screenCenter = screenCenter;
		
	}
	public Point getScreenCenter(){
		return screenCenter.clone();
	}
	public double getLevelWidth(){
		return width;
	}
	public double getLevelHeight(){
		return height;
	}
	public abstract int getGravityInDegrees();
	public abstract AreaDefinerType[] getAreaDefinerTypes();
	public abstract LineDefinerType[] getLineDefinerTypes();
	public abstract DotDefinerType[] getDotDefinerTypes();
	public abstract void buildLevel(Creator creator,GameOverCallback callback);
	
}
