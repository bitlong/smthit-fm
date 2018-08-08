/**
 * 
 */
package com.smthit.lang.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Bean
 *
 */
public class MapUtils {

	/**
	 * 移除map中空key或者value空值
	 * @param map
	 */
	public static void removeNullEntry(Map<?, ?> map){
		removeNullKey(map);
		removeNullValue(map);
	}
	
	/**
	 * 移除map的空key
	 * @param map
	 * @return
	 */
	public static void removeNullKey(Map<?, ?> map){
		Set<?> set = map.keySet();
		for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			remove(obj, iterator);
		}
	}
	
	/**
	 * 移除map中的value空值
	 * @param map
	 * @return
	 */
	public static void removeNullValue(Map<?, ?> map){
		Set<?> set = map.keySet();
		for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			Object value =(Object)map.get(obj);
			remove(value, iterator);
		}
	}
	
	/**
	 * Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。 
	 * Iterator 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，
	 * 所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出 java.util.ConcurrentModificationException 异常。
	 * 所以 Iterator 在工作的时候是不允许被迭代的对象被改变的。
	 * 但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove() 方法会在删除当前迭代对象的同时维护索引的一致性。
	 * @param obj
	 * @param iterator
	 */
	private static void remove(Object obj,Iterator<?> iterator){
		if(obj instanceof String){
			String str = (String)obj;
			if(StringUtils.isBlank(str)){
				iterator.remove();
			}
		}else if(obj instanceof Collection){
			Collection<?> col = (Collection<?>)obj;
			if(col==null||col.isEmpty()){
				iterator.remove();
			}
			
		}else if(obj instanceof Map){
			Map<?, ?> temp = (Map<?, ?>)obj;
			if(temp==null||temp.isEmpty()){
				iterator.remove();
			}
			
		}else if(obj instanceof Object[]){
			Object[] array =(Object[])obj;
			if(array==null||array.length<=0){
				iterator.remove();
			}
		}else{
			if(obj==null){
				iterator.remove();
			}
		}
	}
}
