/**
 * 
 */
package com.smthit.lang.json.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Bean
 *
 */
public class LongJsonSerializer extends JsonSerializer<Long> {
	@Override
	public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		String text = (value == null ? null : String.valueOf(value));
		if (text != null) {
			jsonGenerator.writeString(text);
		}
	}

	public static LongJsonSerializer instance() {
		try {
			return LongJsonSerializer.class.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
}
