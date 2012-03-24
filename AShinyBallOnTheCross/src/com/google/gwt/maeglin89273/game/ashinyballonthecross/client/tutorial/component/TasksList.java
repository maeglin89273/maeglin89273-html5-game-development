/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class TasksList extends GeneralComponent implements StepComponent{
	private static final int WIDTH=255;
	
	private static final int BOX_X_OFFSET=17;
	private static final int BOX_Y_SPACING=30;
	private final SpriteBlock block;
	private boolean enabled=false;
	
	private CheckBox[] boxes;
	
	private final double screenHeight;
	public TasksList(double centerX,double screenHeight) {
		super(new Point(centerX,0), WIDTH, 0);
		this.screenHeight = screenHeight;
		block=new SpriteBlock(0,0,255,0,MEngine.getAssetManager().getSpriteSheet("images/tutorial_tasks.png"));
	}
	@Override
	public void setEnabled(boolean enabled){
		this.enabled=enabled;
	}
	public void setup(int h,int blockY,int checkBoxesNum){
		setEnabled(true);
		this.setHeight(h);
		this.setPositionAt(PositionType.NORTH, new Point(getX(),screenHeight-getHeight()-10));
		block.setHeight(h);
		block.setY(blockY);
		boxes=new CheckBox[checkBoxesNum];
		double offsetY=getTopY()+20;
		for(int i=0;i<boxes.length;i++){
			boxes[i]=new CheckBox(new Point(getLeftX()+BOX_X_OFFSET,offsetY+BOX_Y_SPACING*i));
		}
	}
	public boolean check(int index){
		if(enabled){
			boxes[index].check();
			
			boolean toReturn=true;
			for(int i=0;i<boxes.length&&(toReturn&=boxes[i].isChecked());i++);
			return toReturn;
		}
		return false;
	}
	public boolean isChecked(int index){
		if(enabled){
			return boxes[index].isChecked();
		}else{
			return false;
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Context2d context) {
		if(enabled){
			context.drawImage(block.getSheetImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(),getLeftX(),getTopY(), getWidth(), getHeight());
			for(CheckBox box:boxes){
				box.draw(context);
			}
			
		}
		
	}
	
	private class CheckBox extends GeneralComponent {
		private SpriteBlock block;
		private boolean checked=false;
		protected CheckBox(Point p) {
			super(p, 22.5, 20);
			block=new SpriteBlock(3*(200+SpriteBlock.SPACING),0,45,40, ASBOTXConfigs.Utility.getButtonsSpriteSheet());
		}
		public void check(){
			checked=true;
			block.setY(40+SpriteBlock.SPACING);
		}
		public boolean isChecked(){
			return checked;
		}
		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void draw(Context2d context) {
			context.drawImage(block.getSheetImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(),getLeftX(),getTopY(), getWidth(), getHeight());
		}
		
	}
}


