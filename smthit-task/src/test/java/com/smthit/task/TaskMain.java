/**
 * 
 */
package com.smthit.task;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
	}

}
