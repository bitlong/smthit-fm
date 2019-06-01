package com.smthit.task.engine.event;

import com.smthit.task.engine.Task;
import com.smthit.task.engine.TaskContext;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Bean
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskOverEvent extends TaskEvent {
	public TaskOverEvent(TaskContext taskContext, Task task) {
		this.task = task;
		this.taskContext = taskContext;
	}

}
