/**
 * 
 */
package com.smthit.lang.quartz;

import java.util.HashSet;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public class QuartzManager {
	private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);

	private Scheduler scheduler;

	public QuartzManager() {
	}

	/**
	 * 添加Job
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @param jobClass
	 * @param cron
	 */
	public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass, String cron) {

		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		triggerBuilder.withIdentity(triggerName, triggerGroupName);
		triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));

		triggerBuilder.startNow();

		CronTrigger trigger = (CronTrigger) triggerBuilder.build();

		try {
			//scheduler.addJob(jobDetail, true);
			scheduler.scheduleJob(jobDetail, trigger);
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}

		} catch (Exception exp) {
			logger.error("QuartzManager Add Job Failed! " + exp.getMessage());
		}
	}

	/**
	 * 修改Job的调度时间
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @param cron
	 */
	public void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron) {
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			if (trigger == null)
				return;

			String oldTime = trigger.getCronExpression();

			if (!oldTime.equalsIgnoreCase(cron)) {
				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
				// 触发器名,触发器组
				triggerBuilder.withIdentity(triggerName, triggerGroupName);
				triggerBuilder.startNow();
				// 触发器时间设定
				triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
				// 创建Trigger对象
				trigger = (CronTrigger) triggerBuilder.build();
				// 方式一 ：修改一个任务的触发时间
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (SchedulerException e) {
			throw new ServiceException("Modify Job Time Failed!" + e.getMessage());
		}
	}

	/**
	 * 移除调度任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 */
	public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 移除触发器
			scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
		} catch (SchedulerException e) {
			throw new ServiceException("Remove Job Failed!" + e.getMessage());
		}
	}

	public JobDetail getJob(String jobName, String jobGroupNmae) {
		try {
			return scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupNmae));
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public Set<JobKey> getJobByGroupName(String jobGroupName) {
		GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupContains(jobGroupName);
		
		Set<JobKey> jobKeys = new HashSet<JobKey>();
		
		try {
			jobKeys = scheduler.getJobKeys(matcher);
		} catch (SchedulerException exp) {
			logger.error(exp.getMessage(), exp);
		}
		
		return jobKeys;
	}
	
	public void startJobs() {
		try {
			scheduler.start();
		} catch (Exception exp) {
			logger.error("QuartzManager Start Job Failed! " + exp.getMessage());
		}
	}

	public void shutdownJobs() {
		try {
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (Exception exp) {
			logger.error("QuartzManager Start Job Failed! " + exp.getMessage());
		}
	}

	/**
	 * @param scheduler
	 *            the scheduler to set
	 */
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

}
