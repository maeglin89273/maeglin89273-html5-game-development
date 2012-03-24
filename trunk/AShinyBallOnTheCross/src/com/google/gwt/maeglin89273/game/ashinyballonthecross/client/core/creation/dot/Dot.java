/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.dot;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.CreationDefiner;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.MainCreation;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class Dot extends MainCreation {
	
	protected CssColor dotColor;
	protected static final int DOT_RADIUS=3;
	
	protected Dot(int contentPower,boolean beControlled,Point p, CssColor color) {
		super(contentPower,beControlled, p, 2*DOT_RADIUS,2*DOT_RADIUS, 0);
		if(isVerified()){
			this.dotColor=color;
			
		}
	}
	public CssColor getColor(){
		return dotColor;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setFillStyle(dotColor);
		context.beginPath();
		context.arc(position.getX(), position.getY(), DOT_RADIUS, 0, 2*Math.PI, true);
		context.closePath();
		context.fill();
	}
	
	public static abstract class DotDefiner extends CreationDefiner{
		protected Point position;
		protected final CssColor dotColor;
		protected DotDefiner(int requiredPower,Point iconCorner, CssColor dotColor){
			super(requiredPower, iconCorner);
			this.dotColor = dotColor;
			
		}
		@Override
		public void reset() {
			position=null;
		}

		@Override
		public void sketch(Context2d context) {
			if(position!=null){
				context.setFillStyle(dotColor);
				context.beginPath();
				context.arc(position.getX(), position.getY(), DOT_RADIUS, 0, 2*Math.PI, true);
				context.closePath();
				context.fill();
			}
		}

		@Override
		public void updatePenPosition(Point p) {
			this.position=p;
			
		}

		@Override
		public MainCreation onPenUp(Point p) {
			if(position!=null){
				updatePenPosition(p);
				MainCreation c=defineFinished();
				reset();
				return c;
			}
			return null;
		}

		@Override
		public void onPenDown(Point p) {
			this.position=p;
			
		}
		@Override
		public int getCreationRequiredPower() {
			return this.requiredFullPower;
		}
	}
}
