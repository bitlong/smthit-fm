package com.smthit.task.engine;

import java.util.HashMap;
import java.util.Map;

import com.smthit.task.engine.enums.EnumTaskState;

import lombok.Data;

@Data
public class TaskContext {
	/**
	 * 所带有的参数
	 */
	private Map<String, Object> params;
	
	private int currentStep;
	private int totalStep;
	private TaskException exception;
	private boolean succes;
	private Task task;

	/**
	 * 任务的编号
	 */
	private String taskNo;
	
	/**
	 * 任务执行状态
	 */
	private EnumTaskState taskState;
	
	private String result;
	private Map<String, Object> ext = new HashMap<>();
	
	public TaskContext() {
	}
}
