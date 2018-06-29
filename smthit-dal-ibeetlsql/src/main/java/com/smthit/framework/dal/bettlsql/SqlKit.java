/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import org.beetl.sql.core.SQLManager;

/**
 * @author Bean
 *
 */
public class SqlKit {
	static SQLManager $;
	
	private SqlKit() {
	}
	
	public static SQLManager $() {
		return $;
	}

	public static void $(SQLManager sqlManager) {
		$ = sqlManager;
	}
	
	public static<T> T mapper(Class<T> mapperInterface) {
		return $().getMapper(mapperInterface);
	}
}
