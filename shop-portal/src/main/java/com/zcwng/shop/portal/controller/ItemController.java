package com.zcwng.shop.portal.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zcwng.shop.common.HttpClientUtil;
import com.zcwng.shop.common.JsonUtils;
import com.zcwng.shop.portal.vo.Item;

@Controller
public class ItemController {

	@RequestMapping("/item/{itemId}")
	public Object item(@PathVariable String itemId,ModelAndView v) {
		System.out.println(itemId);
		
		String jsonText = HttpClientUtil.doGet("http://localhost:8083/rest/item/"+itemId);
		System.out.println(jsonText);
		try {
			Item item = JsonUtils.jsonToObject(jsonText, Item.class);
			v.addObject("item",item);
			v.setViewName("item");
			return v;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	@ResponseBody
	@RequestMapping("/item/desc/{itemId}")
	public Object itemDesc(@PathVariable String itemId) {
		System.out.println(itemId);
		
		String jsonText = HttpClientUtil.doGet("http://localhost:8083/rest/item/desc/"+itemId);
		System.out.println(jsonText);
		
		return jsonText;
	}
	
	@ResponseBody
	@RequestMapping("/item/param/{itemId}")
	public Object itemparam(@PathVariable String itemId) {
		System.out.println(itemId);
		
		String jsonText = HttpClientUtil.doGet("http://localhost:8083/rest/item/param/"+itemId);
		System.out.println(jsonText);
		
		return jsonText;
	}
}
