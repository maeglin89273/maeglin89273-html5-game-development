/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.button.key;

import com.google.gwt.maeglin89273.game.physicsbattle.client.component.CreationSketcher;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.SketchersFactory;
import com.google.gwt.maeglin89273.game.mengine.component.CanvasButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;


/**
 * @author Maeglin Liao
 *
 */
public abstract class CreativeKey extends CanvasButton {
	
	protected static SketchersFactory sketchersFactory;
	protected boolean pressed;
	
	protected CreativeKey(Point p, double w, double h,SpriteBlock block) {
		super(p, w, h, block);
		
	}
	public void setPressed(boolean p){
		if(!(this.pressed=p))
			resetSketcher();
	}
	public boolean isPressed(){
		return pressed;
	}
	public static void setSketchersFactory(SketchersFactory factory){
		sketchersFactory=factory;
	}
	public abstract CreationSketcher getSketcher();
	public abstract void next();
	public abstract void prevoius();
	public abstract void resetSketcher();
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.CanvasButton#contain(com.google.gwt.maeglin89273.game.mengine.physics.Point)
	 */
	@Override
	public boolean contain(Point p) {
		if(p.getX()>=getLeftX()&&p.getX()<=getRightX()&&p.getY()>=getTopY()&&p.getY()<=getBottomY())
			return true;
		return false;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.CanvasButton#doTask()
	 */
	@Override
	public void doTask() {
		setPressed(true);

	}
	protected static SpriteSheet getKeySpriteSheet(){
		return MEngine.getAssetManager().getSpriteSheet("buttons.png");
	}

}
