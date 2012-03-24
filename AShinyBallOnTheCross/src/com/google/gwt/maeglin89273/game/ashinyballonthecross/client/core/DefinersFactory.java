/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core;


import java.util.HashMap;
import java.util.Map;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.area.ArrowArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.area.GravitationalArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.area.TriangleArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.dot.CircleBombDot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.dot.PolygonBombDot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.dot.RectangleBombDot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.CementLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.ElasticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.MagneticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.ShapesLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.line.SimpleStaticLine;




/**
 * @author Maeglin Liao
 *
 */
public class DefinersFactory {
	
	private static final String WRONG_KIND_MESSAGE="It is a wrong kind of creation definer.";
	private static final Map<String,CreationDefiner> DEFINERS_MAP=new HashMap<String,CreationDefiner>();
	static{
		DEFINERS_MAP.put("None",new CreationDefiner.NoneDefiner());
		
		//Area Definers
		DEFINERS_MAP.put("GravitationalArea", new GravitationalArea.GravitationalAreaDefiner());
		DEFINERS_MAP.put("ArrowArea", new ArrowArea.ArrowAreaDefiner());
		DEFINERS_MAP.put("TriangleArea", new TriangleArea.TriangleAreaDefiner());
		
		//Line Definers
		DEFINERS_MAP.put("CementLine", new CementLine.CementLineDefiner());
		DEFINERS_MAP.put("SimpleStaticLine", new SimpleStaticLine.SimpleStaticLineDefiner());
		DEFINERS_MAP.put("MagneticLine", new MagneticLine.MagneticLineDefiner());
		DEFINERS_MAP.put("ShapesLine", new ShapesLine.ShapesLineDefiner());
		DEFINERS_MAP.put("ElasticLine", new ElasticLine.ElasticLineDefiner());
		
		//Dot Definers
		DEFINERS_MAP.put("PolygonBombDot", new PolygonBombDot.PolygonBombDotDefiner());
		DEFINERS_MAP.put("CircleBombDot", new CircleBombDot.CircleBombDotDefiner());
		DEFINERS_MAP.put("RectangleBombDot", new RectangleBombDot.RectangleBombDotDefiner());
	}
	public static CreationDefiner[] getDefiners(String[] definerKinds){
		CreationDefiner[] definers =new CreationDefiner[definerKinds.length];
		for(int i=0;i<definers.length;i++){
			definers[i]=getDefiner(definerKinds[i]);
		}
		return definers;
	}
	public static CreationDefiner getDefiner(String definerKind){
		CreationDefiner toReturn= DEFINERS_MAP.get(definerKind);
		if(toReturn==null){
			throw new IllegalArgumentException(WRONG_KIND_MESSAGE);
		}
		return toReturn;
	}
}
