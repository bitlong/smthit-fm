package com.smthit.task.engine;

import org.beetl.sql.core.SQLManager;
import org.springframework.context.ApplicationContext;

import com.smthit.task.SmthitTaskFactory;
import com.smthit.task.biz.TaskHistoryService;
import com.smthit.task.biz.TaskLogService;
import com.smthit.task.biz.TaskMetaService;
import com.smthit.task.biz.TaskService;
import com.smthit.task.dal.dao.TaskDao;
import com.smthit.task.dal.dao.TaskDefineDao;
import com.smthit.task.dal.dao.TaskHistoryDao;
import com.smthit.task.dal.dao.TaskLogDao;
import com.smthit.task.data.Task;
import com.smthit.task.data.TaskDefine;

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
	public void registIfNotExist(TaskDefine taskDefine) {
		
	}
	
	/**
	 * 申请开始一个任务
	 * @param key
	 * @return taskNo 唯一标识一个任务，应用层需要保留以跟踪
	 */
	public Task applyTask(String taskKey) {
		return new Task();
	}
	
	/**
	 * 查询任务的执行状态
	 * @param taskNo
	 */
	public void lookupTaskState(String taskNo) {
		
	}
	
	/**
	 * 获取队列的长度
	 * @return
	 */
	public int getTaskQueueLength() {
		return 0;
	}
}
