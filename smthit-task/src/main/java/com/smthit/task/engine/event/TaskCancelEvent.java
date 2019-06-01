/**
 * 
 */
package com.smthit.task.engine.event;

import com.smthit.task.engine.Task;
import com.smthit.task.engine.TaskContext;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Bean
 * 任务的取消事件
 * @since 1.0.4
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskCancelEvent extends TaskEvent {
	
	public TaskCancelEvent(TaskContext taskContext, Task task) {
		this.task = task;
		this.taskContext = taskContext;
	}
}
