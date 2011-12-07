/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;



import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;



import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteBlock;

/**
 * @author Maeglin Liao
 *
 */
public class BreakableLine extends StaticLine implements ContactListener{
	private static final float STRUCTURE_STRENGTH=4.5f; 
	
	private Vec2 impNormal=null;
	private Point breakPoint=null;
	
	private Fragment[] fragments=new Fragment[2];
	/**
	 * @param creator
	 * @param p1
	 * @param p2
	 */
	public BreakableLine(Creator creator, Point p1, Point p2) {
		this(creator, 0,false, p1, p2);
		
	}
	private BreakableLine(Creator creator,int contentPower, Point p1, Point p2) {
		this(creator, contentPower, true, p1, p2);
	}
	/**
	 * 
	 * @param creator
	 * @param contentPower
	 * @param p1
	 * @param p2
	 */
	private BreakableLine(Creator creator,int contentPower,boolean beControlled, Point p1, Point p2) {
		super(creator,contentPower,beControlled, p1, p2, GameColors.GRAY);
		if(isVerified()){
			this.creator.getWorld().addContactListener(this);
		}
	}
	
	@Override
	public void update(){
		if(breakPoint!=null){// if it breaks
			double fric=breakPoint.delta(pointA).getMagnitude()/pointA.delta(pointB).getMagnitude();
			int conPA=(int)Math.floor(getContentPower()*fric);
			int conPB=getContentPower()-conPA;
			fragments[0]=new Fragment(creator,conPA,pointA,breakPoint);
			fragments[1]=new Fragment(creator,conPB,breakPoint,pointB);
			this.fakeDestroy();
		}
	}
	private void fakeDestroy(){
		creator.getWorld().removeLine(this);
		creator.getWorld().removeContactListener(this);
		body=null;
		aabb=null;
		creator=null;
		lineColor=null;
		
		impNormal=null;
	}
	@Override
	public void destroy(){
		if(breakPoint==null){
			creator.getWorld().removeContactListener(this);
			impNormal=null;
			super.destroy();
			
		}else{
			for(Fragment line:fragments){
				if(!line.isDestroyed()){
					line.destroy();
				}
			}
			fragments=null;
		}
		breakPoint=null;
	}
	
	@Override
	public void beginContact(Contact contact) {
		//Window.alert("contact listener event: beginContact");
	}

	@Override
	public void endContact(Contact contact) {
		//Window.alert("contact listener event: endContact");
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		//Window.alert("contact listener event: preSolve");
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
		if(contact.getFixtureA().equals(fixture)&&(contact.getFixtureB().getUserData() instanceof PhysicalShape)||
		contact.getFixtureB().equals(fixture)&&(contact.getFixtureA().getUserData() instanceof PhysicalShape)){
			
			for(int i=0;i<impulse.tangentImpulses.length;i++){
				if(impulse.normalImpulses[i]!=0){
					if((STRUCTURE_STRENGTH<impulse.normalImpulses[i])){
						WorldManifold wm=new WorldManifold();
						contact.getWorldManifold(wm);
						
						breakPoint=CoordinateConverter.coordWorldToPixels(wm.points[0]);
						impNormal=wm.normal.clone();
						if(contact.getFixtureA().equals(fixture)){
							impNormal.negateLocal();
						}
						impNormal.mulLocal(impulse.normalImpulses[i]);
					}
					break;
				}
			}
		}
	}
	private class Fragment extends FragmentalLine{
		
		private int conPowerForDestroy=0;
		private boolean destroyed=false;
		
		private Fragment(Creator creator,int contentPower,Point p1, Point p2) {
			super(creator, 0,false, p1, p2);
			this.conPowerForDestroy=contentPower;
			this.body.applyForce(impNormal, CoordinateConverter.coordPixelToWorld(breakPoint));
		}
		private boolean isDestroyed(){
			return destroyed;
		}
		@Override
		public void destroy(){
			destroyed=true;
			checkFragments();
			super.destroy();
		}
		@Override
		public int getContentPower(){
			return conPowerForDestroy;
		}
		private void checkFragments(){
			if(BreakableLine.this.beControlled&&fragments[0].isDestroyed()&&fragments[1].isDestroyed()){
				creator.remove(BreakableLine.this);
			}
		}
	}
	public static class BreakableLineDefiner extends StaticLineDefiner{

		public BreakableLineDefiner(Creator creator){
			super(creator,50,new Point(ICON_BOUNDS_PLUS_SPACING,ICON_BOUNDS_PLUS_SPACING),GameColors.GRAY);
		}
		@Override
		protected MainCreation create(int requiredPower){
			return new BreakableLine(creator,requiredPower,pointA,pointB);
		}
	}
}
