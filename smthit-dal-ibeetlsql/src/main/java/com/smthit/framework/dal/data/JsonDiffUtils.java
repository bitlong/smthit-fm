/**
 * 
 */
package com.smthit.framework.dal.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Bean
 *
 */
public class JsonDiffUtils {

	public JsonDiffUtils() {
	}

	/**
	 * 比较两个Json文件属性是否有变化，返回有变化的属性
	 * 
	 * @param srcJson
	 * @param destJson
	 * @return
	 */
	public static String diff(String srcJson, String destJson) {
		JsonParser parser = new JsonParser();
		JsonObject objSrc = (JsonObject) parser.parse(srcJson);

		JsonParser parser1 = new JsonParser();
		JsonObject objDest = (JsonObject) parser1.parse(destJson);

		List<DiffContent> differContents = new ArrayList<DiffContent>();

		if (objSrc != null && objDest != null) {
			Set<Entry<String, JsonElement>> elements = objSrc.entrySet();
			for (Entry<String, JsonElement> entry : elements) {
				String key = entry.getKey();
				JsonElement ele = entry.getValue();
				diff(ele, objDest.get(key), key, differContents);
			}
		}

		if (differContents.size() > 0) {
			// 内容发生变化
			return toJson(differContents);
		}

		return null;
	}

	private static void diff(JsonElement src, JsonElement dest, String path, List<DiffContent> differContents) {
		if (src == null || dest == null)
			return;

		if (src.isJsonPrimitive() && dest.isJsonPrimitive()) {
			if (!src.equals(dest)) {
				differContents.add(new DiffContent(path, src.getAsString(), dest.getAsString()));
			}
		} else if (src.isJsonArray() && dest.isJsonArray()) {
			JsonArray srcArray = (JsonArray) src;
			JsonArray destArray = (JsonArray) dest;
			if (srcArray.size() != destArray.size()) {
				differContents.add(new DiffContent(path, srcArray.toString(), destArray.toString()));
			}
		} else if (src.isJsonObject() && dest.isJsonObject()) {
			JsonObject srcObj = (JsonObject) src;
			JsonObject destObj = (JsonObject) dest;

			if (srcObj != null && srcObj != null) {
				Set<Entry<String, JsonElement>> elements = srcObj.entrySet();
				for (Entry<String, JsonElement> entry : elements) {
					String key = entry.getKey();
					JsonElement ele = entry.getValue();
					diff(ele, destObj.get(key), path + "." + key, differContents);
				}
			}
		} else if (src.isJsonNull() && dest.isJsonObject()) {
			differContents.add(new DiffContent(path, "", dest.toString()));
		} else if (src.isJsonObject() && dest.isJsonNull()) {
			differContents.add(new DiffContent(path, src.toString(), ""));
		} else {
			differContents.add(new DiffContent(path, src.toString(), dest.toString()));
		}
	}

	public static String toJson(Object o) {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.addSerializationExclusionStrategy(new IgnoreStrategy());
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		gsonBuilder.setPrettyPrinting();

		return gsonBuilder.create().toJson(o);

	}
}
