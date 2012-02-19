/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.LocalPlayer;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.WelcomeButton;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.WorldType;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
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
	private LocalPlayer localPlayer;
	private ButtonBoard board;
	private GameButton[] buttons=new GameButton[2];
	
	private GroupLayer layers=new GroupLayer();
	/**
	 * @param game
	 */
	public ASBOTXLevelSelectPage() {
		
		this.localPlayer=((ASBOTXGame)getGame()).getLocalPlayer();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		Point p=MEngine.getMousePosition();
		if(!board.select(p))
			for(int i=0;i<buttons.length&&!buttons[i].onClick(p);i++);
		
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
														 WorldType.INTRO.getTitle(),ASBOTXConfigs.Color.GRAY,ASBOTXConfigs.getCGFont(32)));
		buttons[0]=new LeaderboardButton();
		buttons[1]=new WelcomeButton(new Point(getGameWidth()-25,25),50);
		board=new ButtonBoard(400,getGameWidth()/2-200,110);
		layers.addComponentOnLayer(board);
		for(GameButton  button:buttons){
			layers.addComponentOnLayer(button);
		}
		
		
	}
	private class LeaderboardButton extends BoxButton{

		public LeaderboardButton() {
			super(new Point(67.5f,25f), 135, 50,new SpriteBlock(630,0,272,100,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
			
		}

		@Override
		public void doTask() {
			getGame().setPage(new ASBOTXLeaderboardPage());
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
			
			for(int i=0;i<buttons.length;i++){
				for(int j=0;j<buttons[i].length;j++){
				buttons[i][j]=new LevelSelectButton(new Point(cornerX+bounds/2+j*(bounds+SPACING),
													cornerY+bounds/2+i*(bounds+SPACING)),
													bounds,WorldType.INTRO,++counter,
													localPlayer.isLevelUnlocked(WorldType.INTRO,counter));//adjust later
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
	
	
	private class LevelSelectButton extends BoxButton{
		private final WorldType world;	
		
		private final int levelNum;
		private final String scoreText;
		
		private final String numberFont;
		private final String bestScoreFont;
		
		private final float scoreY;
		private final boolean unlocked;
		/**
		 * @param p
		 * @param w
		 * @param h
		 * @param block
		 */
		private LevelSelectButton(Point p,double bounds,WorldType world,int level,boolean unlocked) {
			super(p,(int)bounds,(int)bounds,
					(unlocked?new SpriteBlock(200+SpriteBlock.SPACING,200+SpriteBlock.SPACING,200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet()):
							new SpriteBlock(2*(200+SpriteBlock.SPACING),200+SpriteBlock.SPACING,200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet())));
			this.unlocked=unlocked;
			this.world = world;
			this.levelNum = level;
			this.numberFont=ASBOTXConfigs.getCGFont((int)(bounds/4));
			if(unlocked){
				this.bestScoreFont=ASBOTXConfigs.getCGFont((int)(bounds/6));
				this.scoreText=((ASBOTXGame)MEngine.getGeneralGame()).getLocalPlayer().getScoreAt(world, level)+"/"+Level.queryFullPower(world,level);
				this.scoreY=(float)(getBottomY()+bounds/12+2.5f);
			}else{
				this.bestScoreFont=null;
				this.scoreText=null;
				this.scoreY=0;
			}
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.mengine.component.GameButton#doTask()
		 */
		@Override
		public void doTask() {
			if(unlocked){
				MEngine.getGeneralGame().setPage(new ASBOTXLoadingLevelPage("levels/"+world.toString()+"_level_"+levelNum+".json"));
			}
		}

		

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
		 */
		
		@Override
		public void draw(Context2d context){
			super.draw(context);
			if(unlocked){
				context.save();
				context.setTextAlign(TextAlign.CENTER);
				context.setTextBaseline(TextBaseline.MIDDLE);
				context.setFillStyle(ASBOTXConfigs.Color.GRAY);
				context.setFont(numberFont);
				context.fillText(Integer.toString(levelNum),getX(),getY());
				context.setFillStyle(ASBOTXConfigs.Color.TRANSPARENT_BLUE);
				context.setFont(bestScoreFont);
				context.fillText(scoreText, getX(), scoreY);
				context.restore();
			}
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
