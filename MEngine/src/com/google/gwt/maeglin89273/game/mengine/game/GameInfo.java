/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.game;

import com.google.gwt.maeglin89273.game.mengine.asset.AssetsBundleWithLookup;

/**
 * @author Maeglin Liao
 *
 */
public class GameInfo {
	private final int width;
	private final int height;
	private final AssetsBundleWithLookup bundle;
	
	public GameInfo(int width,int height ,AssetsBundleWithLookup bundle){
		this.width = width;
		this.height = height;
		this.bundle=bundle;
	}
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @return the sheets
	 */
	public AssetsBundleWithLookup getAssetsBundle() {
		return bundle;
	}
}
