/**
 * 
 */
package com.smthit.lang.logger;

/**
 * @author Bean
 *
 */
public interface LoggerPolicy {
	public String getPolicyName();
	public String getLoggerName(String baseDir, String bizNo);
}
