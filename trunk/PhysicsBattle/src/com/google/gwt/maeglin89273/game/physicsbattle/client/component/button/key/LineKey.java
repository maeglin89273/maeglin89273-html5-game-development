/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.button.key;


import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.SketchersFactory.LineSketcherType;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.line.Line.LineSketcher;
import com.google.gwt.maeglin89273.mengine.core.MEngine;
import com.google.gwt.maeglin89273.mengine.physics.Point;
import com.google.gwt.maeglin89273.mengine.sprite.SpriteBlock;

/**
 * @author Maeglin Liao
 *
 */
public class LineKey extends CreativeKey {
	
	private LineSketcher[] lineSketchers;
	private int index=0;
	public LineKey(Point p,LineSketcherType...types){
		super(p, 250, 100, new SpriteBlock(0,0,500,200));
		
		lineSketchers=new LineSketcher[types.length];
		for(int i=0;i<lineSketchers.length;i++){
			lineSketchers[i]=sketchersFactory.getLineSketcher(types[i]);
		}
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.physicsbattle.client.component.button.key.CreativeKey#getSketcher()
	 */
	@Override
	public LineSketcher getSketcher() {
		return lineSketchers[index];
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.physicsbattle.client.component.button.key.CreativeKey#next()
	 */
	@Override
	public void next() {
		resetSketcher();
		if(++index>lineSketchers.length-1){
			index=0;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.physicsbattle.client.component.button.key.CreativeKey#prevoius()
	 */
	@Override
	public void prevoius() {
		resetSketcher();
		if(--index<0)
			index=lineSketchers.length-1;

	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		if(pressed){
			spriteBlock.setY(420);
		}else{
			spriteBlock.setY(0);
		}
		//do more things...
	}
	@Override
	public void draw(Context2d context){
		super.draw(context);
	}
	@Override
	public void resetSketcher() {
		lineSketchers[index].reset();
	}

}
