/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.button.WelcomeButton;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.level.Level;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.WorldType;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.CircleButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.ImageLayer;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class WorldSelectPage extends HasLoginButtonPage implements MouseDownHandler,MouseUpHandler,KeyPressHandler,KeyDownHandler{
	private GameButton button;
	private WorldMenu menu;
	
	private Point mP=null;
	public WorldSelectPage(){
		super(new Point(getGameWidth()-25,25), new Point(getGameWidth()-55,25),50,
				TextAlign.RIGHT, ASBOTXConfigs.Color.DARK_GRAY);
	}
	@Override
	public void onClick(Point p) {
		if(button.onClick(p)||menu.onClick(p)){
			game.getLocalPlayer().setMenuIndex(menu.getCurrentIndex());
		}
	}
	@Override
	public void onMouseDown(MouseDownEvent event) {
		if(!isBlocked()){
			mP=MEngine.getMousePosition();
		}
	}
	@Override
	public void onMouseUp(MouseUpEvent event) {
		if(!isBlocked()){
			mP=null;
			menu.release();
		}
	}
	@Override
	protected void progressFinished() {
		menu.updateWorldButtons();
	}
	@Override
	public void regHandlers(){
		super.regHandlers();
		MEngine.addMouseDownHandler(this);
		MEngine.addMouseUpHandler(this);
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		if(mP!=null&&!isBlocked()){
			Point tmp=MEngine.getMousePosition();
			menu.move((float)mP.delta(tmp).getVectorX()*1.3f);
			mP=tmp;
		}
		super.update();
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
		button=new WelcomeButton(new Point(25,25),50);
		menu=new WorldMenu(new Point(getGameWidth()/2,getGameHeight()/2),game.getLocalPlayer().getMenuIndex());
		
		root.addComponentOnLayer(button);
		root.addComponentOnLayer(menu);
		root.addLayer(new ImageLayer(MEngine.getAssetManager().getSpriteSheet("images/blue_bg.png"), 720, 540));
	}
	
	private class WorldMenu extends GeneralComponent{
		private RollerControlButton[] buttons=new RollerControlButton[2];
		private ButtonsRoller roller;
		protected WorldMenu(Point p,int index) {
			super(p, getGameWidth(),250);
			roller=new ButtonsRoller(getPosition(),getWidth(),getHeight(),index);
			buttons[0]=new RollerControlButton(new Point(getLeftX()+30,getY()),roller,true);
			buttons[1]=new RollerControlButton(new Point(getRightX()-30,getY()),roller,false);
		}
		public boolean onClick(Point p){
			if(!roller.onClick(p)){
				for(int i=0;i<buttons.length&&!buttons[i].onClick(p);i++){
					if(i==buttons.length-1){
						return false;
					}
				}
			}
			return true;
		}
		public void updateWorldButtons(){
			roller.updateWorldButtons();
		}
		public void move(float delta){
			roller.move(delta);
		}
		public void release(){
			roller.release();
		}
		public int getCurrentIndex(){
			return roller.getCurrentIndex();
		}
		@Override
		public void update() {
			roller.update();
			buttons[0].update();
			controlButton(roller.isBeginning(),0);
			
			buttons[1].update();
			controlButton(roller.isEnd(),1);
			
		}
		private void controlButton(boolean b,int i){
			if(b){
				buttons[i].setEnabled(false);
			}else{
				buttons[i].setEnabled(true);
			}
		}
		@Override
		public void draw(Context2d context) {
			roller.draw(context);
			buttons[0].draw(context);
			buttons[1].draw(context);
		}
		
	}
	private class ButtonsRoller extends GeneralComponent{
		private BigButton[] buttons; 
		private int currentIndex;
		
		private final float SPACING;
		private final float HALF_SPACING;
		private float distance=0;
		
		private final float transY;
		private float[] transX;
		
		private boolean released=true;
		private float fallbackStep=0;
		private byte fallbackDir;
		private final float FALLBACK_SPEED=15;
		private BoxButton sensorButton;
		
		protected ButtonsRoller(Point p, double w,double h,int index) {
			super(p, w,h);
			transY=(float)getY();
					
			
			int offset=2*((int)getHeight()+SpriteBlock.SPACING);
			WorldType[] worlds=WorldType.values();
			
			int arraySize=worlds.length+1;
			buttons=new BigButton[arraySize];
			transX=new float[arraySize];
			
			this.currentIndex=index>arraySize-1?arraySize-1:index;
			/*buttons[0]=new TutorialBigButton(getHeight());*/
			/*for(int i=0;i<worlds.length;i++){
				buttons[i+1]=new WorldButton(getHeight(),(1+i)*offset,worlds[i]);
			}*/
			for(int i=0;i<worlds.length;i++){
				buttons[i]=new WorldButton(getHeight(),(1+i)*offset,worlds[i]);
			}
			buttons[buttons.length-1]=new LeaderBoardBigButton(getHeight());
			
			
			SPACING=(float)getWidth()/2;
			HALF_SPACING=SPACING/2;
			
			for(int i=0;i<transX.length;i++){
				transX[i]=(i-currentIndex+1)*SPACING;
			}
			
			
			sensorButton=new BoxButton(getPosition(), 220, 220, null){
				
				@Override
				public void doTask() {
					buttons[currentIndex].doTask();
					
				}

				@Override
				public void update() {
					// TODO Auto-generated method stub
					
				}
				
			};
		}
		public void updateWorldButtons(){
			for(int i=0;i<buttons.length-1;i++){
				((WorldButton)buttons[i]).updateTotalText();
			}
		}
		public boolean isBeginning(){
			return currentIndex==0;
		}
		public boolean isEnd(){
			return currentIndex==buttons.length-1;
		}
		public void move(float delta){
			float abD=Math.abs(distance);
			float abDD=Math.abs(distance+delta);
			if((abD<HALF_SPACING&&abDD>=HALF_SPACING)||(abD>HALF_SPACING&&abDD<=HALF_SPACING)){
				int d=delta<0?(currentIndex+1<buttons.length?1:0):(currentIndex-1>=0?-1:0);
				if(d!=0){
					currentIndex+=d;
				}else{
					delta=0;
				}
			}
			
			delta=abDD>SPACING?Math.copySign(SPACING-abD, delta):delta;
			
			distance+=delta;	
			
			for(int i=0;i<transX.length;i++){
				transX[i]+=delta;
			}
			released=false;
			
		}
		public void release(){
			distance=0;
			released=true;
			prepareFallback();
		}
		private void prepareFallback(){
			float value=(float)(getX()-transX[currentIndex])/FALLBACK_SPEED;
			fallbackDir=(byte)Math.copySign(1, value);
			fallbackStep=(float)Math.ceil(Math.abs(value));
		}
		public int getCurrentIndex(){
			return currentIndex;
		}
		public boolean onClick(Point p){
			
			return fallbackStep==0&&sensorButton.onClick(p);
		}
		@Override
		public void update() {
			if(fallbackStep>0&&released){
				float speed=fallbackStep==1?(float)getX()-transX[currentIndex]:FALLBACK_SPEED*fallbackDir;
				for(int i=0;i<transX.length;i++){
					transX[i]+=speed;
				}
				fallbackStep--;
			}
		}

		@Override
		public void draw(Context2d context) {
			context.save();
			context.translate(0, transY);
			
			float sa;
			for(int i=-1+currentIndex;i<=1+currentIndex;i++){
				if(i>=0&&i<buttons.length){
					sa=-(float)Math.abs(getX()-transX[i])/720+1;
					context.save();
					context.translate(transX[i], 0);
					context.scale(sa,sa);
					context.setGlobalAlpha(sa);
					buttons[i].draw(context);
					context.restore();
				}
			}
			context.restore();
		}
		
	}
	private class RollerControlButton extends CircleButton{
		private boolean enabled=true;
		private ButtonsRoller roller;
		private boolean left;
		private int rollingStep=0;
		private final int ROLLING_FULL_STEP=24;
		public RollerControlButton(Point p,ButtonsRoller roller,boolean left) {
			super(p, 25,new SpriteBlock((left?5:4)*(200+SpriteBlock.SPACING),2*(200+SpriteBlock.SPACING),200,200, ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
			this.left=left;
			this.roller=roller;
		}
		public void setEnabled(boolean enabled){
			this.enabled=enabled;
		}
		@Override
		public void doTask() {
			if(enabled){
				rollingStep=ROLLING_FULL_STEP;
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
			if(rollingStep>0){
				if(left){
					roller.move(15);
				}else{
					roller.move(-15);
				}
				if((--rollingStep)==0){
					roller.release();
				}
				
			}
		}
		
	}
	private abstract class BigButton extends BoxButton{
		public BigButton(double bounds,int x) {
			super(new Point(0,0), bounds, bounds, new SpriteBlock(x,0,250,250,MEngine.getAssetManager().getSpriteSheet("images/big_buttons.png")));
		}
		
		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
	}
	private class WorldButton extends BigButton{
		private final WorldType world;
		private String totalFont;
		private String totalText;
		private double totalY;
		private long fullTotal=0;
		public WorldButton(double bounds,int x,WorldType world) {
			super(bounds,x);
			for(int i=1;i<10;i++){
				fullTotal+=Level.queryFullPower(world, i);
			}
			int fontSize=(int)Math.ceil(bounds/12);
			this.world=world;
			this.totalFont=ASBOTXConfigs.getCGFont(fontSize);
			updateTotalText();
			this.totalY=(float)(getBottomY()+2.5f);
		}
		public void updateTotalText(){
			this.totalText=((ASBOTXGame)getGame()).getLocalPlayer().getTotalInWorld(world)+"/"+fullTotal;
		}
		@Override
		public void doTask() {
			if(((ASBOTXGame)getGame()).getLocalPlayer().isLevelUnlocked(WorldType.INTRO, 1)){
				getGame().setPage(new LevelSelectPage(world));
			}else{
				getGame().setPage(new LoadingLevelPage(new TutorialGamePage()));
			}
		}
		@Override
		public void draw(Context2d context){
			super.draw(context);
			context.save();
			context.setTextAlign(TextAlign.CENTER);
			context.setTextBaseline(TextBaseline.MIDDLE);
			context.setFillStyle(ASBOTXConfigs.Color.PURE_YELLOW);
			context.setFont(totalFont);
			context.fillText(totalText, getX(), totalY);
			context.restore();
		}
	}
	private class TutorialBigButton extends BigButton{
		/*private String csFont;
		private String csText="NEW!";
		private float csY;	*/
		public TutorialBigButton(double bounds) {
			super(bounds, 250+SpriteBlock.SPACING);
			/*this.csFont=ASBOTXConfigs.getCGBoldFont(20);
			this.csY=(float)(getTopY()-7f);*/
		}
		/*@Override
		public void draw(Context2d context){
			super.draw(context);
			context.save();
			context.setTextAlign(TextAlign.CENTER);
			context.setTextBaseline(TextBaseline.MIDDLE);
			context.setFillStyle(ASBOTXConfigs.Color.RED);
			context.setFont(csFont);
			context.fillText(csText, getX(), csY);
			context.restore();
		}*/
		
		@Override
		public void doTask(){
			getGame().setPage(new LoadingLevelPage(new TutorialGamePage()));
		}
	}
	private class LeaderBoardBigButton extends BigButton{

		public LeaderBoardBigButton(double bounds) {
			super(bounds, 0);
		}
		
		public void doTask() {
			getGame().setPage(new LeaderboardPage());
		}
	}
}
