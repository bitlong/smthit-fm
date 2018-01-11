/**
 * 
 */
package com.smthit.lang.image;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Bean
 * 图片文件存储配置
 */
public class ImageStorageConfig {
	private static Logger logger = Logger.getLogger(ImageStorageConfig.class);
	
	private String rootDirectory = "/tmp";
	private ImageStorageStrategy imageStorageStrategy;
	
	public String copyFile(File srcFile, String suffix) throws IOException {
		String ext = suffix;
		if(StringUtils.isEmpty(ext)) {
			ext = FilenameUtils.getExtension(srcFile.getName());
		}
		
		String filename = imageStorageStrategy.getStorageRelativeFilename() + "." + ext;
		try {
			FileUtils.copyFile(srcFile, new File(rootDirectory + File.separator + filename));
		} catch (IOException e) {
			logger.error("Save image file failed!", e);
			throw e;
		}
		
		return filename;
	}

	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	public void setImageStorageStrategy(ImageStorageStrategy imageStorageStrategy) {
		this.imageStorageStrategy = imageStorageStrategy;
	}
}
