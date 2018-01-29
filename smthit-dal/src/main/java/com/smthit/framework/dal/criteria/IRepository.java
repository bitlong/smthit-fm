/**
 * 
 */
package com.smthit.framework.dal.criteria;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Bean
 *
 */
@NoRepositoryBean
public interface IRepository<T> extends CrudRepository<T, Long>, JpaSpecificationExecutor<T> {
	
	//public List<T> findAll();

	//public List<T> findAll(Set<Long> ids);
}
