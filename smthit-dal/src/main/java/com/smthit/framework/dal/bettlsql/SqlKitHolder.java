/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Bean
 *
 */
public class SqlKitHolder implements ApplicationContextAware {

	/**
	 * 
	 */
	public SqlKitHolder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SQLManager sqlManager = applicationContext.getBean(SQLManager.class);
		if(sqlManager == null) {
			throw new NullPointerException("Can't not find SQLManager of BeetlSQL");
		}
		SqlKit.$(sqlManager);
	}

}
