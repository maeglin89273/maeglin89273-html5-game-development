/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.core.ui;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.CreatorPropertiesChangedListener;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.DefiningEvent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.event.DefiningListener;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import static com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs.Color;
/**
 * @author Liao
 *
 */
public class CreatorPropertiesBar extends GeneralComponent implements CreatorPropertiesChangedListener,DefiningListener{
	private static final String LOWER_SCORE_COLOR=Color.RED.toString();
	private static final String SCORE_STRING="Score";
	
	private static final float HUE_RANGE=40f;
	private static final int HUE_OFFSET=20;
	
	private float hue=HUE_OFFSET+HUE_RANGE;
	private final int maxPower; 
	private final int lastScore;
	private final int requiredScore;
	private int score;
	private final double barLength;
	
	private double powerBarLength;
	private double powerBarX;
	
	private double expectativePowerBarLength;
	private double expectativePowerBarX;
	private boolean expecting=false;
	
	private double scoreFlagPos;
	
	private float frictionP;
	
	
	private final String font;
	private final float textBaselineY;
	private String expColor;
	private CssColor scoreColor;
	/**
	 * @param p
	 * @param screenHeight TODO
	 * @param w
	 * @param h
	 */
	public CreatorPropertiesBar(int screenWidth, int screenHeight,int maxPower,int lastScore,int requiredScore) {
		super(new Point(screenWidth*7/8f-20,screenHeight*25/54f), screenWidth/4f, screenHeight*25/27f);
		this.barLength=this.powerBarLength=this.expectativePowerBarLength=this.scoreFlagPos=getHeight()*0.9;
		this.powerBarX=getRightX()-1.5;
		this.expectativePowerBarX=getRightX()-4.5;
		this.textBaselineY=(float)getHeight()*0.05f;
		this.font=ASBOTXConfigs.getCGFont((int)(screenWidth*13/360f));
		this.maxPower=this.score=maxPower;
		this.lastScore = lastScore;
		this.requiredScore=requiredScore;
		this.detectScoreColor();
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
		context.save();
		
		//draw the score
		context.setFillStyle(scoreColor);
		context.setFont(font);
		context.setTextBaseline(TextBaseline.MIDDLE);
		
		//draw the Score text
		context.setTextAlign(TextAlign.START);
		context.fillText(SCORE_STRING, getLeftX(), textBaselineY);
		
		//draw the score numbers
		context.setTextAlign(TextAlign.RIGHT);
		context.fillText(Integer.toString(score), getRightX(), textBaselineY);
		
		//draw the expecting bar
		if(expecting){
			context.setStrokeStyle(expColor);
			context.setLineWidth(3);
			context.beginPath();
			context.moveTo(expectativePowerBarX, getBottomY());
			context.lineTo(expectativePowerBarX, getBottomY()-expectativePowerBarLength);
			context.stroke();
			expecting=false;
		}
		//draw the power bar
		context.setStrokeStyle("hsl("+hue+",98%,50%)");
		context.setLineWidth(3);
		context.beginPath();
		context.moveTo(powerBarX, getBottomY());
		context.lineTo(powerBarX, getBottomY()-powerBarLength);
		context.stroke();
		//draw the score flag
		context.setLineWidth(2);
		context.beginPath();
		context.setStrokeStyle(Color.DARK_BLUE);
		context.moveTo(powerBarX-5.5f, getBottomY()-scoreFlagPos);
		context.lineTo(powerBarX-1.5f, getBottomY()-scoreFlagPos);
		
		context.stroke();
		context.restore();
	}

	private void detectScoreColor(){
		if(score<requiredScore){
			scoreColor=ASBOTXConfigs.Color.TRANSPARENT_RED;
		}else if(score>lastScore){
			scoreColor=ASBOTXConfigs.Color.TRANSPARENT_BLUE;
		}else{
			scoreColor=ASBOTXConfigs.Color.GRAY;
		}
	}
	@Override
	public void powerChanged(CreatorPropertiesChangedEvent event) {
		frictionP=((float)event.getPower())/this.maxPower;
		hue=HUE_OFFSET+HUE_RANGE*frictionP;
		powerBarLength=barLength*frictionP;
	}

	@Override
	public void scoreChanged(CreatorPropertiesChangedEvent event) {
		score=event.getScore();
		detectScoreColor();
		scoreFlagPos=barLength*((float)event.getScore())/this.maxPower;
	}

	@Override
	public void defining(DefiningEvent event) {
		expecting=true;
		expColor=score<=event.getExpectativeRemainingPower()?"hsla("+hue+",98%,75%,0.7)":LOWER_SCORE_COLOR;
		expectativePowerBarLength=barLength*(float)event.getExpectativeRemainingPower()/this.maxPower;
	}

}
