/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Cross;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.RedGoblet;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public interface LevelBuilder {
	public static final String X="x";
	public static final String Y="y";
	public static final String ANGLE="ang";
	public static final String POSITION="pos";
	
	public void build(Creator creator,JSONObject creation);
	
	public static class ShinyBallBuilder implements LevelBuilder{

		@Override
		public void build(Creator creator, JSONObject creation) {
			new ShinyBall(creator, new Point(creation.get(X).isNumber().doubleValue(),
					 						 creation.get(Y).isNumber().doubleValue()));
			
		}
		
	}
	public static class CrossBuilder implements LevelBuilder{

		@Override
		public void build(Creator creator, JSONObject creation) {
			JSONObject pos=creation.get(POSITION).isObject();
			new Cross(creator,
					new Point(pos.get(X).isNumber().doubleValue(),
					 		  pos.get(Y).isNumber().doubleValue()),
					 		  Math.toRadians(creation.get(ANGLE).isNumber().doubleValue()),
					 		  creator.getGameOverCallback());
			
		}
		
	}
	public static class RedGobletBuilder implements LevelBuilder{
		
		@Override
		public void build(Creator creator, JSONObject creation) {
			JSONObject pos=creation.get(POSITION).isObject();
			new RedGoblet(creator,
					new Point(pos.get(X).isNumber().doubleValue(),
					 		  pos.get(Y).isNumber().doubleValue()),
					 		 Math.toRadians(creation.get(ANGLE).isNumber().doubleValue()),
					 		(int)creation.get("gAng").isNumber().doubleValue());
		}
		
	}
}
