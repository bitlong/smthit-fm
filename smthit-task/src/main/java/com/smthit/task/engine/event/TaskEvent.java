/**
 * 
 */
package com.smthit.task.engine.event;

import com.smthit.task.engine.Task;
import com.smthit.task.engine.TaskContext;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
public class TaskEvent {

	/**
	 * 任务上下文
	 */
	protected TaskContext taskContext;
	
	/**
	 * 当前执行的任务
	 */
	protected Task task;
}
