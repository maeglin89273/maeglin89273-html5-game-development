/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level;

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
	private final Point viewPoint;
	private final int  fullPower;
	protected final double width;
	protected final double height;
	
	/**
	 * 
	 */
	public LevelContext(Point screenCenter,Point cameraViewPoint,double width,double height,int fullPower) {
		this.screenCenter = screenCenter;
		this.viewPoint = cameraViewPoint;
		this.width = width;
		this.height = height;
		this.fullPower = fullPower;
		
	}
	/**
	 * @return the viewPoint
	 */
	public Point getCameraViewPoint() {
		return viewPoint.clone();
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
	public int getFullPower(){
		return fullPower;
	}
	public abstract int getGravityAngleInDegrees();
	public abstract AreaDefinerType[] getAreaDefinerTypes();
	public abstract LineDefinerType[] getLineDefinerTypes();
	public abstract DotDefinerType[] getDotDefinerTypes();
	public abstract void buildLevel(Creator creator,GameOverCallback callback);
}
