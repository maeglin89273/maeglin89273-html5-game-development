/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;

/**
 * @author Maeglin Liao
 *
 */
public class Camera extends GeneralComponent {
	 
	private static final float SCALE_FACTOR=1.01f;
	private static final int MAX_ZOOMING_BUFFER_COUNT=20;
	private static final float MOVING_FRICTION=0.25f;
	
	private int zoomingBufferCount=MAX_ZOOMING_BUFFER_COUNT;
	
	private double scale=1;
	private final float maxScale,minScale;
	
	private boolean zoomIn,zoomOut;
	private boolean stop=true;
	
	private final Vector moveDelta=new Vector(0,0);
	
	private final Vector translate=new Vector(0,0);
	
	private final WorldLayer layer;
	private final WorldBounds bounds;
	/**
	 * @param p
	 * @param cameraWidth
	 * @param cameraHeight
	 */
	public Camera(WorldLayer layer,Point p,WorldBounds bounds,double cameraWidth, double cameraHeight,float maxScale) {
		super(p, cameraWidth, cameraHeight);
		
		this.minScale=(float)Math.max(cameraWidth/bounds.getWidth(), cameraHeight/bounds.getHeight());
		this.maxScale=Math.abs(maxScale);
		
		this.layer=layer;
		this.bounds=bounds;
	}
	public Camera(WorldLayer layer,WorldBounds bounds,double w, double h,float maxScale) {
		this(layer,new Point(w/2,h/2),bounds, w, h, maxScale);
	}
	public void zoomIn(){
		if(scale*SCALE_FACTOR<=maxScale){
			zoomingBufferCount=MAX_ZOOMING_BUFFER_COUNT;
			zoomIn=true;
		}else{
			scale=maxScale;
			zoomIn=false;
		}
	}
	public void zoomOut(){
		if(scale/SCALE_FACTOR>=minScale){
			zoomingBufferCount=MAX_ZOOMING_BUFFER_COUNT;
			zoomOut=true;
		}else{
			scale=minScale;
			zoomOut=false;
		}
	}
	public Point ConvertToWorldPosition(Point p){
		return null;
	}
	
	public void move(Vector delta,boolean stably){
		
		position.translate(delta);
		stably|=constrainBounds();
		
		if((!stably)&&(delta.getSquare()-MOVING_FRICTION*MOVING_FRICTION>=0)){
			moveDelta.setVector(delta);
			moveDelta.minusMagnitude(MOVING_FRICTION);
			stop=false;
		}else{
			moveDelta.setVector(0, 0);
			stop=true;
		}
	}
	private boolean constrainBounds(){
		double offsetX=(getWidth()-bounds.getWidth()*scale)/2;
		double offsetY=(getHeight()-bounds.getHeight()*scale)/2;
		boolean attach=false;
		if(getLeftX()<offsetX){
			position.setX(getWidth()-bounds.getWidth()*scale/2);
			attach=true;
		}else if(getRightX()>offsetX+bounds.getWidth()*scale){
			position.setX(bounds.getWidth()*scale/2);
			attach=true;
		}
		
		if(getTopY()<offsetY){
			position.setY(getHeight()-bounds.getHeight()*scale/2);
			attach=true;
		}else if(getBottomY()>offsetX+bounds.getHeight()*scale){
			position.setY(bounds.getHeight()*scale/2);
			attach=true;
		}
		return attach;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		if(zoomIn){
			if(scale*SCALE_FACTOR<=maxScale){
				scale*=SCALE_FACTOR;
				if(zoomingBufferCount>0){
					zoomingBufferCount--;
				}else{
					zoomIn=false;
				}
			}else{
				scale=maxScale;
				zoomIn=false;
			}
		}
		if(zoomOut){
			if(scale/SCALE_FACTOR>=minScale){
				scale/=SCALE_FACTOR;
				constrainBounds();
				if(zoomingBufferCount>0){
					zoomingBufferCount--;
				}else{
					zoomOut=false;
				}
			}else{
				scale=minScale;
				zoomOut=false;
			}
		}
		if(!stop){
			move(moveDelta,false);
		}
		translate.setVector((getWidth()-bounds.getWidth()*scale)/2-getLeftX(), (getHeight()-bounds.getHeight()*scale)/2-getTopY());
		layer.updateComponents();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.save();
		context.translate(translate.getVectorX(),translate.getVectorY());
		context.scale(scale, scale);
		layer.drawComponents(context);
		context.restore();

	}
	public static class WorldBounds{
		private final Point location;
		private final double width;
		private final double height;
		public WorldBounds(Point location,double w,double h){
			this.location=location;
			this.width=w;
			this.height=h;
		}
		public double getX(){
			return location.getX();
		}
		public double getY(){
			return location.getY();
		}
		public Point getLocation(){
			return location;
		}
		public double getWidth(){
			return width;
		}
		public double getHeight(){
			return height;
		}
	}
}
