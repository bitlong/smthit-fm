/**
 * 
 */
package com.smthit.framework.mvc.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public class AbstractController {
	
	@Autowired
	protected MessageSource messageResource;

	public String getMessage(String code) {
		return messageResource.getMessage(code, new Object[0], Locale.SIMPLIFIED_CHINESE);
	}
	
	public String getMessage(String code, Object[] args) {
		return messageResource.getMessage(code, args, Locale.SIMPLIFIED_CHINESE);
	}
	
	public String getMessage(ServiceException exp) {
		if(!StringUtils.isEmpty(exp.getErrorCode())) {
			return getMessage(exp.getErrorCode());
		} else {
			return exp.getMessage();
		}
	}
	

	public PageRequest createPageRequest(int page, int rows, Sort sort) {
		return new PageRequest(page, rows, sort);
	}
}
