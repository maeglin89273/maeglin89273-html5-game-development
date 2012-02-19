/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator.GravityController;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public class GravityIndicator extends GeneralComponent {
	
	private SpriteSheet img;
	
	private final String textFont;
	
	private final GravityController controller;
	public GravityIndicator(Point p,double diameter,GravityController controller){
		super(p,controller.getAngle(),diameter,diameter);
		this.controller = controller;
		
		img=MEngine.getAssetManager().getSpriteSheet("images/gravity_indicator.png");
		textFont=ASBOTXConfigs.getCGFont((int)(diameter*0.55));
		
	}
	
	@Override
	public void update() {
		if(controller.isRotating()){
			controller.rotate();
			this.setAngle(controller.getAngle());
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.save();
		context.setTextAlign(TextAlign.CENTER);
		context.setTextBaseline(TextBaseline.MIDDLE);
		context.setFont(textFont);
		context.setFillStyle(ASBOTXConfigs.Color.TEXT);
		context.fillText("G",getX(),getY(),width);
		context.translate(getX(), getY());
		context.rotate(getAngle());
		context.drawImage(img.getImage(), -width/2, -height/2, width, height);
		context.restore();
		
	}
}
