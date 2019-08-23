package com.zcwng.shop.common;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static  <T> List<T> jsonToList(String json,Class<T> cls) throws JsonParseException, JsonMappingException, IOException{
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, cls);
		return mapper.readValue(json, javaType);
	}
	
	public static String toJson(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}
	
	public static <T> T jsonToObject(String json,Class<T> cla) throws IOException {
		return mapper.readValue(json, cla);
	}
}
