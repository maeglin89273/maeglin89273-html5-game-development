/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Cross;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.AreaDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.DotDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.LineDefinerType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.AreaBuilder;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LineBuilder;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.asset.JsonFile;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

import static com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.LineBuilder.*;
import static com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.builder.AreaBuilder.*;
/**
 * @author Maeglin Liao
 *
 */
public class Level {
	private enum CreationType{
		SHINY_BALL,CROSS,
		SIMPLE_STATIC_LINE,CEMENT_LINE,WOOD_LINE,MAGNETIC_LINE,ELASTIC_LINE,
		ARROW_AREA
	}
	
	
	public static final int LEVEL_COUNT=9;
	
	public static final String X="x";
	public static final String Y="y";
	
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
	public void buildLevel(Creator creator,GameOverCallback callback){
		JSONObject levelCreations=jsonContext.get("creations").isObject();
		JSONValue unkonwnCreation;
		JSONObject creation;
		JSONArray creationArray;
		int i;
		
		for(String key:levelCreations.keySet()){
			unkonwnCreation=levelCreations.get(key);
			
			
			if(key.endsWith("LINE")){
				Point p1;
				Point p2;
				JSONObject point;
				creationArray=unkonwnCreation.isArray();
				LineBuilder lc;
				
				switch(CreationType.valueOf(key)){
					case SIMPLE_STATIC_LINE:
						lc=new SimpleStaticLineBuilder();
						break;
					case CEMENT_LINE:
						lc =new CementLineBuilder();
						break;
					case WOOD_LINE:
						lc=new WoodLineBuidler();
						break;
					case MAGNETIC_LINE:
						lc=new MagneticLineBuidler();
						break;
						
					default:
						lc=new SimpleStaticLineBuilder();
				}
				
				for(i=0;i<creationArray.size();i++){
					creation=creationArray.get(i).isObject();
					point=creation.get("p1").isObject();
					p1=new Point(point.get(X).isNumber().doubleValue(),
								 point.get(Y).isNumber().doubleValue());
					point=creation.get("p2").isObject();
					p2=new Point(point.get(X).isNumber().doubleValue(),
								 point.get(Y).isNumber().doubleValue());
					lc.buildLine(creator, p1, p2);
				}
			}else if(key.endsWith("AREA")){
				creationArray=unkonwnCreation.isArray();
				AreaBuilder ab=null;
				switch(CreationType.valueOf(key)){
					case ARROW_AREA:
						ab=new ArrowAreaBuilder();
					
				}
				for(i=0;i<creationArray.size();i++){
					ab.buildArea(creator, creationArray.get(i).isObject());
				}
			}else{
				switch(CreationType.valueOf(key)){
					case SHINY_BALL:
						creation=unkonwnCreation.isObject();
						new ShinyBall(creator, new Point(creation.get(X).isNumber().doubleValue(),
														 creation.get(Y).isNumber().doubleValue()));
						break;
					case CROSS:
						creation=unkonwnCreation.isObject();
						new Cross(creator, new Point(creation.get(X).isNumber().doubleValue(),
													 creation.get(Y).isNumber().doubleValue()),
								  gravityAngle, callback);
				}
			}
		}
	}
}
