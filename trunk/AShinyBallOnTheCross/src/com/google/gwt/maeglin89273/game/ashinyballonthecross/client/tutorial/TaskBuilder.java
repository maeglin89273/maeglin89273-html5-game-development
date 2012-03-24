/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial;

import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public interface TaskBuilder{
	public static final String TASKS_KEY="tasks";
	
	public void setupTask(TutorialManager manager);
	
	public static class ButtonBuilder implements TaskBuilder{
		public static final String BUTTON_KEY="button";
		
		@Override
		public void setupTask(TutorialManager manager) {
			manager.getButton().setEnabled(true);
		}
		
	}
	public static class TaskListBuilder implements TaskBuilder{
		public static final String LIST_KEY="list";
		
		public static final String Y_KEY="y";
		public static final String HEIGHT_KEY="h";
		public static final String NUM_KEY="num";
		
		private final int h;
		private final int blockY;
		private final int checkBoxesNum;
		
		public TaskListBuilder(int h,int blockY,int checkBoxesNum){
			this.h = h;
			this.blockY = blockY;
			this.checkBoxesNum = checkBoxesNum;
			
		}
		@Override
		public void setupTask(TutorialManager manager) {
			manager.getTasksList().setup(h, blockY, checkBoxesNum);
		}
		
	}
	public static class BlueMarkBuilder implements TaskBuilder{
		public static final String MARK_KEY="mark";
		
		public static final String INDEX_KEY="index";
		public static final String X_KEY="x";
		public static final String Y_KEY="y";
		public static final String ANGLE_KEY="angle";
		
		private final double x;
		private final double y;
		private final double angle;
		private final int index;
		public BlueMarkBuilder(int index,double x,double y, double angle){
			this.index = index;
			this.x = x;
			this.y = y;
			this.angle = angle;
			
		}
		@Override
		public void setupTask(TutorialManager manager) {
			manager.getBlueMark(index).setup(new Point(x, y), angle);
		}
		
	}
}
