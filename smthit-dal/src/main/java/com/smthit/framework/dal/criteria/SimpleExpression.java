/**
 * 
 */
package com.smthit.framework.dal.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;


/**
 * @author Bean
 *
 */
public class SimpleExpression implements Criterion {

	private String fieldName;
	private Object value;
	private Operator operator;

	protected SimpleExpression(String fieldName, Object value, Operator operator) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getValue() {
		return value;
	}

	public Operator getOperator() {
		return operator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ilego.lecms.common.dao.Criterion#toPredicate(javax.persistence.criteria
	 * .Root, javax.persistence.criteria.CriteriaQuery,
	 * javax.persistence.criteria.CriteriaBuilder)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	@Override
	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		Path expression = null;
		
		if (fieldName.contains(".")) {
			String[] names = StringUtils.split(fieldName, '.');
			expression = root.get(names[0]);
			for (int i = 1; i < names.length; i++) {
				expression = expression.get(names[i]);
			}
		} else {
			expression = root.get(fieldName);
		}

		switch (operator) {
		case EQ:
			return builder.equal(expression, value);
		case NE:
			return builder.notEqual(expression, value);
		case LIKE:
			return builder.like((Expression<String>) expression, "%" + value
					+ "%");
		case LT:
			return builder.lessThan(expression, (Comparable) value);
		case GT:
			return builder.greaterThan(expression, (Comparable) value);
		case LTE:
			return builder.lessThanOrEqualTo(expression, (Comparable) value);
		case GTE:
			return builder.greaterThanOrEqualTo(expression, (Comparable) value);
		case ISNULL:
			return builder.isNull(expression);
		default:
			return null;
		}
	}
}
