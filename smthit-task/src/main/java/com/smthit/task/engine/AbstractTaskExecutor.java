/**
 * 
 */
package com.smthit.task.engine;

import java.util.HashSet;
import java.util.Set;

import com.smthit.task.data.Task;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 */
@Slf4j
public abstract class AbstractTaskExecutor implements TaskExecutor {
	@Getter
	@Setter
	private TaskContext context;
	@Getter
	@Setter
	private Task task;
	
	private Set<String> taskKeys = new HashSet<String>();
	@Getter
	private int totalStep;
	@Getter
	private int currentStep;
	
	public AbstractTaskExecutor() {
	}
	
	public AbstractTaskExecutor(TaskContext context, Task task) {
		this.context = context;
		this.task = task;
	} 
	
	@Override
	public Set<String> getTaskKeys() {
		return taskKeys;
	}

	@Override
	public void addTaskKey(String key) {
		if(taskKeys.contains(key)) {
			return;
		}
		
		taskKeys.add(key);
	}

	@Override
	public boolean before(TaskContext context, Task task) {
		try {
			return doBefore(context, task);
		} catch(Exception exp) {
			log.error(exp.getMessage(), exp);
		}
		
		return false;
	}

	@Override
	public void run(TaskContext context, Task task) {
		try {
			before(context, task);
			doRun(context, task);
		} catch(Exception exp) {
			log.error(exp.getMessage(), exp);
			TaskException taskException = new TaskException(exp.getMessage(), exp);
			onError(context, task, taskException);
		} finally {
			after(context, task);
		}
	}

	
	@Override
	public void after(TaskContext context, Task task) {
		doAfter(context, task);
		//TODO处理任务的完成状态
	}
	
	/**
	 * 当前进度更新
	 * @param currentStep
	 */
	protected void notifyProcess(Integer currentStep) {
		this.currentStep = currentStep;
		//TODO
	}
	
	/**
	 * 设置总进度
	 * @param totalStep
	 */
	protected void setTotalProcess(Integer totalStep) {
		this.totalStep = totalStep;
		//TODO 
	}
	
	public abstract boolean doAfter(TaskContext context, Task task); 
	public abstract boolean doBefore(TaskContext context, Task task);
	public abstract boolean doRun(TaskContext context, Task task);
	public abstract boolean onError(TaskContext context, Task task, TaskException exception);
}
