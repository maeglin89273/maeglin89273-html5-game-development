/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.key;


import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;



/**
 * @author Maeglin Liao
 *
 */
public abstract class CreativeKey extends BoxButton {
	protected static final int BLANK_KEY_X=0;
	protected static final int BLANK_KEY_Y=200+SpriteBlock.SPACING;
	protected static DefinersFactory definersFactory;
	protected final CreationDefiner[] definers;
	protected int index=0;
	protected boolean pressed;
	protected final Point unPressedBlockPos;
	protected CreativeKey(Point p, double w, double h,Point unPressedBlockPos,CreationDefiner[] definers) {
		super(p, w, h,new SpriteBlock((int)unPressedBlockPos.getX(),(int)unPressedBlockPos.getY(),200,200,
				MEngine.getAssetManager().getSpriteSheet("images/buttons.png")));
		this.unPressedBlockPos = unPressedBlockPos;
		
		this.definers = definers;
		
	}
	@Override
	public void update(){
		// TODO Auto-generated method stub
	}
	@Override
	public void draw(Context2d context){
		super.draw(context);
		if(pressed){
			SpriteBlock sb=getDefiner().getDefinerIcon();
			context.drawImage(sb.getSheetImage(), sb.getX(), sb.getY(), sb.getWidth(), sb.getHeight(),getLeftX(),getTopY(), getWidth(), getHeight());
		}
	}
	public void setPressed(boolean p){
		if(this.pressed=p){
			spriteBlock.setPosition(CreativeKey.BLANK_KEY_X,CreativeKey.BLANK_KEY_Y);
		}else{
			spriteBlock.setPosition((int)unPressedBlockPos.getX(),(int)unPressedBlockPos.getY());
			resetDefiner();
		}
	}
	public boolean isPressed(){
		return pressed;
	}
	public static void setSketchersFactory(DefinersFactory factory){
		definersFactory=factory;
	}
	public CreationDefiner getDefiner(){
		return definers[index];
	}
	public void next(){
		resetDefiner();
		if(++index>definers.length-1){
			index=0;
		}
	}
	public void prevoius(){
		resetDefiner();
		if(--index<0)
			index=definers.length-1;
	}
	public void resetDefiner(){
		definers[index].reset();
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.CanvasButton#doTask()
	 */
	@Override
	public void doTask() {
		setPressed(true);

	}
}
