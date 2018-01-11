package com.smthit.lang.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class HibernateObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5800681063330490516L;

	public HibernateObjectMapper() {
		super();
		
		disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
}
