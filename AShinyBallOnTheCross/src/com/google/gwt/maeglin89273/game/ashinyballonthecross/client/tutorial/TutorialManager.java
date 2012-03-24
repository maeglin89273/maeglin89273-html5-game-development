/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component.BlueMark;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component.NextStepButton;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component.StepBoard;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component.StepComponent;
import com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial.component.TasksList;
import com.google.gwt.maeglin89273.game.mengine.asset.JsonFile;
import com.google.gwt.maeglin89273.game.mengine.physics.Point;

/**
 * @author Maeglin Liao
 *
 */
public class TutorialManager{

	private final TaskDetector detector;
	private int stepIndex=-1;
	
	private final StepBoard board;
	
	private final StepComponent[] taskCmpts;
	private static final int BUTTON_INDEX=0,LIST_INDEX=1,MARK_INDEX=2;
	
	private Step[] steps;
	public TutorialManager(JsonFile configs,double gameWidth,double gameHeight,int markNum,TaskHandler[] handlers){
		this.detector=new TaskDetector(this);
		
		this.board=new StepBoard(new Point(10,10),gameWidth/2);
		
		this.taskCmpts=new StepComponent[2+(markNum<1?1:markNum)];
		this.taskCmpts[BUTTON_INDEX]=new NextStepButton(new Point(gameWidth-130,gameHeight-60),this);
		this.taskCmpts[LIST_INDEX]=new TasksList(gameWidth-185,gameHeight);	
		for(int i=2;i<this.taskCmpts.length;i++){
			this.taskCmpts[i]=new BlueMark();
		}
		
		this.parseSteps(configs,handlers);
	}
	public void startTutorial(){
		toNextStep();
	}
	private void parseSteps(JsonFile jsonConfigs,TaskHandler[] handlers){
		JSONArray stepConfigs=jsonConfigs.getJsonValue().isObject().get("tutorial_steps").isArray();
		this.steps=new Step[stepConfigs.size()];
		JSONObject jsonStep;
		for(int i=0;i<steps.length;i++){
			jsonStep=stepConfigs.get(i).isObject();
			this.steps[i]=new Step(jsonStep.get(Step.TITLE_KEY).isString().stringValue(),
					(int)jsonStep.get(Step.CORNER_X_KEY).isNumber().doubleValue(), 
					(int)jsonStep.get(Step.CORNER_Y_KEY).isNumber().doubleValue(),
					parseTasks(jsonStep), handlers[i]);
		}
	}
	private TaskBuilder[] parseTasks(JSONObject jsonStep) {
		JSONObject jsonTasks=jsonStep.get(TaskBuilder.TASKS_KEY).isObject();
		TaskBuilder[] builders=new TaskBuilder[jsonTasks.size()];
		int i=0;
		if(jsonTasks.containsKey(TaskBuilder.ButtonBuilder.BUTTON_KEY)){
			builders[i++]=new TaskBuilder.ButtonBuilder();
		}
		
		if(jsonTasks.containsKey(TaskBuilder.BlueMarkBuilder.MARK_KEY)){
			JSONObject jsonMark=jsonTasks.get(TaskBuilder.BlueMarkBuilder.MARK_KEY).isObject();
			builders[i++]=new TaskBuilder.BlueMarkBuilder((int) jsonMark.get(TaskBuilder.BlueMarkBuilder.INDEX_KEY).isNumber().doubleValue(),
														  jsonMark.get(TaskBuilder.BlueMarkBuilder.X_KEY).isNumber().doubleValue(),
														  jsonMark.get(TaskBuilder.BlueMarkBuilder.Y_KEY).isNumber().doubleValue(),
														  jsonMark.get(TaskBuilder.BlueMarkBuilder.ANGLE_KEY).isNumber().doubleValue());
		}
		
		if(jsonTasks.containsKey(TaskBuilder.TaskListBuilder.LIST_KEY)){
			JSONObject jsonList=jsonTasks.get(TaskBuilder.TaskListBuilder.LIST_KEY).isObject();
			builders[i++]=new TaskBuilder.TaskListBuilder((int)jsonList.get(TaskBuilder.TaskListBuilder.HEIGHT_KEY).isNumber().doubleValue(),
														  (int)jsonList.get(TaskBuilder.TaskListBuilder.Y_KEY).isNumber().doubleValue(),
														  (int)jsonList.get(TaskBuilder.TaskListBuilder.NUM_KEY).isNumber().doubleValue());
		}
		return builders;
	}

	public TaskDetector getDetector(){
		return detector;
	}
	
	public int getStepIndex(){
		return stepIndex;
	}
	public StepBoard getStepBoard(){
		return board;
	}
	public NextStepButton getButton(){
		return (NextStepButton) this.taskCmpts[BUTTON_INDEX];
	}
	public TasksList getTasksList(){
		return (TasksList) this.taskCmpts[LIST_INDEX];
	}
	public BlueMark getBlueMark(int index){
		return (BlueMark) this.taskCmpts[MARK_INDEX+index];
	}
	public void handInEvidence(Object evidence){
		if(steps[getStepIndex()].getTaskHandler().handleTask(evidence,this)&&getStepIndex()+1<steps.length){
			toNextStep();
		}
	}
	private void toNextStep(){
		stepIndex++;
		Step step=steps[stepIndex];
		getStepBoard().nextStep(step.getTitle(),
								step.getBoardCornerX(),
								step.getBoardCornerY());
		for(StepComponent c:taskCmpts){
			c.setEnabled(false);
		}
		
		for(TaskBuilder builder:step.getTaskBuilder()){
			builder.setupTask(this);
		}
	}
	
}
