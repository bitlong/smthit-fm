package com.smthit.lang.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ValidateUtil {
 
	public static <T> boolean isEmpty(T t){
    	return !isNotEmpty(t);
    }
    public static <T> boolean isNotEmpty(T t){
    	boolean result = false;
    	if(t instanceof List){
    		result = isNotEmpty((List<?>)t);
    	}else if(t instanceof String){
    		result = isNotEmpty(t.toString());
    	}if(t instanceof Map){
    		result = isNotEmpty((Map<?,?>)t);
    	}else if(t instanceof Object){
    		result = isNotEmptyObj(t);
    	}
    	return result;
    }
    private static boolean isNotEmptyObj(Object str){
    	return str != null;
    }
    private static boolean isNotEmpty(Map<?,?> map){
    	return map != null && map.size() > 0;
    }
    private static boolean isNotEmpty(List<?> list){
    	return list != null && list.size() > 0;
    }
    
    private static boolean isNotEmpty(String str){
    	return str != null && !"".equals(str);
    }
    
    public static boolean isNumStr(String numStr, int min, int max){
    	return isNotEmpty(numStr) && numStr.matches("\\d{" +min+ "," +max+ "}");
    }
    
    public static void main(String[] args){
    	System.out.println(isNotEmpty(new ArrayList<String>()));
    }
}