/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Cross;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.DotDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.CementLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class Level3 extends LevelContext {

	/**
	 * @param screenCenter
	 */
	public Level3(Point screenCenter) {
		super(screenCenter,screenCenter,900,675,400);
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
		
		return new LineDefinerType[]{LineDefinerType.SIMPLE_STATIC_LINE,LineDefinerType.ELASTIC_LINE,LineDefinerType.SHAPES_LINE};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getDotDefinerTypes()
	 */
	@Override
	public DotDefinerType[] getDotDefinerTypes() {
		
		return new DotDefinerType[]{DotDefinerType.CIRCLE_BOMB_DOT};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#buildLevel(com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator, com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback)
	 */
	@Override
	public void buildLevel(Creator creator, GameOverCallback callback) {
		new ShinyBall(creator,new Point(361,0));
		new CementLine(creator,new Point(360,170),new Point(510,320));
		new CementLine(creator,new Point(510,320),new Point(360,470));
		new CementLine(creator,new Point(360,470),new Point(210,320));
		new CementLine(creator,new Point(210,320),new Point(360,170));
		new Cross(creator, new Point(getScreenCenter().getX(),getScreenCenter().getY()+50),getGravityAngleInDegrees(), callback);

	}

	@Override
	public int getGravityAngleInDegrees() {
		
		return 90;
	}

}
