package com.smthit.task.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beetl.sql.core.SQLManager;
import org.springframework.context.ApplicationContext;

import com.smthit.task.SmthitTaskFactory;
import com.smthit.task.core.biz.TaskHistoryService;
import com.smthit.task.core.biz.TaskLogService;
import com.smthit.task.core.biz.TaskMetaService;
import com.smthit.task.core.biz.TaskService;
import com.smthit.task.core.dal.dao.TaskDao;
import com.smthit.task.core.dal.dao.TaskDefineDao;
import com.smthit.task.core.dal.dao.TaskHistoryDao;
import com.smthit.task.core.dal.dao.TaskLogDao;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Bean
 * @since 1.0.4
 */
public class TaskEngine {
	@Setter
	private SmthitTaskFactory smthitTaskFactory;
	
	@Setter
	private SQLManager sqlManager;
	@Setter
	private ApplicationContext applicationContext;
	@Setter
	private TaskExecuteEngine taskExecuteEngine;
	@Getter
	private TaskService taskService;
	@Getter
	private TaskLogService taskLogService;
	@Getter
	private TaskHistoryService taskHistoryServcie;
	@Getter
	private TaskMetaService taskMetaService;
	
	
	Map<String, List<TaskExecutorDefine<?>>> taskExecutors = new HashMap<>(50);
	
	public TaskEngine(SQLManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	public void init() {
		//注册相关的Dao
		initDao();
		initService();
	}	
	
	private void initDao() {
		//注册相关的Dao, 将来可以用目录扫描自动装载的方式
		TaskDao taskDao = sqlManager.getMapper(TaskDao.class);
		TaskDefineDao taskDefineDao = sqlManager.getMapper(TaskDefineDao.class);
		TaskHistoryDao taskHistoryDao = sqlManager.getMapper(TaskHistoryDao.class);
		TaskLogDao taskLogDao = sqlManager.getMapper(TaskLogDao.class);
		
		applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(taskDao, TaskDao.class.getName());
		applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(taskDefineDao, TaskDefineDao.class.getName());
		applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(taskHistoryDao, TaskHistoryDao.class.getName());
		applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(taskLogDao, TaskLogDao.class.getName());
		
		applicationContext.getAutowireCapableBeanFactory().autowireBean(taskDao);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(taskDefineDao);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(taskHistoryDao);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(taskLogDao);
	}	
	
	private void initService() {
		//注册Service
		taskService = new TaskService();
		applicationContext.getAutowireCapableBeanFactory().autowireBean(taskService);

		taskLogService = new TaskLogService();
		applicationContext.getAutowireCapableBeanFactory().autowireBean(taskLogService);
		
		taskHistoryServcie = new TaskHistoryService();
		applicationContext.getAutowireCapableBeanFactory().autowireBean(taskHistoryServcie);
		
		taskMetaService = new TaskMetaService();
		applicationContext.getAutowireCapableBeanFactory().autowireBean(taskMetaService);
	}
	
	/**
	 * 注册任务, 检查任务是否在数据库中存在，不存在则注册改任务
	 * @since 1.0.4
	 */
	public void registIfNotExist(TaskExecutorDefine<?> executor) {
		
		Set<String> taskKeys = executor.getTaskKeys();
		if(taskKeys == null || taskKeys.size() == 0) {
			return;
		}
		
		taskKeys.forEach(key -> {
			List<TaskExecutorDefine<?>> executors = taskExecutors.get(key);
			if(executors == null) {
				executors = new ArrayList<>();
				taskExecutors.put(key, executors);
			}
			
			if(!executors.contains(executor)) {
				executors.add(executor);
			}
		});
		
	}

	/**
	 * 获取可用的执行器
	 * @param taskKey
	 * @return
	 * @since 1.0.4
	 */
	public List<AbstractTaskExecutor> getAvailableTaskExecutors(String taskKey) {
		List<TaskExecutorDefine<?>> taskExecutorDefines = taskExecutors.get(taskKey);
		if(taskExecutorDefines == null) {
			return Collections.emptyList();
		}
		
		List<TaskExecutor> result = new ArrayList<>();
		
		taskExecutorDefines.forEach(d -> {
			result.add(d.createTaskExecutor());
		});
		
		
		return null;
	}
}
