/**
 * 
 */
package com.smthit.lang.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bean
 *
 */
public class QuartzTriggerListener implements TriggerListener {
	private static Logger logger = LoggerFactory.getLogger(QuartzTriggerListener.class);
	
	public QuartzTriggerListener() {
	}

	@Override
	public String getName() {
		return "QuartzTriggerListener";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		logger.info("触发任务:" + context.getJobDetail().getKey().getName() + ", " + context.getJobRunTime());
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
		logger.info("完成任务:" + context.getJobDetail().getKey().getName() + ", " + context.getJobRunTime());
	}

}
