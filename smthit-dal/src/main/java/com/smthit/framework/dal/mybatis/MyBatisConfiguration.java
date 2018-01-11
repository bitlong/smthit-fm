/**
 * 
 */
package com.smthit.framework.dal.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.Assert;

public class MyBatisConfiguration {
	
	private static MyBatisConfiguration instance;

	private Integer timeout;

	private Integer maxRowCount;

	private Integer pageSize;

	private SqlSessionFactory sqlSessionFactory;

	private MyBatisInterceptor logInterceptor = new MyBatisInterceptor();

	public static MyBatisConfiguration instance() {
		Assert.isTrue(isLoad());
		return instance;
	}

	public static boolean isLoad() {
		return instance != null;
	}

	public MyBatisConfiguration() {
		Assert.isTrue(!isLoad());
		load(this);
	}

	public static void load(MyBatisConfiguration configuration) {
		instance = configuration;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.sqlSessionFactory.getConfiguration().setDefaultStatementTimeout(
				timeout);
		this.timeout = timeout;
	}

	public Integer getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(Integer maxRowCount) {
		this.maxRowCount = maxRowCount;
		this.logInterceptor.setMaxRowCount(maxRowCount);
	}

	public void enableLog() {
		setLogEnable(true);
	}

	public void disbleLog() {
		setLogEnable(false);
	}

	public Boolean getLogEnable() {
		return this.logInterceptor.getLogEnable();
	}

	public void setLogEnable(Boolean logEnable) {
		this.logInterceptor.setLogEnable(logEnable);
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlSessionFactory.getConfiguration()
				.addInterceptor(logInterceptor);
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void destroy() {
		instance = null;
	}
}
