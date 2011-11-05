package com.google.gwt.maeglin89273.game.mengine.core;


import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class MousePosition implements MouseMoveHandler{
	private final Point position=new Point(0,0);
	private final Canvas canvas;
	MousePosition(Canvas canvas){
		this.canvas=canvas;
	}
	public  void setMouseX(int x){
		position.setX(x);
	}
	public void setMouseY(int y){
		position.setY(y);
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
		position.setX(event.getRelativeX(canvas.getCanvasElement()));
		position.setY(event.getRelativeY(canvas.getCanvasElement()));
	}
}
