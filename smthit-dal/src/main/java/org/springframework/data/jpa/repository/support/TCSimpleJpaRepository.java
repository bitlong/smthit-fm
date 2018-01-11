/**
 * 
 */
package org.springframework.data.jpa.repository.support;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * @author Bean
 *
 */
public class TCSimpleJpaRepository<T> extends SimpleJpaRepository<T, Long> {

	public TCSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
	}

	
}
