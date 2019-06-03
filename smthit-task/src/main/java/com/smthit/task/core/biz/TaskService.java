/**
 * 
 */
package com.smthit.task.core.biz;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.eventbus.Subscribe;
import com.smthit.framework.dal.bettlsql.convert.BeetlTailReformer;
import com.smthit.lang.enums.EnumLogicStatus;
import com.smthit.lang.json.GsonUtil;
import com.smthit.lang2.utils.AssertUtil;
import com.smthit.task.core.dal.dao.TaskDao;
import com.smthit.task.core.dal.entity.TaskPO;
import com.smthit.task.engine.Task;
import com.smthit.task.engine.TaskEventBusFactory;
import com.smthit.task.engine.enums.EnumTaskState;
import com.smthit.task.engine.event.TaskCancelEvent;
import com.smthit.task.engine.event.TaskExecuting;
import com.smthit.task.engine.event.TaskOverEvent;
import com.smthit.task.engine.event.TaskStartEvent;
import com.smthit.task.engine.event.TaskTerminalEvent;
import com.smthit.task.spi.api.ITaskService;
import com.smthit.task.spi.model.TaskVO;

/**
 * @author Bean 任务的业务类
 * @since 1.0.4
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

	/**
	 * 根据任务编号获取一个任务的状态
	 * 
	 * @param taskNo
	 * @return
	 * @since 1.0.5
	 */
	public TaskVO getTask(String taskNo) {
		TaskPO po = taskDao.createLambdaQuery().andEq(TaskPO::getTaskNo, taskNo).single();

		if (po != null) {
			return (new BeetlTailReformer<>(TaskVO.class)).toVO(po);
		}

		return null;
	}

	public void deleteTask(String taskNo) {
		TaskPO po = taskDao.createLambdaQuery().andEq(TaskPO::getTaskNo, taskNo).single();

		AssertUtil.assertNotNull(po, "任务不存在, 任务编号: " + taskNo);
		po.setStatus(EnumLogicStatus.DELETED.getValue());
		taskDao.updateById(po);

	}

	/**
	 * 批量查询某个用户的任务列表
	 * 
	 * @param userId
	 * @param page
	 * @return
	 * @since 1.0.5
	 */
	public PageQuery<TaskVO> pagingTask(Long userId, PageQuery<TaskVO> page) {
		PageQuery<TaskPO> pos = taskDao.createLambdaQuery().andEq(TaskPO::getOwnerUserId, userId).desc(TaskPO::getId)
				.page(page.getPageNumber(), page.getPageSize());
		page.setList(new BeetlTailReformer<TaskPO, TaskVO>(TaskVO.class).toVOs(pos.getList()));
		return page;
	}

	/**
	 * 添加一个任务到数据库中，TaskEngine调用，外部不调用
	 * 
	 * @param data
	 * @since 1.0.4
	 */
	public void addTask(Task data) {

		TaskPO taskPO = new TaskPO();

		taskPO.setTaskNo(data.getTaskNo());
		taskPO.setOwnerUserId(data.getUserId());
		// TODO
		taskPO.setStatus(1);
		taskPO.setTaskType(data.getTaskType().getValue());
		taskPO.setSuccess(false);
		taskPO.setTotalStep(data.getTotalStep());
		taskPO.setCurrentStep(data.getCurrentStep());
		taskPO.setCreatedAt(Calendar.getInstance().getTime());
		taskPO.setUpdatedAt(Calendar.getInstance().getTime());

		taskPO.setName(data.getName());
		taskPO.setTaskState(EnumTaskState.CREATED.getValue());

		taskDao.insertTemplate(taskPO);
	}

	/**
	 * 任务开始时执行
	 * 
	 * @param taskStartEvent
	 */
	@Subscribe
	public void onEvent(TaskStartEvent taskStartEvent) {
		TaskPO taskPO = taskDao.createLambdaQuery().andEq(TaskPO::getTaskNo, taskStartEvent.getTask().getTaskNo())
				.single();

		if (taskPO != null) {
			taskPO.setBeginTime(new Date());
			taskPO.setUpdatedAt(new Date());
			taskPO.setDuration(0L);
			taskPO.setCurrentStep(0);
			taskPO.setTaskState(EnumTaskState.EXECUTING.getValue());
			taskPO.setSuccess(false);
			taskDao.updateById(taskPO);
		}
	}

	@Subscribe
	public void onEvent(TaskOverEvent taskOverEvent) {
		TaskPO taskPO = taskDao.createLambdaQuery().andEq(TaskPO::getTaskNo, taskOverEvent.getTask().getTaskNo())
				.single();

		if (taskPO != null) {
			taskPO.setEndTime(new Date());
			taskPO.setUpdatedAt(new Date());
			taskPO.setDuration(taskPO.getEndTime().getTime() - taskPO.getBeginTime().getTime());
			taskPO.setCurrentStep(taskPO.getTotalStep());
			taskPO.setTaskState(EnumTaskState.OVER.getValue());

			taskPO.setResult(taskOverEvent.getResult());
			taskPO.setExt(GsonUtil.toJson(taskOverEvent.getExt()));

			taskPO.setSuccess(true);
			taskDao.updateById(taskPO);
		}
	}

	@Subscribe
	public void onEvent(TaskExecuting taskExecuting) {
		TaskPO taskPO = taskDao.createLambdaQuery().andEq(TaskPO::getTaskNo, taskExecuting.getTask().getTaskNo())
				.single();

		if (taskPO != null) {
			taskPO.setUpdatedAt(new Date());
			taskPO.setDuration(Calendar.getInstance().getTime().getTime() - taskPO.getBeginTime().getTime());
			taskPO.setCurrentStep(taskExecuting.getCurrentStep());
			taskPO.setTaskState(EnumTaskState.EXECUTING.getValue());
			taskPO.setSuccess(false);
			taskDao.updateById(taskPO);
		}
	}

	@Subscribe
	public void onEvent(TaskCancelEvent taskCancelEvent) {
		TaskPO taskPO = taskDao.createLambdaQuery().andEq(TaskPO::getTaskNo, taskCancelEvent.getTask().getTaskNo())
				.single();

		if (taskPO != null) {
			taskPO.setUpdatedAt(new Date());
			taskPO.setDuration(Calendar.getInstance().getTime().getTime() - taskPO.getBeginTime().getTime());
			taskPO.setTaskState(EnumTaskState.CANCEL.getValue());
			taskPO.setSuccess(false);
			taskDao.updateById(taskPO);
		}
	}

	@Subscribe
	public void onEvent(TaskTerminalEvent taskTerminal) {
		TaskPO taskPO = taskDao.createLambdaQuery().andEq(TaskPO::getTaskNo, taskTerminal.getTask().getTaskNo())
				.single();

		if (taskPO != null) {
			taskPO.setUpdatedAt(new Date());
			taskPO.setDuration(Calendar.getInstance().getTime().getTime() - taskPO.getBeginTime().getTime());
			taskPO.setTaskState(EnumTaskState.TERMINAL.getValue());
			taskPO.setSuccess(false);
			taskDao.updateById(taskPO);
		}
	}
}
