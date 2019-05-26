/**
 * 
 */
package com.smthit.task.engine;

import java.util.Set;

import com.smthit.task.data.Task;

/**
 * @author Bean
 *
 */
public interface TaskExecutor {
	public Set<String> getTaskKeys();
	public void addTaskKey(String key);
	
	public boolean before(TaskContext context, Task task);
	public void run(TaskContext context, Task task);
	public void after(TaskContext context, Task task);
}
