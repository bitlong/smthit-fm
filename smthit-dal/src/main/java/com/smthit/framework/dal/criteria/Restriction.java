package com.smthit.framework.dal.criteria;

import org.springframework.data.domain.Pageable;

public class Restriction<T> {
	
	private Criteria<T> criteria;
	private Pageable pageable;

	public Restriction() {
		criteria = new Criteria<T>();
	}
	
	public Restriction(Pageable pageable) {
		criteria = new Criteria<T>();
		this.pageable = pageable;
	}
	
	public Criteria<T> getCriteria() {
		return criteria;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
	
	public Criteria<T> addCriteria(Criterion criterion) {
		if(criterion != null) {
			criteria.add(criterion);
		}
		return criteria;
	}
}
