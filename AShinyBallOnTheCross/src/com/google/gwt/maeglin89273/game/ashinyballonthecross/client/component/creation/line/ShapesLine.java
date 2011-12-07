/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.Creation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.MainCreation;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Circle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Polygon;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.shape.Rectangle;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.GameColors;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;
import com.google.gwt.user.client.Random;

/**
 * @author Maeglin Liao
 *
 */
public class ShapesLine extends Line {

	private ShapesLine(Creator creator,Point p1, Point p2,ShapesLineDefiner.ShapeDefiner[] shapeSketches,int count) {
		super(creator, 0,false, p1, p2);
		
		ShapesLineDefiner.ShapeDefiner shapeDefiner;
		for(int i=0;i<count;i++){
			shapeDefiner=shapeSketches[i];
			if(shapeDefiner instanceof ShapesLineDefiner.CircleDefiner){
				new Circle(creator,shapeDefiner.getPosition(),((ShapesLineDefiner.CircleDefiner)shapeDefiner).getRadius(),shapeDefiner.getColor());
			}else if(shapeDefiner instanceof ShapesLineDefiner.RectangleDefiner){
				new Rectangle(creator,shapeDefiner.getPosition(),shapeDefiner.getAngle(),((ShapesLineDefiner.RectangleDefiner)shapeDefiner).getWidth(),((ShapesLineDefiner.RectangleDefiner)shapeDefiner).getHeight(),shapeDefiner.getColor());
			}else{
				new Polygon(creator,shapeDefiner.getPosition(),shapeDefiner.getAngle(),((ShapesLineDefiner.PolygonDefiner)shapeDefiner).getVertices(),shapeDefiner.getColor());
			}
		}
		shapeSketches=null;
		this.destroy();
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
	
	public static class ShapesLineDefiner extends LineDefiner{
		private final int shapesMaxCount;
		private int showingShapesCount;
		private float spacing;
		private ShapeDefiner[] shapes;
		
		public ShapesLineDefiner(Creator creator,float spacing){
			super(creator,75,null);
			this.spacing=spacing;
			this.shapesMaxCount=(int)Math.floor(Line.MAX_LENGTH/spacing);//200 is the max length of line
			shapes=new ShapeDefiner[shapesMaxCount];
			reset();
		}
		@Override
		public void updatePenPosition(Point p){
			super.updatePenPosition(p);
			if(pointB!=null){
				Vector v=pointA.delta(pointB);
				double a=v.getAngle();
				//update
				showingShapesCount=(int)Math.floor(v.getMagnitude()/spacing);
				for(int i=0;i<shapesMaxCount;i++){
					shapes[i].setAngle(a);
					shapes[i].setPosition(pointA.getX()+(i+1)*spacing*Math.cos(a),pointA.getY()+(i+1)*spacing*Math.sin(a));
				}
			}
		}
		@Override
		public void sketch(Context2d context) {
			if(pointA!=null&&pointB!=null){
				for(int i=0;i<showingShapesCount;i++){
					shapes[i].sketch(context);
				}
			}
		}
		@Override
		public void reset(){
			super.reset();
			
			for(int i=0;i<shapesMaxCount;i++){
				//generate some random shape
				switch(Random.nextInt(3)){
					case 0:
						shapes[i]=new CircleDefiner(4+Random.nextInt(7));
						break;
					case 1:
						shapes[i]=new RectangleDefiner(5+Random.nextInt(21), 5+Random.nextInt(21));
						break;
					case 2:
						shapes[i]=new PolygonDefiner(Polygon.generateRandomInscribedPolygonVertices(3+Random.nextInt(6),12+Random.nextInt(4)));
				}
				
			}
		}
		@Override 
		public int getCreationRequiredPower(){
			return 0;
		}
		@Override
		protected MainCreation create(int requiredPower) {
			return new ShapesLine(creator,pointA,pointB,shapes,showingShapesCount);
		}
		
		
		//shape definers
		private abstract class ShapeDefiner{
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
		private class CircleDefiner extends ShapeDefiner{
			private int radius;
			CircleDefiner(int radius){
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
		private class RectangleDefiner extends ShapeDefiner{
			private double width;
			private double height;
			RectangleDefiner(double w,double h){
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
				context.rotate(angle);
				
				context.strokeRect(-width/2, -height/2, width, height);
				
				context.restore();
				
			}
			
		}
		private class PolygonDefiner extends ShapeDefiner{
			private Vector[] vertices;
			PolygonDefiner(Vector[] v){
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
				context.rotate(angle);
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
