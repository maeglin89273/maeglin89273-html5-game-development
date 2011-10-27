package com.google.gwt.maeglin89273.game.mengine.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;
import com.google.gwt.user.client.ui.Image;




public class AssetManager {
	
	private List<SpriteSheet> spriteSheets=new ArrayList<SpriteSheet>();
	
	
	private boolean loaded=true;
	private int loadedSuccessfullyCount=0;
	private int loadedUnsuccessfullyCount=0;
	private Done done=null;
	
	private String prefix;
	AssetManager(String prefix){
		this.prefix=prefix;
	}
	public String getAssetsPrefix(){
		return prefix;
	}
	public int getLoadedPercentage(){
		return (int)((((double)loadedSuccessfullyCount)/spriteSheets.size())*100);
	}
	private void checkDoesLoadDone(){
		if(loadedSuccessfullyCount==spriteSheets.size()){//spriteSheets.length+other assets 
			loaded=true;
			if(done!=null)
				done.execute();
		}
	}
	public int getErrorCount(){
		return this.loadedUnsuccessfullyCount;
	}
	public boolean isDataLoaded(){
		return loaded;
	}
	void waitDataLoaded(Done d){
		done=d;
	}
	
	
	
	public void loadSpriteSheets(SpriteSheet... sheets){
		if(sheets!=null){
			loaded=false;
			for(SpriteSheet sheet:sheets){
				sheet.loadImage(new Image(sheet.getPath()));
				spriteSheets.add(sheet);
			}
			
		}
		
	}
	public void spriteSheetOnLoad(){
		this.loadedSuccessfullyCount++;
		checkDoesLoadDone();
	}
	public void spriteSheetLoadFailed() {
		this.loadedUnsuccessfullyCount++;
	}
	public SpriteSheet getSpriteSheet(String path){
		path=prefix+path;
		for(SpriteSheet sheet:spriteSheets){
			if(sheet.getPath().equals(path))
				return sheet;
		}
		throw new IllegalArgumentException("The sprite sheet is not founded.");
	}
	interface Done{
		public void execute();
	}
	
}
