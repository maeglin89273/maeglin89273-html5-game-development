/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.line;


import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.mengine.component.Spacial;
import com.google.gwt.maeglin89273.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class BlackStaticLine extends LimitedTimeStaticLine {
	public static final int LIMITED_TIME=30; 
	/**
	 * @param world
	 * @param p1
	 * @param p2
	 */
	public BlackStaticLine(PhysicalWorld space, Point p1, Point p2) {
		super(space, p1, p2, 30, GameColors.BLACK_LINE_COLOR);
		
	}
	public static class BlackStaticLineSketcher extends LimitedTimeStaticLineSketcher{

		public BlackStaticLineSketcher(PhysicalWorld world){
			super(world);
			lineColor=GameColors.BLACK_LINE_COLOR;
		}
		@Override
		public void sketchFinished(){
			world.add(new BlackStaticLine(world,pointA,pointB));
			reset();
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
