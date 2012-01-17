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
public abstract class AreaBuilder {
	public abstract void buildArea(Creator creator,JSONObject jsonArea);
	
	public static class ArrowAreaBuilder extends AreaBuilder{

		@Override
		public void buildArea(Creator creator,JSONObject jsonArea) {
			JSONObject pos=jsonArea.get("pos").isObject();
			new ArrowArea(creator, 
					new Point(pos.get(Level.X).isNumber().doubleValue(),
							  pos.get(Level.Y).isNumber().doubleValue()),
					jsonArea.get("ang").isNumber().doubleValue(),
					jsonArea.get("r").isNumber().doubleValue());
			
		}
		
	}
}
