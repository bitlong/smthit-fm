/**
 * 
 */
package com.smthit.framework.dal.mybatis;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "update", args = { Statement.class }),
		@Signature(type = StatementHandler.class, method = "batch", args = { Statement.class }),
		@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class }) })
public class MyBatisInterceptor implements Interceptor {

//	private static final Log logger = LogFactory
//			.getLog(MyBatisInterceptor.class);
	private static final Logger logger = LoggerFactory.getLogger(MyBatisInterceptor.class);

	private Boolean logEnable = false;

	private Integer maxRowCount = Integer.MAX_VALUE;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Thread t = Thread.currentThread();
		Field f = ReflectionUtils.findField(RoutingStatementHandler.class,
				"delegate");
		f.setAccessible(true);
		BaseStatementHandler bsh = (BaseStatementHandler) ReflectionUtils
				.getField(f, invocation.getTarget());
		f = ReflectionUtils.findField(BaseStatementHandler.class,
				"mappedStatement");
		f.setAccessible(true);
		if (logEnable && logger.isInfoEnabled()) {
			MappedStatement ms = (MappedStatement) ReflectionUtils.getField(f,
					bsh);
			logger.info(t.getName() + "	" + ms.getId() + ",the sql is:"
					+ bsh.getBoundSql().getSql().replace('\n', ' '));
		}
		long begin = System.currentTimeMillis();
		Object result = invocation.proceed();
		long end = System.currentTimeMillis();
		if (logEnable && logger.isInfoEnabled()) {
			MappedStatement ms = (MappedStatement) ReflectionUtils.getField(f,
					bsh);
			logger.info(t.getName() + "	" + ms.getId()
					+ ",the sql execute time is " + ((end - begin) / 1000.0)
					+ " s.");
		}
		return result;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof RoutingStatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}

	public Boolean getLogEnable() {
		return logEnable;
	}

	public void setLogEnable(Boolean logEnable) {
		this.logEnable = logEnable;
	}

	public Integer getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(Integer maxRowCount) {
		this.maxRowCount = maxRowCount;
	}
}