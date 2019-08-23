package com.zcwng.shop.portal.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.common.HttpClientUtil;
import com.zcwng.shop.common.JsonUtils;
import com.zcwng.shop.common.Paging;
import com.zcwng.shop.portal.service.SearchService;
import com.zcwng.shop.portal.vo.ItemInfo;

@Service
public class SearchServiceImpl implements SearchService{

	ObjectMapper om = new ObjectMapper();
	
	@Override
	public Paging<ItemInfo> search(String q,int page) throws JsonProcessingException, IOException {
		//门户网站从Search服务中远程获取数据
		String url="http://localhost:8084/query?kw="+q+"&page="+page;
		String json=HttpClientUtil.doGet(url);
		JsonNode root = om.readTree(json);
		long count = root.get("total").asLong();
		String content = root.get("list").toString();
		//System.out.println(content);
		List<ItemInfo> list = JsonUtils.jsonToList(content, ItemInfo.class);

		return new Paging<ItemInfo>(list, page, count, 20);
	}

}
