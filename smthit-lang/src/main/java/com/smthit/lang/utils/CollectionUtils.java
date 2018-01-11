/**
 * 
 */
package com.smthit.lang.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bean
 *
 */
public class CollectionUtils {
	
	/**
	 * 将一个迭代器转化成一个List进行存储
	 * @param itr
	 * @return
	 */
	public static <T> List<T> toList(Iterable<T> itr) {
		if(itr instanceof List) {
			return (List<T>)itr;
		}
		
		List<T> arrayList = new ArrayList<T>();
		while(itr.iterator().hasNext()) {
			arrayList.add(itr.iterator().next());
		}
		
		return arrayList;
	}
}
