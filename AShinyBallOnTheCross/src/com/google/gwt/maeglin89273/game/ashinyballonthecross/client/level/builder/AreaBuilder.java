/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder;


import com.google.gwt.json.client.JSONObject;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.ArrowArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class AreaBuilder implements LevelBuilder{
	public void build(Creator creator,JSONObject creation){
		this.buildArea(creator, creation);
	}
	public abstract void buildArea(Creator creator,JSONObject jsonArea);
	
	public static class ArrowAreaBuilder extends AreaBuilder{

		@Override
		public void buildArea(Creator creator,JSONObject jsonArea) {
			JSONObject pos=jsonArea.get(POSITION).isObject();
			new ArrowArea(creator, 
					new Point(pos.get(X).isNumber().doubleValue(),
							  pos.get(Y).isNumber().doubleValue()),
							  Math.toRadians(jsonArea.get(ANGLE).isNumber().doubleValue()),
							  jsonArea.get("r").isNumber().doubleValue());
			
		}
		
	}
}
