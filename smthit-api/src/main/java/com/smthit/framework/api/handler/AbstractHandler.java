/**
 * 
 */
package com.smthit.framework.api.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.google.common.collect.Range;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.smthit.framework.api.annotation.Handler;
import com.smthit.framework.api.annotation.ProtocolHandler;
import com.smthit.framework.api.exception.ApiException;
import com.smthit.framework.api.protocol.meta.FieldMeta;
import com.smthit.framework.api.protocol.meta.HandlerMeta;
import com.smthit.framework.api.protocol.meta.ProtocolDescriptor;
import com.smthit.framework.api.utils.ReflectMethodUtils;

/**
 * @author Bean
 * @since 1.0.0
 */
public abstract class AbstractHandler {
	private static Logger logger = Logger.getLogger(AbstractHandler.class);
	
	@Autowired
	protected MessageSource messageResource;
	
	/**
	 * @since 1.0.0
	 */
	@PostConstruct
	public void init() {
		//logger.info(String.format("Protocol handler[%s] init.", getClass().toString()));
		
		//TODO根据每一个Handler中的注解，将handler注入到ProtocolDispatch中去
		
		if(getClass().isAnnotationPresent(ProtocolHandler.class)) {
			ProtocolHandler a = getClass().getAnnotation(ProtocolHandler.class);
			//logger.info(String.format("Reigist Protocol is %s", a.protocol()));
			
			ProtocolDescriptor descriptor = new ProtocolDescriptor();
			
			String protocol = a.protocol();
			//协议处理器信息
			descriptor.setDesc(a.desc());
			descriptor.setProtocol(a.protocol());
			descriptor.setHandlerClass(ProtocolHandler.class.getCanonicalName());
			
			ProtocolMapper.singleton().addProtocolDescriptor(descriptor);
			
			//协议的处理方法
			Method[] methods = getClass().getMethods();
			for(Method m : methods) {
				if(m.isAnnotationPresent(Handler.class)) {
					Handler handler = m.getAnnotation(Handler.class);
					double since = handler.since();
					double until = handler.until();
					boolean requireLogin = handler.require_login();
					
					Range<Double> handlerVersion = Range.closed(since, until);
					
					ProtocolMethod pm = new ProtocolMethod();
					pm.setHandler(this);
					pm.setMethod(m);
					pm.setVersion(handlerVersion);
					pm.setRequireLogin(requireLogin);
					
					ProtocolMapper.singleton().addProtocolMethod(a.protocol(), pm);
					
					HandlerMeta meta = new HandlerMeta();
					
					meta.setProtocol(protocol);
					meta.setSince(since);
					meta.setUntil(until);
					meta.setMethodName(m.getName());
					
					ProtocolMapper.singleton().addHandlerMeta(meta);
				}
			}
			
			//协议的请求响应对象
			Class<?> requestClass = a.request();
			Class<?> responseClass = a.response();
			
			descriptor.setRequestClass(requestClass);
			descriptor.setResponseClass(responseClass);
			
			List<Field> fields = ReflectMethodUtils.getClassFields(requestClass, true);
			for(Field f : fields) {
				//logger.info(f.getName() + "," + f.getDeclaringClass().getPackage().getName());
				FieldMeta fieldMeta = createFieldMeta(f);
				pareseChildFieldMeta(f, fieldMeta);
				descriptor.getRequestFields().add(fieldMeta);
			}
			
			fields = ReflectMethodUtils.getClassFields(responseClass, true);
			for(Field f : fields) {
				//logger.info(f.getName() + "," + f.getDeclaringClass().getPackage().getName());				
				FieldMeta fieldMeta = createFieldMeta(f);				
				pareseChildFieldMeta(f, fieldMeta);								
				descriptor.getResponsetFields().add(fieldMeta);
			}
			
		} else {
			throw new ApiException(String.format("%s did not add protocol annotation.", getClass().toString()));
		}
	}
	
	private void pareseChildFieldMeta(Field parent, FieldMeta fieldMeta) {
		Class<?> subClass = parent.getType();
		if(subClass.isAssignableFrom(List.class)) {
			//logger.info("is list type.");
			
			ParameterizedType type = (ParameterizedType)parent.getGenericType();
			//logger.info(type.getTypeName());
			Type[] types = type.getActualTypeArguments();
			if(types != null && types.length > 0) {
				String genericType = types[0].getTypeName();
				try {
					Class<?> genericTypeClass = Class.forName(genericType);
					List<Field> fields = ReflectMethodUtils.getClassFields(genericTypeClass, true);
					
					for(Field f : fields) {
						FieldMeta meta = createFieldMeta(f);
						fieldMeta.getSubFields().add(meta);
					}
					
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage(), e);
				}
			}
		} else {
			List<Field> fields = ReflectMethodUtils.getClassFields(subClass, true);
			if(fields.size() == 0)
				return;
			
			for(Field f : fields) {
				FieldMeta meta = createFieldMeta(f);
				fieldMeta.getSubFields().add(meta);
			}
		}
	}
	
	private FieldMeta createFieldMeta(Field field) {
		double since, until;
		if(field.isAnnotationPresent(Since.class)) {
			Since sinceAnnotation = field.getAnnotation(Since.class);
			since = sinceAnnotation.value();
		} else {
			since = 0.1;
		}
		
		if(field.isAnnotationPresent(Until.class)) {
			Until untilAnnotation = field.getAnnotation(Until.class);
			until = untilAnnotation.value();
		} else {
			until = Double.MAX_VALUE;
		}
				
		return new FieldMeta(field.getName(), since, until, field.getType().getSimpleName());
	}
	
	/**
	 * @since 1.0.0
	 */
	@PreDestroy
	public void destory() {
		logger.info(String.format("Protocol handler[%s] destory.", getClass().toString()));
	}
	

	public String getMessage(String code) {
		return messageResource.getMessage(code, new Object[0], Locale.SIMPLIFIED_CHINESE);
	}
	
	public String getMessage(String code, Object[] args) {
		return messageResource.getMessage(code, args, Locale.SIMPLIFIED_CHINESE);
	}
	
	public String getMessage(ApiException exp) {
		if(StringUtils.isNotEmpty(exp.getErrorCode())) {
			return getMessage(exp.getErrorCode());
		} else {
			return exp.getMessage();
		}
	}
} 
