/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.Player;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.LevelSelectButton;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.WorldType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Window;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXLevelSelectPage extends GeneralPage {
	private Player player;
	private ButtonBoard board;
	private LeaderboardButton leaderboardButton;
	private GroupLayer layers=new GroupLayer();
	/**
	 * @param game
	 */
	public ASBOTXLevelSelectPage() {
		
		this.player=((ASBOTXGame)getGame()).getPlayer();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		Point p=MEngine.getMousePosition();
		if(!board.select(p))
			leaderboardButton.onClick(p);
		
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		layers.draw(context);

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#onScreen()
	 */
	@Override
	public void onScreen() {
		layers.addComponentOnLayer(new GameLabel(new Point(getGameWidth()/2,60),TextAlign.CENTER,TextBaseline.MIDDLE,
														 WorldType.INTRO.getTitle(),ASBOTXConfigs.Color.GRAY,ASBOTXConfigs.getGameFont(32)));
		leaderboardButton=new LeaderboardButton();
		board=new ButtonBoard(400,getGameWidth()/2-200,120);
		layers.addComponentOnLayer(board);
		layers.addComponentOnLayer(leaderboardButton);
		
		
	}
	private class LeaderboardButton extends BoxButton{

		public LeaderboardButton() {
			super(new Point(70,25f), 140f, 50,new SpriteBlock(630,0,280,100,MEngine.getAssetManager().getSpriteSheet("images/buttons.png")));
			
		}

		@Override
		public void doTask() {
			Window.alert("Total="+player.getTotalInWorld(WorldType.INTRO));
			
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class ButtonBoard extends GeneralComponent{
		private LevelSelectButton[][] buttons=new LevelSelectButton[3][3];
		
		private static final int SPACING=35;
		private double bounds;
		
		private int cornerX;
		private int cornerY;
		ButtonBoard(double bounds,int cornerX,int cornerY){
			super(new Point(cornerX+bounds/2,cornerY+bounds/2), bounds, bounds);
			this.cornerX=cornerX;
			this.cornerY=cornerY;
			this.bounds=bounds;
			
			bounds-=2*SPACING;
			bounds/=3;
			int counter=0;
			outerLoop:
			for(int i=0;i<buttons.length;i++){
				for(int j=0;j<buttons[i].length;j++){
				buttons[i][j]=new LevelSelectButton(new Point(cornerX+bounds/2+j*(bounds+SPACING),
													cornerY+bounds/2+i*(bounds+SPACING)),
													bounds,WorldType.INTRO,++counter,
													player.isLevelUnlocked(WorldType.INTRO,counter));//adjust later
				
				//remove it after finishing designing 9 levels
				if(counter==5)
					break outerLoop;
				}
				
			}
		}
		 boolean select(Point p){
			if(p.getX()>cornerX&&p.getX()<cornerX+bounds&&p.getY()>cornerY&&p.getY()<cornerY+bounds){
				Point cl=p.clone();
				cl.translate(-cornerX,-cornerY);
				
				int i=(int)(cl.getY()*buttons.length/bounds);
				int j=(int)(cl.getX()*buttons.length/bounds);
				if(buttons[i][j]!=null&&buttons[i][j].onClick(p)){
					return true;
				}
				return false;
			}
			return false;
		}
		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void draw(Context2d context) {
			outer:
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					if(buttons[i][j]!=null){
						buttons[i][j].draw(context);
					}else{
						break outer;
					}
				}
			}
			
		}
	}
	
}
