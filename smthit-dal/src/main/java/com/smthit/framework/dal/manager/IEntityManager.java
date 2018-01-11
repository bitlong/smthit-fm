/**
 * 
 */
package com.smthit.framework.dal.manager;

import java.util.Map;

/**
 * @author Bean
 *
 */
public interface IEntityManager<T> {
	public T update(Long id, Map<String, Object> params);
	public T create(Map<String, Object> params);
	public void delete(Long id);
	public void delete(T t);
	public void save(T t);
}
