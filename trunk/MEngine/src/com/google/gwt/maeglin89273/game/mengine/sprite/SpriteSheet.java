package com.google.gwt.maeglin89273.game.mengine.sprite;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.maeglin89273.game.mengine.core.AssetsManager;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class SpriteSheet implements LoadHandler,ErrorHandler{
	
	
	private String path;
	private ImageElement imageE;
	private Image img;
	private boolean imageLoaded=false;
	
	
	public SpriteSheet(String path){
		this.path=MEngine.getAssetsManager().getAssetsPrefix()+path;
	}
	public ImageElement getImage(){
		if(!imageLoaded)
			throw new IllegalStateException("The image hasn't loaded");
		return imageE;
	}
	public void loadImage(Image img){
		this.img=img;
		this.img.addLoadHandler(this);
		this.img.addErrorHandler(this);
		this.img.setVisible(false);
		RootPanel.get().add(this.img);
	}
	public String getPath(){
		return path;
	}
	public boolean isImageLoaded(){
		return imageLoaded;
	}
	public int getWidth(){
		if(img==null){
			return 0;
		}
		return img.getWidth();
	}
	public int getHeight(){
		if(img==null){
			return 0;
		}
		return img.getHeight();
	}
	@Override
	public void onError(ErrorEvent event) {
		MEngine.getAssetsManager().spriteSheetLoadFailed();
		
	}
	@Override
	public void onLoad(LoadEvent event) {
		imageE=(ImageElement)img.getElement().cast();
		RootPanel.get().remove(img);
		imageLoaded=true;
		MEngine.getAssetsManager().spriteSheetOnLoad();
		
	}
	@Override
	public boolean equals(Object o){
		return (o instanceof SpriteSheet && ((SpriteSheet)o).getPath().equals(getPath()));
	}
}