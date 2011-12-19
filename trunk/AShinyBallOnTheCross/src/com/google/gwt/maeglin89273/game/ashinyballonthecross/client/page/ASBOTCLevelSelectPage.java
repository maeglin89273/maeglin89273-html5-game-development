/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.page;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui.button.LevelSelectButton;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTCConfigurations;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.game.GeneralGame;
import com.google.gwt.maeglin89273.game.mengine.layer.ComponentLayer;
import com.google.gwt.maeglin89273.game.mengine.layer.GroupLayer;
import com.google.gwt.maeglin89273.game.mengine.page.GeneralPage;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class ASBOTCLevelSelectPage extends GeneralPage {
	private ButtonBoard board;
	private GroupLayer layers=new GroupLayer();
	/**
	 * @param game
	 */
	public ASBOTCLevelSelectPage(GeneralGame game) {
		super(game);
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		board.select(MEngine.getMousePosition());
		
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		layers.draw(context);

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.page.Page#onScreen()
	 */
	@Override
	public void onScreen() {
		layers.addLayer(new ComponentLayer(new GameLabel(new Point(getGameWidth()/2,60),"You Are Playing The Experimental Version",25)));
		board=new ButtonBoard(410,getGameWidth()/2-200,120);
		
		
		
		
	}
	private class ButtonBoard{
		private LevelSelectButton[][] buttons=new LevelSelectButton[3][3];
		
		private static final int SPACING=20;
		private double bounds;
		
		private int cornerX;
		private int cornerY;
		ButtonBoard(double bounds,int cornerX,int cornerY){
			this.cornerX=cornerX;
			this.cornerY=cornerY;
			this.bounds=bounds;
			
			bounds-=2*SPACING;
			bounds/=3;
			int counter=0;
			outerLoop:
			for(int i=0;i<buttons.length;i++){
				for(int j=0;j<buttons[i].length;j++){
				buttons[i][j]=new LevelSelectButton(game,new Point(cornerX+bounds/2+j*(bounds+SPACING),cornerY+bounds/2+i*(bounds+SPACING)),bounds,++counter);
				layers.addLayer(new ComponentLayer(buttons[i][j]));
				
				//remove it after finishing designing 9 levels
				if(counter==5)
					break outerLoop;
				}
				
			}
		}
		 void select(Point p){
			if(p.getX()>cornerX&&p.getX()<cornerX+bounds&&p.getY()>cornerY&&p.getY()<cornerY+bounds){
				Point cl=p.clone();
				cl.translate(-cornerX,-cornerY);
				
				int i=(int)(cl.getY()*buttons.length/bounds);
				int j=(int)(cl.getX()*buttons.length/bounds);
				if(buttons[i][j]!=null&&buttons[i][j].contain(p))
					buttons[i][j].doTask();
			}
		}
	}
	public class GameLabel extends GeneralComponent{
		private String string;
		private String textFont; 
		protected GameLabel(Point p,String string,int textSize) {
			super(p, 0,0);
			this.string=string;
			this.textFont=ASBOTCConfigurations.getGameFont(textSize);
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void draw(Context2d context) {
			context.save();
			context.setTextAlign(TextAlign.CENTER);
			context.setTextBaseline(TextBaseline.MIDDLE);
			context.setFillStyle(ASBOTCConfigurations.Color.GRAY);
			context.setFont(textFont);
			context.fillText(string,getX(),getY());
			context.restore();
			
		}
		
	}
}
