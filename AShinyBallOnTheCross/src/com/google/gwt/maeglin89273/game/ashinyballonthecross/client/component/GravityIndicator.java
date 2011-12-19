/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component;

import java.util.ArrayList;


import org.jbox2d.common.Vec2;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GravityChangedEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GravityChangedListener;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.timer.RepeatingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;
import com.google.gwt.user.client.Random;


/**
 * @author Maeglin Liao
 *
 */
public class GravityIndicator extends GeneralComponent {
	public static final float GRAVITY_MAGNITUDE=9.8f;
	
	private SpriteSheet img;
	
	private int RotationUnit=0;
	private int gravityAngle;
	private int nextGravityAngle;
	
	private final Vec2 gravity;
	
	
	private ArrayList<GravityChangedListener> listenerList=new ArrayList<GravityChangedListener>();
	private final String textFont;
	
	public GravityIndicator(Point p,double diameter,int sec,int angleInDegrees){
		super(p,Math.toRadians(angleInDegrees),diameter,diameter);
		
		this.gravityAngle=this.nextGravityAngle=-angleInDegrees;
		gravity=getGravityByDegrees(angleInDegrees);
		
		img=MEngine.getAssetManager().getSpriteSheet("gravity_indicator.png");
		textFont=ASBOTCConfigurations.getGameFont((int)(diameter*0.55));
		
	}
	public static Vec2 getGravityByDegrees(int angleInDegree){
		return new Vec2((float)(GRAVITY_MAGNITUDE*Math.cos(Math.toRadians(-angleInDegree))),
				 (float)(GRAVITY_MAGNITUDE*Math.sin(Math.toRadians(-angleInDegree))));
	}
	public void addGravityChangeListener(GravityChangedListener listener){
		listenerList.add(listener);
	}
	public void removeGravityChangeListener(GravityChangedListener listener){
		listenerList.remove(listener);
	}
	public Vec2 getGravity(){
		return gravity;
	}
	@Override
	public void update() {
		
		if(gravityAngle!=nextGravityAngle){
			gravityAngle+=RotationUnit;
			if(gravityAngle>=360){
				gravityAngle-=360;
			}else if(gravityAngle<0){
				gravityAngle+=360;
			}
			
			rotateGravity(gravityAngle);
			fireGravityChangedEvents();
		}
	}
	public void rotate(int angleInDegree){
		int delta;
		
		nextGravityAngle=-angleInDegree;
		if(nextGravityAngle>180){
			delta=gravityAngle-(nextGravityAngle-180);
		}else{
			delta=gravityAngle-(nextGravityAngle+180);
		}
		
		if(delta==0){
			return;
		}
		RotationUnit=delta/Math.abs(delta);
	}
	private void rotateGravity(int angle){
		double radians=Math.toRadians(angle);
		setAngle(-radians);
		gravity.set((float)(GRAVITY_MAGNITUDE*Math.cos(radians)),
					(float)(GRAVITY_MAGNITUDE*Math.sin(radians)));
	}
	private void fireGravityChangedEvents(){
		GravityChangedEvent event=new GravityChangedEvent(this,new Vec2(gravity));
		for(int i=listenerList.size()-1;i>=0;i--){
			listenerList.get(i).gravityChanged(event);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.save();
		context.setTextAlign(TextAlign.CENTER);
		context.setTextBaseline(TextBaseline.MIDDLE);
		context.setFont(textFont);
		context.setFillStyle(ASBOTCConfigurations.Color.TEXT);
		context.fillText("G",getX(),getY(),width);
		context.translate(getX(), getY());
		context.rotate(getAngle());
		context.drawImage(img.getImage(), -width/2, -height/2, width, height);
		context.restore();
		
	}
}
