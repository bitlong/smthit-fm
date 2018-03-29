/**
 * 
 */
package com.smthit.framework.mvc.thymeleaf.menu;

import java.util.List;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
public class Menu {
	private String key;
	private String name;
	private String type;
	private String cls;
	private String url;
	private String target;
	private String permission;
	
	private List<Menu> children;
	
}
