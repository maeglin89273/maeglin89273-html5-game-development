/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.component;

import org.jbox2d.collision.AABB;
import org.jbox2d.dynamics.World;

import com.google.gwt.maeglin89273.game.mengine.layer.Camera.WorldBounds;
import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;



/**
 * @author Maeglin Liao
 *
 */
public interface Spacial extends GameComponent {
	
	public abstract boolean isOutOfBounds(AABB aabb);
	public abstract void add(Physical c);
	public abstract void remove(Physical c);
	public abstract World getWorld();
}
