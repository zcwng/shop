package com.zcwng.shop.shopmanagerweb.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itshidu.web.tools.Result;
import com.zcwng.shop.manager.pojo.TbItem;
import com.zcwng.shop.shopmanagerweb.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired ItemService itemService;
	
	@ResponseBody
	@RequestMapping("list")
	public Object list(int page,int rows) {		
		return itemService.list(page, rows);
	}
	
	@ResponseBody
	@RequestMapping("/rest/delete")
	public Object itemDelete(String ids) {		
		return itemService.delete(ids);
	}
	
	@ResponseBody
	@RequestMapping("/rest/instock")
	public Object instock(String ids) {		
		return itemService.instock(ids);
	}
	
	@ResponseBody
	@RequestMapping("/rest/reshelf")
	public Object reshelf(String ids) {		
		return itemService.reshelf(ids);
	}
	/* 根据分类ID获取子分类信息，当不传id时默认是0  */
	@ResponseBody
	@RequestMapping("/cat/list")
	public Object catList(@RequestParam(defaultValue ="0") Long id) {		
		return itemService.categoryList(id);
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Object save(TbItem item,String desc,String itemParams) {		 
		return itemService.save(item,desc,itemParams);
	}
	
	@ResponseBody
	@RequestMapping("/param/query/itemcatid/{itemCatId}")
	public Object itemParamQuery(@PathVariable Long itemCatId) {		
		return itemService.itemParemQuery(itemCatId);
	}
	
	@ResponseBody
	@RequestMapping("/param/save/{itemCatId}")
	public Object paramSave(@PathVariable Long itemCatId,String paramData) {		
		return itemService.paramSave(itemCatId,paramData);
	}
	
	@ResponseBody
	@RequestMapping("/param/list")
	public Object paramlist(@RequestParam(defaultValue="1") int page,int rows) {		
		return itemService.paramlist(page,rows);
	}
}
