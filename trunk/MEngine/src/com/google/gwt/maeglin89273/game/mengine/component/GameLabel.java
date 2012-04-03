/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.component;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class GameLabel extends GeneralComponent{
	private CssColor fillColor;
	private CssColor strokeColor;
	private TextAlign align;
	private TextBaseline baseline;
	private String text;
	private String textFont;
	private float strokeWidth;
	public GameLabel(Point p,TextAlign align,TextBaseline baseline,String text,CssColor textColor,CssColor strokeColor,float strokeWidth,String font) {
		super(p, 0,0);
		this.align=align;
		this.baseline=baseline;
		this.text=text==null?"":text;
		this.textFont=font;
		this.fillColor=textColor;
		this.strokeColor=strokeColor;
		this.strokeWidth=strokeWidth;
	}
	public GameLabel(Point p,TextAlign align,TextBaseline baseline,String text,CssColor textColor,String font) {
		this(p, align, baseline, text, textColor, null,0, font);
	}
	public void setStrokeColor(CssColor color){
		this.strokeColor=color;
	}
	public void setStrokeWidth(float width){
		this.strokeWidth=width;
	}
	public void setFillColor(CssColor color){
		this.fillColor=color;
	}
	public String getText(){
		return this.text;
	}
	public void setText(String text){
		this.text=text==null?"":text;;
	}
	public void clearText(){
		this.text="";
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Context2d context) {
		context.save();
		
		context.setTextAlign(align);
		context.setTextBaseline(baseline);
		context.setFillStyle(fillColor);
		context.setFont(textFont);
		context.fillText(text,getX(),getY());
		if(strokeColor!=null){
			context.setLineWidth(strokeWidth);
			context.setStrokeStyle(strokeColor);
			context.strokeText(text, getX(), getY());
		}
		context.restore();
		
	}
	
}
