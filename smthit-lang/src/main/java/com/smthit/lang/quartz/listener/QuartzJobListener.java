/**
 * 
 */
package com.smthit.lang.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bean
 *
 */
public class QuartzJobListener implements JobListener {
	private static Logger logger = LoggerFactory.getLogger(QuartzJobListener.class);

	/**有s
	 * 
	 */
	public QuartzJobListener() {
	}

	@Override
	public String getName() {
		return "QuartzJobListener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		logger.info("开始执行:" + context.getJobDetail().getKey().getName());
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {

	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		logger.info("执行完成:" + context.getJobDetail().getKey().getName() + ", " + context.getJobRunTime());
	}

}
