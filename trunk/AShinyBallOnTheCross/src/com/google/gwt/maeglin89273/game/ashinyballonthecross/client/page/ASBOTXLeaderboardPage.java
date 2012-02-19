/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.Glass;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.IDEnteredBoard;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.IDEnteredBoard.CloseTask;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.LevelMenuButton;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.LoginButtonAndIDDisplay;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.LoginButtonAndIDDisplay.AppendProgress;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginResponse;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Leader;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.LeaderboardService;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.LeaderboardServiceAsync;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.button.CircleButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.ComponentLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.ImageLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXLeaderboardPage extends GeneralPage implements KeyPressHandler,KeyDownHandler {
	private LeaderboardServiceAsync leaderboardSvc=GWT.create(LeaderboardService.class);
	private static final String ERROR_TEXT="Please contact the developer about this error.";
	
	private GroupLayer layers;
	
	private GameButton[] buttons=new GameButton[3];
	private LeaderboardTable table;
	private IDEnteredBoard board;
	private GameLabel progressLabel;
	GameLabel totalLabel;
	
	private ASBOTXGame game;
	
	private boolean connectionLock=false;
	
	private boolean blocked=false;
	/**
	 * 
	 */
	public ASBOTXLeaderboardPage() {
		this.game=(ASBOTXGame)getGame();
	}
	public LeaderboardServiceAsync getLeaderboardService(){
		if(leaderboardSvc==null){
			leaderboardSvc=GWT.create(LeaderboardService.class); 
		}
		return leaderboardSvc;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		Point p=MEngine.getMousePosition();
		if(blocked){
			board.getButton().onClick(p);
		}else if(p.getY()>420){
				for(int i=0;i<buttons.length&&!buttons[i].onClick(p);i++);
		}

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

	}
	@Override
	public void onKeyPress(KeyPressEvent event) {
		if(blocked){
			board.onKeyPress(event);
		}
	}
	@Override
	public void onKeyDown(KeyDownEvent event) {
		if(blocked){
			board.onKeyDown(event);
		}
		
	}
	@Override
	public void regHandlers(){
		super.regHandlers();
		MEngine.addKeyPressHandler(this);
		MEngine.addKeyDownHandler(this);
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#onScreen()
	 */
	@Override
	public void onScreen() {
		
		//initialize the components
		LoginInfo info=((ASBOTXGame)getGame()).getLoginInfo();
		
		buttons[0]=new LevelMenuButton(new Point(50,getGameHeight()-50), 100);
		buttons[1]=new RefreshButton(new Point(getGameWidth()/2,getGameHeight()-60),50);
		
		buttons[2]=new LoginButtonAndIDDisplay(new Point(getGameWidth()-50,getGameHeight()-50),new Point(3,108),
				TextAlign.LEFT,100,ASBOTXConfigs.Color.BORDER_DARK_GRAY,info.getUrl(),
				info.getStatus()==LoginInfo.Status.LOGGED_IN?game.getLocalPlayer().getID():"",
				ASBOTXConfigs.Utility.switchStatus(info.getStatus()),new AppendProgress(){

					@Override
					public void execute(CheckLoginResponse response) {
						totalLabel.setText("Total "+game.getLocalPlayer().getTotal());
						if(response.getStatus()==CheckLoginResponse.Status.NEW_PLAYER){
							board=new IDEnteredBoard(new Point(getGameWidth()/2,getGameHeight()/2),
									new CloseTask(){

										@Override
										public void doTask() {
											layers.removeLayer(1);
											layers.removeLayer(0);
											blocked=false;
											((LoginButtonAndIDDisplay)buttons[2]).setID(game.getLocalPlayer().getID());
											board=null;
											reconnect();
										}
								
							});
							layers.insertLayer(0, new ComponentLayer(new Glass(getGameWidth(),getGameHeight())));
							layers.insertLayer(0,new ComponentLayer(board));
							blocked=true;
						}else{
							reconnect();
						}
					}
			
		});
		if(!info.isConnectionSuccess()){
			((LoginButtonAndIDDisplay)buttons[2]).setEnable(false);
		}
		
		table=new LeaderboardTable(new Point(getGameWidth()/2,getGameHeight()/2));
		progressLabel=new GameLabel(new Point(getGameWidth()/2,108), TextAlign.CENTER, TextBaseline.MIDDLE,
				"", ASBOTXConfigs.Color.BORDER_DARK_GRAY, ASBOTXConfigs.getCGFont(12));
		totalLabel=new GameLabel(new Point(getGameWidth()-3,108), TextAlign.RIGHT, TextBaseline.MIDDLE, 
				"Total "+game.getLocalPlayer().getTotal(), ASBOTXConfigs.Color.BORDER_DARK_GRAY,
				ASBOTXConfigs.getCGFont(12));
		
		//put the components on layers
		layers=new GroupLayer();
		for(int i=0;i<buttons.length;i++){
			layers.addComponentOnLayer(buttons[i]);
		}
		layers.addComponentOnLayer(table);
		layers.addComponentOnLayer(progressLabel);
		layers.addComponentOnLayer(totalLabel);
		layers.addLayer(new ImageLayer(MEngine.getAssetManager().getSpriteSheet("images/leaderboard.png"),
				new Point(0,0), getGameWidth(), getGameHeight()));
		
		//connect to the server
		table.showText("Connecting...");
		reconnect();
	}
	
	private void reconnect(){
		 
		//lock buttons
		connectionLock=true;
		
		progressLabel.setText("downloading leaderboard...");
		getLeaderboardService().getLeaderboard(new LeaderboardCallback());
			
	}
	
	private void showError(Throwable caught){
		progressLabel.setText(ERROR_TEXT);
		if(caught instanceof StatusCodeException){
			StatusCodeException e=(StatusCodeException)caught;
			switch(e.getStatusCode()){
				case 500:
					table.showText("Server error occurred.");
					break;
				case 0:
					table.showText("Offline.");
					progressLabel.clearText();
					break;
				default:
					table.showText("Error occurred.");
			}
		}else{
			table.showText("Error occurred.");
		}
		connectionLock=false;
	}
	private class RefreshButton extends CircleButton{
		
		public RefreshButton(Point p, double r) {
			super(p, r, new SpriteBlock(2*(200+SpriteBlock.SPACING),
					3*(200+SpriteBlock.SPACING),200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		}

		@Override
		public void doTask() {
			if(!connectionLock){
				reconnect();
				progressLabel.setText("refreshing...");
			}
		}
		
		@Override
		public void update() {
			// TODO Auto-generated method stub
		}
		
	}
	
	private class LeaderboardTable extends GeneralComponent{
		private Leader[] leaders;
		private int highlightIndex=-1;
		private boolean showLabel=true;
		private GameLabel bigLabel; 
		private final double hightlightOffset;
		private final String font=ASBOTXConfigs.getCGFont(15);
		private final String boldFont=ASBOTXConfigs.getCGBoldFont(15);
		
		public LeaderboardTable(Point p) {
			super(p, getGameWidth(),300);
			bigLabel=new GameLabel(p, TextAlign.CENTER, TextBaseline.MIDDLE,"",
					ASBOTXConfigs.Color.GRAY, ASBOTXConfigs.getCGFont(45));
			hightlightOffset=getTopY()+15;
		}
		public void setLeaders(Leader[] leaders){
			this.leaders=leaders;
			this.showLabel=false;
		}
		
		public void showText(String s){
			this.bigLabel.setText(s);
			this.showLabel=true;
		}
		
		public void setHighlight(int pos){
			this.highlightIndex=pos-1;
		}
		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void draw(Context2d context) {
			if(showLabel){
				bigLabel.draw(context);
			}else{
				double y;
				
				context.setFillStyle(ASBOTXConfigs.Color.GRAY);
				context.setTextAlign(TextAlign.CENTER);
				context.setTextBaseline(TextBaseline.MIDDLE);
				context.setFont(font);
				context.setLineWidth(30);
				
				for(int i=0;i<leaders.length;i++){
					y=hightlightOffset+30*i;
					if(i==highlightIndex){
						context.save();
						context.setStrokeStyle(ASBOTXConfigs.Color.LIGHT_BLUE);
						context.setFillStyle(ASBOTXConfigs.Color.PURE_YELLOW);
						context.setFont(boldFont);
						context.beginPath();
						context.moveTo(0,y);
						context.lineTo(getGameWidth(), y);
						context.stroke();
						
						
						drawLeaderProfile(context,leaders[i],y);
						
						context.restore();
					}else{
						drawLeaderProfile(context,leaders[i],y);
					}
				}
			}
			
		}
		private void drawLeaderProfile(Context2d context,Leader leader,double y){
			context.fillText(Integer.toString(leader.getPosition()),30,y);
			context.fillText(leader.getID(), 150, y,180);
			context.fillText(leader.getDateText(), 450, y);
			context.fillText(Long.toString(leader.getTotal()), 650, y);
		}
	}
	
	
	private class LeaderboardCallback implements AsyncCallback<Leader[]>{

		@Override
		public void onFailure(Throwable caught) {
			
			// show the error message
			showError(caught);
		}

		@Override
		public void onSuccess(Leader[] result) {
			
			//paste the leaderboard on the table
			progressLabel.clearText();
			connectionLock=false;
			
			table.setLeaders(result);
			if(game.getLoginInfo().getStatus()==LoginInfo.Status.LOGGED_IN){
				String id=game.getLocalPlayer().getID();
				for(Leader leader:result){
					if(leader.getID().equals(id)){
						table.setHighlight(leader.getPosition());
						break;
					}
				}
			}else{
				table.setHighlight(0);
			}
			
		}
		
	};
}
