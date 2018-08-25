/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import java.util.ArrayList;
import java.util.List;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 * TODO 数据源的方式有多种，需要考虑怎么扩展
 * 对等模式，Master-Slave模式， 主读写-从只读，分库分表（shard模式）
 */
@Data
@Slf4j
public class SqlKitHolder implements ApplicationContextAware {
	private List<String> multiDS = new ArrayList<String>();
	
	/**
	 * 是否支持多数据源配置
	 */
	private boolean supportMultiDS = false;
	/**
	 * 主数据源
	 */
	private String masterDS = "dataSource";
	
	public SqlKitHolder() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (!supportMultiDS) {
			try {
				SQLManager sqlManager = (SQLManager) applicationContext.getBean("sqlManagerFactoryBean");
				sqlManager.setBaseMapper(BaseViewMapper.class);
				
				SqlKit.$(sqlManager);
			} catch (Exception e) {
				throw new ApplicationContextException("Can't not find Default SQLManager of BeetlSQL");
			}
		} else {
			if(multiDS.size() <= 1) {
				throw new ApplicationContextException("在多数据源模式下，至少要配置一个以上数据源.");
			}
			
			for(String ds : multiDS) {
				String sqlManagerFactoryName = ds + "SqlManagerFactoryBean";

				try {
					SQLManager sqlManager = (SQLManager) applicationContext.getBean(sqlManagerFactoryName);
					sqlManager.setBaseMapper(BaseViewMapper.class);
					
					SqlKit.$(ds, sqlManager);
					
					//配置主数据源
					if(ds.equals(masterDS)) {
						SqlKit.$(sqlManager);
					}
					
				} catch(Exception e) {
					log.error(e.getMessage(), e);
					throw new ApplicationContextException(e.getMessage(), e);
				}
				

			}
		}
		
	}

	public void addDS(String dsName) {
		if(!supportMultiDS)
			throw new ApplicationContextException("在单数据源模式下，无法调用该操作.");
		
		if(multiDS.contains(dsName)) {
			throw new ApplicationContextException("数据源已经存在.");
		}
		
		multiDS.add(dsName);
	}
	
	public void setMasterDS(String dsName) {
		if(!supportMultiDS)
			throw new ApplicationContextException("在单数据源模式下，无法调用该操作.");
		
		this.masterDS = dsName;
		multiDS.add(dsName);
	}
}
