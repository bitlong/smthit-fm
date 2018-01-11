/**
 * 
 */
package com.smthit.framework.api.utils;

/**
 * @author Bean
 *
 */
public class ServerContext {
	private int port;
	private String contextPath;
	private String descriptor;
	private String resourceBase;
	private boolean configurationDiscovered;
	private boolean parentLoaderPriority;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public String getResourceBase() {
		return resourceBase;
	}

	public void setResourceBase(String resourceBase) {
		this.resourceBase = resourceBase;
	}

	public boolean isConfigurationDiscovered() {
		return configurationDiscovered;
	}

	public void setConfigurationDiscovered(boolean configurationDiscovered) {
		this.configurationDiscovered = configurationDiscovered;
	}

	public boolean isParentLoaderPriority() {
		return parentLoaderPriority;
	}

	public void setParentLoaderPriority(boolean parentLoaderPriority) {
		this.parentLoaderPriority = parentLoaderPriority;
	}

}
