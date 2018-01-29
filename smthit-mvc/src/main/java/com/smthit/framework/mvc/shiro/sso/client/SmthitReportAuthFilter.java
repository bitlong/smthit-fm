/**
 * 
 */
package com.smthit.framework.mvc.shiro.sso.client;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;

/**
 * @author Bean
 * 报表鉴权
 */
public class SmthitReportAuthFilter extends AdviceFilter {

	/**
	 * 
	 */
	public SmthitReportAuthFilter() {
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		return super.preHandle(request, response);
	}
}
