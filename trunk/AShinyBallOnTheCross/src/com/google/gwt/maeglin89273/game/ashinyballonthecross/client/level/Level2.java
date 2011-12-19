/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Cross;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.DotDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.SimpleStaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class Level2 extends LevelContext {

	/**
	 * @param screenCenter
	 */
	public Level2(Point screenCenter) {
		super(screenCenter,screenCenter,screenCenter.getX()*2,screenCenter.getY()*2.25,300);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getAreaDefinerTypes()
	 */
	@Override
	public AreaDefinerType[] getAreaDefinerTypes() {
		
		return new AreaDefinerType[]{AreaDefinerType.ARROW_AREA,AreaDefinerType.GRAVITATIONAL_AREA};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getLineDefinerTypes()
	 */
	@Override
	public LineDefinerType[] getLineDefinerTypes() {
		
		return new LineDefinerType[]{LineDefinerType.SIMPLE_STATIC_LINE,LineDefinerType.ELASTIC_LINE};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getDotDefinerTypes()
	 */
	@Override
	public DotDefinerType[] getDotDefinerTypes() {
		
		return new DotDefinerType[]{DotDefinerType.POLYGON_BOMB_DOT};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#buildLevel(com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator, com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback)
	 */
	@Override
	public void buildLevel(Creator creator, GameOverCallback callback) {
		new ShinyBall(creator,new Point(500,150));
		new SimpleStaticLine(creator,new Point(160,270),new Point(560,270));
		new Cross(creator, new Point(getScreenCenter().getX(),385),getGravityAngleInDegrees(), callback);
	}

	@Override
	public int getGravityAngleInDegrees() {
		
		return 180;
	}

}
