/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.LocalPlayer;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginRequest;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginResponse;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Player;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Maeglin Liao
 *
 */
public class LoginButtonAndIDDisplay extends BoxButton{
	private String url;
	private IDLabel label;
	private boolean enableAndDisplay=true;
	private AppendProgress progress;
	
	private ASBOTXGame game;
	public LoginButtonAndIDDisplay(Point buttonPos,Point labelPos,TextAlign align,
			int bounds,CssColor labelColor,String url,String id,LoginInfo.Status status,AppendProgress progress) {
		super(buttonPos, bounds,bounds,new SpriteBlock(3*(200+SpriteBlock.SPACING),
				chooseSpriteBlockY(status),200,200,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
		this.game=(ASBOTXGame)MEngine.getGeneralGame();
		this.url=url;
		this.label=new IDLabel(labelPos,align,labelColor,id);
		this.progress=progress;
	}
	public void setID(String id){
		label.setText(id);
	}
	public void setEnable(boolean enable){
		if(enable&&this.enableAndDisplay){
			return;
		}
		if(enable){
			updateProperties(game.getLoginInfo(),game.getLocalPlayer().getID());
		}
		enableAndDisplay=enable;
	}
	public boolean isEnable(){
		return this.enableAndDisplay;
	}
	@Override
	public void doTask() {
		if(enableAndDisplay){
			openLoginWindow(url);
		}
		
	}
	@Override
	public void draw(Context2d context){
		if(enableAndDisplay){
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
			p.@com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.LoginButtonAndIDDisplay::secondCheck()();
		}
	}-*/;
	private void secondCheck(){
		final LocalPlayer localPlayer=game.getLocalPlayer();
		game.getPlayerService().checkLogin(new CheckLoginRequest(ASBOTXConfigs.CLOSE_PAGE_PATH,
				localPlayer.getPlayer()),
				new AsyncCallback<CheckLoginResponse>(){

					@Override
					public void onFailure(Throwable caught) {
						//don't display the button
						game.setLoginInfo(LoginInfo.getExceptionalLoginInfo(caught));
						setEnable(false);
					}

					@Override
					public void onSuccess(CheckLoginResponse result) {
						game.setLoginInfo(result.getLoginInfo());
						updateProperties(result.getLoginInfo(),result.getStatus()==CheckLoginResponse.Status.NOT_LOGGED_IN?null:result.getPlayer().getID());
						result.handleThis(localPlayer);
						
						if(progress!=null){
							progress.execute(result);
						}
					}
		});
		
	}
	private void updateProperties(LoginInfo info,String id){
		LoginInfo.Status s;
		switch(info.getStatus()){
			case LOGGED_IN:
				label.setText(id==null?"":id);
				s=LoginInfo.Status.LOGGED_OUT;
				break;
			case LOGGED_OUT:
				label.setText("");
				s=LoginInfo.Status.LOGGED_IN;
				break;
			default:
				setEnable(false);
				return;
		}
		url=info.getUrl();
		spriteBlock.setY(chooseSpriteBlockY(s));
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
	private class IDLabel extends GameLabel{

		public IDLabel(Point p,TextAlign align,CssColor labelColor,String nickname) {
			super(p,align, TextBaseline.MIDDLE, nickname,
					labelColor, ASBOTXConfigs.getCGFont(12));
			// TODO Auto-generated constructor stub
		}
		
	}
	public interface AppendProgress{
		public void execute(CheckLoginResponse response);
	}
}
