/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class BlueMark extends GeneralComponent implements StepComponent{
	
	private SpriteBlock spriteBlock;
	private boolean enabled=false;
	public BlueMark() {
		super(new Point(0,0), 0, 100, 100);
		int offset=3*(200+SpriteBlock.SPACING);
		this.spriteBlock=new SpriteBlock(offset,offset,200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet());
	}
	
	@Override
	public void setEnabled(boolean enabled){
		this.enabled=enabled;
	}
	
	public void setup(Point p,double a){
		setEnabled(true);
		this.setPosition(p);
		this.setAngle(a);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Context2d context) {
		if(enabled){
			context.save();
			context.translate(getX(), getY());
			context.rotate(getAngle());
			context.drawImage(spriteBlock.getSheetImage(), spriteBlock.getX(), spriteBlock.getY(), spriteBlock.getWidth(), spriteBlock.getHeight(),-getWidth()/2,-getHeight()/2, getWidth(), getHeight());
			context.restore();
		}
	}
	
}
