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
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.Glass;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.WorldType;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.CircleButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.ComponentLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXScoreShowingPage extends GeneralPage{
	private enum Status{NEW_HIGH_SCORE,GENERAL,LEVEL_FAILED}
	private Status status;
	private Player player;
	private final int score;
	private final Level level;
	private ScoreBoard board;
	private GroupLayer layers;
	
	/**
	 * @param game
	 */
	public ASBOTXScoreShowingPage(GroupLayer gameRootLayer,Level level,int score) {
		
		this.layers=new GroupLayer();
		this.layers.addLayer(gameRootLayer);
		this.level = level;
		this.score = score;
		this.player=((ASBOTXGame)getGame()).getPlayer();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		board.onClick(MEngine.getMousePosition());

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		layers.draw(context);
		//display the score panel
		
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#onScreen()
	 */
	@Override
	public void onScreen() {
		saveAndSetStatus();
		this.board=new ScoreBoard(new Point(getGameWidth()/2,getGameHeight()/2));
		layers.insertLayer(0,new ComponentLayer(new Glass(getGameWidth(),getGameHeight())));
		layers.insertLayer(0,new ComponentLayer(board));
		
		
	}
	
	private void saveAndSetStatus(){
		if(player.getScoreAt(level)<score){
			player.setScoreAt(level, score);
			status=Status.NEW_HIGH_SCORE;
			if(level.getLevelNumber()<9){
				unlockNextLevel(level,level.getWorldType(),level.getLevelNumber()+1);
			}else{
				//unlock at the next world level 1
			}
		}else{
			if(score>=level.getRequiredScore()){
				status=Status.GENERAL;
			}else{
				status=Status.LEVEL_FAILED;
			}
		}
		player.save();
	}
	
	private void unlockNextLevel(Level thisLevel,WorldType nextWorld,int nextLevelNumber){
		if(!player.isLevelUnlocked(nextWorld, nextLevelNumber)){
				if(score>=level.getRequiredScore()){
					player.setScoreAt(nextWorld,nextLevelNumber,0);
				}else{
					status=Status.LEVEL_FAILED;
				}
		}
	}
	private class ScoreBoard extends GeneralComponent{
		private static final int BUTTONS_SPACING=180;
		private static final int BUTTONS_BOUNDS=120;
		GameButton[] buttons;
		GameLabel title;
		GameLabel[] scoreLabels;
		SpriteSheet board=MEngine.getAssetManager().getSpriteSheet("images/score_board.png");
		
		ScoreBoard(Point center) {
			super(center, 540,375);
			
			this.title=new GameLabel(new Point(getLeftX()+15,getTopY()+25), TextAlign.LEFT, TextBaseline.TOP,
					level.toString(), ASBOTXConfigs.Color.TRANSLUCENT_DARK_GRAY, ASBOTXConfigs.getGameFont(35));
			double buttonsY=getBottomY()-20;
			Point leftButtonPos=new Point(getX()-BUTTONS_SPACING,buttonsY);
			Point centerButtonPos=new Point(getX(),buttonsY);
			Point rightButtonPos=new Point(getX()+BUTTONS_SPACING,buttonsY);
			
			
			switch(status){
				case NEW_HIGH_SCORE:
					
					scoreLabels=new GameLabel[]{new GameLabel(new Point(getX(),getY()-40), TextAlign.CENTER, TextBaseline.MIDDLE,
							"New High Score!", ASBOTXConfigs.Color.LIGHT_BLUE,ASBOTXConfigs.Color.DARK_BLUE,0.5f, ASBOTXConfigs.getGameFont(45)),
							new GameLabel(new Point(getX(),getY()+50), TextAlign.CENTER, TextBaseline.MIDDLE,
									Integer.toString(score),ASBOTXConfigs.Color.LIGHT_YELLOW, ASBOTXConfigs.Color.YELLOW_BORDER,2, ASBOTXConfigs.getGameFont(70))};
					buttons=new GameButton[]{new LevelMenuButton(leftButtonPos,BUTTONS_BOUNDS),
							 new ReplayButton(centerButtonPos,BUTTONS_BOUNDS),
							 new NextLevelButton(rightButtonPos,BUTTONS_BOUNDS)};
					break;
				case GENERAL:
					scoreLabels=new GameLabel[]{new GameLabel(new Point(getX(),getY()-30), TextAlign.CENTER, TextBaseline.MIDDLE,
							"High Score "+player.getScoreAt(level), ASBOTXConfigs.Color.LIGHT_BLUE,ASBOTXConfigs.Color.DARK_BLUE,0.5f, ASBOTXConfigs.getGameFont(45)),
												new GameLabel(new Point(getX(),getY()+50), TextAlign.CENTER, TextBaseline.MIDDLE,
									"Score "+score, ASBOTXConfigs.Color.GRAY,ASBOTXConfigs.Color.DARK_GRAY,1, ASBOTXConfigs.getGameFont(45))};
					buttons=new GameButton[]{new LevelMenuButton(leftButtonPos,BUTTONS_BOUNDS),
											 new ReplayButton(centerButtonPos,BUTTONS_BOUNDS),
											 new NextLevelButton(rightButtonPos,BUTTONS_BOUNDS)};
					break;
				case LEVEL_FAILED:
					scoreLabels=new GameLabel[]{new GameLabel(new Point(getX(),getY()+20), TextAlign.CENTER, TextBaseline.MIDDLE,
												"Level Failed!", ASBOTXConfigs.Color.BLACK, ASBOTXConfigs.getGameFont(45))};
					buttons=new GameButton[]{new LevelMenuButton(leftButtonPos,BUTTONS_BOUNDS),
			 				 new ReplayButton(rightButtonPos,BUTTONS_BOUNDS)};
							 				 
			}
		}
		public void onClick(Point p){
			for(GameButton button:buttons){
				if(button.onClick(p)){
					return;
				}
				
			}
		}
		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void draw(Context2d context) {
			context.drawImage(board.getImage(), getLeftX(), getTopY(), getWidth(), getHeight());
			//draw scores and title...
			title.draw(context);
			for(GameLabel scoreLabel:scoreLabels){
				scoreLabel.draw(context);
			}
			for(GameButton button:buttons){
				button.draw(context);
			}
		}
		
		
		private class NextLevelButton extends CircleButton{
			
			NextLevelButton(Point p, int d) {
				super(p, d/2, new SpriteBlock(200+SpriteBlock.SPACING,3*(200+SpriteBlock.SPACING),200,200,
						MEngine.getAssetManager().getSpriteSheet("images/buttons.png")));
				
			}

			@Override
			public void doTask() {
				if(level.getLevelNumber()<9){
					getGame().setPage(new ASBOTXLoadingLevelPage("levels/"+level.getWorldType().toString()+"_level_"+(level.getLevelNumber()+1)+".json"));
				}else{
					//to the next world level 1
				}
				
			}

			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}
			
		}
		private class ReplayButton extends CircleButton{

			public ReplayButton(Point p, int d) {
				super(p, d/2, new SpriteBlock(2*(200+SpriteBlock.SPACING),3*(200+SpriteBlock.SPACING),200,200,
						MEngine.getAssetManager().getSpriteSheet("images/buttons.png")));
				// TODO Auto-generated constructor stub
			}

			@Override
			public void doTask() {
				getGame().setPage(new ASBOTXLoadingLevelPage("levels/"+level.getWorldType().toString()+"_level_"+level.getLevelNumber()+".json"));
				
			}

			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}
			
		}
		private class LevelMenuButton extends BoxButton{

			public LevelMenuButton(Point p, double bounds){
					
				super(p, bounds,bounds,new SpriteBlock(0,3*(200+SpriteBlock.SPACING),200,200,
						MEngine.getAssetManager().getSpriteSheet("images/buttons.png")));
				
			}

			@Override
			public void doTask() {
				getGame().setPage(new ASBOTXLevelSelectPage());//the other constructor of LevelSelectPage require WorldType constant as a parameter
				
			}

			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}
			
		}
	}
	
	
}
