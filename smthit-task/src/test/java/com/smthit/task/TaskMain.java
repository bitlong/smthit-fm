/**
 * 
 */
package com.smthit.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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
public class TaskMain implements CommandLineRunner {
	private static ConfigurableApplicationContext applicationContext;
	
	private TaskMain() {
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//初始化	
		applicationContext = SpringApplication.run(TaskMain.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		SmthitTaskFactory.setApplicationContext(applicationContext);
		//SmthitTaskFactory.getSmthitTaskFactory().init(applicationContext, sqlManager);		
		TaskExecutorDefine<DemoTaskExecutor> define = new TaskExecutorDefine<DemoTaskExecutor>(DemoTaskExecutor.TASK_KEY, DemoTaskExecutor.class);
		SmthitTaskFactory.getSmthitTaskFactory().registTaskExecutor(define);
	}

}
