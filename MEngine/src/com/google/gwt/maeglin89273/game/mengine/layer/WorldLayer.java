/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.Component;
import com.google.gwt.maeglin89273.game.mengine.layer.Camera.WorldBounds;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public class WorldLayer extends GroupLayer {
	private final Camera camera;
	public WorldLayer(Component c,float maxScale){
		
		this.camera=new Camera(c, maxScale);
	}
	public WorldLayer(WorldBounds bounds,float maxScale){
		this.camera=new Camera(bounds, maxScale);
	}
	public WorldLayer(Component c,Point cameraPosition,float maxScale){
		this.camera=new Camera(c, cameraPosition, maxScale);
	}
	public WorldLayer(WorldBounds bounds,Point cameraPosition,float maxScale){
		this.camera=new Camera(bounds, cameraPosition, maxScale);
	}
	
	public Camera getCamera(){
		return camera;
	}
	@Override
	public void update(){
		camera.update();
		super.update();
	}
	@Override
	public void draw(Context2d context){
		context.save();
		camera.setContext2d(context);
		super.draw(context);
		context.restore();
		
	}
}
