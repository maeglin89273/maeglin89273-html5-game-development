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
public abstract class CircleButton extends GameButton{
	protected double radius;
	
	/**
	 * @param p
	 * @param w
	 * @param h
	 * @param block
	 */
	public CircleButton(Point p, double r, SpriteBlock block) {
		super(p, 2*r,2*r, block);
		this.radius=r;
	}
	public double getRadius(){
		return radius;
	}

	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GameButton#contain(com.google.gwt.maeglin89273.game.mengine.physics.Point)
	 */
	@Override
	public boolean contains(Point p) {
		return radius*radius>=p.delta(getPosition()).getSquare();
	}

	

}
