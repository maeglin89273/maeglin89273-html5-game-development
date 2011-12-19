/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.levelequipment;

import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class LevelEquipment {
	protected Creator creator;
	protected Point position;
	protected double angle; 
	protected LevelEquipment(Creator creator,Point position,double angle){
		this.creator=creator;
		this.position=position;
		this.angle=angle;
	}
}
