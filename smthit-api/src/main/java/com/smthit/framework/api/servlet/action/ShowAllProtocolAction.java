/**
 * 
 */
package com.smthit.framework.api.servlet.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smthit.framework.api.handler.ProtocolMapper;
import com.smthit.framework.api.protocol.meta.ProtocolDescriptor;
import com.smthit.framework.api.servlet.AbstractAction;

/**
 * @author Bean
 *
 */
public class ShowAllProtocolAction extends AbstractAction {

	@Override
	public String doAction(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		Map<String, ProtocolDescriptor> protocols = ProtocolMapper.singleton().getProtocolDescriptors();
		
		model.put("protocols", protocols.values());
		
		return "list";
	}
}
