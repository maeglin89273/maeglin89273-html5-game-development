/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.*;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTCGamePage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.ASBOTCLoadingLevelPage;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.mengine.component.GameButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;

/**
 * @author Maeglin Liao
 *
 */
public class LevelSelectButton extends GameButton {
	private final int levelIdx;
	private final String font;
	 
	/**
	 * @param p
	 * @param w
	 * @param h
	 * @param block
	 */
	public LevelSelectButton(GeneralGame game,Point p,double bounds,int level) {
		super(game,p,bounds,bounds,new SpriteBlock(0,200+SpriteBlock.SPACING,200,200,MEngine.getAssetManager().getSpriteSheet("buttons.png")));
		this.levelIdx = level;
		this.font=ASBOTCConfigurations.getGameFont((int)(35*bounds/200));
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GameButton#doTask()
	 */
	@Override
	public void doTask() {
		Point sp=new Point(game.getWidth()/2.0,game.getHeight()/2.0);
		LevelContext level=null;
		switch(this.levelIdx){
			case 1:
				level=new Level1(sp);
				break;
			case 2:
				level=new Level2(sp);
				break;
			case 3:
				level=new Level3(sp);
				break;
			case 4:
				level=new Level4(sp);
				break;
			case 5:
				level=new Level5(sp);
				break;
		}
		game.setPage(new ASBOTCLoadingLevelPage(game,level));
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GameButton#contain(com.google.gwt.maeglin89273.game.mengine.physics.Point)
	 */
	@Override
	public boolean contain(Point p) {
		if(p.getX()>=getLeftX()&&p.getX()<=getRightX()&&p.getY()>=getTopY()&&p.getY()<=getBottomY())
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	
	@Override
	public void draw(Context2d context){
		super.draw(context);
		context.save();
		context.setTextAlign(TextAlign.CENTER);
		context.setTextBaseline(TextBaseline.MIDDLE);
		context.setFillStyle(ASBOTCConfigurations.Color.GRAY);
		context.setFont(font);
		context.fillText("Level "+levelIdx,getX(),getY());
		context.restore();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
