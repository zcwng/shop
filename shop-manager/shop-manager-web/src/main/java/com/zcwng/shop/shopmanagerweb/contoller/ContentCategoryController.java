package com.zcwng.shop.shopmanagerweb.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcwng.shop.shopmanagerweb.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired ContentCategoryService cateService;
	
	@ResponseBody
	@RequestMapping("/list")
	public Object list(@RequestParam(defaultValue="0", name="id") Long parentId) {		
		return cateService.getContentCategoryList(parentId).get("date");
	}
	
	@ResponseBody
	@RequestMapping("/create")
	public Object create(Long parentId,String name) {		
		return cateService.create(parentId,name);
	}
}
