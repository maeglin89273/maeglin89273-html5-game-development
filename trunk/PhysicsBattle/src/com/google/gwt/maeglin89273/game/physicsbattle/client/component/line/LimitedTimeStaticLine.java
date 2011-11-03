/**
 * 
 */
package com.google.gwt.maeglin89273.game.physicsbattle.client.component.line;



import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.physicsbattle.client.component.PhysicalWorld;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.timer.SchedulingTimer;
import com.google.gwt.maeglin89273.game.mengine.timer.TimerTask;


/**
 * @author Maeglin Liao
 *
 */
public abstract class LimitedTimeStaticLine extends PhysicalLine{

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.utility.component.Physical#getBody()
	 */
	
	protected CssColor lineColor;
	protected LimitedTimeStaticLine(PhysicalWorld world,Point p1,Point p2,int time,CssColor color){
		super(p1, p2, world);
		
		this.lineColor=color;
		final SchedulingTimer countdownTimer=new SchedulingTimer(new TimerTask(){

			@Override
			public void doTask() {
				destory();
				
			}
			
		},time*1000);
		countdownTimer.start();
	}
	
	public CssColor getColor(){
		return lineColor;
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
	
	protected abstract static class LimitedTimeStaticLineSketcher extends Line.LineSketcher{
		
		protected CssColor lineColor;
		protected LimitedTimeStaticLineSketcher(PhysicalWorld world) {
			super(world);
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
