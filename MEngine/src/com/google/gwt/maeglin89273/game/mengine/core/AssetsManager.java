package com.google.gwt.maeglin89273.game.mengine.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;
import com.google.gwt.user.client.ui.Image;




public class AssetsManager {
	
	private List<SpriteSheet> spriteSheets=new ArrayList<SpriteSheet>();
	
	private boolean loaded=true;
	private int loadedSuccessfullyCount=0;
	private int loadedUnsuccessfullyCount=0;
	private List<DataLoadedListener> dataLoadedListeners=new ArrayList<DataLoadedListener>();
	
	private String prefix;
	
	AssetsManager(String prefix){
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
			for(int i=dataLoadedListeners.size()-1;i>=0;i--){
				dataLoadedListeners.get(i).done();
				dataLoadedListeners.remove(i);
			}
			dataLoadedListeners=null;
		}
	}
	public int getErrorCount(){
		return this.loadedUnsuccessfullyCount;
	}
	public boolean isDataLoaded(){
		return loaded;
	}
	public void addDataLoadedListener(DataLoadedListener d){
		dataLoadedListeners.add(d);
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
		String fullPath=prefix+path;
		for(SpriteSheet sheet:spriteSheets){
			if(sheet.getPath().equals(fullPath))
				return sheet;
		}
		throw new IllegalArgumentException("The sprite sheet \""+path+"\" is not found." +
				"Please assured that it's under the\""+prefix+"\" directory.");
	}
	public interface DataLoadedListener{
		public void done();
	}
	
}
