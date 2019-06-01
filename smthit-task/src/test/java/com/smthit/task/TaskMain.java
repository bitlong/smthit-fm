/**
 * 
 */
package com.smthit.task;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.smthit.task.engine.TaskExecutorDefine;
import com.smthit.task.engine.demo.DemoTaskExecutor;

/**
 * @author Bean
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.smthit.task"})
@EnableTransactionManagement(proxyTargetClass = true)
public class TaskMain {
	
	public TaskMain() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//初始化		
		//SmthitTaskFactory.getSmthitTaskFactory().init(applicationContext, sqlManager);
		
		TaskExecutorDefine<DemoTaskExecutor> define = new TaskExecutorDefine<DemoTaskExecutor>(DemoTaskExecutor.TASK_KEY, DemoTaskExecutor.class);
		SmthitTaskFactory.getSmthitTaskFactory().registTaskExecutor(define);
	}

}
