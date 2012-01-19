package com.google.gwt.maeglin89273.game.mengine.asset.sprite;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.maeglin89273.game.mengine.asset.Asset;



public class SpriteSheet extends Asset{
	
	private ImageElement imageE;
	
	public SpriteSheet(String url){
		super(url);
		imageE=Document.get().createImageElement();
		imageE.setSrc(getUrl());
	}
	public SpriteSheet(ImageElement e){
		super(e.getSrc());
		imageE=e;
	}
	public ImageElement getImage(){
		return imageE;
	}
	
	
	
	public int getWidth(){
		if(!isLoaded()){
			return 0;
		}
		return imageE.getWidth();
	}
	public int getHeight(){
		if(!isLoaded()){
			return 0;
		}
		return imageE.getHeight();
	}
	
	
	@Override
	public boolean equals(Object o){
		return (o instanceof SpriteSheet && ((SpriteSheet)o).getUrl().equals(getUrl()));
	}
	@Override 
	public int hashCode(){
		return 2;
	}
}