/**
 * 
 */
package com.smthit.framework.api.protocol.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bean
 *
 */
public class ProtocolDescriptor {
	private String protocol;
	private String desc;
	private String handlerClass;
	
	private Class<?> requestClass;
	private Class<?> responseClass;
	
	private List<FieldMeta> requestFields = new ArrayList<FieldMeta>();
	private List<FieldMeta> responsetFields = new ArrayList<FieldMeta>();
	
	private List<HandlerMeta> handlerMetas = new ArrayList<HandlerMeta>();

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<HandlerMeta> getHandlerMetas() {
		return handlerMetas;
	}

	public void setHandlerMetas(List<HandlerMeta> handlerMetas) {
		this.handlerMetas = handlerMetas;
	}

	public String getHandlerClass() {
		return handlerClass;
	}

	public void setHandlerClass(String handlerClass) {
		this.handlerClass = handlerClass;
	}
	
	public List<FieldMeta> getRequestFields() {
		return requestFields;
	}

	public List<FieldMeta> getResponsetFields() {
		return responsetFields;
	}

	@Override
	public String toString() {
		return "ProtocolDescriptor [protocol=" + protocol + ", desc=" + desc
				+ ", handlerClass=" + handlerClass + "]";
	}	
	
	public Class<?> getRequestClass() {
		return requestClass;
	}

	public void setRequestClass(Class<?> requestClass) {
		this.requestClass = requestClass;
	}

	public Class<?> getResponseClass() {
		return responseClass;
	}

	public void setResponseClass(Class<?> responseClass) {
		this.responseClass = responseClass;
	}

	public String printProtocol(boolean html) {
		StringBuffer sb = new StringBuffer();
		
		//协议
		sb.append("Protocol:").append(protocol).append(" ").append("Desc:").append(desc).append("\n");
		
		//请求
		sb.append("Request:").append("\n").append("{").append("\n");
		for(FieldMeta meta : requestFields) {
			sb.append(meta.toString());
		}
		sb.append("}\n");
		
		//响应
		sb.append("Response:").append("\n").append("{").append("\n");
		for(FieldMeta meta : responsetFields) {
			sb.append(meta.toString());
		}
		sb.append("}\n");
		
		//处理器
		sb.append("Handler:");
		sb.append(handlerClass).append("\n");
		
		for(HandlerMeta meta : handlerMetas) {
			sb.append("    ");
			sb.append(meta.toString());
		}
		
		if(html) {
			String tmp = sb.toString();
			tmp = tmp.replace(" ", "&nbsp;");
			tmp = tmp.replace("\n", "<br \\>");
			
			return tmp;
		}
		
		return sb.toString();
	}
	
}
