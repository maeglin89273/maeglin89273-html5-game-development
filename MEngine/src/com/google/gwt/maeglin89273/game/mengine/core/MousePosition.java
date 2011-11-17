package com.google.gwt.maeglin89273.game.mengine.core;


import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class MousePosition implements MouseMoveHandler{
	private final Point position=new Point(0,0);
	private final CanvasElement element;
	MousePosition(Canvas canvas){
		this.element=canvas.getCanvasElement();
	}
	
	public Point getPosition(){
		return position.clone();
	}
	public int getX(){
		return (int)position.getX();
	}
	public int getY(){
		return (int)position.getY();
	}
	@Override
	public void onMouseMove(MouseMoveEvent event) {
		position.setX(event.getRelativeX(element));
		position.setY(event.getRelativeY(element));
	}
}
