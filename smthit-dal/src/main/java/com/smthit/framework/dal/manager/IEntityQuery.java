/**
 * 
 */
package com.smthit.framework.dal.manager;

import org.springframework.data.domain.Pageable;

import com.smthit.framework.dal.criteria.Restriction;
import com.smthit.framework.dal.data.Pagination;

/**
 * @author Bean
 *
 */
public interface IEntityQuery<T> {
	public T getById(Long id);
	public Pagination<T> pagingData(Pageable pagable);
	public Pagination<T> pagingData(Restriction<T> restriction);
}
