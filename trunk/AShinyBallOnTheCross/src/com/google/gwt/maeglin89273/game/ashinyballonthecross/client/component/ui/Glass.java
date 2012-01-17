/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class Glass extends GeneralComponent {

	/**
	 * @param p
	 * @param w
	 * @param h
	 */
	public Glass(double w, double h) {
		super(new Point(w/2,h/2), w, h);
		
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
		context.setFillStyle(ASBOTXConfigs.Color.GLASS);
		context.fillRect(0, 0,getWidth(),getHeight());

	}

}
