package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component;


import java.util.ArrayList;
import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Creation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.area.Area;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.dot.Dot;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line.Line;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.PhysicalShape;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GravityChangedEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.GravityChangedListener;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.Physical;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;



public class PhysicalWorld extends GeneralComponent implements Spacial,GravityChangedListener,ContactListener {
	
	private final World world;
	private final AABB aabb;
	private static final float STEP=1/60f;
	
	
	private final List<PhysicalShape> shapes=new ArrayList<PhysicalShape>();
	private final List<Line> lines=new ArrayList<Line>();
	private final List<Area> areas=new ArrayList<Area>();
	private final List<Dot> dots=new ArrayList<Dot>();
	private final List<Creation> otherCreations=new ArrayList<Creation>();
	private final List<ContactListener> contactListeners=new ArrayList<ContactListener>();
	
	public PhysicalWorld(Point screenCenter,double w,double h,Vec2 gravity){
		super(screenCenter,w,h);
		
		world=new World(gravity,true);
		this.aabb=CoordinateConverter.pixelAABBToWorldAABB(new PixelAABB(getPositionAt(PositionType.SOUTHWEST),getPositionAt(PositionType.NORTHEAST)));
		world.setContactListener(this);
	}
	
	
	//adders
	@Override
	public void add(Physical c) {
		if(c instanceof PhysicalShape){
			addShape((PhysicalShape)c);
		}else if(c instanceof Dot){
			addDot((Dot)c);
		}else if(c instanceof Line){
			addLine((Line)c);
		}else if(c instanceof Area){
			addArea((Area)c);
		}
		
	}
	public void add(Creation c){
		if(c instanceof PhysicalShape){
			addShape((PhysicalShape)c);
		}else if(c instanceof Dot){
			addDot((Dot)c);
		}else if(c instanceof Line){
			addLine((Line)c);
		}else if(c instanceof Area){
			addArea((Area)c);
		}else{
			otherCreations.add(c);
		}
	}
	public void addShape(PhysicalShape shape){
		shapes.add(shape);
		
	}
	public void addLine(Line line){
		lines.add(line);
		
	}
	public void addDot(Dot dot){
		dots.add(dot);
	}
	public void addArea(Area area){
		areas.add(area);
		
	}
	
	//removers
	@Override
	public void remove(Physical c) {
		if(c instanceof PhysicalShape){
			removeShape((PhysicalShape)c);
		}else if(c instanceof Dot){
			removeDot((Dot)c);
		}else if(c instanceof Line){
			removeLine((Line)c);
		}else if(c instanceof Area){
			removeArea((Area)c);
		}
	}
	public void remove(Creation c){
		if(c instanceof PhysicalShape){
			removeShape((PhysicalShape)c);
		}else if(c instanceof Dot){
			removeDot((Dot)c);
		}else if(c instanceof Line){
			removeLine((Line)c);
		}else if(c instanceof Area){
			removeArea((Area)c);
		}else{
			removeOtherCreation(c);
		}
	}
	private void removeOtherCreation(Creation c){
		int ri=lines.lastIndexOf(c);
		if(ri>=0 && (otherCreations.remove(ri)instanceof Physical)){
			world.destroyBody(((Physical)c).getBody());
		}
	}
	public void removeShape(PhysicalShape shape){
		if(shapes.remove(shape)){
			world.destroyBody(shape.getBody());
		}
	}
	public void removeLine(Line line){
		int ri=lines.lastIndexOf(line);
		if(ri>=0 && (lines.remove(ri)instanceof Physical)){
			world.destroyBody(((Physical)line).getBody());
		}
	}
	public void removeDot(Dot dot){
		int ri=dots.lastIndexOf(dot);
		if(ri>=0){//if(ri>=0 && (dots.remove(ri)instanceof Physical)){
			dots.remove(ri);//world.destroyBody(((Physical)dot).getBody());
			
		}
	}
	public void removeArea(Area area){
		int ri=areas.lastIndexOf(area);
		if(ri>=0 && (areas.remove(ri)instanceof Physical)){
			world.destroyBody(((Physical)area).getBody());
		}
	}
	
	@Override
	public void update() {
		int i;
		
		world.step(STEP, 8, 3);
		
		for(i=areas.size()-1;i>=0;i--){
			areas.get(i).update();
		}
		for(i=lines.size()-1;i>=0;i--){
			lines.get(i).update();
		}
		for(i=dots.size()-1;i>=0;i--){
			dots.get(i).update();
			
		}
		for(i=shapes.size()-1;i>=0;i--){
			shapes.get(i).update();
			
		}
		for(i=otherCreations.size()-1;i>=0;i--){
			otherCreations.get(i).update();
			
		}
	}
	
	@Override
	public void draw(Context2d context) {
		
		for(Area area:areas){
			area.draw(context);
		}
		for(Line line:lines){
			line.draw(context);
		}
		for(Dot dot:dots){
			dot.draw(context);
		}
		for(Physical shape:shapes){
			shape.draw(context);
		}
		for(Creation creation:otherCreations){
			creation.draw(context);
		}
	}
	@Override
	public World getWorld() {
		return world;
	}
	
	@Override
	public boolean isOutOfBounds(AABB aabb) {
		if(aabb.upperBound.y<this.aabb.lowerBound.y||aabb.lowerBound.y>this.aabb.upperBound.y||
			aabb.upperBound.x<this.aabb.lowerBound.x||aabb.lowerBound.x>this.aabb.upperBound.x)
			return true;
		return false;
	}
	
	
	@Override
	public void gravityChanged(GravityChangedEvent event) {
		for(Physical p:shapes){//and others..
			p.getBody().setAwake(true);
			
		}
		world.setGravity(event.getGravity());
	}
	
	//contact methods
	public void addContactListener(ContactListener l){
		contactListeners.add(l);
	}
	public void removeContactListener(ContactListener l){
		contactListeners.remove(contactListeners.lastIndexOf(l));
	}
	@Override
	public void beginContact(Contact contact) {
		for(int i=contactListeners.size()-1;i>=0;i--)
			contactListeners.get(i).beginContact(contact);
	}

	@Override
	public void endContact(Contact contact) {
		for(int i=contactListeners.size()-1;i>=0;i--)
			contactListeners.get(i).endContact(contact);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		for(int i=contactListeners.size()-1;i>=0;i--)
			contactListeners.get(i).preSolve(contact, oldManifold);
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		for(int i=contactListeners.size()-1;i>=0;i--)
			contactListeners.get(i).postSolve(contact, impulse);
	}
}
