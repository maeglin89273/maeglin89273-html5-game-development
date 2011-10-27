/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.utility.event;

import java.util.EventObject;

import org.jbox2d.common.Vec2;

/**
 * @author Liao
 *
 */
public class GravityChangedEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vec2 gravity;
	public GravityChangedEvent(Object source,Vec2 gravity) {
		super(source);
		this.gravity=gravity;
	}
	public Vec2 getGravity(){
		return gravity;
	}

}
