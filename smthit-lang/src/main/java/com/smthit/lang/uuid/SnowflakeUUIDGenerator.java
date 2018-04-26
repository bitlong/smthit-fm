/**
 * 
 */
package com.smthit.lang.uuid;

/**
 * @author Bean
 * 参考twitter snowfake 的实现方式
 * @see http://github.com/twitter/snowflake
 */
public class SnowflakeUUIDGenerator {
	private static IdWorker worker;
	private static int workerId = 0;
	private static int dataCenterId = 0;

	private SnowflakeUUIDGenerator() {
	}

	public static IdWorker getWorker() {
		if(worker == null) {
			worker = new IdWorker(workerId, dataCenterId);
			return worker;
		}
		
		return worker;
	}
}
