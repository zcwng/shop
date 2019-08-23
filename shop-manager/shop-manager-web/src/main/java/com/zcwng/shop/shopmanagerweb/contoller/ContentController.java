package com.zcwng.shop.shopmanagerweb.contoller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcwng.shop.manager.pojo.TbContent;
import com.zcwng.shop.shopmanagerweb.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired ContentService contentService;
	
	@ResponseBody
	@RequestMapping("/query/list")
	public Object querylist(Long categoryId,int page,int rows) {
		
		return contentService.queryList(categoryId,page,rows);
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Object save(TbContent content) {
		
		return contentService.save(content);
	}
}
