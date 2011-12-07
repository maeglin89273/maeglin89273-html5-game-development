/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.pseudo;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Cross;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.DotDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.BreakableLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.SimpleStaticLine;
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
		super(screenCenter);
		this.width=screenCenter.getX()*2.5;
		this.height=screenCenter.getY()*2.5;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.pseudo.LevelContext#getAreaDefinerTypes()
	 */
	@Override
	public AreaDefinerType[] getAreaDefinerTypes() {
		
		return new AreaDefinerType[]{AreaDefinerType.ARROW_AREA,AreaDefinerType.MAGNETIC_AREA};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.pseudo.LevelContext#getLineDefinerTypes()
	 */
	@Override
	public LineDefinerType[] getLineDefinerTypes() {
		
		return new LineDefinerType[]{LineDefinerType.SIMPLE_STATIC_LINE,LineDefinerType.ELASTIC_LINE,LineDefinerType.SHAPES_LINE};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.pseudo.LevelContext#getDotDefinerTypes()
	 */
	@Override
	public DotDefinerType[] getDotDefinerTypes() {
		
		return new DotDefinerType[]{DotDefinerType.CIRCLE_BOMB_DOT};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.pseudo.LevelContext#buildLevel(com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator, com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback)
	 */
	@Override
	public void buildLevel(Creator creator, GameOverCallback callback) {
		new ShinyBall(creator,new Point(361,0));
		new BreakableLine(creator,new Point(360,170),new Point(510,320));
		new BreakableLine(creator,new Point(510,320),new Point(360,470));
		new BreakableLine(creator,new Point(360,470),new Point(210,320));
		new BreakableLine(creator,new Point(210,320),new Point(360,170));
		new Cross(creator, new Point(getScreenCenter().getX(),getScreenCenter().getY()+50),Math.toRadians(getGravityInDegrees()-90), callback);

	}

	@Override
	public int getGravityInDegrees() {
		
		return 90;
	}

}
