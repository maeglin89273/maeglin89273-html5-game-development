/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.creation.line;



import org.jbox2d.collision.shapes.PolygonShape;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.Creator;
import com.google.gwt.maeglin89273.game.mengine.physics.CoordinateConverter;
import com.google.gwt.maeglin89273.game.mengine.physics.PixelAABB;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.physics.Vector;
import com.google.gwt.user.client.Window;



/**
 * @author Maeglin Liao
 *
 */
public abstract class StaticLine extends PhysicalLine{

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Physical#getBody()
	 */
	
	protected StaticLine(Creator creator,int contentPower,boolean beControlled,Point p1,Point p2, CssColor color){
		super(creator,contentPower,beControlled, p1, p2, color);
		if(isVerified()){
			Vector rpA=position.delta(pointA);
			Vector rpB=position.delta(pointB);
			PolygonShape lineP=new PolygonShape();
			lineP.setAsEdge(CoordinateConverter.vectorPixelToWorld(rpA), CoordinateConverter.vectorPixelToWorld(rpB));
			fixture=body.createFixture(lineP, 0f);
			aabb=fixture.getAABB();
			PixelAABB pAABB=CoordinateConverter.worldAABBToPixelAABB(aabb);
			setWidth(pAABB.getWidth());
			setHeight(pAABB.getHeight());
			
			
		}
	}
	 
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.setLineWidth(2);
		context.setStrokeStyle(lineColor);
		context.beginPath();
		context.moveTo(pointA.getX(),pointA.getY());
		context.lineTo(pointB.getX(), pointB.getY());
		context.stroke();
		
	}
	protected abstract static class StaticLineDefiner extends Line.LineDefiner{
		
		protected CssColor lineColor;
		protected StaticLineDefiner(Creator creator,int requiredFullPower,Point iconCorner,CssColor lineColor) {
			super(creator,requiredFullPower,iconCorner);
			this.lineColor=lineColor;
		}

		@Override
		public void sketch(Context2d context) {
			if(pointA!=null&&pointB!=null){
				
				context.setLineWidth(2);
				context.setStrokeStyle(lineColor);
				context.beginPath();
				context.moveTo(pointA.getX(), pointA.getY());
				context.lineTo(pointB.getX(),pointB.getY());
				context.stroke();
			}
			
		}
		
	}
}
