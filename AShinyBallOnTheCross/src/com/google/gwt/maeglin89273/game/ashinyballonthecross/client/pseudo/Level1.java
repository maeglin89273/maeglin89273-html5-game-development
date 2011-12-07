/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.pseudo;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Cross;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.DotDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.SimpleStaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;

/**
 * @author Maeglin Liao
 *
 */
public class Level1 extends LevelContext {

	/**
	 * @param screenCenter
	 */
	public Level1(Point screenCenter) {
		super(screenCenter);
		this.width=screenCenter.getX()*2;
		this.height=screenCenter.getY()*2;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.pseudo.LevelContext#buildLevel(com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator)
	 */
	@Override
	public void buildLevel(Creator creator,GameOverCallback callback) {
		new ShinyBall(creator,new Point(175,125));
		/*Vector d=CoordinateConverter.vectorWorldToPixel(gravityIndicator.getGravity());
		d.setMagnitude(ShinyBall.CORE_RADIUS);
		Point tangetPoint=shinyBall.getPosition();
		tangetPoint.translate(d);
		d.setMagnitude(15);
		d.setVector(-d.getVectorY(), d.getVectorX());
		
		Point pA=tangetPoint.clone();
		pA.translate(d);
		
		d.reverse();
		tangetPoint.translate(d);
		new SimpleStaticLine(creator, pA, tangetPoint);
		new SimpleStaticLine(creator, new Point(0,450), new Point(getGameWidth(),450));*/
		new SimpleStaticLine(creator, new Point(150,150), new Point(400,250));
		new Cross(creator, new Point(getScreenCenter().getX(),400), Math.toRadians(getGravityInDegrees()-90), callback);
	}

	@Override
	public AreaDefinerType[] getAreaDefinerTypes() {
		
		return new AreaDefinerType[]{AreaDefinerType.NONE};
	}

	@Override
	public LineDefinerType[] getLineDefinerTypes() {
		
		return new LineDefinerType[]{LineDefinerType.SIMPLE_STATIC_LINE};
	}

	@Override
	public DotDefinerType[] getDotDefinerTypes() {
		
		return new DotDefinerType[]{DotDefinerType.NONE};
	}

	@Override
	public int getGravityInDegrees() {
		
		return 90;
	}

}
