package com.smthit.task.engine;

import lombok.Data;

@Data
public class TaskContext {
	private int currentStep;
	private int totalStep;
	private TaskException exception;
	private boolean succes;
	
	public TaskContext() {
	}

}
