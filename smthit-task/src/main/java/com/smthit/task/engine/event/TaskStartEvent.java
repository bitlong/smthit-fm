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
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskStartEvent extends TaskEvent {
	
	public TaskStartEvent(TaskContext taskContext, Task task) {
		this.task = task;
		this.taskContext = taskContext;
	}

}
