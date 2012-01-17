/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Dynamic;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public abstract class BreakableStaticLine extends ContactStaticLine {
	protected Vec2 impOnNormal=null;
	protected Point breakPoint=null;
	
	private LineFragment[] lineFragments=new LineFragment[2];
	
	/**
	 * @param creator
	 * @param contentPower
	 * @param beControlled
	 * @param p1
	 * @param p2
	 * @param color
	 */
	public BreakableStaticLine(Creator creator, int contentPower,
			boolean beControlled, Point p1, Point p2, CssColor color) {
		super(creator, contentPower, beControlled, p1, p2, color);
	}
	@Override
	public void update(){
		if(breakPoint!=null&&impOnNormal!=null){// if it broke
			double fric=Point.getDistance(breakPoint, pointA)/Point.getDistance(pointA, pointB);
			int conPA=(int)Math.floor(getContentPower()*fric);
			int conPB=getContentPower()-conPA;
			lineFragments[0]=new LineFragment(creator,conPA,pointA,breakPoint);
			lineFragments[1]=new LineFragment(creator,conPB,breakPoint,pointB);
			this.fakeDestroy();
		}
	}
	private void fakeDestroy(){
		/*
		 * parent's destroy processes, except recycling.
		 *  we do not recycle the power
		 */
		creator.getWorld().removeLine(this);
		creator.getWorld().removeContactListener(this);
		body=null;
		aabb=null;
		creator=null;
		lineColor=null;
		//the own field
		impOnNormal=null;
		
	}
	@Override
	public void destroy(){
		if(breakPoint==null){
			super.destroy();
			lineFragments=null;
			impOnNormal=null;
		}else{
			for(LineFragment line:lineFragments){
				if(!line.isDestroyed()){
					line.destroy();
				}
			}
			
		}
		
	}
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#beginContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void beginContact(Contact contact,Fixture thisFixture, Fixture thatFixture) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#endContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void endContact(Contact contact,Fixture thisFixture, Fixture thatFixture) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#preSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.collision.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold,Fixture thisFixture, Fixture thatFixture) {
		// TODO Auto-generated method stub

	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse,Fixture thisFixture, Fixture thatFixture) {
		if(thatFixture.getBody().getUserData() instanceof Dynamic){
			for(int i=0;i<impulse.tangentImpulses.length;i++){
				if(impulse.normalImpulses[i]!=0){
					if(doesLineBreak(impulse.normalImpulses[i])){
						WorldManifold wm=new WorldManifold();
						contact.getWorldManifold(wm);
								
						breakPoint=CoordinateConverter.coordWorldToPixels(wm.points[0]);
						impOnNormal=wm.normal.clone();
								
						if(contact.getFixtureA().equals(fixture)){
							impOnNormal.negateLocal();
						}
						impOnNormal.mulLocal(impulse.normalImpulses[i]);
						//give a bonus!
						this.creator.bonus(Math.round(impulse.normalImpulses[i]));		
					}
					return;
				}
			}
		}
	}
	public abstract boolean doesLineBreak(float normalImpulse);
	
	private class LineFragment extends FragmentalLine{
		
		private int conPowerForDestroy=0;
		
		
		private LineFragment(Creator creator,int contentPower,Point p1, Point p2) {
			super(creator, 0,false, p1, p2, BreakableStaticLine.this.getColor());
			this.conPowerForDestroy=contentPower;
			this.body.applyForce(impOnNormal, CoordinateConverter.coordPixelToWorld(breakPoint));
		}
		
		@Override
		public void destroy(){
			checkFragments();
			super.destroy();
		}
		@Override
		public int getContentPower(){
			return conPowerForDestroy;
		}
		private void checkFragments(){
			if(lineFragments[0].isDestroyed()&&lineFragments[1].isDestroyed()){
				if(BreakableStaticLine.this.beControlled){
					creator.remove(BreakableStaticLine.this);
				}
				breakPoint=null;
				lineFragments=null;
				
			}
		}
	}
}
