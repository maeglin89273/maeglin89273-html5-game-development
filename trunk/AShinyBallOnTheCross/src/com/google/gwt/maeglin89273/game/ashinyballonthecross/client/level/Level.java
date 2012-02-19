/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level;

import static com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LevelBuilder.X;
import static com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LevelBuilder.Y;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.DotDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.AreaBuilder.ArrowAreaBuilder;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LevelBuilder;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LineBuilder.CementLineBuilder;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LineBuilder.MagneticLineBuilder;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LineBuilder.SimpleStaticLineBuilder;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LineBuilder.ElasticLineBuilder;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LineBuilder.WoodLineBuilder;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.WorldType;
import com.google.gwt.maeglin89273.game.mengine.asset.JsonFile;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
/**
 * @author Maeglin Liao
 *
 */
public class Level {
	
	public static final int LEVEL_COUNT=9;
	
	private final JSONObject jsonContext;
	
	private final WorldType world;
	private final int levelNumber;
	private final int gravityAngle;
	private final int requiredScore;
	private final int  fullPower;
	
	private final double width;
	private final double height;
	
	private final Point screenCenter;
	private final Point viewPoint;
	
	private final AreaDefinerType[] areaDefs;
	private final LineDefinerType[] lineDefs;
	private final DotDefinerType[] dotDefs;
	
	private static final Map<String,LevelBuilder> BUILDER_MAP=new HashMap<String,LevelBuilder>();
	static{
		BUILDER_MAP.put("SIMPLE_STATIC_LINE", new SimpleStaticLineBuilder());
		BUILDER_MAP.put("CEMENT_LINE", new CementLineBuilder());
		BUILDER_MAP.put("WOOD_LINE", new WoodLineBuilder());
		BUILDER_MAP.put("MAGNETIC_LINE", new MagneticLineBuilder());
		BUILDER_MAP.put("ELASTIC_LINE", new ElasticLineBuilder());
		
		BUILDER_MAP.put("ARROW_AREA", new ArrowAreaBuilder());
		
		BUILDER_MAP.put("SHINY_BALL", new LevelBuilder.ShinyBallBuilder());
		BUILDER_MAP.put("CROSS", new LevelBuilder.CrossBuilder());
		BUILDER_MAP.put("RED_GOBLET", new LevelBuilder.RedGobletBuilder());
	}
	/**
	 * 
	 */
	public Level(Point screenCenter,JsonFile file) {
		this.screenCenter = screenCenter;
		this.jsonContext=file.getJsonValue().isObject();
		
		this.world=WorldType.valueOf(jsonContext.get("world").isString().stringValue());
		this.levelNumber=(int)jsonContext.get("levelNum").isNumber().doubleValue();
		this.gravityAngle=(int)jsonContext.get("gravityAng").isNumber().doubleValue();
		this.fullPower=(int)jsonContext.get("fullPower").isNumber().doubleValue();
		this.requiredScore=(int)jsonContext.get("reqScore").isNumber().doubleValue();
		
		JSONObject bounds=jsonContext.get("bounds").isObject();
		this.width = bounds.get("width").isNumber().doubleValue();
		this.height = bounds.get("height").isNumber().doubleValue();
		
		JSONObject camera=jsonContext.get("camera").isObject();
		this.viewPoint = new Point(camera.get(X).isNumber().doubleValue(),
								   camera.get(Y).isNumber().doubleValue());
		JSONArray defs;
		int i;
		
		defs=jsonContext.get("areaDefs").isArray();
		areaDefs=new AreaDefinerType[defs.size()];
		for(i=0;i<areaDefs.length;i++){
			areaDefs[i]=AreaDefinerType.valueOf(defs.get(i).isString().stringValue());
		}
		
		defs=jsonContext.get("lineDefs").isArray();
		lineDefs=new LineDefinerType[defs.size()];
		for(i=0;i<lineDefs.length;i++){
			lineDefs[i]=LineDefinerType.valueOf(defs.get(i).isString().stringValue());
		}
		
		defs=jsonContext.get("dotDefs").isArray();
		dotDefs=new DotDefinerType[defs.size()];
		for(i=0;i<dotDefs.length;i++){
			dotDefs[i]=DotDefinerType.valueOf(defs.get(i).isString().stringValue());
		}
		
		
	}
	
	public Point getCameraViewPoint() {
		return viewPoint.clone();
	}
	public Point getScreenCenter(){
		return screenCenter.clone();
	}
	public double getLevelWidth(){
		return width;
	}
	public double getLevelHeight(){
		return height;
	}
	
	public int getFullPower(){
		return fullPower;
	}
	public static int queryFullPower(WorldType worldType,int levelNum){ 
		return (int)MEngine.getAssetManager().getJson("levels/"+worldType.toString()+"_level_"+levelNum+".json")
				.getJsonValue().isObject().get("fullPower").isNumber().doubleValue();
	}
	public int getRequiredScore() {
		return requiredScore;
	}
	public int getGravityAngleInDegrees(){
		return gravityAngle;
	}
	public int getLevelNumber(){
		return levelNumber;
	}
	public WorldType getWorldType(){
		return world;
	}
	public AreaDefinerType[] getAreaDefinerTypes(){
		return areaDefs;
	}
	public LineDefinerType[] getLineDefinerTypes(){
		return lineDefs;
	}
	public DotDefinerType[] getDotDefinerTypes(){
		return dotDefs;
	}
	@Override
	public String toString(){
		return world.getTitle()+"-"+levelNumber;
	}
	public void buildLevel(Creator creator){
		JSONObject levelCreations=jsonContext.get("creations").isObject();
		JSONValue unknownCreation;
		LevelBuilder builder;
		
		JSONArray creationArray;
		
		int i;
		
		for(String key:levelCreations.keySet()){
			unknownCreation=levelCreations.get(key);
			builder=BUILDER_MAP.get(key);
			if(unknownCreation.isArray()!=null){
				creationArray=unknownCreation.isArray();
				
				for(i=0;i<creationArray.size();i++){
					builder.build(creator, creationArray.get(i).isObject());
				}
			}else{
				builder.build(creator, unknownCreation.isObject());
			}
		}
	}
}
