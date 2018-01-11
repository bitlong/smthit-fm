/**
 * 
 */
package com.smthit.lang.logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.varia.LevelRangeFilter;

/**
 * @author Bean 动态按业务创建Logger，存储在不同的路径下
 */
public class TiCaiLoggerFactory {
	private static Logger factoryLogger = Logger.getLogger(TiCaiLoggerFactory.class);
	
	private static final Map<String, Logger> loggerMap = new HashMap<String, Logger>();

	private String baseDir;

	/**
	 * 
	 */
	public TiCaiLoggerFactory(String baseDir) {
		this.baseDir = baseDir;
	}
	
	public String getBaseDir(){
		return this.baseDir;
	}

	public Logger getLogger(String bizNo, LoggerPolicy loggerPolicy) {
		if (StringUtils.isEmpty(baseDir) || StringUtils.isEmpty(bizNo)) {
			throw new LogException("日志参数错误");
		}

		String logFieName = loggerPolicy.getLoggerName(baseDir, bizNo);

		Logger logger = loggerMap.get(bizNo);

		if (logger == null) {
			try {
                logger = Logger.getLogger(loggerPolicy.getPolicyName() + "(" + bizNo + ")");  

				//TODO 这些配置信息，未来都可以做到策略里
				PatternLayout playout = new PatternLayout();
				playout.setConversionPattern("%d{[yyyy-MM-dd HH:mm:ss.SSS]}:[%p] %m%n");
				RollingFileAppender fAppender = new RollingFileAppender(playout, logFieName);
				
				fAppender.setMaxFileSize("500MB");
				fAppender.setMaxBackupIndex(3);
				fAppender.setAppend(true);
				fAppender.setThreshold(Level.INFO);
				fAppender.setLayout(playout);
				LevelRangeFilter filterInfo = new LevelRangeFilter();
				filterInfo.setLevelMin(Level.INFO);
				filterInfo.setLevelMax(Level.ERROR);
				fAppender.addFilter(filterInfo);
				logger.addAppender(fAppender);
				
				loggerMap.put(bizNo, logger);
			} catch (IOException exp) {
				factoryLogger.error("获取Logger失败", exp);
				return factoryLogger;
			}			
		}
		
		return logger;
	}
	
	

}
