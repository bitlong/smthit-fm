/**
 * 
 */
package com.smthit.framework.mvc.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Time;

/**
 * @author Bean
 *
 */
public class CustomTimeEditor extends PropertyEditorSupport {
	
	public CustomTimeEditor() {
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String[] tmp = text.split(":");
		if(tmp.length < 3) {
			text += ":00";
		}
		setValue(Time.valueOf(text));
	}

	@Override
	public String getAsText() {
		Time time = (Time)getValue();
		if(time != null) {
			return time.toString();
		}
		return "";
	}	
}
