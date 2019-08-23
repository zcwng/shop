package com.zcwng.shop.search.controller;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itshidu.web.tools.Result;
import com.zcwng.shop.search.entity.ItemInfo;
import com.zcwng.shop.search.mapper.ItemInfoMapper;
import com.zcwng.shop.search.service.SearchService;
/**
 * 你们都曾为自己的信念死过一次，
 * 你们的英勇撼动了吾王莫甘娜的心灵，
 * 你们将在自由的国度获得重生，
 * 一千年后，你们的文明将会永远留在你们的记忆力，
 * 那是你们作为凡人奋战过的地方。
 * 但你们的故事，会在别的文明延续，
 * 去那里，作为超级战士，
 * 为吾王而战。。。
 * @author Administrator
 *
 */

@Controller
public class ContentController {

	@Autowired SearchService serachService;;
	
	@Autowired ItemInfoMapper mapper;
	@Autowired SolrClient solr; 
	
	@ResponseBody
	@RequestMapping("/hello")
	public Object hello() throws SolrServerException, IOException {
		
		List<ItemInfo> list = mapper.selectforsolr();
		
/*		list.forEach(item->{
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_sell_point", item.getSellPoint());
			doc.addField("item_price",item.getPrice());
			doc.addField("item_image",item.getImage());
			doc.addField("item_category_name", item.getCategoryName());
			doc.addField("item_desc",item.getDesc());
			try {
				solr.add(doc);
			} catch (SolrServerException e) {
			} catch (IOException e) {
			}
		});		
		solr.commit();		
*/		return "ok";
	}
	
	
	@ResponseBody
	@RequestMapping("/query")
	public Object query(String kw,@RequestParam(defaultValue="1")int page) {
		if(StringUtils.isEmpty(kw)) {
			return Result.of(400,"关键字是必填项");
		}
		
		return serachService.query(kw,page);
	}
	
}
