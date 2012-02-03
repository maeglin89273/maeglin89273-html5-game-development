/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;





/**
 * @author Maeglin Liao
 *
 */
public abstract class GamePage extends Page {
	protected GroupLayer rootLayer;
	public GamePage(){
		this.rootLayer=new GroupLayer();
	}
	@Override
	public void update(){
		rootLayer.update();
	}
	@Override
	public void draw(Context2d context){
		rootLayer.draw(context);
	}
}
