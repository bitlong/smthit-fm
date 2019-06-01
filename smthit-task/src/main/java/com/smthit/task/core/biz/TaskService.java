/**
 * 
 */
package com.smthit.task.core.biz;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.eventbus.Subscribe;
import com.smthit.task.core.dal.dao.TaskDao;
import com.smthit.task.engine.Task;
import com.smthit.task.engine.TaskEventBusFactory;
import com.smthit.task.engine.event.TaskCancelEvent;
import com.smthit.task.engine.event.TaskExecuting;
import com.smthit.task.engine.event.TaskOverEvent;
import com.smthit.task.engine.event.TaskStartEvent;
import com.smthit.task.engine.event.TaskTerminalEvent;
import com.smthit.task.spi.api.ITaskService;

/**
 * @author Bean
 * 任务的业务类
 * @since 1.0.0
 */
@Service
@Transactional
public class TaskService implements ITaskService {
	@Autowired
	private TaskDao taskDao;
	
	public TaskService() {
		
	}
	
	@PostConstruct
	public void init() {
		TaskEventBusFactory.getTaskEventBus().register(this);
	}
	
	@PreDestroy
	public void destory() {
		TaskEventBusFactory.getTaskEventBus().unregister(this);
	}

	public void addTask(Task data) {		
	}
	
	/**
	 * 任务开始时执行
	 * @param taskStartEvent
	 */
	@Subscribe
	public void onEvent(TaskStartEvent taskStartEvent) {
	}
	
	@Subscribe
	public void onEvent(TaskOverEvent taskOverEvent) {
	}
	
	@Subscribe
	public void onEvent(TaskExecuting taskExecuting) {
	}
	
	@Subscribe
	public void onEvent(TaskCancelEvent taskExecuting) {
	}
	
	@Subscribe
	public void onEvent(TaskTerminalEvent taskExecuting) {
	}
}
