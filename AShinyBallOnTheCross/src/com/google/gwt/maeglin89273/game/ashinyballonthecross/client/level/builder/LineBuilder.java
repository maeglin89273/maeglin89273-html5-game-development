/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.CementLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.MagneticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.SimpleStaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.WoodLine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class LineBuilder {

	public abstract void buildLine(Creator creator,Point p1,Point p2);
	
	public static class SimpleStaticLineBuilder extends LineBuilder{

		@Override
		public void buildLine(Creator creator, Point p1, Point p2) {
			new SimpleStaticLine(creator,p1,p2);
			
		}
		
	}
	public static class CementLineBuilder extends LineBuilder{

		@Override
		public void buildLine(Creator creator, Point p1, Point p2) {
			new CementLine(creator, p1, p2);
			
		}
		
	}
	public static class WoodLineBuidler extends LineBuilder{

		@Override
		public void buildLine(Creator creator, Point p1, Point p2) {
			new WoodLine(creator,p1,p2);
			
		}
		
	}
	public static class MagneticLineBuidler extends LineBuilder{

		@Override
		public void buildLine(Creator creator, Point p1, Point p2) {
			new MagneticLine(creator,p1,p2);
			
		}
		
	}
}
