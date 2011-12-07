/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.ArrowArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.MagneticArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.Area.AreaDefiner;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot.CircleBombDot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot.PolygonBombDot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot.RectangleBombDot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot.Dot.DotDefiner;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.BreakableLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.ElasticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.ShapesLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.SimpleStaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.Line.LineDefiner;



/**
 * @author Maeglin Liao
 *
 */
public class DefinersFactory {
	public enum LineDefinerType{BREALABLE_LINE,SIMPLE_STATIC_LINE,ELASTIC_LINE,SHAPES_LINE,NONE};
	public enum DotDefinerType{POLYGON_BOMB_DOT,CIRCLE_BOMB_DOT,RECTANGLE_BOMB_DOT,NONE};
	public enum AreaDefinerType{MAGNETIC_AREA,ARROW_AREA,NONE};
	
	private Creator creator;
	public DefinersFactory(Creator creator){
		this.creator=creator;
	}
	public CreationDefiner[] getAreaDefiners(AreaDefinerType... types){
		CreationDefiner[] definers =new CreationDefiner[types.length];
		for(int i=0;i<definers.length;i++){
			definers[i]=getAreaDefiner(types[i]);
		}
		return definers;
	}
	public CreationDefiner getAreaDefiner(AreaDefinerType type){
		switch(type){
			case MAGNETIC_AREA:
				return new MagneticArea.MagneticAreaDefiner(creator);
			case ARROW_AREA:
				return new ArrowArea.ArrowAreaDefiner(creator);
			case NONE:
				return new CreationDefiner.NoneDefiner();
		}
		throw new IllegalArgumentException("It is a wrong type.");
	}
	public CreationDefiner[] getLineDefiners(LineDefinerType... types){
		CreationDefiner[] definers =new CreationDefiner[types.length];
		for(int i=0;i<definers.length;i++){
			definers[i]=getLineDefiner(types[i]);
		}
		return definers;
	}
	public CreationDefiner getLineDefiner(LineDefinerType type){
		switch(type){
			case BREALABLE_LINE:
				return new BreakableLine.BreakableLineDefiner(creator);
			case SIMPLE_STATIC_LINE:
				return new SimpleStaticLine.BlackStaticLineDefiner(creator);
			case ELASTIC_LINE:
				return new ElasticLine.ElasticLineDefiner(creator);
			case SHAPES_LINE:
				return new ShapesLine.ShapesLineDefiner(creator, 23);
			case NONE:
				return new CreationDefiner.NoneDefiner();
		}
		throw new IllegalArgumentException("It is a wrong type.");
	}
	public CreationDefiner[] getDotDefiners(DotDefinerType... types){
		CreationDefiner[] definers =new CreationDefiner[types.length];
		for(int i=0;i<definers.length;i++){
			definers[i]=getDotDefiner(types[i]);
		}
		return definers;
	}
	public CreationDefiner getDotDefiner(DotDefinerType type){
		switch(type){
			case POLYGON_BOMB_DOT:
				return new PolygonBombDot.PolygonBombDotDefiner(creator);
			case CIRCLE_BOMB_DOT:
				return new CircleBombDot.CircleBombDotDefiner(creator);
			case RECTANGLE_BOMB_DOT:
				return new RectangleBombDot.RectangleBombDotDefiner(creator);
			case NONE:
				return new CreationDefiner.NoneDefiner();
		}
		throw new IllegalArgumentException("It is a wrong type.");
	}
}
