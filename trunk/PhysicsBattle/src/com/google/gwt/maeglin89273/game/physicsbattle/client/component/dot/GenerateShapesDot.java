/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.dot;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class GenerateShapesDot extends Dot {
	
	protected ShapesGenerator shapesGenerator;
	protected int shapesCount;
	protected GenerateShapesDot(Point p,PhysicalWorld world,CssColor color,ShapesGenerator generator,int count){
		super(p, world);
		
		this.shapesGenerator=generator;
		this.shapesCount=count;
		this.dotColor=color;
	}
	@Override
	public void update() {
		if(shapesCount>0){
			world.add(shapesGenerator.generate(position));
			shapesCount--;
		}
	}

	protected interface ShapesGenerator{
		public PhysicalShape generate(Point p);
	}
}
