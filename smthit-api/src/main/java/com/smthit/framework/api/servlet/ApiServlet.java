/**
 * 
 */
package com.smthit.framework.api.servlet;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smthit.framework.api.exception.ApiException;
import com.smthit.framework.api.handler.ProtocolDispatcher;
import com.smthit.framework.api.handler.ProtocolMapper;
import com.smthit.framework.api.handler.UserSecurity;
import com.smthit.framework.api.protocol.DefaultErrorResponse;
import com.smthit.framework.api.protocol.Request;
import com.smthit.framework.api.protocol.RequestHeader;
import com.smthit.framework.api.protocol.Response;
import com.smthit.framework.api.protocol.meta.ProtocolDescriptor;
import com.smthit.framework.api.utils.CharsetUtils;
import com.smthit.framework.api.utils.EncryptUtils;

/**
 * @author Bean
 * @since 1.0.0
 */
public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 2445844557962713984L;
	private static Logger logger = Logger.getLogger(ApiServlet.class);
	
	private ProtocolDispatcher dispacher;
	
	private MessageSource messageSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		logger.info("EnterpriseApiServlet starting...");
		
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

		dispacher = new ProtocolDispatcher();
		dispacher.setUserSecurity(wac.getBean(UserSecurity.class));
		
		messageSource = (MessageSource)wac.getBean("messageSource");
		
        logger.info("EnterpriseApiServlet started.");
	}	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Request wokeRequest = null;
		Response wokeReponse = null;
		try {
			wokeRequest = wrapperHeader(req);
			wokeReponse = dispacher.dispatch(wokeRequest);
		} catch(ApiException e) {
			logger.error(e.getMessage() + "[" + e.getErrorCode() + "]", e);
			String msg = "";
			if(StringUtils.isNotEmpty(e.getErrorCode())) {
				msg = messageSource.getMessage(e.getErrorCode(), null, Locale.CHINA);
			} else {
				msg = e.getMessage();
			}
			wokeReponse = new DefaultErrorResponse(500, msg, e.getMessage());
		} catch(Throwable exp) {
			logger.error(exp.getMessage(), exp);
			wokeReponse = new DefaultErrorResponse(500, "服务器内部错误,已经发送管理员进行处理.", exp.getMessage());
		}
		
		output(resp, wokeRequest, wokeReponse);
	}	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html");
			
			BufferedWriter bw = new BufferedWriter(resp.getWriter());
			
			bw.write("<!DOCTYPE HTML>");
			bw.write("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body>");
			bw.write("It's work!");
			bw.write("<body></html>");
			
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Request wrapperHeader(HttpServletRequest req) {
		
		String protocol = "unkown", version = "1.0", shadow = "unkown";
		
		String wokeHeader = req.getHeader("smthit-api-header");
		logger.debug("smthit-api-header:" + wokeHeader);
		if(StringUtils.isBlank(wokeHeader)) {
			throw new ApiException("协议请求头错误.");
		}
		
		String[] headers = wokeHeader.split(";");
		for(int i = 0; i < headers.length; i++) {
			String[] kv = headers[i].split(":");
			if(kv == null || kv.length != 2)
				continue;
			
			String key = kv[0];
			String value = kv[1];
			
			if("client-version".equalsIgnoreCase(key)) {
				if(Objects.isNull(value)) {
					version = "1.0";
				} else {
					version = value;
				}
			} else if(RequestHeader.PROTOCOL.equalsIgnoreCase(key)) {
				protocol = value;
			} else if(RequestHeader.SHADOW.equalsIgnoreCase(key)) {
				shadow = value;	
			}
		}
		
		//查询Mapper,获得指定的Request的类型，进行反序列化
		ProtocolDescriptor pd = ProtocolMapper.singleton().getProtocolDescriptors().get(protocol);
		if(pd == null) {
			throw new ApiException("当前使用的协议不被支持.");
		}
		
		Class<?> requestClass = pd.getRequestClass();
		Request wokeRequest = null;

		String sid = req.getHeader(RequestHeader.SID);
		double ver = Double.parseDouble(version);
		
		//body
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is;
		try {
			is = req.getInputStream();
	        int read = -1;
	        while((read = is.read()) != -1) {
	        	os.write(read);
	        }
	        
	        //TODO 如果是json的协议，就反序列化
	        byte[] input = new byte[0];
	        if(ver > 1.0) {
	        	String SERVER_KEY = "abc";
	        	if(StringUtils.isEmpty(sid)) {
	        		sid = "GUEST";
	        	}
	        	
	        	String hex = EncryptUtils.byte2hex(os.toByteArray());
	        	logger.info("request:" + hex);
	        	
	        	input = EncryptUtils.decryptAES(os.toByteArray(), EncryptUtils.MD5(EncryptUtils.MD5(sid) + SERVER_KEY));
	        	
	        } else {
	        	input = os.toByteArray();
	        }
	        
	        if(input != null){
	        	 String requestStr = new String(input, CharsetUtils.DEFAULT_CHARSET);

	 	        logger.debug("Request:" + requestStr);
	 	        
	 	        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setDateFormat("yyyy-MM-dd HH:mm:ss").setVersion(ver).create();
	 	        wokeRequest = (Request)gson.fromJson(requestStr, requestClass);
	 	        
	 	        wokeRequest.setBody(input);
	        }else{
//	        	 String requestStr = new String(input, CharsetUtils.DEFAULT_CHARSET);
	        	 Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setDateFormat("yyyy-MM-dd HH:mm:ss").setVersion(ver).create();
		 	     wokeRequest = (Request)gson.fromJson("{}", requestClass);
	        	 wokeRequest.setBody(os.toByteArray());
	        }
	       
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new ApiException("协议解析失败.");
		}
		
		if(wokeRequest != null) {
			
			wokeRequest.getHeader().setSid(sid);
			wokeRequest.getHeader().setAgent(req.getHeader(RequestHeader.AGENT));
			wokeRequest.getHeader().setTime(Calendar.getInstance().getTimeInMillis());
			wokeRequest.getHeader().setProtocol(protocol);
			wokeRequest.getHeader().setVersion(ver);
			wokeRequest.getHeader().setShadow(shadow);
			
			if(StringUtils.isEmpty(req.getCharacterEncoding())) {
				wokeRequest.getHeader().setCharsetEncoding(CharsetUtils.DEFAULT_CHARSET);
			} else {
				wokeRequest.getHeader().setCharsetEncoding(req.getCharacterEncoding());
			}
		}

		return wokeRequest;
	}
	
	private void output(HttpServletResponse httpServletRepsonse, Request request, Response resposne) {
		//version
		Gson gson;
		if(request != null) {
			gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setDateFormat("yyyy-MM-dd HH:mm:ss").setVersion(request.getHeader().getVersion()).create();
		} else {
			gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		}
		
		try {
			String content = gson.toJson(resposne);
			httpServletRepsonse.getOutputStream().write(content.getBytes(CharsetUtils.DEFAULT_CHARSET));
			httpServletRepsonse.getOutputStream().flush();
			logger.debug("Response:" + content);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ApiException(e.getMessage());
		}
	}
}
