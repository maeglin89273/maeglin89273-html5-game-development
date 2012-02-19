/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.GameComponent;

/**
 * @author Liao
 *
 */
public class GroupLayer extends Layer {
	private List<Layer> layers=new ArrayList<Layer>();
	public ComponentLayer addComponentOnLayer(GameComponent c){
		ComponentLayer layer=new ComponentLayer(c);
		this.addLayer(layer);
		return layer;
	}
	public void addLayer(Layer layer){
		layer.setIndex(layers.size());
		layers.add(layer);
		layer.setParentLayer(this);
	}
	public void insertLayer(int index,Layer layer){
		layer.setIndex(index);
		layers.add(index, layer);
		layer.setParentLayer(this);
		for(index++;index<layers.size();index++){
			layers.get(index).setIndex(index);
		}
	}
	public void removeLayer(Layer layer){
		if(layer.getIndex()>=0){
			int index=layer.getIndex();
			layers.remove(index);
			layer.setIndex(-1);
			layer.setParentLayer(null);
			for(;index<layers.size();index++){
				layers.get(index).setIndex(index);
			}
		}
	}
	public void removeLayer(int index){
		if(index>=0){
			layers.remove(index);
			for(;index<layers.size();index++){
				layers.get(index).setIndex(index);
			}
		}
	}
	public int getSize(){
		return layers.size();
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
