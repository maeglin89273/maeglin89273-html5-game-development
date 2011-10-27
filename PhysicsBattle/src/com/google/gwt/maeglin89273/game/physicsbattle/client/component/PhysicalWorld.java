package com.google.gwt.maeglin89273.game.physicsbattle.client.component;


import java.util.ArrayList;
import java.util.HashSet;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.line.GrayStaticLine;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.line.Line;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.Circle;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.Polygon;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.Rectangle;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.event.GravityChangedEvent;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.event.GravityChangedListener;
import com.google.gwt.maeglin89273.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.mengine.component.Physical;
import com.google.gwt.maeglin89273.mengine.component.Spacial;
import com.google.gwt.maeglin89273.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.mengine.physics.Point;
import com.google.gwt.maeglin89273.mengine.utility.CoordinateConverter;
import com.google.gwt.user.client.Random;


public class PhysicalWorld extends GeneralComponent implements Spacial,GravityChangedListener {
	
	
	private final World world;
	private final float step=1/60f;
	private final ArrayList<PhysicalShape> shapes=new ArrayList<PhysicalShape>();
	private final ArrayList<Line> lines=new ArrayList<Line>();
	public PhysicalWorld(int w,int h,Vec2 gravity){
		super(new Point(0,0),w,h);
		world=new World(gravity,true);
		
	}
	
	@Override
	public void add(Physical c) {
		if(c instanceof PhysicalShape){
			addShape((PhysicalShape)c);
		}else if(c instanceof Line){
			addLine((Line)c);
		}
		
	}
	public void addShape(PhysicalShape shape){
		shapes.add(shape);
	}
	public void addLine(Line line){
		lines.add(line);
	}
	@Override
	public void update() {
		world.step(step, 8, 3);
		for(int i=shapes.size()-1;i>=0;i--){
			shapes.get(i).update();
			
		}
	}
	
	@Override
	public void draw(Context2d context) {
		
		for(Physical shape:shapes){
			shape.draw(context);
		}
		for(Line line:lines){
			line.draw(context);
		}
		
	}
	@Override
	public World getWorld() {
		return world;
	}
	@Override
	public void remove(Physical c) {
		if(c instanceof PhysicalShape){
			removeShape((PhysicalShape)c);
		}else if(c instanceof Line){
			removeLine((Line)c);
		}
	}
	public void removeShape(PhysicalShape shape){
		if(shapes.remove(shape)){
			world.destroyBody(shape.getBody());
		}
	}
	public void removeLine(Line line){
		if(lines.remove(line)){
			world.destroyBody(line.getBody());
		}
	}
	
	@Override
	public boolean isOutOfBounds(PixelAABB aabb) {
		if(aabb.getTopY()>height||aabb.getRightX()<0||aabb.getLeftX()>width)
			return true;
		return false;
	}

	@Override
	public void gravityChange(GravityChangedEvent event) {
		for(Physical p:shapes){
			p.getBody().setAwake(true);
			
		}
		world.setGravity(event.getGravity());
	}

}
