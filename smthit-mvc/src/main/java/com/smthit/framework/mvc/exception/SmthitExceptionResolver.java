/**
 * 
 */
package com.smthit.framework.mvc.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.google.gson.Gson;
import com.smthit.lang.data.ResponseData;
import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public class SmthitExceptionResolver extends SimpleMappingExceptionResolver {

	public SmthitExceptionResolver() {
	}

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		logger.info(ex.getMessage(), ex);
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request; 
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		if ("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
			httpServletResponse.addHeader("Content-Type", "application/json");
			httpServletResponse.addHeader("Pragma", "no-cache");
			httpServletResponse.setCharacterEncoding("UTF-8");
			
			PrintWriter out;
			try {
				out = httpServletResponse.getWriter();
	            
				ResponseData data = ResponseData.newFailed("服务器内部错误,请联系管理员。错误内容:" + ex.getMessage());
	            data.setStatus(500);
	            
				if(ex.getClass().isAssignableFrom(UnauthenticatedException.class)) {
					//未登录
					data.setMessage("用户会话超时,当前操作必须登录后执行.");
					data.setStatus(501);
				} else if(ex.getClass().isAssignableFrom(UnauthorizedException.class)) {
					//未授权
					data.setMessage("没有权限进行当前操作.");
					data.setStatus(501);
				} else if(ex.getClass().isAssignableFrom(ServiceException.class)) {
					data.setMessage("操作失败:" + ex.getMessage());
					data.setStatus(502);
				} else if(ex.getClass().isAssignableFrom(ConstraintViolationException.class)) {
					//验证失败
					ConstraintViolationException cve = (ConstraintViolationException)ex;
					
					StringBuffer sb = new StringBuffer();
					
					for(ConstraintViolation<?> cv : cve.getConstraintViolations()) {
						sb.append(cv.getMessage()).append("<br/>");
					}
					data.setMessage(sb.toString());
					data.setStatus(503);
				}
				
	            Gson gson = new Gson();
	            out.print(gson.toJson(data));
	            out.flush();
	            out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		return super.resolveException(request, response, handler, ex);
	}
}
