/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.layer.Camera.WorldBounds;

/**
 * @author Maeglin Liao
 *
 */
public abstract class WorldLayer implements HasComponentsLayer {
	private Camera camera;
	protected WorldLayer(WorldBounds bounds,double cameraWidth,double cameraHeight,float maxScale){
		this.camera=new Camera(this, bounds, cameraWidth, cameraHeight, maxScale);
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.layer.Layer#update()
	 */
	public Camera getCamera(){
		return camera;
	}
	@Override
	public void update() {
		camera.update();

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.layer.Layer#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		camera.draw(context);

	}
}
