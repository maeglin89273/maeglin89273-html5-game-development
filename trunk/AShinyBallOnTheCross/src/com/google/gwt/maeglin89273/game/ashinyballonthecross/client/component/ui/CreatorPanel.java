/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.CreationDefiner;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.DefinersFactory.*;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.key.*;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.DefiningEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.DefiningListener;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Window;

/**
 * @author Maeglin Liao
 *
 */
public class CreatorPanel extends GeneralComponent {
	public static final int DOT_KEY_INDEX=2;
	public static final int LINE_KEY_INDEX=1;
	public static final int AREA_KEY_INDEX=0;
	
	private final CreativeKey[] keys=new CreativeKey[3];
	private boolean anyKeyPressed=false;
	
	private DefiningListener dListener;
	private Creator creator;
	/**
	 * @param p
	 * @param w
	 * @param h
	 */
	public CreatorPanel(Creator creator,double screenWidth,double screenHeight) {
		super(new Point(screenWidth*3/16,screenHeight-screenWidth*1/16), screenWidth*3/8, screenWidth*1/8);
		double halfH=getHeight()/2;
		double oneThirdW=getWidth()/3;
		this.creator=creator;
		this.keys[AREA_KEY_INDEX]=new AreaKey(new Point(getX()-oneThirdW,getTopY()+halfH),oneThirdW,getHeight(),creator.getLevel().getAreaDefinerTypes());
		this.keys[LINE_KEY_INDEX]=new LineKey(new Point(getX(),getTopY()+halfH),oneThirdW,getHeight(),creator.getLevel().getLineDefinerTypes());
		this.keys[DOT_KEY_INDEX]=new DotKey(new Point(getX()+oneThirdW,getTopY()+halfH),oneThirdW,getHeight(),creator.getLevel().getDotDefinerTypes());
	
	}
	public void setDefiningListener(DefiningListener listener){
		this.dListener=listener;
	}
	public void removeDefiningListener(){
		this.dListener=null;
	}
	
	public CreativeKey[] getKeys(){
		return keys;
	}
	public void previous(){
		for(CreativeKey key:keys){
			if(key.isPressed()){
				key.prevoius();
			}
		}
	}
	public void next(){
		for(CreativeKey key:keys){
			if(key.isPressed()){
				key.next();
			}
		}
	}
	public void updatePenPosition(Point p){
		if(isAnyKeyPressed()){
			int totalExRePower=0;
			for(CreativeKey k:keys){
				if(k.isPressed()){
					k.getDefiner().updatePenPosition(p);
					totalExRePower+=k.getDefiner().getCreationRequiredPower();
				}
			}
			if(this.dListener!=null){
				totalExRePower=Math.max(0, creator.getPower()-totalExRePower);
				this.dListener.defining(new DefiningEvent(this,totalExRePower));
			}
		}
	}
	public void sketch(Context2d context){
		for(CreativeKey k:keys){
			if(k.isPressed()){
				k.getDefiner().sketch(context);
			}
		}
	}
	public void onPenDown(Point p){
		for(CreativeKey key:keys){
			if(key.isPressed()){
				key.getDefiner().onPenDown(p);
				
			}
		}
	}
	public void onPenUp(Point p){
		for(CreativeKey key:keys){
			if(key.isPressed()){
				key.getDefiner().onPenUp(p);
			}
		}
	}
	public void onKeyChange(int keyIndex,boolean pressed){
		keys[keyIndex].setPressed(pressed);
		boolean p=false;
		for(int i=0;i<keys.length&&(!p);i++){
			p|=keys[i].isPressed();
		}
		anyKeyPressed=p;
	}
	
	public void resetDefiners(){
		for(CreativeKey key:keys){
			if(key.isPressed()){
				key.resetDefiner();
			}
		}
	}
	public boolean isAnyKeyPressed(){
		return anyKeyPressed;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		for(CreativeKey key:keys){
			key.update();
		}

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		for(CreativeKey key:keys){
			key.draw(context);
		}
		

	}

}
