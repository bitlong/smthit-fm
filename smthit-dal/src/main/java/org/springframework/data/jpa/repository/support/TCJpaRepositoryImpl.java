/**
 * 
 */
package org.springframework.data.jpa.repository.support;

import static org.springframework.data.jpa.repository.query.QueryUtils.getQueryString;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.smthit.framework.dal.criteria.EntityModel2;

/**
 * @author Bean
 *
 */
@Repository  
@Transactional(readOnly = true)  
public class TCJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements JpaRepository<T, ID>,
	JpaSpecificationExecutor<T>, TCJpaRepository<T, ID> {

	private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";

	private final EntityManager em;
    private final JpaEntityInformation<T, ?> entityInformation;  
	
    public final static String DELETED_FIELD = "deleted";  
    
    public static final String COUNT_QUERY_STRING = "select count(%s) from %s x where x.deleted = false";  
    
	private static final String EQUALS_CONDITION_STRING = "%s.%s = :%s";

	private final PersistenceProvider provider;

	public TCJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
			EntityManager em) {
		super(entityInformation, em);
		
		this.em = em;
		this.entityInformation = entityInformation;
		this.provider = PersistenceProvider.fromEntityManager(em);
	}

	public TCJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}

	@Override
	@Transactional
	public void logicDelete(ID id) {
        T entity = findOne(id);  
        if (null == entity || !(entity instanceof EntityModel2)) {  
            return;  
        }  
        
        EntityModel2 model = (EntityModel2) entity;  
        model.setDeleted(true);  
  
        em.merge(model);
	}

	@Override
	@Transactional
	public void logicDelete(T entity) {
        if (null == entity || !(entity instanceof EntityModel2)) {  
            return;  
        }  
  
        EntityModel2 model = (EntityModel2) entity;  
        model.setDeleted(true);  
  
        if (model.getId() <= 0) {  
            em.persist(model);  
        } else {  
            em.merge(model);  
        }  
	}

	@Override
	@Transactional
	public void logicDelete(Iterable<? extends T> entities) {
        if (null == entities) {  
            return;  
        }  
  
        for (T entity : entities) {  
            logicDelete(entity);  
        }
	}
	
	@Override
	public long count() {
        return em.createQuery(getCountQueryString(), Long.class)  
                .getSingleResult();
	}

	protected String getCountQueryPlaceholder() {    	  
        return "x";  
    }  

    private String getCountQueryString() {  
        String countQuery = String.format(COUNT_QUERY_STRING, getCountQueryPlaceholder(), "%s");  
        return getQueryString(countQuery, entityInformation.getEntityName());  
    }

	public boolean logicExists(ID id) {
		Assert.notNull(id, ID_MUST_NOT_BE_NULL);

		if(EntityModel2.class.isAssignableFrom(getDomainClass())) {
			if (entityInformation.getIdAttribute() == null) {
				 EntityModel2 entity = (EntityModel2)findOne(id);
				 if(entity != null) {
					 return !entity.isDeleted();
				 } else {
					 return false;
				 }
			}
			
			String placeholder = provider.getCountQueryPlaceholder();
			String entityName = entityInformation.getEntityName();
			Iterable<String> idAttributeNames = entityInformation.getIdAttributeNames();

			StringBuilder sb = new StringBuilder(String.format(COUNT_QUERY_STRING, placeholder, entityName));

			for (String idAttribute : idAttributeNames) {
				sb.append(" AND ");
				sb.append(String.format(EQUALS_CONDITION_STRING, "x", idAttribute, idAttribute));
			}
			
			String existsQuery = sb.toString();

			TypedQuery<Long> query = em.createQuery(existsQuery, Long.class);

			if (!entityInformation.hasCompositeId()) {
				query.setParameter(idAttributeNames.iterator().next(), id);
				return query.getSingleResult() == 1L;
			}

			for (String idAttributeName : idAttributeNames) {

				Object idAttributeValue = entityInformation.getCompositeIdAttributeValue(id, idAttributeName);

				boolean complexIdParameterValueDiscovered = idAttributeValue != null
						&& !query.getParameter(idAttributeName).getParameterType().isAssignableFrom(idAttributeValue.getClass());

				if (complexIdParameterValueDiscovered) {
					// fall-back to findOne(id) which does the proper mapping for the parameter.
					 EntityModel2 entity = (EntityModel2)findOne(id);
					 if(entity != null) {
						 return !entity.isDeleted();
					 } else {
						 return false;
					 }
				}

				query.setParameter(idAttributeName, idAttributeValue);
			}

			return query.getSingleResult() == 1L;
			
		} else {
			return exists(id);
		}
	}
	
	/**
	 * Creates a {@link TypedQuery} for the given {@link Specification} and {@link Sort}.
	 *
	 * @param spec can be {@literal null}.
	 * @param domainClass must not be {@literal null}.
	 * @param sort can be {@literal null}.
	 * @return
	 */
	protected <S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<S> query = builder.createQuery(domainClass);

		Root<S> root = applySpecificationToCriteria(spec, domainClass, query);
		query.select(root);

		if (sort != null) {
			query.orderBy(toOrders(sort, root, builder));
		}

		return applyRepositoryMethodMetadata(em.createQuery(query));
	}

	/**
	 * Creates a new count query for the given {@link Specification}.
	 *
	 * @param spec can be {@literal null}.
	 * @param domainClass must not be {@literal null}.
	 * @return
	 */
	protected <S extends T> TypedQuery<Long> getCountQuery(Specification<S> spec, Class<S> domainClass) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

		if (query.isDistinct()) {
			query.select(builder.countDistinct(root));
		} else {
			query.select(builder.count(root));
		}

		return em.createQuery(query);
	}

	/**
	 * Applies the given {@link Specification} to the given {@link CriteriaQuery}.
	 *
	 * @param spec can be {@literal null}.
	 * @param domainClass must not be {@literal null}.
	 * @param query must not be {@literal null}.
	 * @return
	 * 
	 * 重写applySpecificationToCriteria，根据applySpecificationToCriteria查询的都加上逻辑删除过滤的条件
	 */
	private <S, U extends T> Root<U> applySpecificationToCriteria(Specification<U> spec, Class<U> domainClass,
			CriteriaQuery<S> query) {

		Assert.notNull(query);
		Assert.notNull(domainClass);
		Root<U> root = query.from(domainClass);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		//增加逻辑删除的代码
		Predicate deletedPredicate = null;
		if(EntityModel2.class.isAssignableFrom(getDomainClass())) {
			Path<Boolean> deletePath = root.<Boolean>get(DELETED_FIELD);
			deletedPredicate = builder.isFalse(deletePath);
		}

		if (spec == null) {
			query.where(deletedPredicate);
			return root;
		}
		
		Predicate predicate = spec.toPredicate(root, query, builder);
		
		if (predicate != null) {
			if(deletedPredicate != null) {
				predicate = builder.and(predicate, deletedPredicate);
			}
			query.where(predicate);
		}

		return root;
	}

	private <S> TypedQuery<S> applyRepositoryMethodMetadata(TypedQuery<S> query) {

		if (getRepositoryMethodMetadata() == null) {
			return query;
		}

		LockModeType type = getRepositoryMethodMetadata().getLockModeType();
		TypedQuery<S> toReturn = type == null ? query : query.setLockMode(type);

		applyQueryHints(toReturn);

		return toReturn;
	}

	private void applyQueryHints(Query query) {
		for (Entry<String, Object> hint : getQueryHints().entrySet()) {
			query.setHint(hint.getKey(), hint.getValue());
		}
	}
}
