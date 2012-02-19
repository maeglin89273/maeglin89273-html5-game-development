/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.CementLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.ElasticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.MagneticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.SimpleStaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.WoodLine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class LineBuilder implements LevelBuilder{
	protected static final String P1="p1";
	protected static final String P2="p2";
	public void build(Creator creator,JSONObject creation){
		Point p1;
		Point p2;
		JSONObject point;
		point=creation.get(P1).isObject();
		p1=new Point(point.get(X).isNumber().doubleValue(),
					 point.get(Y).isNumber().doubleValue());
		point=creation.get(P2).isObject();
		p2=new Point(point.get(X).isNumber().doubleValue(),
					 point.get(Y).isNumber().doubleValue());
		this.buildLine(creator, p1, p2);
	}
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
	public static class WoodLineBuilder extends LineBuilder{

		@Override
		public void buildLine(Creator creator, Point p1, Point p2) {
			new WoodLine(creator,p1,p2);
			
		}
		
	}
	public static class MagneticLineBuilder extends LineBuilder{

		@Override
		public void buildLine(Creator creator, Point p1, Point p2) {
			new MagneticLine(creator,p1,p2);
			
		}
	}
	public static class ElasticLineBuilder extends LineBuilder{
		@Override
		public void buildLine(Creator creator, Point p1, Point p2) {
			new ElasticLine(creator,p1,p2);
			
		}
	}
}
