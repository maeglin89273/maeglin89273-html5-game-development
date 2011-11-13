/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.game;


import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;

/**
 * @author Maeglin Liao
 *
 */
public class GameInfo {
	private final int width;
	private final int height;
	private final String[] sheetsPath;
	private final boolean hasLoadingPage;
	public GameInfo(int width,int height ,boolean hasLoadingPage,String... spriteSheetPath){
		this.width = width;
		this.height = height;
		this.hasLoadingPage = hasLoadingPage;
		
		sheetsPath=spriteSheetPath;
	}
	public GameInfo(int width,int height ,String... spriteSheetPath){
		this(width, height, false, spriteSheetPath);
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
	public SpriteSheet[] getSpriteSheets() {
		SpriteSheet[] sheets=new SpriteSheet[sheetsPath.length];
		for(int i=0;i<sheets.length;i++){
			sheets[i]=new SpriteSheet(sheetsPath[i]);
		}
		return sheets;
	}
	/**
	 * @return the hasLoadingPage
	 */
	public boolean hasLoadingResourcesPage() {
		return hasLoadingPage;
	}
}
