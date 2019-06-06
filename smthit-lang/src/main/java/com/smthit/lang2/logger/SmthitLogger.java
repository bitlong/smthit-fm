/**
 * 
 */
package com.smthit.lang2.logger;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.appender.rolling.action.DeleteAction;
import org.apache.logging.log4j.core.appender.rolling.action.Duration;
import org.apache.logging.log4j.core.appender.rolling.action.IfFileName;
import org.apache.logging.log4j.core.appender.rolling.action.IfLastModified;
import org.apache.logging.log4j.core.appender.rolling.action.PathCondition;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.smthit.lang2.uuid.SmthitUUID;

import lombok.Setter;

/**
 * @author Bean
 * 日志的格式
 * @since 1.0.5
 */
public class SmthitLogger {
	private static final String LOGGER_MONITOR = "monitor";
	private static final String LOGGER_STATS = "stats";
	private static final String LOGGER_DESC = "desc";
	private static final String LOGGER_VISIT = "visit";
	private static final String LOGGER_AUDIT = "audit";
	private static final String LOGGER_ERROR = "error";
	
	
	
	@Setter
	private static String logDir = "/data/";
	
	private static final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	private static final Configuration config = ctx.getConfiguration();
	
	private SmthitLogger() {
	}

	public static void initLogger(String logDir) {
		SmthitLogger.logDir = logDir;	
	}
	
	/**
	 * 错误类日志
	 * @return
	 */
	public static Logger getErrorLogger() {
		return getLogger(LOGGER_ERROR);
	}
	
	/**
	 * 默认的类的的日志
	 * @param cls
	 * @return
	 */
	public static Logger getLogger(Class<?> cls) {
		return LogManager.getLogger(cls);
	}
	
	/**
	 * 监控日志
	 * @return
	 */
	public static Logger getMonitorLogger() {
		return getLogger(LOGGER_MONITOR);
	}
	
	/**
	 * 统计日志
	 * @return
	 */
	public static Logger getStatsLogger() {
		return getLogger(LOGGER_STATS);
	}
	
	/**
	 * 描述性日志
	 * @return
	 */
	public static Logger getDescLogger() {
		return getLogger(LOGGER_DESC);
	}
	
	/**
	 * 访问日志
	 * @return
	 */
	public static Logger getVisitLogger() {
		return getLogger(LOGGER_VISIT);
	}
	
	/**
	 * 安全审计日志
	 * @return
	 */
	public static Logger getAuditLogger() {
		return getLogger(LOGGER_AUDIT);
	}
	
	private static void start(String loggerName) {		
		
		//创建一个展示的样式：PatternLayout，   还有其他的日志打印样式。
		Layout<?> layout = PatternLayout.newBuilder()
			.withConfiguration(config).withPattern("%X{requestId}-%X{userId} %d [%t] %p %c - %m%n").build();
		//单个日志文件大小
		
		TimeBasedTriggeringPolicy tbtp = TimeBasedTriggeringPolicy.createPolicy("1", "true");
		
		TriggeringPolicy tp = SizeBasedTriggeringPolicy.createPolicy("10M");
		CompositeTriggeringPolicy policyComposite = CompositeTriggeringPolicy.createPolicy(tbtp, tp);
		
		String loggerDir = logDir + File.separator + loggerName;
		
		//删除日志的条件
		IfFileName ifFileName = IfFileName.createNameCondition(null, loggerName + "\\.\\d{4}-\\d{2}-\\d{2}.*");
		IfLastModified ifLastModified = IfLastModified.createAgeCondition(Duration.parse("1d"));
		DeleteAction deleteAction = DeleteAction.createDeleteAction(
				loggerDir, false, 1, false, null,
				new PathCondition[]{ifLastModified,ifFileName}, null, config);
		
		Action[] actions = new Action[]{deleteAction};

		DefaultRolloverStrategy strategy = DefaultRolloverStrategy.createStrategy("7", "1", null, null, actions, false, config);				
		
		String loggerPathPrefix = loggerDir + File.separator + loggerName;
		RollingFileAppender appender = RollingFileAppender.newBuilder()
				.withFileName(loggerPathPrefix + ".log")
				.withFilePattern(loggerPathPrefix + ".%d{yyyy-MM-dd}.%i.log")
				.withAppend(true)
				.withStrategy(strategy)
				.withName(loggerName)
				.withPolicy(policyComposite)
				.withLayout(layout)
				.withConfiguration(config)
				.build();
		
		appender.start();
		config.addAppender(appender);
		
		AppenderRef ref = AppenderRef.createAppenderRef(loggerName, null, null);
		AppenderRef[] refs = new AppenderRef[]{ref};
		LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.ALL, loggerName, "true", refs, null, config, null);
		
		loggerConfig.addAppender(appender, null, null);
		config.addLogger(loggerName, loggerConfig);
		
		ctx.updateLoggers();
	}
	
	/**使用完之后记得调用此方法关闭动态创建的logger，避免内存不够用或者文件打开太多*/
	public static void stop(String loggerName) {
		synchronized (config){
			config.getAppender(loggerName).stop();
			config.getLoggerConfig(loggerName).removeAppender(loggerName);
			config.removeLogger(loggerName);
			ctx.updateLoggers();
		}
	}
	
	/**获取Logger*/
	public static Logger getLogger(String loggerName) {
		synchronized (config) {
			if (!config.getLoggers().containsKey(loggerName)) {
				start(loggerName);
			}
		}
		return LogManager.getLogger(loggerName);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		ThreadContext.put("requestId", SmthitUUID.uuid());
		for(int i = 0; i < 10; i++){
			String name = "s" + String.valueOf(i);
			Logger logger = getLogger(name);
			logger.info("asdfasdf");
			stop(name);
		}
		ThreadContext.remove("requestId");
	}
}
