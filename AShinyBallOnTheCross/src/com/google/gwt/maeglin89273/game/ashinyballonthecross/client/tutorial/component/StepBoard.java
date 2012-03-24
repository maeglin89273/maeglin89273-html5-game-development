/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class StepBoard extends GeneralComponent{
	private final GameLabel titleLabel;
	private final SpriteBlock block;
	public StepBoard(Point leftTopCorner,double centerX) {
		super(leftTopCorner, 200, 420);
		this.titleLabel=new GameLabel(new Point(centerX,30),
				TextAlign.CENTER,TextBaseline.MIDDLE,null, ASBOTXConfigs.Color.GRAY, ASBOTXConfigs.getCGFont(32));
		this.block=new SpriteBlock(0,0,200,420,MEngine.getAssetManager().getSpriteSheet("images/tutorial_steps.png"));
	}
	
	public void nextStep(String title, int x,int y){
		titleLabel.setText(title);
		block.setPosition(x, y);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Context2d context) {
		this.titleLabel.draw(context);
		context.drawImage(block.getSheetImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(),getX(),getY(), getWidth(), getHeight());
	}
	
}
