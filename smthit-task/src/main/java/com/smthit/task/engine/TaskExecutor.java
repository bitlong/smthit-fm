/**
 * 
 */
package com.smthit.task.engine;

/**
 * @author Bean
 * 任务执行期
 * @since 1.0.0
 */
public interface TaskExecutor {
	
	
	/**
	 * 任务执行前的预处理工作
	 * @param context
	 * @return
	 * @since 1.0.4
	 */
	boolean before();
	
	/**
	 * 任务执行中的处理
	 * @param context
	 * @since 1.0.4
	 */
	void run();
	
	/**
	 * 任务执行后的处理
	 * @param context
	 * @since 1.0.4
	 */
	void after();
}
