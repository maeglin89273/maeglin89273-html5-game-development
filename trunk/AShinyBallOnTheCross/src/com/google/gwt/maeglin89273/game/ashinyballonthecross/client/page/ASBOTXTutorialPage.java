/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.WelcomeButton;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.Camera;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.WorldLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXTutorialPage extends GeneralPage implements MouseDownHandler,MouseUpHandler,MouseWheelHandler{
	private GameButton[] buttons=new GameButton[2];
	
	private GroupLayer root;
	private WorldLayer mainLayer;
	private Camera camera;
	private Point grabPos=null;
	/**
	 * 
	 */
	public ASBOTXTutorialPage() {
		buttons[0]=new GuideButton(getGameHeight()/2);
		buttons[1]=new WelcomeButton(new Point(25,25),50);
		mainLayer=new WorldLayer(new TutorialImage(),1);
		root=new GroupLayer();
		this.camera=mainLayer.getCamera();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		Point p=MEngine.getMousePosition();
		for(int i=0;i<buttons.length&&!buttons[i].onClick(p);i++);
	}
	@Override
	public void onMouseUp(MouseUpEvent event) {
		
		if(event.getNativeButton()==NativeEvent.BUTTON_LEFT){
			Vector v=MEngine.getMousePosition().delta(grabPos);
			v.setVectorX(0);
			camera.move(v, false);
			grabPos=null;
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		if(event.getNativeButton()==NativeEvent.BUTTON_LEFT){
			grabPos=MEngine.getMousePosition();
		}
		
	}
	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		camera.move(new Vector(0,event.isSouth()?15:-15), false);
		
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		if(grabPos!=null){
			Point mP=MEngine.getMousePosition();
			Vector v=mP.delta(grabPos);
			v.setVectorX(0);
			camera.move(v, true);
			grabPos.setPosition(mP);
		}
		root.update();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		root.draw(context);

	}
	@Override
	public void regHandlers(){
		super.regHandlers();
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseUpHandler(this);
		MEngine.addMouseWheelHandler(this);
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#onScreen()
	 */
	@Override
	public void onScreen() {
		for(GameButton  button:buttons){
			root.addComponentOnLayer(button);
		}
		root.addLayer(mainLayer);
		camera.setPosition(new Point(getGameWidth()/2,getGameHeight()/2));
	}
	private class TutorialImage extends GeneralComponent{
		private SpriteSheet img;
		protected TutorialImage() {
			super(new Point(720,710), 1440, 1420);
			this.img=MEngine.getAssetManager().getSpriteSheet("images/tutorial.png");
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void draw(Context2d context) {
			context.drawImage(img.getImage(),60,50);
			
		}
		
	}
	private class GuideButton extends BoxButton{
		private boolean next=true;
		private boolean moving=false;
		private int count=0;
		private double vY;
		public GuideButton(double y) {
			super(new Point(getGameWidth()-25,y), 50, 50,new SpriteBlock(5*(200+SpriteBlock.SPACING),2*(200+SpriteBlock.SPACING),200,200, ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
			
		}

		

		@Override
		public void doTask() {
			moving=true;
			vY=-camera.getTopY()/24;
			setNext(!next);
		}
		public void setNext(boolean next){
			this.next=next;
			if(next){
				spriteBlock.setX(5*(200+SpriteBlock.SPACING));
				this.setX(getGameWidth()-30);
			}else{
				spriteBlock.setX(4*(200+SpriteBlock.SPACING));
				this.setX(30);
			}
		}
		@Override
		public void update() {
			if(moving){
				camera.move(new Vector(next?-30:30,vY), true);
				if(++count==24){
					count=0;
					moving=false;
				}
			}
		}
		
	}
	
	
}
