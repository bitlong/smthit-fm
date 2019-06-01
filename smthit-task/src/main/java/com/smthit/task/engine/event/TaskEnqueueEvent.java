/**
 * 
 */
package com.smthit.task.engine.event;

import com.smthit.task.engine.Task;
import com.smthit.task.engine.TaskContext;

/**
 * @author Bean
 * @since 1.0.4
 */
public class TaskEnqueueEvent extends TaskEvent {
	public TaskEnqueueEvent(TaskContext taskContext, Task task) {
		this.task = task;
		this.taskContext = taskContext;
	}
}
