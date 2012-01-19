/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.component.button;

import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class BoxButton extends GameButton{

	/**
	 * @param p
	 * @param w
	 * @param h
	 * @param block
	 */
	public BoxButton(Point p, double w, double h, SpriteBlock block) {
		super(p, w, h, block);
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GameButton#contain(com.google.gwt.maeglin89273.game.mengine.physics.Point)
	 */
	@Override
	public boolean contains(Point p) {
		return p.getX()>=getLeftX()&&p.getX()<=getRightX()&&p.getY()>=getTopY()&&p.getY()<=getBottomY();
	}

	

}
