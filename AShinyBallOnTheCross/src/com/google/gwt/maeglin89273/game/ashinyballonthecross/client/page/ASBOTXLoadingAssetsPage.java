/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.Glass;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginRequest;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CheckLoginResponse;

import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.page.LoadingAssetsPage;
import com.google.gwt.maeglin89273.game.mengine.page.MEngineLogoPage;
import com.google.gwt.maeglin89273.game.mengine.page.MaeglinStudiosPage;
import com.google.gwt.maeglin89273.game.mengine.page.Page;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.service.LoginInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTXLoadingAssetsPage extends LoadingAssetsPage{
	private boolean assetsLoaded=false;
	private boolean loginSvcResponded=false;
	private boolean trigger=false;
	private GroupLayer layers;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.LoadingPage#update()
	 */
	public ASBOTXLoadingAssetsPage(){
		super(null);
	}
	@Override
	public void update() {
		if(trigger){
			this.nextPage=new MEngineLogoPage(new MaeglinStudiosPage(new ASBOTXWelcomePage(),"images/"),"images/");
			layers=null;
			toNextPage();
		}else{
			layers.update();
			
			if(assetsLoaded&&loginSvcResponded){
				trigger=true;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.LoadingPage#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		layers.draw(context);
	}
	@Override
	public void onScreen() {
		final ASBOTXGame game=(ASBOTXGame)getGame();
		game.initLocalPlayer();
		game.getPlayerService().checkLogin(new CheckLoginRequest(ASBOTXConfigs.CLOSE_PAGE_PATH,game.getLocalPlayer().getPlayer()),
				new AsyncCallback<CheckLoginResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				game.setLoginInfo(LoginInfo.getExceptionalLoginInfo(caught));
				loginSvcResponded=true;
			}

			@Override
			public void onSuccess(CheckLoginResponse result) {
				game.setLoginInfo(result.getLoginInfo());
				result.handleThis(game.getLocalPlayer());
				loginSvcResponded=true;
			}
			
		});
		layers=new GroupLayer();
		layers.addComponentOnLayer(new GameLabel(new Point(getGameWidth()/2,getGameHeight()/2),
				TextAlign.CENTER, TextBaseline.MIDDLE, "Loading...", ASBOTXConfigs.Color.WHITE,
				ASBOTXConfigs.getCGFont(26)));
		layers.addComponentOnLayer(new LoadingBar(35));
		layers.addComponentOnLayer(new Glass(getGameWidth(), getGameHeight()));
		
	}
	@Override
	public void done() {
		assetsLoaded=true;
	}
	
	
	private class LoadingBar extends GeneralComponent{
		private int percentage;
		
		protected LoadingBar(int yOffset){
			super(new Point(getGameWidth()/2,getGameHeight()/2+yOffset), 400, 10);
		}

		@Override
		public void update() {
			percentage=MEngine.getAssetManager().getLoadedPercentage();
		}

		@Override
		public void draw(Context2d context) {
			context.setStrokeStyle(ASBOTXConfigs.Color.WHITE);
			context.setLineWidth(getHeight());
			context.beginPath();
			context.moveTo(getLeftX(),getY());
			context.lineTo(getLeftX()+(getWidth()/100)*percentage,getY());
			context.stroke();
			
		}
		
	}

	
	
}
