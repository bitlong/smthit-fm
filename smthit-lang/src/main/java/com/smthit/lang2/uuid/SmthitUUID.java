/**
 * 
 */
package com.smthit.lang2.uuid;

import java.util.UUID;

import com.smthit.lang.uuid.IdWorker;

/**
 * @author Bean UUID生成唯一ID
 */
public final class SmthitUUID {

	private SmthitUUID() {
	}

	public static IdWorker defaultWorker() {
		return UUIDinner.defaultIdWorker;
	}

	private static class UUIDinner {
		private static final int workerId = 0;
		private static final int dataCenterId = 0;

		private static final IdWorker defaultIdWorker = new IdWorker(workerId, dataCenterId);
	}

	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	public static String[] uuid(int number) {
		if (number < 1) {
			return new String[0];
		}
		String[] retArray = new String[number];
		
		for (int i = 0; i < number; i++) {
			retArray[i] = uuid();
		}
		
		return retArray;
	}

}
