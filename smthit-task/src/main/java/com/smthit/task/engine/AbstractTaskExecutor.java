/**
 * 
 */
package com.smthit.task.engine;

import com.smthit.task.engine.event.TaskEvent;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 */
@Data
@Slf4j
public abstract class AbstractTaskExecutor implements TaskExecutor {
	@Getter
	private TaskContext context;
	
	@Getter
	private Task task;
	
	@Getter
	private int totalStep;

	private int currentStep;
	
	public AbstractTaskExecutor(TaskContext context, Task task) {
		this.context = context;
		this.task = task;
	} 
	
	@Override
	public boolean before() {
		try {
			return doBefore(context);
		} catch(Exception exp) {
			log.error(exp.getMessage(), exp);
		}
		
		return false;
	}

	@Override
	public void run() {
		try {
			before();
			doRun(context);
		} catch(Exception exp) {
			log.error(exp.getMessage(), exp);
			TaskException taskException = new TaskException(exp.getMessage(), exp);
			onError(context, taskException);
		} finally {
			after();
		}
	}

	
	@Override
	public void after() {
		doAfter(context);
		//TODO处理任务的完成状态
	}
	
	/**
	 * 当前进度更新
	 * @param currentStep
	 */
	protected void notifyProcess(Integer currentStep) {
		this.currentStep = currentStep;

		TaskEvent taskEvent = new TaskEvent();
		taskEvent.setTask(task);
		
		//TODO
		
		TaskEventBusFactory.getTaskEventBus().post(taskEvent);
	}
	
	public abstract boolean doAfter(TaskContext context); 
	public abstract boolean doBefore(TaskContext context);
	public abstract boolean doRun(TaskContext context);
	public abstract boolean onError(TaskContext context, TaskException exception);
}
