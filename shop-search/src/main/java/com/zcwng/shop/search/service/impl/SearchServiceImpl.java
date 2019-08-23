package com.zcwng.shop.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itshidu.web.tools.Result;
import com.zcwng.shop.search.entity.ItemInfo;
import com.zcwng.shop.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{

	@Autowired SolrClient solrClient;
	
	@Value("${SEARCH_RESULT_PAGE_SIZE}")
	int PAGE_SIZE;
	
	@Override
	public Result query(String kw, int page) {
		SolrQuery solrQuery = new SolrQuery(kw);
		solrQuery.setStart((page -1) * PAGE_SIZE);
		solrQuery.setRows(PAGE_SIZE);
		//高亮显示
		solrQuery.setHighlight(true);
		//设置高亮显示的域
		solrQuery.addHighlightField("item_title");
		//高亮显示前缀
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		//后缀
		solrQuery.setHighlightSimplePost("</em>");
		//设置默认搜索域
		solrQuery.set("df", "item_keywords");
		 
		try {
			QueryResponse resp = solrClient.query(solrQuery);	//商品查询
			SolrDocumentList documentList = resp.getResults();	//商品列表
			List<ItemInfo> list = new ArrayList<ItemInfo>();
			
			for(SolrDocument doc:documentList) {
				ItemInfo item = new ItemInfo();
				
				item.setId(Long.parseLong((String) doc.get("id")));
				item.setPrice((Long) doc.get("item_price"));
				item.setSellPoint((String) doc.get("item_sell_point"));
				item.setImage((String) doc.get("item_image"));
				item.setCategoryName((String) doc.get("item_category_name"));
				item.setTitle((String)doc.get("item_title"));
				list.add(item);
			}
			return Result.of(200).put("list", list)
								 .put("total", documentList.getNumFound());
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
