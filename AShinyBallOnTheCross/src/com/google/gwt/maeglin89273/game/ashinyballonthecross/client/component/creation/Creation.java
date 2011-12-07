/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation;


import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;


/**
 * @author Maeglin Liao
 *
 */
public abstract class Creation extends GeneralComponent {
	protected Creator creator;
	private final int contentPower;
	
	private final boolean verified;
	
	
	/**before create it ,please call isVerified() method in the sub class to check whether it is able to be created or not. 
	 * @param cotentPower TODO
	 * @param addedToWorld TODO
	 * @param beControlled TODO
	 * @param p
	 * @param w
	 * @param h
	 */
	public Creation(Creator creator,int cotentPower, Point p, double w, double h, double a) {
		super(p, a, w, h);
		contentPower = cotentPower;
		
		
		if(creator.verify(this)){
			this.creator=creator;
			this.creator.getWorld().add(this);
			this.verified=true;
		}else{
			this.verified=false;
		}
		
	}
	/**
	 * 
	 * @return true if it has been verified
	 */
	public boolean isVerified(){
		return verified;
	}
	public int getContentPower(){
		return contentPower;
	}
	/**
	 * recycle itself by default
	 */
	public void destroy(){
		this.creator.recycle(this);
		creator.getWorld().remove(this);
		this.creator=null;
	}
}
