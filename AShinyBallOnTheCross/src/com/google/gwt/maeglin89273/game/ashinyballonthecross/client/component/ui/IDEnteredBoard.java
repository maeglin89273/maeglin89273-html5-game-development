/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.component.ui;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.ASBOTXGame;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.utility.ASBOTXConfigs;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.CreateStatus;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.shared.Player;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteBlock;
import com.google.gwt.maeglin89273.game.mengine.component.GameLabel;
import com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent;
import com.google.gwt.maeglin89273.game.mengine.component.button.BoxButton;
import com.google.gwt.maeglin89273.game.mengine.component.button.GameButton;
import com.google.gwt.maeglin89273.game.mengine.core.MEngine;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Maeglin Liao
 *
 */
public class IDEnteredBoard extends GeneralComponent implements KeyPressHandler,KeyDownHandler{
	private StringBuffer sb=new StringBuffer();
	
	private SpriteBlock block;
	
	private SubmitButton button;
	private GameLabel idLabel;
	private GameLabel messageLabel;
	/**
	 * @param p
	 * @param w
	 * @param h
	 */
	public IDEnteredBoard(Point p,CloseTask task) {
		super(p, 450, 150);
		block=new SpriteBlock(0,500+SpriteBlock.SPACING,720,240,MEngine.getAssetManager().getSpriteSheet("images/boards.png"));
		button=new SubmitButton(getPositionAt(PositionType.SOUTH),task);
		idLabel=new GameLabel(new Point(getX(),getY()), TextAlign.CENTER, TextBaseline.MIDDLE, "", ASBOTXConfigs.Color.BLACK, ASBOTXConfigs.getCGFont(28));
		messageLabel=new GameLabel(new Point(getX(),getBottomY()-40), TextAlign.CENTER, TextBaseline.MIDDLE, "", ASBOTXConfigs.Color.ORANGE, ASBOTXConfigs.getCGBoldFont(12));
	}
	@Override
	public void onKeyPress(KeyPressEvent event) {
		char c=event.getCharCode();
		if(((c>='!'&&c<='~')||(c==' '&&sb.length()>0))&&sb.length()<15){
			idLabel.setText(sb.append(c).toString());
			button.setEnabled(true);
		}
		
	}
	@Override
	public void onKeyDown(KeyDownEvent event) {
		if(event.getNativeKeyCode()==KeyCodes.KEY_BACKSPACE){
			idLabel.setText(sb.deleteCharAt(sb.length()-1).toString());
			if(sb.length()==0){
				button.setEnabled(false);
			}else{
				button.setEnabled(true);
			}
			event.preventDefault();
			event.stopPropagation();
		}
		
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	public GameButton getButton(){
		return button;
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.maeglin89273.game.mengine.component.GeneralComponent#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	@Override
	public void draw(Context2d context) {
		context.drawImage(block.getSheetImage(),
				block.getX(), block.getY(), block.getWidth(), block.getHeight(),
				getLeftX(), getTopY(), getWidth(), getHeight());
		idLabel.draw(context);
		messageLabel.draw(context);
		button.draw(context);
	}
	private class SubmitButton extends BoxButton{
		private boolean enabled=false;
		private final CloseTask task;
		public SubmitButton(Point p,CloseTask task) {
			super(p, 65, 50,new SpriteBlock(910,100+SpriteBlock.SPACING,130,100,ASBOTXConfigs.Utility.getButtonsSpriteSheet()));
			this.task=task;
		}
		public void setEnabled(boolean enabled){
			this.enabled=enabled;
			spriteBlock.setY(enabled?0:100+SpriteBlock.SPACING);
		}
		@Override
		public void doTask() {
			
			if(enabled){
				setEnabled(false);
				final ASBOTXGame game=(ASBOTXGame)MEngine.getGeneralGame();
				Player player=game.getLocalPlayer().getPlayer();
				player.setID(sb.toString().trim());
				game.getPlayerService().createNewPlayer(player, new AsyncCallback<CreateStatus>(){
	
					@Override
					public void onFailure(Throwable caught) {
						messageLabel.setText("an Error occured. Please contact the developer about this error");
						setEnabled(true);
					}
	
					@Override
					public void onSuccess(CreateStatus result) {
						messageLabel.setText(result.getMessage());
						if(result==CreateStatus.SUCCESS){
							game.getLocalPlayer().setID(sb.toString());
							task.doTask();
						}else if(result!=CreateStatus.DUPLICATED){
							setEnabled(true);
						}
					}
					
				});
			}
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public interface CloseTask{
		public abstract void doTask();
	}
	
}
