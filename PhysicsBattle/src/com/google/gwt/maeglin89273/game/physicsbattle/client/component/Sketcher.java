/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public interface Sketcher {
	void reset();
	void sketch(Context2d context);
	void updatePenPosition(Point p);
	void onPenUp(Point p);
	void onPenDown(Point p);
	void sketchFinished();
}
