/**
 * 
 */
package com.google.gwt.maeglin89273.shared.test.volcanogame.component;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Random;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.core.AssetManager;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;
import com.google.gwt.maeglin89273.game.mengine.utility.*;
/**
 * @author Maeglin Liao
 *
 */
public class VolcanoWorld extends GeneralComponent implements Spacial {
	private final ArrayList<Physical> fireballs=new ArrayList<Physical>();
	private final Volcano volcano;
	private final World world;
	private final SpriteSheet background;
	private final SpriteSheet clouds;
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Spacial#isOutOfBounds(com.google.gwt.maeglin89273.game.mengine.utility.component.Physical)
	 */
	public VolcanoWorld(int width,int height){
		super(new Point(0,0),width,height);
		this.background=MEngine.getAssetManager().getSpriteSheet("volcano_background.png");
		this.clouds=MEngine.getAssetManager().getSpriteSheet("clouds.png");
		world=new World(new Vec2(0,-10f),true);
		volcano=new Volcano(this,new Point(250,425));
		
	}
	@Override
	public boolean isOutOfBounds(PixelAABB aabb) {
		if(aabb.getTopY()>height||aabb.getRightX()<0||aabb.getLeftX()>width)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Spacial#add(com.google.gwt.maeglin89273.game.mengine.utility.component.Physical)
	 */
	@Override
	public void add(Physical c) {
		fireballs.add(c);

	}
	public void erupt(){
		add(new FireBall(this,new Point(volcano.getLeftCraterPoint().getX()+Random.nextInt(Volcano.CRATER_WIDTH+1),volcano.getTopY()),2+Random.nextInt(5)));
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Spacial#remove(com.google.gwt.maeglin89273.game.mengine.utility.component.Physical)
	 */
	@Override
	public void remove(Physical c) {
		fireballs.remove(c);

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Spacial#getWorld()
	 */
	@Override
	public World getWorld() {
		
		return world;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		world.step(1/60f, 8, 3);
		for(int i=fireballs.size()-1;i>=0;i--){
			fireballs.get(i).update();
			
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.drawImage(background.getImage(), 0, 0);
		volcano.draw(context);
		context.save();
		//context.setShadowBlur(12);
		for(Physical fireball:fireballs){
			fireball.draw(context);
		}
		
		/*context.setShadowBlur(3);
		context.setShadowOffsetX(4);
		context.setShadowOffsetY(4);
		context.setShadowColor("rgba(65,65,65,0.3)");*/
		context.drawImage(clouds.getImage(), 20, 30);
		context.restore();
	}
	
}
