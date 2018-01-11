/**
 * 
 */
package com.smthit.lang.image.impl;

import java.io.File;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.smthit.lang.image.ImageStorageStrategy;
import com.smthit.lang.utils.EncryptUtils;

/**
 * @author Bean
 *
 */
public class DateStorageStrategy implements ImageStorageStrategy {

	@Override
	public String getStorageRelativeFilename() {
		Date current = new Date();
		
		String dir = DateFormatUtils.format(current, "yyyy" + File.separator + "MM");
		
		Random random = new Random();
		
		random.setSeed(current.getTime());
		int rd = random.nextInt(1000) + 1000;
		
		String filename = EncryptUtils.MD5(current.getTime() + rd + "");
		
		return dir + File.separator + filename;
	}

}
