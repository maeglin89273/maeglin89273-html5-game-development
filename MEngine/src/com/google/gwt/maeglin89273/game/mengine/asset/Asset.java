/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.asset;

import com.google.gwt.user.client.Window;

/**
 * @author Maeglin Liao
 *
 */
public abstract class Asset{
	private boolean loaded=false;
	private final String url;
	
	public Asset(String url){
		this.url=url;
	}
	public void onLoad(){
		loaded=true;
	}
	
	public boolean isLoaded(){
		return loaded;
	}
	public String getUrl(){
		return url;
	}
	
	public void onError(Throwable exception) {
		Window.alert("loading "+url+" failed.\n"+exception.toString());
	}
	
}
