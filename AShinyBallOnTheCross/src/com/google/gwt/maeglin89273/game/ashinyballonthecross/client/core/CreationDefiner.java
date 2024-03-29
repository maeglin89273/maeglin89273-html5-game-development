/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.MainCreation;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;

/**
 * @author Maeglin Liao
 *
 */
public abstract class CreationDefiner {
	protected static final int ICON_BOUNDS=200;
	protected static final int ICON_BOUNDS_PLUS_SPACING=ICON_BOUNDS+SpriteBlock.SPACING;
	protected final int requiredFullPower;
	private final SpriteBlock icon;
	
	protected CreationDefiner(int requiredFullPower,Point iconCorner){
		this.requiredFullPower = requiredFullPower;
		if(iconCorner==null){
			this.icon=new SpriteBlock(840, 840, ICON_BOUNDS, ICON_BOUNDS, MEngine.getAssetManager().getSpriteSheet("images/definers_icons.png"));
		}else{
			this.icon=new SpriteBlock((int)iconCorner.getX(), (int)iconCorner.getY(), ICON_BOUNDS, ICON_BOUNDS,
					MEngine.getAssetManager().getSpriteSheet("images/definers_icons.png"));
		}
	}
	protected MainCreation defineFinished(){
		return create(getCreationRequiredPower()); 
		
	}
	public SpriteBlock getDefinerIcon(){
		return icon;
	}
	
	public abstract void reset();
	public abstract void sketch(Context2d context);
	public abstract void updatePenPosition(Point p);
	public abstract MainCreation onPenUp(Point p);
	public abstract void onPenDown(Point p);
	public abstract int getCreationRequiredPower();
	protected abstract MainCreation create(int requiredPower);
	
	public static class NoneDefiner extends CreationDefiner {

		/**
		 * @param creator
		 * @param requiredFullPower
		 * @param iconCorner
		 */
		public NoneDefiner() {
			super(0, new Point(3*ICON_BOUNDS_PLUS_SPACING,2*ICON_BOUNDS_PLUS_SPACING));
			
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.CreationDefiner#reset()
		 */
		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.CreationDefiner#sketch(com.google.gwt.canvas.dom.client.Context2d)
		 */
		@Override
		public void sketch(Context2d context) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.CreationDefiner#updatePenPosition(com.google.gwt.maeglin89273.game.mengine.physics.Point)
		 */
		@Override
		public void updatePenPosition(Point p) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.CreationDefiner#onPenUp(com.google.gwt.maeglin89273.game.mengine.physics.Point)
		 */
		@Override
		public MainCreation onPenUp(Point p) {
			return null;
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.CreationDefiner#onPenDown(com.google.gwt.maeglin89273.game.mengine.physics.Point)
		 */
		@Override
		public void onPenDown(Point p) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.CreationDefiner#getCreationRequiredPower()
		 */
		@Override
		public int getCreationRequiredPower() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.creation.CreationDefiner#create(int)
		 */
		@Override
		protected MainCreation create(int requiredPower) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
