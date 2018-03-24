/**
 * 
 */
package com.smthit.framework.dal.bettlsql.generator;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.kit.GenKit;
import org.beetl.sql.ext.gen.GenConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Bean
 *
 */
public class SourceGeneratorKit {
	
	@Autowired
	private SQLManager sqlManager; 
	
	public SourceGeneratorKit() {
	}

	public void gen(String table, String className, String pkg, boolean overrite) throws Exception {
		String srcPath = GenKit.getJavaSRCPath();
		POJOSourceGen gen = new POJOSourceGen(sqlManager, table, className, pkg, srcPath, new GenConfig());
        gen.gen(overrite);

	}
}
