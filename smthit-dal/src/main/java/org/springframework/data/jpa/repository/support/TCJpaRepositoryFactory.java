/**
 * 
 */
package org.springframework.data.jpa.repository.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

/**
 * @author Bean
 *
 */
public class TCJpaRepositoryFactory<S, ID extends Serializable> extends JpaRepositoryFactory {

	public TCJpaRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
	}

	@SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
	@Override
	protected <T, ID extends Serializable> SimpleJpaRepository<T, ID> getTargetRepository(
			RepositoryInformation information, EntityManager entityManager) {
		return new TCJpaRepositoryImpl(information.getDomainType(), entityManager);
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return TCJpaRepositoryImpl.class;
	}	
}
