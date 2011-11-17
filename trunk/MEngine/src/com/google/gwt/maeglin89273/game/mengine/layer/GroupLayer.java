/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * @author Liao
 *
 */
public class GroupLayer extends Layer {
	private List<Layer> layers=new ArrayList<Layer>();
	public void addLayer(Layer layer){
		layers.add(layer);
		layer.setIndex(layers.size()-1);
		layer.setParentLayer(this);
	}
	public void addLayer(int index,Layer layer){
		layers.add(index, layer);
		layer.setIndex(index);
		layer.setParentLayer(this);
	}
	public void removeLayer(Layer layer){
		if(layer.getIndex()>=0){
			layers.remove(layer.getIndex());
			layer.setParentLayer(null);
		}
	}
	public List<Layer> getLayers(){
		return layers;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		for(int i=layers.size()-1;i>=0;i--)
			layers.get(i).update();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		for(int i=layers.size()-1;i>=0;i--)
			layers.get(i).draw(context);
	}

}
