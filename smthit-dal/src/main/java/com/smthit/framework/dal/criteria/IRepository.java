/**
 * 
 */
package com.smthit.framework.dal.criteria;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Bean
 *
 */
@NoRepositoryBean
public interface IRepository<T> extends CrudRepository<T, Long>, JpaSpecificationExecutor<T> {

}
