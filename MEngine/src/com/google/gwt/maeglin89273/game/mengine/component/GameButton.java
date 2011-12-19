/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;

/**
 * @author Maeglin Liao
 *
 */
public abstract class GameButton extends GeneralComponent {
	protected SpriteBlock spriteBlock;
	protected GeneralGame game;
	protected GameButton(Point p,double w,double h,SpriteBlock block){
		this(null, p, w, h, block);
	}
	protected GameButton(GeneralGame game,Point p,double w,double h,SpriteBlock block){
		super(p, 0, w, h);
		spriteBlock=block;
		this.game=game;
	}
	public abstract void doTask();
	public abstract boolean contain(Point p);
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.drawImage(spriteBlock.getSheetImage(), spriteBlock.getX(), spriteBlock.getY(), spriteBlock.getWidth(), spriteBlock.getHeight(),getLeftX(),getTopY(), getWidth(), getHeight());
	}
}
