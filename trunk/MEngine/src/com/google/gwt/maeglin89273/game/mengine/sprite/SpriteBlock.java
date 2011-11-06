package com.google.gwt.maeglin89273.game.mengine.sprite;

import com.google.gwt.dom.client.ImageElement;

public class SpriteBlock {
	public static final int SPACITNG=10;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private final SpriteSheet sheet;
	public SpriteBlock(int x,int y,int width,int height,SpriteSheet sheet) {
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
		this.sheet=sheet;
	}
	public SpriteBlock(int width,int height,SpriteSheet sheet){
		this(0, 0, width, height,sheet);
	}
	public int getX() {
		return x;
	}
	public void setPosition(int x,int y){
		setX(x);
		setY(y);
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public SpriteSheet getSpriteSheet(){
		return sheet;
	}
	public ImageElement getSheetImage(){
		return sheet.getImage();
	}
}