/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Cross;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.DotDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.ArrowArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.levelequipment.WoodShelf;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.levelequipment.ShapesFountain;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.levelequipment.Shelf;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.CementLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.MagneticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.SimpleStaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class Level5 extends LevelContext {

	/**
	 * @param screenCenter
	 * @param cameraViewPoint
	 * @param width
	 * @param height
	 */
	public Level5( Point screenCenter) {
			
		super(screenCenter, new Point(360,90), 820, 1200,800);
		
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
		
		return new AreaDefinerType[]{AreaDefinerType.GRAVITATIONAL_AREA,AreaDefinerType.ARROW_AREA};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getLineDefinerTypes()
	 */
	@Override
	public LineDefinerType[] getLineDefinerTypes() {
		
		return new LineDefinerType[]{LineDefinerType.ELASTIC_LINE,LineDefinerType.MAGNETIC_LINE};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#getDotDefinerTypes()
	 */
	@Override
	public DotDefinerType[] getDotDefinerTypes() {
		
		return new DotDefinerType[]{DotDefinerType.NONE};
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.LevelContext#buildLevel(com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator, com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback)
	 */
	@Override
	public void buildLevel(Creator creator, GameOverCallback callback) {
		//new ShapesFountain(creator, new Point(-50,-100),-Math.PI/12,7);
		//new ShapesFountain(creator, new Point(770,50),-Math.PI*11/12,7);
		
		
		//new WoodShelf(creator,new Point(500,325),100,Math.PI*1/12,false);
		//new WoodShelf(creator,new Point(250,325),Shelf.STANDARD_WIDTH,0,true);
		new SimpleStaticLine(creator, new Point(200,420), new Point(500,340));
		new MagneticLine(creator,new Point(520,400),new Point(565,320));
		//new CementLine(creator, new Point(-10,575), new Point(700,380));
		new ArrowArea(creator, new Point(360,700), -Math.PI/3, 65);
		new ArrowArea(creator, new Point(100,670), -Math.PI/2, 50);
		new ArrowArea(creator, new Point(300,580), -Math.PI/2, 35);
		new ArrowArea(creator, new Point(550,600), -Math.PI/2, 40);
		
		new ShinyBall(creator, new Point(360,700));
		new Cross(creator, new Point(360,150), getGravityAngleInDegrees(), callback);
	}

}
