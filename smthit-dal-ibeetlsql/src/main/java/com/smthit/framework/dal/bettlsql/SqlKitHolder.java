/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;

/**
 * @author Bean
 *
 */
public class SqlKitHolder implements ApplicationContextAware {

	public SqlKitHolder() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SQLManager sqlManager = (SQLManager)applicationContext.getBean("sqlManagerFactoryBean");
	
		if(sqlManager == null) {
			throw new NullPointerException("Can't not find SQLManager of BeetlSQL");
		}
		
		try {
			SqlKit.$(sqlManager);
		} catch (Exception e) {
			throw new ApplicationContextException("Can't not find SQLManager of BeetlSQL");
		}
	}

}
