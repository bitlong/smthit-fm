/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import java.util.HashMap;
import java.util.Map;

import org.beetl.sql.core.SQLManager;

import com.smthit.lang.exception.ObjectNotFoundException;
import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public class SqlKit {
	private static SQLManager $;
	
	private static Map<String, SQLManager> mapSM = new HashMap<String, SQLManager>();

	private SqlKit() {
	}

	/**
	 * 获取默认的主数据源，如果是单数据源就是单数据源对应的SQLManager
	 * @return
	 */
	public static SQLManager $() {
		return $;
	}

	public static void $(SQLManager sqlManager) {
		$ = sqlManager;
	}
	
	public static void $(String dsName, SQLManager sqlManager) {
		if(!mapSM.containsKey(dsName)) {
			mapSM.put(dsName, sqlManager);
		} else {
			throw new ServiceException("数据源 " + dsName + " 已经存在.");
		}
	}

	public static <T> T mapper(Class<T> mapperInterface) {
		return $().getMapper(mapperInterface);
	}

	public static <T> T mapper(String dsName, Class<T> mapperInterface) {
		return $(dsName).getMapper(mapperInterface);
	}
	
	public static SQLManager $(String dsName) {
		if(mapSM.containsKey(dsName)) {
			return mapSM.get(dsName);
		} else {
			throw new ObjectNotFoundException("数据源 " + dsName + "所对应的 SQLManager不存在");
		}
	}

	
}
