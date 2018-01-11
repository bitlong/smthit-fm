/**
 * 
 */
package com.smthit.lang.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.smthit.lang.utils.EnumStatus;

/**
 * @author Bean
 * 枚举类型在Gson中定制序列化适配
 */
public abstract class EnumSerializer<T extends EnumStatus> implements JsonSerializer<T>, JsonDeserializer<T>  {

	public EnumSerializer() {
	}

	@Override
	public JsonElement serialize(T t, Type type, JsonSerializationContext context) {
		EnumStatus status = (EnumStatus)t;
		return new JsonPrimitive(status.getValue());
	}

	@Override
	public T deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		return doSerialize((int)json.getAsInt());
	}	
	
	public abstract T doSerialize(int value); 
}
