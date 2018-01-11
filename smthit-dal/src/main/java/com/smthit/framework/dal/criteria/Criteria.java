/**
 * 
 */
package com.smthit.framework.dal.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author Bean
 *
 */
public class Criteria<T> implements Specification<T>{
	private List<Criterion> criterions = new ArrayList<Criterion>(); 
	
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
        
		if (!criterions.isEmpty()) {
            List<Predicate> predicates = new ArrayList<Predicate>();  
            for(Criterion c : criterions){  
                predicates.add(c.toPredicate(root, query, builder));  
            }
            
            if (predicates.size() > 0) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));  
            }
        }
        
		return builder.conjunction();
	}

    public Criteria<T> add(Criterion criterion){  
        if(criterion!=null){  
            criterions.add(criterion);  
        }  
        
        return this;
    }
}
