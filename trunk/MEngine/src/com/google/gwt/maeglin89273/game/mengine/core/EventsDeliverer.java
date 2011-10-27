/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.core;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author Maeglin Liao
 *
 */
public class EventsDeliverer {
	private final Canvas canvas;
	private final List<HandlerRegistration> registrations=new ArrayList<HandlerRegistration>();
	EventsDeliverer(Canvas canvas){
		this.canvas=canvas;
	}
	public void clearHandlers(){
		for(HandlerRegistration registration:registrations)
			registration.removeHandler();
		registrations.clear();
	}
	public void addKeyDownHandler(KeyDownHandler h){
		registrations.add(canvas.addKeyDownHandler(h));
		
	}
	public void addKeyUpHandler(KeyUpHandler h){
		registrations.add(canvas.addKeyUpHandler(h));
	}
	public void addMouseMoveHandler(MouseMoveHandler h){
		registrations.add(canvas.addMouseMoveHandler(h));
	}
	public void addMouseDownHandler(MouseDownHandler h){
		registrations.add(canvas.addMouseDownHandler(h));
	}
	public void addMouseUpHandler(MouseUpHandler h){
		registrations.add(canvas.addMouseUpHandler(h));
	}
	
	public void addKeyPressHandler(KeyPressHandler h){
		registrations.add(canvas.addKeyPressHandler(h));
	}
	public void addClickHandler(ClickHandler h){
		registrations.add(canvas.addClickHandler(h));
	}
	public void addMouseOutHandler(MouseOutHandler h){
		registrations.add(canvas.addMouseOutHandler(h));
	}
}
