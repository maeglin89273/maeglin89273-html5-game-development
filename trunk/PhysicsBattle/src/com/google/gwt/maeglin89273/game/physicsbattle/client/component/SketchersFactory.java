/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component;

import com.google.gwt.maeglin89273.game.physicsbattle.client.component.line.BlackStaticLine;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.line.GrayStaticLine;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.line.ShapesLine;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.line.Line;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;

/**
 * @author Maeglin Liao
 *
 */
public class SketchersFactory {
	public enum LineSketcherType{GRAY_STATIC_LINE,BLACK_STATIC_LINE,SHAPES_LINE};
	
	
	private PhysicalWorld world;
	public SketchersFactory(PhysicalWorld space){
		this.world=space;
	}
	public Line.LineSketcher getLineSketcher(LineSketcherType type){
		switch(type){
			case GRAY_STATIC_LINE:
				return new GrayStaticLine.GrayStaticLineSketcher(world);
				
			case BLACK_STATIC_LINE:
				return new BlackStaticLine.BlackStaticLineSketcher(world);
			case SHAPES_LINE:
				return new ShapesLine.ShapesLineSketcher(world, 23);
		}
		throw new IllegalArgumentException("It is a wrong type.");
	}
	
}
