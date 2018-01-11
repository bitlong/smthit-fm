/**
 * 
 */
package com.smthit.framework.api.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smthit.framework.api.exception.ApiException;
import com.smthit.framework.api.protocol.meta.HandlerMeta;
import com.smthit.framework.api.protocol.meta.ProtocolDescriptor;

/**
 * @author Bean
 * @since 1.0.0
 */
public class ProtocolMapper {
	private static ProtocolMapper single = new ProtocolMapper();
	
	public static ProtocolMapper singleton() {
		return single;
	}
	
	private Map<String, List<ProtocolMethod>> methodMapper = new HashMap<String, List<ProtocolMethod>>();
	private Map<String, ProtocolDescriptor> protocolDescriptors = new HashMap<String, ProtocolDescriptor>();
	
	private ProtocolMapper() {
	}
	
	/**
	 * 
	 * @param protocol
	 * @param protocolMethod
	 * @return
	 * 
	 * @since 1.0.0
	 */
	public ProtocolMapper addProtocolMethod(String protocol, ProtocolMethod protocolMethod) {
		
		List<ProtocolMethod> methods;
		if(methodMapper.containsKey(protocol)) {
			methods = methodMapper.get(protocol);
		} else {
			methods = new ArrayList<ProtocolMethod>();
			methodMapper.put(protocol, methods);
		}
		
		//check protocol method is collision or not.
		if(methods.size() > 0) {
			for(ProtocolMethod m : methods) {
				if(m.getVersion().contains(protocolMethod.getVersion().lowerEndpoint()) || m.getVersion().contains(protocolMethod.getVersion().upperEndpoint())) {
					throw new ApiException(String.format("%s's %s(%s) protocol Method is collision with %s(%s)",
							protocol,
							m.getMethod().getName(),
							m.getVersion(),
							protocolMethod.getMethod().getName(),
							protocolMethod.getVersion()));
				}
			}
		}
		
		//add protocol's method to mapper.
		methods.add(protocolMethod);
		
		return this;
	}
	
	public void addProtocolDescriptor(ProtocolDescriptor protocolDescriptor) {
		if(protocolDescriptors.containsKey(protocolDescriptor.getProtocol())) {
			return;
		} 
		
		protocolDescriptors.put(protocolDescriptor.getProtocol(), protocolDescriptor);
	}
	
	public void addHandlerMeta(HandlerMeta handlerMeta) {
		ProtocolDescriptor protocolDescriptor = protocolDescriptors.get(handlerMeta.getProtocol());
		
		List<HandlerMeta> metas = protocolDescriptor.getHandlerMetas();
		
		metas.add(handlerMeta);
	}
	
	/**
	 * 
	 * @param protocol
	 * @param version
	 * @return
	 * 
	 * @since 1.0.0
	 */
	public ProtocolMethod findProtocolMethod(String protocol, double version) {
		List<ProtocolMethod> list = methodMapper.get(protocol);
		
		for(ProtocolMethod m : list) {
			if(m.getVersion().contains(version)) {
				return m;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 * 
	 * @since 1.0.0
	 */
	public Map<String, ProtocolDescriptor> getProtocolDescriptors() {
		return protocolDescriptors;
	}
}
