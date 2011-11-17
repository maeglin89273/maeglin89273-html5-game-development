/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.layer;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.maeglin89273.game.mengine.sprite.SpriteSheet;

/**
 * @author Liao
 *
 */
public class ImageLayer extends Layer {
	private double alpha=1;
	private final SpriteSheet sheet;
	private final Point imgPos;
	private final int imgWidth;
	private final int imgHeight;
	public ImageLayer(SpriteSheet sheet,Point imgPos,int imgWidthInGame,int imgHeightInGame){
		this.sheet = sheet;
		this.imgPos = imgPos;
		imgWidth = imgWidthInGame;
		imgHeight = imgHeightInGame;
		
	}
	public void setAlpha(double alpha){
		this.alpha=alpha;
	}
	public double getAlpha(){
		return alpha;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#update()
	 */
	@Override
	public void update() {
		return;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.game.HasGameLoop#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		if(alpha!=1){
			context.save();
			context.setGlobalAlpha(alpha);
			context.drawImage(sheet.getImage(), imgPos.getX(),imgPos.getY(),imgWidth,imgHeight);
			context.restore();
		}else{
			context.drawImage(sheet.getImage(), imgPos.getX(),imgPos.getY(),imgWidth,imgHeight);
		}
		
	}

}
