/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.line;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.Circle;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.Polygon;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.shape.Rectangle;
import com.google.gwt.maeglin89273.game.physicsbattle.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class ShapesLine extends Line {

	protected ShapesLine(Point p1, Point p2, PhysicalWorld world) {
		super(p1, p2, world);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		// TODO Auto-generated method stub

	}
	public static class ShapesLineSketcher extends LineSketcher{
		private int shapesMaxCount;
		private int showingShapesCount;
		private float spacing;
		private ShapeSketch[] shapes;
		
		public ShapesLineSketcher(PhysicalWorld world,float spacing){
			super(world);
			this.spacing=spacing;
			this.shapesMaxCount=(int)Math.floor(Line.MAX_LENGTH/spacing);//200 is the max length of line
			shapes=new ShapeSketch[shapesMaxCount];
			reset();
		}
		@Override
		public void updatePenPosition(Point p){
			super.updatePenPosition(p);
			if(pointB!=null){
				Vector v=new Vector(pointB.getX()-pointA.getX(),pointB.getY()-pointA.getY());
				double a=Math.atan2(-v.getVectorY(), v.getVectorX());
				//update
				showingShapesCount=(int)Math.floor(v.getMagnitude()/spacing);
				for(int i=0;i<shapesMaxCount-1;i++){
					shapes[i].setAngle(a);
					shapes[i].setPosition(pointA.getX()+(i+1)*spacing*Math.cos(a),pointA.getY()-(i+1)*spacing*Math.sin(a));
				}
			}
		}
		@Override
		public void sketch(Context2d context) {
			if(pointA!=null&&pointB!=null){
				for(int i=0;i<showingShapesCount-1;i++){
					shapes[i].sketch(context);
				}
			}
		}
		@Override
		public void reset(){
			super.reset();
			
			for(int i=0;i<shapesMaxCount-1;i++){
				//generate some random shape
				switch(Random.nextInt(3)){
					case 0:
						shapes[i]=new CircleSketch(4+Random.nextInt(7));
						break;
					case 1:
						shapes[i]=new RectangleSketch(5+Random.nextInt(21), 5+Random.nextInt(21));
						break;
					case 2:
						shapes[i]=new PolygonSketch(Polygon.generateRandomInscribedPolygonVertices(3+Random.nextInt(6),12+Random.nextInt(4)));
				}
				
			}
		}
		@Override
		public void sketchFinished() {
			ShapeSketch shape;
			for(int i=0;i<showingShapesCount-1;i++){
				shape=shapes[i];
				if(shape instanceof CircleSketch){
					world.addShape(new Circle(world,shape.getPosition(),((CircleSketch)shape).getRadius(),shape.getColor()));
				}else if(shape instanceof RectangleSketch){
					world.addShape(new Rectangle(world,shape.getPosition(),((RectangleSketch)shape).getWidth(),((RectangleSketch)shape).getHeight(),shape.getAngle(),shape.getColor()));
				}else{
					world.addShape(new Polygon(world,shape.getPosition(),((PolygonSketch)shape).getVertices(),shape.getAngle(),shape.getColor()));
				}
			}
			reset();
		}
		
		private abstract class ShapeSketch{
			protected final Point position=new Point(0,0); 
			protected double angle;
			protected CssColor color=GameColors.getRandomShapeBorderColor();
			void setPosition(double x,double y){
				this.position.setPosition(x,y);
			}
			Point getPosition(){
				return position;
			}
			void setAngle(double angle){
				this.angle=angle;
			}
			double getAngle(){
				return angle;
			}
			CssColor getColor(){
				return color;
			}
			abstract void sketch(Context2d context);
		}
		private class CircleSketch extends ShapeSketch{
			private int radius;
			CircleSketch(int radius){
				this.radius=radius;
			}
			int getRadius(){
				return radius;
			}
			@Override
			void sketch(Context2d context) {
				context.setStrokeStyle(color);
				context.setLineWidth(1.5);
				context.beginPath();
				context.arc(position.getX(), position.getY(), radius, 0, 2*Math.PI,true);
				context.closePath();
				context.stroke();
				
			}
		}
		private class RectangleSketch extends ShapeSketch{
			private double width;
			private double height;
			RectangleSketch(double w,double h){
				this.width=w;
				this.height=h;
			}
			double getWidth(){
				return width;
			}
			double getHeight(){
				return height;
			}
			@Override
			void sketch(Context2d context) {
				context.save();
				context.setStrokeStyle(color);
				
				context.setLineWidth(1.25);
				context.translate(position.getX(),position.getY());
				context.rotate(-angle);
				
				context.strokeRect(-width/2, -height/2, width, height);
				
				context.restore();
				
			}
		}
		private class PolygonSketch extends ShapeSketch{
			private Vector[] vertices;
			PolygonSketch(Vector[] v){
				this.vertices=v;
			}
			Vector[] getVertices(){
				return vertices;
			}
			@Override
			void sketch(Context2d context) {
				context.save();
				context.setStrokeStyle(color);
				
				context.setLineWidth(1.25);
				context.translate(position.getX(),position.getY());
				context.rotate(-angle);
				context.beginPath();
				context.moveTo(vertices[0].getVectorX(), vertices[0].getVectorY());
				
				for(int i=1;i<vertices.length;i++){
					context.lineTo(vertices[i].getVectorX(), vertices[i].getVectorY());
				}
				context.closePath();
				context.stroke();
				context.restore();
				
			}
		}
	}
}
