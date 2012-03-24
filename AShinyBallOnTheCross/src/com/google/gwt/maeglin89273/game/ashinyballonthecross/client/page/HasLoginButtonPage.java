/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.LocalPlayer;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui.Glass;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginRequest;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginResponse;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CreateStatus;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Player;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.PlayerCreatedResponse;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.ComponentLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Maeglin Liao
 *
 */
public abstract class HasLoginButtonPage extends GeneralPage implements KeyDownHandler,KeyPressHandler {
	private boolean blocked=false;
	
	private IDEnteredBoard board;
	
	protected GroupLayer root;
	protected LoginButtonAndIDDisplay loginButton;

	protected ASBOTXGame game;
	/**
	 * 
	 */
	protected HasLoginButtonPage(final Point buttonPos,final Point labelPos,final int buttonBounds,final TextAlign labelAlign,final CssColor textColor) {
		game=(ASBOTXGame) getGame();
		root=new GroupLayer();
		
		if(game.getLoginInfo()==null){
			game.getPlayerService().checkLogin(new CheckLoginRequest(ASBOTXConfigs.CLOSE_PAGE_PATH,game.getLocalPlayer().getPlayer()),
					new AsyncCallback<CheckLoginResponse>(){
	
				@Override
				public void onFailure(Throwable caught) {
					handleResponseFailure(caught);
				}
	
				@Override
				public void onSuccess(CheckLoginResponse result) {
					handleResponseSuccess(result);
					addLoginButton(buttonPos, labelPos, buttonBounds,labelAlign, textColor);
				}
				
			});
		}else{
			addLoginButton(buttonPos, labelPos, buttonBounds,labelAlign, textColor);
		}
	}
	private void addLoginButton(Point buttonPos,Point labelPos,int buttonBounds,TextAlign labelAlign,CssColor textColor){
		loginButton=new LoginButtonAndIDDisplay(buttonPos, labelPos, buttonBounds,labelAlign, textColor);
		root.addComponentOnLayer(loginButton);
	}
	private IDEnteredBoard getBoard(){
		if(board==null){
			board=new IDEnteredBoard(new Point(game.getWidth()/2,game.getHeight()/2));
		}
		return board;
	}
	protected boolean isBlocked(){
		return blocked;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		Point p=MEngine.getMousePosition();
		if(blocked){
			getBoard().getButton().onClick(p);
		}else{
			if(loginButton==null||!loginButton.onClick(p)){
				onClick(p);
			}
		}
	}
	protected abstract void onClick(Point p);
	protected abstract void progressFinished();
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
	 * @see com.google.gwt.event.dom.client.KeyPressHandler#onKeyPress(com.google.gwt.event.dom.client.KeyPressEvent)
	 */
	@Override
	public void onKeyPress(KeyPressEvent event) {
		if(blocked){
			getBoard().onKeyPress(event);
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.KeyDownHandler#onKeyDown(com.google.gwt.event.dom.client.KeyDownEvent)
	 */
	@Override
	public void onKeyDown(KeyDownEvent event) {
		if(blocked){
			getBoard().onKeyDown(event);
		}
	}
	@Override
	public void regHandlers(){
		super.regHandlers();
		MEngine.addKeyPressHandler(this);
		MEngine.addKeyDownHandler(this);
	}
	private void checkIsUserANewPlayer(CheckLoginResponse result){
		if(result.getStatus()==CheckLoginResponse.Status.NEW_PLAYER){
			root.insertLayer(0, new ComponentLayer(new Glass(game.getWidth(),game.getHeight())));
			root.insertLayer(0,new ComponentLayer(getBoard()));
			game.getLocalPlayer().setID(null);
			blocked=true;
		}else{
			progressFinished();
		}
	}
	
	private void closeIDBoard(){
		root.removeLayer(1);
		root.removeLayer(0);
		loginButton.setID(game.getLocalPlayer().getID());
		blocked=false;
		progressFinished();
	}
	private void handleResponseFailure(Throwable caught){
		game.setLoginInfo(LoginInfo.getExceptionalLoginInfo(caught));
	}
	private void handleResponseSuccess(CheckLoginResponse result){
		game.setLoginInfo(result.getLoginInfo());
		result.handleThis(game.getLocalPlayer());
		checkIsUserANewPlayer(result);
	}
	
	
	public class LoginButtonAndIDDisplay extends BoxButton{
		private GameLabel label;
		private boolean enabled=true;
		
		public LoginButtonAndIDDisplay(Point buttonPos,Point labelPos,int bounds,TextAlign align,CssColor labelColor) {
			super(buttonPos, bounds,bounds,new SpriteBlock(3*(200+SpriteBlock.SPACING),
					chooseSpriteBlockY(ASBOTXConfigs.Utility.switchStatus(game.getLoginInfo().getStatus())),
					200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
			
			if(game.getLoginInfo().isConnectionSuccess()){
				this.label=new GameLabel(labelPos,align, TextBaseline.MIDDLE,
						game.getLoginInfo().getStatus()==LoginInfo.Status.LOGGED_IN?game.getLocalPlayer().getID():"",
						labelColor, ASBOTXConfigs.getCGFont(12));
			}else{
				setEnabled(false);
			}
		}
		
		public void setID(String id){
			label.setText(id);
		}
		public void setEnabled(boolean enable){
			if(!(enable^this.enabled)){
				return;
			}
			if(enable){
				updateProperties();
			}
			enabled=enable;
		}
		
		@Override
		public void doTask() {
			if(enabled){
				openLoginWindow(game.getLoginInfo().getUrl());
			}
			
		}
		@Override
		public void draw(Context2d context){
			if(enabled){
				super.draw(context);
				label.draw(context);
			}
		}
		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		private native void openLoginWindow(String url)/*-{
			var p=this;
			$wnd.open(url,"_blank","enable");
			$wnd.c=function(){
				p.@com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page.HasLoginButtonPage.LoginButtonAndIDDisplay::secondCheck()();
			}
		}-*/;
		public void secondCheck(){
			if(enabled){
				final LocalPlayer localPlayer=game.getLocalPlayer();
				game.getPlayerService().checkLogin(new CheckLoginRequest(ASBOTXConfigs.CLOSE_PAGE_PATH,
						localPlayer.getPlayer()),
						new AsyncCallback<CheckLoginResponse>(){
	
							@Override
							public void onFailure(Throwable caught) {
								//don't display the button
								handleResponseFailure(caught);
								setEnabled(false);
							}
	
							@Override
							public void onSuccess(CheckLoginResponse result) {
								handleResponseSuccess(result);
								updateProperties();
							}
						});
			}
			
		}
		private void updateProperties(){
			LoginInfo.Status s;
			switch(game.getLoginInfo().getStatus()){
				case LOGGED_IN:
					setID(game.getLocalPlayer().getID());
					s=LoginInfo.Status.LOGGED_OUT;
					break;
				case LOGGED_OUT:
					setID("");
					s=LoginInfo.Status.LOGGED_IN;
					break;
				default:
					setEnabled(false);
					return;
			}
			spriteBlock.setY(chooseSpriteBlockY(s));
		}
	}
	//a helper method for LoginButtonAndLabelDisplay 
	private static int chooseSpriteBlockY(LoginInfo.Status status){
		switch(status){
			case LOGGED_IN:
				return 200+SpriteBlock.SPACING;
						
			case LOGGED_OUT:
				return 2*(200+SpriteBlock.SPACING);
			}
				return 0;
	}
	
	private class IDEnteredBoard extends GeneralComponent implements KeyPressHandler,KeyDownHandler{
		private StringBuffer sb=new StringBuffer();
		
		private SpriteBlock block;
		
		private SubmitButton button;
		private GameLabel idLabel;
		private GameLabel messageLabel;
		/**
		 * @param p
		 * @param w
		 * @param h
		 */
		public IDEnteredBoard(Point p) {
			super(p, 450, 150);
			block=new SpriteBlock(0,500+SpriteBlock.SPACING,720,240,MEngine.getAssetManager().getSpriteSheet("images/boards.png"));
			button=new SubmitButton(getPositionAt(PositionType.SOUTH));
			idLabel=new GameLabel(new Point(getX(),getY()), TextAlign.CENTER, TextBaseline.MIDDLE, "", ASBOTXConfigs.Color.BLACK, ASBOTXConfigs.getCGFont(28));
			messageLabel=new GameLabel(new Point(getX(),getBottomY()-40), TextAlign.CENTER, TextBaseline.MIDDLE, "", ASBOTXConfigs.Color.ORANGE, ASBOTXConfigs.getCGBoldFont(12));
		}
		@Override
		public void onKeyPress(KeyPressEvent event) {
			char c=event.getCharCode();
			if(((c>='!'&&c<='~')||(c==' '&&sb.length()>0))&&sb.length()<15){
				idLabel.setText(sb.append(c).toString());
				button.setEnabled(true);
			}
			
		}
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if(event.getNativeKeyCode()==KeyCodes.KEY_BACKSPACE){
				idLabel.setText(sb.deleteCharAt(sb.length()-1).toString());
				if(sb.length()==0){
					button.setEnabled(false);
				}else{
					button.setEnabled(true);
				}
				event.preventDefault();
				event.stopPropagation();
			}
			
		}
		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
		 */
		@Override
		public void update() {
			// TODO Auto-generated method stub

		}
		public GameButton getButton(){
			return button;
		}
		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
		 */
		@Override
		public void draw(Context2d context) {
			context.drawImage(block.getSheetImage(),
					block.getX(), block.getY(), block.getWidth(), block.getHeight(),
					getLeftX(), getTopY(), getWidth(), getHeight());
			idLabel.draw(context);
			messageLabel.draw(context);
			button.draw(context);
		}
		
		private class SubmitButton extends BoxButton{
			private boolean enabled=false;
			public SubmitButton(Point p) {
				super(p, 65, 50,new SpriteBlock(910,100+SpriteBlock.SPACING,130,100,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
			}
			public void setEnabled(boolean enabled){
				this.enabled=enabled;
				spriteBlock.setY(enabled?0:100+SpriteBlock.SPACING);
			}
			@Override
			public void doTask() {
				
				if(enabled){
					setEnabled(false);
					messageLabel.setText("checkiing...");
					final ASBOTXGame game=(ASBOTXGame)MEngine.getGeneralGame();
					Player player=game.getLocalPlayer().getPlayer();
					player.setID(sb.toString().trim());
					game.getPlayerService().createNewPlayer(player, new AsyncCallback<PlayerCreatedResponse>(){
		
						@Override
						public void onFailure(Throwable caught) {
							messageLabel.setText("an Error occured. Please contact the developer about this error");
							setEnabled(true);
						}
		
						@Override
						public void onSuccess(PlayerCreatedResponse result) {
							messageLabel.setText(result.getStatus().getMessage());
							if(result.getStatus()==CreateStatus.SUCCESS){
								game.getLocalPlayer().setID(sb.toString());
								game.getLocalPlayer().setTimestamp(result.getCreateTime());
								
								closeIDBoard();
								sb.delete(0, sb.length());
								idLabel.clearText();
								messageLabel.clearText();
								setEnabled(false);
								
							}else if(result.getStatus()!=CreateStatus.DUPLICATED){
								setEnabled(true);
							}
						}
					});
				}
			}

			@Override
			public void update() {
				// TODO Auto-generated method stub
			}
		}
	}
}
