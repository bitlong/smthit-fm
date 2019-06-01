/**
 * 
 */
package com.smthit.task.engine;

import com.google.common.eventbus.EventBus;

/**
 * @author Bean
 * 任务事件工厂类
 * @since 1.0.0
 */
public class TaskEventBusFactory {
	private static EventBus eventBus = new EventBus("TaskExecuteBus");
	
	private TaskEventBusFactory() { 
	}
	
	public static EventBus getTaskEventBus() {
		return eventBus;
	}
}
