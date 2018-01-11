/**
 * 
 */
package com.smthit.framework.api.servlet;

/**
 * @author Bean
 *
 */
public class Navigator {
	private String name;
	private String url;
	
	public Navigator(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
