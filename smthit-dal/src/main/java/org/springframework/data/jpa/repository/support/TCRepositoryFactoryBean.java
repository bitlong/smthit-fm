/**
 * 
 */
package org.springframework.data.jpa.repository.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author Bean
 *
 */
public class TCRepositoryFactoryBean<R extends JpaRepository<S, ID>, S, ID extends Serializable>
		extends JpaRepositoryFactoryBean<R, S, ID> {

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(
			EntityManager entityManager) {
		return new TCJpaRepositoryFactory<R, ID>(entityManager);
	}

}
