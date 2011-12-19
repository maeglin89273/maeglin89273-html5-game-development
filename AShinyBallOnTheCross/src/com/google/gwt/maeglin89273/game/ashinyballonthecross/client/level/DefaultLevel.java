/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.DotDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class DefaultLevel extends LevelContext {

	/**
	 * @param screenCenter
	 * @param cameraViewPoint
	 * @param width
	 * @param height
	 */
	public DefaultLevel(double width, double height) {
			
		super(new Point(width/2,height/2), null, width, height,0);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getGravityAngleInDegrees()
	 */
	@Override
	public int getGravityAngleInDegrees() {
		// TODO Auto-generated method stub
		return 90;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getAreaDefinerTypes()
	 */
	@Override
	public AreaDefinerType[] getAreaDefinerTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getLineDefinerTypes()
	 */
	@Override
	public LineDefinerType[] getLineDefinerTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getDotDefinerTypes()
	 */
	@Override
	public DotDefinerType[] getDotDefinerTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#buildLevel(com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator, com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback)
	 */
	@Override
	public void buildLevel(Creator creator, GameOverCallback callback) {
		// TODO Auto-generated method stub

	}

}
