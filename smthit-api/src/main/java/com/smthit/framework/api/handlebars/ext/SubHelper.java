/**
 * 
 */
package com.smthit.framework.api.handlebars.ext;

import static org.apache.commons.lang3.Validate.notEmpty;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.EmbeddedHelper;

/**
 * @author Bean
 *
 */
public class SubHelper implements Helper<String> {
	  /**
	   * A singleton instance of this helper.
	   */
	  public static final Helper<String> INSTANCE = new EmbeddedHelper();

	  /**
	   * The helper's name.
	   */
	  public static final String NAME = "sub";
	  
	  public CharSequence apply(final String path, final Options options)
	      throws IOException {
	    notEmpty(path, "found '%s', expected 'partial's name'", path);
	    
	    Template template = options.handlebars.compile(path);
	    
	    StringBuilder script = new StringBuilder();
	    script.append(template.text()).append("\n");
	    options.fn();
	    
	    return new Handlebars.SafeString(script);
	  }	
}
