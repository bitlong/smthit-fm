/**
 * 
 */
package com.smthit.framework.mvc.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import com.google.gson.Gson;
import com.smthit.lang.data.ResponseData;

/**
 * @author Bean
 *
 */
public class SmthitRolesAuthorizationFilter extends RolesAuthorizationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request; 
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if ("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
			httpServletResponse.addHeader("Content-Type", "application/json");
			httpServletResponse.addHeader("Pragma", "no-cache");
			httpServletResponse.setCharacterEncoding("UTF-8");
			
			PrintWriter out = httpServletResponse.getWriter();
			
            ResponseData data = ResponseData.newFailed("没有权限进行当前操作.");
            data.setStatus(500);
            Gson gson = new Gson();
            out.print(gson.toJson(data));
            out.flush();
            out.close();
			
			return false;
		}
		
		return super.onAccessDenied(request, response);
	}

	@Override
	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException {
		return super.isAccessAllowed(request, response, mappedValue);
	}

}
