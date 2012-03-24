/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.tutorial;


/**
 * @author Maeglin Liao
 *
 */
public class TaskDetector{
	private final TutorialManager manager;
	public TaskDetector(TutorialManager manager){
		this.manager=manager;
	}
	
	public void detect(int stepIndex,Object evidence){
		if(stepIndex==manager.getStepIndex()){
			manager.handInEvidence(evidence);
		}
	}
}
