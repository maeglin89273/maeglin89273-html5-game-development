/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class RedGoblet extends Goblet {
	private final int rotatedAngle;
	/**
	 * @param creator
	 * @param p
	 * @param angle
	 * @param defaultColor
	 * @param contactColor
	 */
	public RedGoblet( Point p, double angle,int rotatedAngle) {
		super( p, angle, ASBOTXConfigs.Color.DARK_GRAY, ASBOTXConfigs.Color.RED);
		this.rotatedAngle = rotatedAngle;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.Goblet#doEffect()
	 */
	@Override
	protected void doEffect() {
		creator.getGravityController().setAngle(this.rotatedAngle);
	}

}
