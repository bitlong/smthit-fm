/**
 * 
 */
package com.smthit.framework.mvc.thymeleaf.menu;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.smthit.lang.exception.ServiceException;

import lombok.Cleanup;

/**
 * @author Bean
 *
 */
public class MenuLoader {
	public static String menuConfig = "/conf/menus.json";
	
	public MenuLoader() {
	}

	public static List<Menu> loadMainMenus() {
		List<Menu> menus = new ArrayList<Menu>();
		
		Gson gson = new GsonBuilder().create();
		
		try {
		    Type type = new TypeToken<ArrayList<Menu>>() {}.getType();  

			@Cleanup InputStream is = MenuLoader.class.getResourceAsStream(menuConfig);
			menus = gson.fromJson(new InputStreamReader(is, "UTF-8"), type);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		
		return menus;
	}
}
