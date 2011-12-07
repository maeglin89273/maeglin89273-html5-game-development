/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.layer.Camera.WorldBounds;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public class WorldLayer extends GroupLayer {
	private final Camera camera;
	public WorldLayer(Spacial s,float maxScale){
		this.camera=new Camera(s, maxScale);
		this.addLayer(new ComponentLayer(s));
	}
	public WorldLayer(WorldBounds bounds,float maxScale){
		this.camera=new Camera(bounds, maxScale);
	}
	public WorldLayer(Spacial s,Point cameraPosition,float maxScale){
		this.camera=new Camera(s, cameraPosition, maxScale);
		this.addLayer(new ComponentLayer(s));
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
