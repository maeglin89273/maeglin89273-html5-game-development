/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.GameComponent;
import com.google.gwt.maeglin89273.game.mengine.layer.Camera.WorldBounds;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public class WorldLayer extends GroupLayer {
	private final Camera camera;
	public WorldLayer(GameComponent space,float maxScale){
		this.camera=new Camera(space, maxScale);
		this.addLayer(new ComponentLayer(space));
	}
	public WorldLayer(WorldBounds bounds,float maxScale){
		this.camera=new Camera(bounds, maxScale);
	}
	public WorldLayer(GameComponent space,Point cameraPosition,float maxScale,float minScale){
		this.camera=new Camera(space, cameraPosition, maxScale,minScale);
		this.addLayer(new ComponentLayer(space));
	}
	public WorldLayer(WorldBounds bounds,Point cameraPosition,float maxScale,float minScale){
		this.camera=new Camera(bounds, cameraPosition, maxScale,minScale);
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
