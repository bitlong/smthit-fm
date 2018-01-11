/**
 * 
 */
package org.springframework.data.jpa.repository.support;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Bean
 *
 */
@NoRepositoryBean
public interface TCJpaRepository<T, ID extends Serializable> extends
		JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	
	public void logicDelete(ID id);

	public void logicDelete(T entity);

	public void logicDelete(Iterable<? extends T> entities);

	public boolean logicExists(ID id);
}
