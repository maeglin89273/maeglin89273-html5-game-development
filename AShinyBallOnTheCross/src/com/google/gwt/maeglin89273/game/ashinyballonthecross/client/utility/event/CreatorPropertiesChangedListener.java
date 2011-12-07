/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event;

import java.util.EventListener;

/**
 * @author Maeglin Liao
 *
 */
public interface CreatorPropertiesChangedListener extends EventListener{
	public abstract void powerChanged(CreatorPropertiesChangedEvent event);
	public abstract void scoreChanged(CreatorPropertiesChangedEvent event);
}
