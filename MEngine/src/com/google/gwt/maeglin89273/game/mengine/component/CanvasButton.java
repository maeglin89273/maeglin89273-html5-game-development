/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;

/**
 * @author Maeglin Liao
 *
 */
public abstract class CanvasButton extends GeneralComponent {
	protected SpriteSheet buttonImg;
	protected SpriteBlock spriteBlock;
	protected CanvasButton(Point p,double w,double h,SpriteSheet img,SpriteBlock block){
		super(p, w, h);
		buttonImg=img;
		spriteBlock=block;
		
	}
	public abstract void doTask();
	public abstract boolean contain(Point p);
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.shared.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		Point nw=getPositionAt(PositionType.NORTHWEST);
		context.drawImage(buttonImg.getImage(), spriteBlock.getX(), spriteBlock.getY(), spriteBlock.getWidth(), spriteBlock.getHeight(),nw.getX(),nw.getY(), getWidth(), getHeight());
	}

}
