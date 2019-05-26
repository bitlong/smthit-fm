/**
 * 
 */
package com.smthit.task;

import org.beetl.sql.core.SQLManager;
import org.springframework.context.ApplicationContext;

import com.smthit.task.biz.TaskService;
import com.smthit.task.data.TaskDefine;
import com.smthit.task.engine.TaskEngine;

import lombok.Setter;

/**
 * @author Bean
 *
 */
public class SmthitTaskFactory {
	private static SmthitTaskFactory smithTaskFactory;
	
	private TaskEngine taskEngine;
	@Setter
	private static ApplicationContext applicationContext;
	
	public static SmthitTaskFactory getSmthitTaskFactory() {
		return smithTaskFactory;
	}
	
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
	
	public void registTaskDefine(TaskDefine taskDefine) {
		taskEngine.registIfNotExist(taskDefine);
	}
}
