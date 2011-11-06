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
	private static final float ATTACHED_FRICTION=0.45f;
	
	//zooming fields
	private double scale=1;
	private final float maxScale,minScale;
	private boolean zoomIn,zoomOut;
	private int zoomingBufferCount=MAX_ZOOMING_BUFFER_COUNT;
	
	//moving fields
	private final Vector moveDelta=new Vector(0,0);
	private Vector friction;
	private boolean stop=true;
	
	private final Vector translate=new Vector(0,0);
	private final Point offset =new Point(0,0); 
	
	
	private final WorldLayer layer;
	private final WorldBounds bounds;
	
	private static int cameraWidth;
	private static int cameraHeight;
	/**
	 * @param p
	 * @param cameraWidth
	 * @param cameraHeight
	 */
	public Camera(WorldLayer layer,WorldBounds bounds,Point p,float maxScale) {
		super(p, cameraWidth, cameraHeight);
		
		this.minScale=(float)Math.max(cameraWidth/bounds.getWidth(), cameraHeight/bounds.getHeight());
		this.maxScale=Math.abs(maxScale);
		
		this.layer=layer;
		this.bounds=bounds;
		calculateOffset();
	}
	public Camera(WorldLayer layer,WorldBounds bounds,float maxScale) {
		this(layer,bounds,new Point(cameraWidth/2,cameraHeight/2),maxScale);
	}
	public static void setCameraWidth(int w){
		cameraWidth=w;
	}
	public static int getCameraWidth(){
		return cameraWidth;
	}
	public static void setCameraHeight(int h){
		cameraHeight=h;
	}
	public static int getCameraHeight(){
		return cameraHeight; 
	}
	public static void setCameraSize(int w,int h){
		setCameraWidth(w);
		setCameraHeight(h);
	}
	public void zoomIn(){
		if(scale*SCALE_FACTOR<=maxScale){
			zoomingBufferCount=MAX_ZOOMING_BUFFER_COUNT;
			zoomIn=true;
		}else{
			scale=maxScale;
			calculateOffset();
			zoomIn=false;
		}
	}
	public void zoomOut(){
		if(scale/SCALE_FACTOR>=minScale){
			zoomingBufferCount=MAX_ZOOMING_BUFFER_COUNT;
			zoomOut=true;
		}else{
			scale=minScale;
			calculateOffset();
			zoomOut=false;
		}
	}
	public Point ConvertToWorldPosition(Point p){
		Point clone=p.clone();
		clone.translate(getLeftX(), getTopY());
		Vector delta=offset.delta(clone);
		delta.divided(scale);
		clone.setPosition(bounds.getX()+delta.getVectorX(), bounds.getY()+delta.getVectorY());
		return clone;
	}
	
	public void move(Vector delta,boolean stably){
		
		position.translate(delta);
		friction=constrainBounds(delta);
		
		if((!stably)&&(friction.getSquare()!=0)&&(delta.getSquare()-friction.getSquare()>=0)){
			moveDelta.setVector(delta);
			moveDelta.add(friction);
			stop=false;
		}else{
			moveDelta.setVector(0, 0);
			stop=true;
		}
	}
	
	private Vector constrainBounds(Vector velocity){
		boolean attachX=false;
		boolean attachY=false;
		if(getLeftX()<offset.getX()){
			position.setX(getWidth()-bounds.getWidth()*scale/2);
			attachX=true;
		}else if(getRightX()>offset.getX()+bounds.getWidth()*scale){
			position.setX(bounds.getWidth()*scale/2);
			attachX=true;
		}
		
		if(getTopY()<offset.getY()){
			position.setY(getHeight()-bounds.getHeight()*scale/2);
			attachY=true;
		}else if(getBottomY()>offset.getY()+bounds.getHeight()*scale){
			position.setY(bounds.getHeight()*scale/2);
			attachY=true;
		}
		if(velocity==null){
			return null;
		}else{
			Vector clone=velocity.clone();
			if(attachX||attachY){
				clone.setMagnitude(ATTACHED_FRICTION);
				if(attachX)clone.setVectorX(0);
				if(attachY)clone.setVectorY(0);
			}else{
				clone.setMagnitude(MOVING_FRICTION);
			}
			clone.reverse();
			return clone;
		}
	}
	
	private void calculateOffset(){
		this.offset.setX((getWidth()-bounds.getWidth()*scale)/2);
		this.offset.setY((getHeight()-bounds.getHeight()*scale)/2);
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		if(zoomIn||zoomOut){
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
					constrainBounds(null);
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
			calculateOffset();
		}
		if(!stop){
			move(moveDelta,false);
		}
		translate.setVector(offset.delta(getLeftX(), getTopY()));
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
