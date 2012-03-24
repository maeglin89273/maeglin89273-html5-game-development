/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.TutorialManager;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.button.CircleButton;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class NextStepButton extends CircleButton implements StepComponent{
	
	private final TutorialManager manager;
	
	private boolean enabled=false;
	
	public NextStepButton(Point p,TutorialManager manager) {
		super(p, 50, new SpriteBlock(4*(200+SpriteBlock.SPACING),2*(200+SpriteBlock.SPACING),200,200, ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		this.manager = manager;
	}
	@Override
	public void setEnabled(boolean enabled){
		this.enabled=enabled;
	}
	@Override
	public boolean onClick(Point p){
		if(enabled){
			return super.onClick(p);
		}
		return false;
	}
	@Override
	public void doTask() {
		if(enabled){
			//detect step 0,2,4
			manager.getDetector().detect(manager.getStepIndex(),this);
		}
	}
	@Override
	public void draw(Context2d context){
		if(enabled){
			super.draw(context);
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
