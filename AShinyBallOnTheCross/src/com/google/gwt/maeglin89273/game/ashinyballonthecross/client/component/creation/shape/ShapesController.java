/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;

/**
 * @author Maeglin Liao
 *
 */
public class ShapesController extends MainCreation {
	private Set<PhysicalShape> shapes;
	private boolean destroying=false;
	/**
	 * @param creator
	 * @param cotentPower
	 * @param beControlled
	 * @param p
	 * @param w
	 * @param h
	 * @param a
	 */
	public ShapesController(Creator creator) {
			
		super(creator, 0, true, null, 0, 0, 0);
		if(this.isVerified()){
			shapes=new HashSet<PhysicalShape>();
		}
	}
	public void addShape(PhysicalShape shape){
		this.shapes.add(shape);
	}
	public void removeShape(PhysicalShape shape){
		this.shapes.remove(shape);
		if(shapes.isEmpty()&&!destroying){
			this.destroy();
		}
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		// TODO Auto-generated method stub

	}
	@Override 
	public void destroy(){
		this.destroying=true;
		super.destroy();
		for(PhysicalShape shape:shapes){
			shape.destroy();
		}
		shapes=null;
		
	}
}
