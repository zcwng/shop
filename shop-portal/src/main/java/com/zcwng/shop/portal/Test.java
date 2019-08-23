package com.zcwng.shop.portal;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcwng.shop.common.HttpClientUtil;

public class Test {

	public static void main(String[] args) throws ParseException, IOException {
		
		String adJson = HttpClientUtil.doGet("http://localhost:8083/rest/content/category/89");
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode root =mapper.readTree(adJson);
			JsonNode listNode =root.get("data");
			String text = mapper.writeValueAsString(listNode);
			List<HashMap<String,Object>>list = mapper.readValue(text,new TypeReference<List<HashMap<String,Object>>>() {});
			list.forEach(map->{
				System.out.println(map.get("url"));
			});
			
			
			//List<HashMap<String,Object>> list = mapper.readValue(text,new TypeReference<List<HashMap<String,Object>>>(){});
			
			//System.out.println(text);
		
		
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
