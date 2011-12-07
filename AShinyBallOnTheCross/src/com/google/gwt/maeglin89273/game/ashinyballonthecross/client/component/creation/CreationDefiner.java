/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;

/**
 * @author Maeglin Liao
 *
 */
public abstract class CreationDefiner {
	protected static final int ICON_BOUNDS=200;
	protected static final int ICON_BOUNDS_PLUS_SPACING=ICON_BOUNDS+SpriteBlock.SPACING;
	protected final int requiredFullPower;
	protected final Creator creator;
	private final SpriteBlock icon;
	
	protected CreationDefiner(Creator creator,int requiredFullPower,Point iconCorner){
		this.creator = creator;
		this.requiredFullPower = requiredFullPower;
		if(iconCorner==null){
			this.icon=new SpriteBlock(420, 420, ICON_BOUNDS, ICON_BOUNDS, MEngine.getAssetManager().getSpriteSheet("definers_icons.png"));
		}else{
			this.icon=new SpriteBlock((int)iconCorner.getX(), (int)iconCorner.getY(), ICON_BOUNDS, ICON_BOUNDS,
					MEngine.getAssetManager().getSpriteSheet("definers_icons.png"));
		}
	}
	public MainCreation defineFinished(){
		return create(getCreationRequiredPower()); 
		
	}
	public SpriteBlock getDefinerIcon(){
		return icon;
	}
	
	public abstract void reset();
	public abstract void sketch(Context2d context);
	public abstract void updatePenPosition(Point p);
	public abstract void onPenUp(Point p);
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
			super(null, 0, new Point(3*ICON_BOUNDS_PLUS_SPACING,2*ICON_BOUNDS_PLUS_SPACING));
			
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner#reset()
		 */
		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner#sketch(com.google.gwt.canvas.dom.client.Context2d)
		 */
		@Override
		public void sketch(Context2d context) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner#updatePenPosition(com.google.gwt.maeglin89273.game.mengine.physics.Point)
		 */
		@Override
		public void updatePenPosition(Point p) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner#onPenUp(com.google.gwt.maeglin89273.game.mengine.physics.Point)
		 */
		@Override
		public void onPenUp(Point p) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner#onPenDown(com.google.gwt.maeglin89273.game.mengine.physics.Point)
		 */
		@Override
		public void onPenDown(Point p) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner#getCreationRequiredPower()
		 */
		@Override
		public int getCreationRequiredPower() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner#create(int)
		 */
		@Override
		protected MainCreation create(int requiredPower) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
