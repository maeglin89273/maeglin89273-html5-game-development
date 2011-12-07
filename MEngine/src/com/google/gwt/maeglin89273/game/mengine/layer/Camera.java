/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.Spacial;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;

/**
 * @author Maeglin Liao
 *
 */
public class Camera extends GeneralComponent {
	 
	private static final float SCALE_FACTOR=1.01f;
	private static final int MAX_ZOOMING_BUFFER_COUNT=15;
	private static final float MOVING_VELOCITY_FACTOR=0.9f;
	private static final float ATTACHED_VELOCITY_FACTOR=0.25f;
	
	//zooming fields
	private double scale=1;
	private final float maxScale,minScale;
	private boolean zoomIn,zoomOut;
	private int zoomingBufferCount=MAX_ZOOMING_BUFFER_COUNT;
	
	//moving fields
	private final Vector moveDelta=new Vector(0,0);
	private boolean stop=true;
	
	private final Vector translate=new Vector(0,0);//really change
	private final Point originPos=new Point(0,0);//really change
	private final Point scaledBoundsCorner =new Point(0,0); //original unit
	
	private final WorldBounds bounds;
	
	private static int cameraWidth;
	private static int cameraHeight;
	/**
	 * @param p
	 * @param cameraWidth
	 * @param cameraHeight
	 */
	Camera(WorldBounds bounds,Point p,float maxScale) {
		super(p, 0, cameraWidth, cameraHeight);
		
		this.minScale=(float)Math.max(cameraWidth/bounds.getWidth(), cameraHeight/bounds.getHeight());
		this.maxScale=Math.abs(maxScale);
		
		this.bounds=bounds;
		updateScaledProperties();
		constrainBounds(null);
	}
	Camera(Spacial s,float maxScale){
		this(s, s.getPosition(), maxScale);
	}
	Camera(Spacial s,Point p,float maxScale) {
		this(new WorldBounds(new Point(s.getLeftX(),s.getTopY()),s.getWidth(),s.getHeight()), p, maxScale);
		
	}
	Camera(WorldBounds bounds,float maxScale) {
		this(bounds,new Point(bounds.getWidth()/2+ bounds.getX(),bounds.getHeight()+bounds.getY()),maxScale);
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
			updateScaledProperties();
			zoomIn=false;
		}
	}
	public void zoomOut(){
		if(scale/SCALE_FACTOR>=minScale){
			zoomingBufferCount=MAX_ZOOMING_BUFFER_COUNT;
			zoomOut=true;
		}else{
			scale=minScale;
			updateScaledProperties();
			zoomOut=false;
		}
	}
	public Point ConvertToWorldPosition(Point p){
		Point clone=p.clone();
		clone.translate(getLeftX(), getTopY());
		Vector delta=scaledBoundsCorner.delta(clone);
		delta.divided(scale);
		clone.setPosition(bounds.getX()+delta.getVectorX(), bounds.getY()+delta.getVectorY());
		return clone;
	}
	
	public void move(Vector delta,boolean stably){
		
		position.translate(delta);
		constrainBounds(delta);
		
		if((!stably)&&(delta.getSquare()>0.0001f)){
			moveDelta.setVector(delta);
			stop=false;
		}else{
			moveDelta.setVector(0, 0);
			stop=true;
		}
	}
	
	private void constrainBounds(Vector velocity){
		boolean attachX=false;
		boolean attachY=false;
		if(getLeftX()<scaledBoundsCorner.getX()){
			position.setX(scaledBoundsCorner.getX()+getWidth()/2);
			attachX=true;
		}else if(getRightX()>scaledBoundsCorner.getX()+bounds.getWidth()*scale){
			position.setX(scaledBoundsCorner.getX()+bounds.getWidth()*scale-getWidth()/2);
			attachX=true;
		}
		
		if(getTopY()<scaledBoundsCorner.getY()){
			position.setY(scaledBoundsCorner.getY()+getHeight()/2);
			attachY=true;
		}else if(getBottomY()>scaledBoundsCorner.getY()+bounds.getHeight()*scale){
			position.setY(scaledBoundsCorner.getY()+bounds.getHeight()*scale-getHeight()/2);
			attachY=true;
		}
		
		if(velocity!=null){
			if(attachX||attachY){
				velocity.mutiply(ATTACHED_VELOCITY_FACTOR);
				if(attachX)velocity.reverse();
				if(attachY)velocity.reverse();
			}else{
				velocity.mutiply(MOVING_VELOCITY_FACTOR);
			}
		}
	}
	
	private void updateScaledProperties(){
		this.scaledBoundsCorner.setX(bounds.getCenter().getX()-bounds.getWidth()*scale/2);
		this.scaledBoundsCorner.setY(bounds.getCenter().getY()-bounds.getHeight()*scale/2);
		this.originPos.setPosition((1-scale)*bounds.getCenter().getX(), (1-scale)*bounds.getCenter().getY());
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
			updateScaledProperties();
		}
		if(zoomOut){
			if(scale/SCALE_FACTOR>=minScale){
					scale/=SCALE_FACTOR;
				if(zoomingBufferCount>0){
					zoomingBufferCount--;
				}else{
					zoomOut=false;
				}
			}else{
				scale=minScale;
				zoomOut=false;
			}
			updateScaledProperties();
			constrainBounds(null);
		}
			
		
		if(!stop){
			move(moveDelta,false);
		}
		translate.setVector(this.originPos.delta2(getLeftX(), getTopY()));
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		this.setContext2d(context);
	}
	public void setContext2d(Context2d context){
		context.translate(translate.getVectorX(),translate.getVectorY());
		context.scale(scale, scale);
	}
	public static class WorldBounds{
		private final Point corner;
		private final Point center;
		private final double width;
		private final double height;
		public WorldBounds(Point corner,double w,double h){
			this.corner=corner;
			this.center=new Point(corner.getX()+w/2,corner.getY()+h/2);
			this.width=w;
			this.height=h;
		}
		public double getX(){
			return corner.getX();
		}
		public double getY(){
			return corner.getY();
		}
		public Point getCorner(){
			return corner;
		}
		public Point getCenter(){
			return center;
		}
		public double getWidth(){
			return width;
		}
		public double getHeight(){
			return height;
		}
	}
}
