/**
 * 
 */
package com.smthit.task.engine;

import com.smthit.task.engine.event.TaskExecuting;
import com.smthit.task.engine.event.TaskOverEvent;
import com.smthit.task.engine.event.TaskStartEvent;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 */
@Data
@Slf4j
public abstract class AbstractTaskExecutor implements TaskExecutor {
	private TaskContext context;
	private Task task;
	private int totalStep;
	private int currentStep;

	public AbstractTaskExecutor() {}
	
	@Override
	public boolean before() {
		try {
			TaskStartEvent taskEvent = new TaskStartEvent(context, task);
			TaskEventBusFactory.getTaskEventBus().post(taskEvent);
			
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
		TaskOverEvent taskEvent = new TaskOverEvent(context, task);
		
		taskEvent.setResult(context.getResult());
		taskEvent.setExt(context.getExt());
		
		TaskEventBusFactory.getTaskEventBus().post(taskEvent);
	}
	
	/**
	 * 当前进度更新
	 * @param currentStep
	 */
	protected void notifyProcess(Integer currentStep) {
		this.currentStep = currentStep;

		TaskExecuting taskEvent = new TaskExecuting(context, task);
		taskEvent.setCurrentStep(currentStep);
		TaskEventBusFactory.getTaskEventBus().post(taskEvent);
	}
	
	public abstract boolean doAfter(TaskContext context); 
	public abstract boolean doBefore(TaskContext context);
	public abstract boolean doRun(TaskContext context);
	public abstract boolean onError(TaskContext context, TaskException exception);
}
