/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.StaticLine;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.ShinyBall;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GameOverCallback;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Window;

/**
 * @author Maeglin Liao
 *
 */
public class Cross extends Creation {
        private static final double SQRT_2=Math.sqrt(2);
        
        private CrossLine[] crossLines=new CrossLine[2];
        private GameOverCallback callback;
        
        private ShinyBall ball;
        /**
         * @param creator
         * @param cotentPower
         * @param p
         * @param w
         * @param h
         * @param a
         */
        public Cross(Creator creator, Point p,double a,GameOverCallback callback) {
                super(creator,0, p, 15*(1+SQRT_2), 15*(1+SQRT_2/2), a);
                
                this.callback=callback;
                
                Point nwP=this.getPositionAt(PositionType.NORTHWEST);
                Point neP=this.getPositionAt(PositionType.NORTHEAST);
                Point seP=nwP.clone();
                Point swP=neP.clone();
                seP.translate(getHeight(), getHeight());
                swP.translate(-getHeight(), getHeight());
                Point[] ps=Point.getRotatedPoint(getPosition(),getAngle(),nwP,neP,seP,swP);
                crossLines[0]=new CrossLine(this.creator,ps[0],ps[2]);
                crossLines[1]=new CrossLine(this.creator,ps[1],ps[3]);
                
        }

        /* (non-Javadoc)
         * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
         */
        @Override
        public void update() {
                
                if(ball!=null&&!ball.getBody().isAwake()){
                        callback.showScore(this.creator);
                        this.destroy();
                }
                        
        }

        /* (non-Javadoc)
         * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
         */
        @Override
        public void draw(Context2d context) {
                // TODO Auto-generated method stub
        }
        @Override
        public void destroy(){
                super.destroy();
                for(CrossLine cl:crossLines){
                        cl.destroy();
                }
                crossLines=null;
                callback=null;
        }
        private class CrossLine extends StaticLine implements ContactListener{
                private boolean contact=false;
		private final int anotherLineIdx;
                private CrossLine(Creator creator, Point p1,Point p2,int anotherLineIndex) {
                        super(creator,0,false, p1, p2, GameColors.DARK_GRAY);
                        
                        this.creator.getWorld().addContactListener(this);
			this.anotherLineIdx=anotherLineIndex;
                }
                
                @Override
                public void destroy(){
                        this.creator.getWorld().removeContactListener(this);
                        super.destroy();
                }
                @Override
                public void beginContact(Contact contact) {
                        // TODO Auto-generated method stub
                }

                @Override
                public void endContact(Contact contact) {
                        Fixture fixA=contact.getFixtureA();
                        Fixture fixB=contact.getFixtureB();
                        if(fixA.equals(fixture)&&(fixB.getUserData() instanceof ShinyBall)||
                                fixB.equals(fixture)&&(fixA.getUserData() instanceof ShinyBall)){
				contact=false;
                                for(CrossLine l:crossLines)
                                        l.setColor(GameColors.DARK_GRAY);
                                ball=null;
                        }
                        
                }

                @Override
                public void preSolve(Contact contact, Manifold oldManifold) {
                        // TODO Auto-generated method stub
                        
                }

                @Override
                public void postSolve(Contact contact, ContactImpulse impulse) {
                        Fixture fixA=contact.getFixtureA();
                        Fixture fixB=contact.getFixtureB();
                        if(fixA.equals(fixture)&&(fixB.getUserData() instanceof ShinyBall)){
				this.contact=true;
				if(crossLines[anotherLineIdx].isContact()){
					ball=(ShinyBall)fixB.getUserData();
				}
                                for(CrossLine l:crossLines)
                                        l.setColor(GameColors.LIGHT_BLUE);
                                
                        }else if(fixB.equals(fixture)&&(fixA.getUserData() instanceof ShinyBall)){
				this.contact=true;
				if(crossLines[anotherLineIdx].isContact()){
                               		ball=(ShinyBall)fixA.getUserData();
				}
                                for(CrossLine l:crossLines)
                                        l.setColor(GameColors.LIGHT_BLUE);
                        }
                        
                }
                private void setColor(CssColor color){
                        this.lineColor=color;
                }
		private boolean isContact(){
			return contact;
		}
        }	
}