package com.google.gwt.maeglin89273.game.mengine.component;

import org.jbox2d.collision.AABB;
import org.jbox2d.dynamics.Body;

import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;


/**
 * @author Maeglin Liao
 *
 */
public interface Physical extends GameComponent{
	public Body getBody();
	public Spacial getSpace();
	public void destory();
	public AABB getAABB();
}
