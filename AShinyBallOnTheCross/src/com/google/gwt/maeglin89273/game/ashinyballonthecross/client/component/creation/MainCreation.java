/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class MainCreation extends Creation {
	protected final boolean beControlled;
	/**
	 * @param creator
	 * @param cotentPower
	 * @param p
	 * @param w
	 * @param h
	 * @param a
	 */
	public MainCreation(Creator creator, int cotentPower, boolean beControlled, Point p,
			double w, double h, double a) {
		super(creator, cotentPower, p, w, h, a);
		this.beControlled = beControlled;
		if(isVerified()&&this.beControlled){
			this.creator.add(this);
		}
		
	}
	@Override
	public void destroy(){
		if(beControlled){
			this.creator.remove(this);
		}
		super.destroy();
	}

}
