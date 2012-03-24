/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.CementLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.ElasticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.MagneticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.SimpleStaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.WoodLine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class LineBuilder implements LevelBuilder{
	protected static final String P1="p1";
	protected static final String P2="p2";
	public void build(JSONObject creation){
		Point p1;
		Point p2;
		JSONObject point;
		point=creation.get(P1).isObject();
		p1=new Point(point.get(X).isNumber().doubleValue(),
					 point.get(Y).isNumber().doubleValue());
		point=creation.get(P2).isObject();
		p2=new Point(point.get(X).isNumber().doubleValue(),
					 point.get(Y).isNumber().doubleValue());
		this.buildLine( p1, p2);
	}
	public abstract void buildLine( Point p1,Point p2);
	
	public static class SimpleStaticLineBuilder extends LineBuilder{

		@Override
		public void buildLine(  Point p1, Point p2) {
			new SimpleStaticLine(p1,p2);
			
		}
		
	}
	public static class CementLineBuilder extends LineBuilder{

		@Override
		public void buildLine(  Point p1, Point p2) {
			new CementLine( p1, p2);
			
		}
		
	}
	public static class WoodLineBuilder extends LineBuilder{

		@Override
		public void buildLine(  Point p1, Point p2) {
			new WoodLine(p1,p2);
			
		}
		
	}
	public static class MagneticLineBuilder extends LineBuilder{

		@Override
		public void buildLine(  Point p1, Point p2) {
			new MagneticLine(p1,p2);
			
		}
	}
	public static class ElasticLineBuilder extends LineBuilder{
		@Override
		public void buildLine(Point p1, Point p2) {
			new ElasticLine(p1,p2);
			
		}
	}
}
