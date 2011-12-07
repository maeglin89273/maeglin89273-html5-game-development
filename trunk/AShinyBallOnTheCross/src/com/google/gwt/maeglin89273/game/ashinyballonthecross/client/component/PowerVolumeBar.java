/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedListener;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Window;
/**
 * @author Liao
 *
 */
public class PowerVolumeBar extends GeneralComponent implements CreatorPropertiesChangedListener{
	
	
	
	private float hue;
	private double length;
	private double scoreIndY;
	private float frictionP;
	private float frictionS;
	
	private static final float HUE_RANGE=40f;
	private static final int HUE_OFFSET=20;
	/**
	 * @param p
	 * @param w
	 * @param h
	 */
	public PowerVolumeBar(Point p,int length) {
		super(p, 3, length);
		this.length=this.scoreIndY=length;
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setStrokeStyle("hsl("+hue+",98%,50%)");
		context.setLineWidth(getWidth());
		context.beginPath();
		context.moveTo(getX(), getBottomY());
		context.lineTo(getX(), getBottomY()-length);
		context.stroke();
		context.setLineWidth(2);
		context.beginPath();
		context.setStrokeStyle(GameColors.DARK_BLUE);
		context.moveTo(getX()+1.5f, getBottomY()-scoreIndY);
		context.lineTo(getX()+5.5f, getBottomY()-scoreIndY);
		
		context.stroke();
	}


	@Override
	public void powerChanged(CreatorPropertiesChangedEvent event) {
		frictionP=((float)event.getPower())/event.getMaxPower();
		hue=HUE_OFFSET+HUE_RANGE*frictionP;
		length=getHeight()*frictionP;
	}

	@Override
	public void scoreChanged(CreatorPropertiesChangedEvent event) {
		frictionS=((float)event.getScore())/event.getMaxPower();
		scoreIndY=getHeight()*frictionS;
		
	}

}
