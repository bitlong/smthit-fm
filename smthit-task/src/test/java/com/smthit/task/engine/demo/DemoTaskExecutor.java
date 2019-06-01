/**
 * 
 */
package com.smthit.task.engine.demo;

import com.smthit.task.engine.AbstractTaskExecutor;
import com.smthit.task.engine.TaskContext;
import com.smthit.task.engine.TaskException;

/**
 * @author Bean
 *
 */
public class DemoTaskExecutor extends AbstractTaskExecutor {
	public final static String TASK_KEY = "DemoTask";

	public DemoTaskExecutor() {
	}

	@Override
	public boolean doBefore(TaskContext context) {
		//初始化目标步数
		this.setTotalStep(10);
		//初始化执行的条件
		
		return true;
	}
	@Override
	public boolean doRun(TaskContext context) {
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
	public boolean doAfter(TaskContext context) {
		return true;
	}

	@Override
	public boolean onError(TaskContext context, TaskException exception) {
		return false;
	}
}
