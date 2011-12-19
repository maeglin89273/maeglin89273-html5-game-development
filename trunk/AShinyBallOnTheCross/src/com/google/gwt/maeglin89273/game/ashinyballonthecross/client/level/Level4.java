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
public class Level4 extends LevelContext {

	/**
	 * @param screenCenter
	 * @param cameraViewPoint
	 */
	public Level4(Point screenCenter) {
		super(screenCenter,new Point(220,270),950,900,400);
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getGravityInDegrees()
	 */
	@Override
	public int getGravityAngleInDegrees() {
		
		return 90;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getAreaDefinerTypes()
	 */
	@Override
	public AreaDefinerType[] getAreaDefinerTypes() {
		
		return new AreaDefinerType[]{AreaDefinerType.GRAVITATIONAL_AREA};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getLineDefinerTypes()
	 */
	@Override
	public LineDefinerType[] getLineDefinerTypes() {
		return new LineDefinerType[]{LineDefinerType.ELASTIC_LINE,LineDefinerType.SIMPLE_STATIC_LINE};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getDotDefinerTypes()
	 */
	@Override
	public DotDefinerType[] getDotDefinerTypes() {
		
		return new DotDefinerType[]{DotDefinerType.RECTANGLE_BOMB_DOT,DotDefinerType.CIRCLE_BOMB_DOT};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#buildLevel(com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator, com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback)
	 */
	@Override
	public void buildLevel(Creator creator, GameOverCallback callback) {
		new ShinyBall(creator,new Point(185,487.5));
		new SimpleStaticLine(creator,new Point(-90,25),new Point(-90,100));
		new SimpleStaticLine(creator,new Point(85,25),new Point(85,100));
		
		new SimpleStaticLine(creator,new Point(-90,100),new Point(-15,150));
		new SimpleStaticLine(creator,new Point(85,100),new Point(10,150));
		
		new SimpleStaticLine(creator,new Point(-15,150),new Point(-15,350));
		new SimpleStaticLine(creator,new Point(10,150),new Point(10,340));
		
		new SimpleStaticLine(creator,new Point(-15,350),new Point(185,500));
		new SimpleStaticLine(creator,new Point(10,340),new Point(185,474));
		
		new SimpleStaticLine(creator,new Point(185,500),new Point(330,470));
		new SimpleStaticLine(creator,new Point(185,474),new Point(330,444));
		
		new SimpleStaticLine(creator,new Point(330,444),new Point(348,455));
		
		new SimpleStaticLine(creator,new Point(330,470),new Point(330,650));
		new SimpleStaticLine(creator,new Point(348,455),new Point(348,650));
		
		new SimpleStaticLine(creator,new Point(393,650),new Point(393,610));
		new SimpleStaticLine(creator,new Point(420,650),new Point(420,610));
		
		new SimpleStaticLine(creator,new Point(348,650),new Point(393,650));
		
		
		new Cross(creator, new Point(370,630),getGravityAngleInDegrees(), callback);
	}

}
