/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.component.button;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class GameButton extends GeneralComponent {
	protected SpriteBlock spriteBlock;
	
	protected GameButton(Point p,double w,double h,SpriteBlock block){
		super(p, 0, w, h);
		spriteBlock=block;
	}
	public boolean onClick(Point p){
		if(contains(p)){
			doTask();
			return true;
		}
		return false;
	}
	public abstract void doTask();
	public abstract boolean contains(Point p);
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.drawImage(spriteBlock.getSheetImage(), spriteBlock.getX(), spriteBlock.getY(), spriteBlock.getWidth(), spriteBlock.getHeight(),getLeftX(),getTopY(), getWidth(), getHeight());
	}
}
