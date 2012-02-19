/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.ImageLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXInformationPage extends GeneralPage {
	private InformationRoll roll;
	private GameButton button;
	private ImageLayer background;
	private GroupLayer root;
	/**
	 * 
	 */
	public ASBOTXInformationPage() {
		this.roll=new InformationRoll(600,540);
		this.button=new BackButton(new Point(50,getGameHeight()-50));
		this.background=new ImageLayer(MEngine.getAssetManager().getSpriteSheet("images/gray_bg.png"), getGameWidth(), getGameHeight());
		this.root=new GroupLayer();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		button.onClick(MEngine.getMousePosition());

	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		root.update();

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		root.draw(context);

	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#onScreen()
	 */
	@Override
	public void onScreen() {
		root.addComponentOnLayer(button);
		root.addComponentOnLayer(roll);
		root.addLayer(background);
	}
	private class InformationRoll extends GeneralComponent implements MouseWheelHandler{
		private SpriteBlock block;
		private static final int V=1;
		
		protected InformationRoll(double w, double h) {
			super(new Point(getGameWidth()/2,getGameHeight()+h/2), w, h);
			this.block=new SpriteBlock((int)w,(int)h, MEngine.getAssetManager().getSpriteSheet("images/information.png"));
		}

		@Override
		public void update() {
			roll(V);
			
		}
		private void roll(int velocity){
			if(getTopY()>0){
				setY(getY()-velocity);
			}else if(getTopY()==0){
				block.setY(block.getY()+velocity);
				if(block.getY()+getHeight()>=block.getSpriteSheet().getHeight()){
					setY(getY()-velocity);
				}
			}else{
				setY(getY()-velocity);
				if(getBottomY()<=0){
					reset();
				}
			}
		}
		private void reset(){
			setY(getGameHeight()+getHeight()/2);
			block.setY(0);
		}
		public void accelerate(boolean down){
			if(down){
				roll(10*V);
			}else{
				roll(-20*V);
			}
		}
		@Override
		public void draw(Context2d context) {
			context.drawImage(block.getSheetImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(),
					getLeftX(), getTopY(), getWidth(), getHeight());
			
		}

		@Override
		public void onMouseWheel(MouseWheelEvent event) {
			roll.accelerate(event.isSouth());
			
		}
		
	}
	private class BackButton extends BoxButton{

		public BackButton(Point p) {
			super(p, 100, 100,new SpriteBlock(4*(200+SpriteBlock.SPACING),2*(200+SpriteBlock.SPACING),200,200, ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		}

		@Override
		public void doTask() {
			getGame().setPage(new ASBOTXWelcomePage());
			
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
