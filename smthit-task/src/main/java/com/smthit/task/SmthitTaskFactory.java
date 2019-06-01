/**
 * 
 */
package com.smthit.task;

import java.util.List;

import org.beetl.sql.core.SQLManager;
import org.springframework.context.ApplicationContext;

import com.smthit.task.core.biz.TaskService;
import com.smthit.task.engine.AbstractTaskExecutor;
import com.smthit.task.engine.TaskEngine;
import com.smthit.task.engine.TaskExecutorDefine;

import lombok.Setter;

/**
 * @author Bean
 * @since 1.0.4
 */
public class SmthitTaskFactory {
	private static SmthitTaskFactory smithTaskFactory;
	
	private TaskEngine taskEngine;
	
	@Setter
	private static ApplicationContext applicationContext;
	
	public static SmthitTaskFactory getSmthitTaskFactory() {
		return smithTaskFactory;
	}
	
	/**
	 * 初始化任务执行器
	 * @param applicationContext
	 * @param sqlManager
	 * @since 1.0.4
	 */
	public void init(ApplicationContext applicationContext, SQLManager sqlManager) {
		taskEngine = new TaskEngine(sqlManager);
		taskEngine.setApplicationContext(applicationContext);
		taskEngine.setSmthitTaskFactory(this);
		
		taskEngine.init();
	}
	
	public TaskService getTaskService() {
		return taskEngine.getTaskService();
	}
	
	public TaskEngine getTaskEngine() {
		return taskEngine;
	}
	
	public void registTaskExecutor(TaskExecutorDefine<?> taskExecutorDefine) {
		taskEngine.registIfNotExist(taskExecutorDefine);
	}
	
	public List<AbstractTaskExecutor> getAvailableTaskExecutors(String taskKey) {
		return taskEngine.getAvailableTaskExecutors(taskKey);
	} 
}
