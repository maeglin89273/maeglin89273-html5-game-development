/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.*;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTXLoadingLevelPage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;

/**
 * @author Maeglin Liao
 *
 */
public class LevelSelectButton extends BoxButton{
	private final WorldType world;	
	
	private final int levelNum;
	private final String scoreText;
	
	private final String numberFont;
	private final String bestScoreFont;
	
	private final float scoreY;
	private final boolean unlocked;
	/**
	 * @param p
	 * @param w
	 * @param h
	 * @param block
	 */
	public LevelSelectButton(Point p,double bounds,WorldType world,int level,boolean unlocked) {
		super(p,(int)bounds,(int)bounds,
				(unlocked?new SpriteBlock(200+SpriteBlock.SPACING,200+SpriteBlock.SPACING,200,200,ASBOTXConfigs.getButtonSpriteSheet()):
						new SpriteBlock(2*(200+SpriteBlock.SPACING),200+SpriteBlock.SPACING,200,200,ASBOTXConfigs.getButtonSpriteSheet())));
		this.unlocked=unlocked;
		this.world = world;
		this.levelNum = level;
		this.numberFont=ASBOTXConfigs.getGameFont((int)(bounds/4));
		if(unlocked){
			this.bestScoreFont=ASBOTXConfigs.getGameFont((int)(bounds/6));
			this.scoreText=((ASBOTXGame)MEngine.getGeneralGame()).getPlayer().getScoreAt(world, level)+"/"+Level.queryFullPower(world,level);
			this.scoreY=(float)(getBottomY()+bounds/12+2.5f);
		}else{
			this.bestScoreFont=null;
			this.scoreText=null;
			this.scoreY=0;
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GameButton#doTask()
	 */
	@Override
	public void doTask() {
		if(unlocked){
			MEngine.getGeneralGame().setPage(new ASBOTXLoadingLevelPage("levels/"+world.toString()+"_level_"+levelNum+".json"));
		}
	}

	

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	
	@Override
	public void draw(Context2d context){
		super.draw(context);
		if(unlocked){
			context.save();
			context.setTextAlign(TextAlign.CENTER);
			context.setTextBaseline(TextBaseline.MIDDLE);
			context.setFillStyle(ASBOTXConfigs.Color.GRAY);
			context.setFont(numberFont);
			context.fillText(Integer.toString(levelNum),getX(),getY());
			context.setFillStyle(ASBOTXConfigs.Color.TRANSPARENT_BLUE);
			context.setFont(bestScoreFont);
			context.fillText(scoreText, getX(), scoreY);
			context.restore();
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
