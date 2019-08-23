package com.zcwng.shop.rest;


import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zcwng.shop.manager.mapper.TbItemCatMapper;
import com.zcwng.shop.manager.pojo.TbItemCat;
import com.zcwng.shop.manager.pojo.TbItemCatExample;



@SpringBootApplication
@MapperScan("com.zcwng.shop.manager.mapper")
@RestController
public class ShopRestApplication {
	
	@Autowired TbItemCatMapper itemCatMapper;

	public static void main(String[] args) {
		SpringApplication.run(ShopRestApplication.class, args);
	}
	
	
	@RequestMapping("/rest/itemcat/all")
	public Object itemCatAll() throws JsonProcessingException {
		ObjectNode root = mapper.createObjectNode();
		root.set("data", run(0)); //put放普通属性，set放子元素
		String s=mapper.writeValueAsString(root);
		return "category.getDataService("+s+");";
	}
	
	ObjectMapper mapper = new ObjectMapper();
	
	private ArrayNode run(long parentId) {
		TbItemCatExample example=new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example); //parent 0 
		ArrayNode array = mapper.createArrayNode();
		list.forEach(cat->{
			if(cat.getIsParent()) {
				ObjectNode cn = mapper.createObjectNode();
				cn.put("u", "/products/"+cat.getId()+".html");
				cn.put("n", String.format("<a href='/products/%s.html'>%s</a>", cat.getId(),cat.getName()));
				cn.set("i", run(cat.getId()));
				array.add(cn);
			}else {
				array.add(String.format("/products/%s|%s", cat.getId(),cat.getName()));
			}
		});
		
		return array;
	}
	
	
}

