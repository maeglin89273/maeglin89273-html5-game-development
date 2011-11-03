/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.line;

import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class GrayStaticLine extends LimitedTimeStaticLine {
	public static final int LIMITED_TIME=3;
	/**
	 * @param world
	 * @param p1
	 * @param p2
	 */
	public GrayStaticLine(PhysicalWorld space, Point p1, Point p2) {
		super(space, p1, p2, 3,GameColors.DARK_GRAY_LINE_COLOR);
	}
	public static class GrayStaticLineSketcher extends LimitedTimeStaticLineSketcher{

		public GrayStaticLineSketcher(PhysicalWorld world){
			super(world);
			lineColor=GameColors.DARK_GRAY_LINE_COLOR;
		}
		@Override
		public void sketchFinished(){
			new GrayStaticLine(world,pointA,pointB);
			reset();
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
