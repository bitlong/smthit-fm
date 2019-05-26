/**
 * 
 */
package com.smthit.task.engine.demo;

import com.smthit.task.data.Task;
import com.smthit.task.engine.AbstractTaskExecutor;
import com.smthit.task.engine.TaskContext;
import com.smthit.task.engine.TaskException;

/**
 * @author Bean
 *
 */
public class DemoTaskExecutor extends AbstractTaskExecutor {

	@Override
	public boolean doBefore(TaskContext context, Task task) {
		//初始化目标步数
		setTotalProcess(10);
		//初始化执行的条件
		
		return true;
	}
	@Override
	public boolean doRun(TaskContext context, Task task) {
		int currentStep = 1;
		
		notifyProcess(currentStep);
		while(true) {
			currentStep++;
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				break;
			}
			
			notifyProcess(currentStep);
			if(currentStep >= 10) {
				break;
			}
		}
		
		return true;
	}

	@Override
	public boolean doAfter(TaskContext context, Task task) {
		return true;
	}

	@Override
	public boolean onError(TaskContext context, Task task, TaskException exception) {
		return false;
	}
}
