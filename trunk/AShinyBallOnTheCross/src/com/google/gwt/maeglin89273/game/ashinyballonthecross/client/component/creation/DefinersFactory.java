/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.ArrowArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.GravitationalArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.TriangleArea;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot.CircleBombDot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot.PolygonBombDot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot.RectangleBombDot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.CementLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.ElasticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.MagneticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.ShapesLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.SimpleStaticLine;




/**
 * @author Maeglin Liao
 *
 */
public class DefinersFactory {
	public enum LineDefinerType{CEMENT_LINE,SIMPLE_STATIC_LINE,ELASTIC_LINE,SHAPES_LINE,MAGNETIC_LINE,NONE};
	public enum DotDefinerType{POLYGON_BOMB_DOT,CIRCLE_BOMB_DOT,RECTANGLE_BOMB_DOT,NONE};
	public enum AreaDefinerType{GRAVITATIONAL_AREA,ARROW_AREA,TRIANGLE_AREA,NONE};
	
	private static final String WRONG_TYPE_MESSAGE="It is a wrong type.";
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
			case GRAVITATIONAL_AREA:
				return new GravitationalArea.GravitationalAreaDefiner(creator);
			case ARROW_AREA:
				return new ArrowArea.ArrowAreaDefiner(creator);
			case TRIANGLE_AREA:
				return new TriangleArea.TriangleAreaDefiner(creator);
			case NONE:
				return new CreationDefiner.NoneDefiner();
		}
		throw new IllegalArgumentException(WRONG_TYPE_MESSAGE);
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
			case CEMENT_LINE:
				return new CementLine.CementLineDefiner(creator);
			case SIMPLE_STATIC_LINE:
				return new SimpleStaticLine.BlackStaticLineDefiner(creator);
			case ELASTIC_LINE:
				return new ElasticLine.ElasticLineDefiner(creator);
			case SHAPES_LINE:
				return new ShapesLine.ShapesLineDefiner(creator);
			case MAGNETIC_LINE:
				return new MagneticLine.MagneticLineDefiner(creator);
			case NONE:
				return new CreationDefiner.NoneDefiner();
		}
		throw new IllegalArgumentException(WRONG_TYPE_MESSAGE);
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
		throw new IllegalArgumentException(WRONG_TYPE_MESSAGE);
	}
}
