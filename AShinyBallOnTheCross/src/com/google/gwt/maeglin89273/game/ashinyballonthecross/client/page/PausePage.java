/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.Glass;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.ImageLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.Layer;
import com.google.gwt.maeglin89273.game.mengine.page.GamePage;
import com.google.gwt.maeglin89273.game.mengine.page.SinglePage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public class PausePage extends SinglePage{
	
	private static final String pauseTextFont=ASBOTXConfigs.getCGFont(26);
	private static final String clickTextFont=ASBOTXConfigs.getCGFont(15);

	private Level level;
	
	private GroupLayer root;
	private GameLabel clickLabel;
	private HintButton button;
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.GeneralPage#onClick(com.google.gwt.maeglin89273.game.mengine.utility.physics.Point)
	 */
	public PausePage(GamePage gamePage){
		super(gamePage);
		this.level=((ASBOTXGamePage)gamePage).getLevel();
	}
	@Override
	public void onClick(ClickEvent event){
		if(button==null||!button.onClick(MEngine.getMousePosition())){
			this.toNextPage();
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#update()
	 */
	@Override
	public void update() {
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.page.Page#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		root.draw(context);
	}
	@Override
	public void onScreen() {
		Point center=new Point(getGameWidth()/2,getGameHeight()/2);
		clickLabel=new GameLabel(new Point(center.getX(),center.getY()+32), TextAlign.CENTER, TextBaseline.MIDDLE, "click here to continue", ASBOTXConfigs.Color.WHITE, clickTextFont);
		root=new GroupLayer();
		if(level.hasHint()){
			this.button=new HintButton(new Point(center.getX(),center.getY()+225));
			root.addComponentOnLayer(button);
		}
		root.addComponentOnLayer(new GameLabel(center, TextAlign.CENTER, TextBaseline.MIDDLE, "Pause", ASBOTXConfigs.Color.WHITE, pauseTextFont));
		root.addComponentOnLayer(clickLabel);
		root.addComponentOnLayer(new Glass(getGameWidth(),getGameHeight()));
		root.addLayer(new Layer(){

			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void draw(Context2d context) {
				nextPage.draw(context);
			}
			
		});
		
		
		
		
	}
	private class HintButton extends BoxButton{
		private static final int HINT_WIDTH=560;
		private static final int HINT_HEIGHT=420;
		public HintButton(Point p) {
			super(p, 100, 50, new SpriteBlock(ASBOTXConfigs.Utility.buttonSpacingTimes(4),
					ASBOTXConfigs.Utility.buttonSpacingTimes(3),
					200,100,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		}

		@Override
		public void doTask() {
			
			root.removeLayer(0);
			root.insertLayer(0,
					new ImageLayer(MEngine.getAssetManager().getSpriteSheet("images/hints/"+level.getWorldType()+"_hint_"+level.getLevelNumber()+".png"),
					new Point((getGameWidth()-HINT_WIDTH)/2,(getGameHeight()-HINT_HEIGHT)/2), HINT_WIDTH, HINT_HEIGHT));
			clickLabel.setText("click anywhere to continue");
			clickLabel.setY(30);
			button=null;
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
	}
}
