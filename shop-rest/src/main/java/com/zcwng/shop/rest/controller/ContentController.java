package com.zcwng.shop.rest.controller;
//

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcwng.shop.manager.mapper.TbContentMapper;
import com.zcwng.shop.manager.pojo.TbContent;
import com.zcwng.shop.manager.pojo.TbContentExample;
import com.itshidu.web.tools.Result;

@Controller
public class ContentController {
	
	@Autowired TbContentMapper contentMapper;
	@Autowired StringRedisTemplate redis;

	/**
	 * 根据类别ID获取内容列表
	 * @param cid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/rest/content/category/{cid}")
	public Object a(@PathVariable Long cid) throws Exception {
		String text = null;
		//解决缓存击穿问题
		synchronized (redis) {
			text=(String) redis.opsForHash().get("ContentList", cid.toString());
		}
		if(text==null) {
			System.out.println("缓存未命中，执行SQL");
			TbContentExample example=new TbContentExample();
			example.createCriteria().andCategoryIdEqualTo(cid);
			List<TbContent> list = contentMapper.selectByExample(example);
			String str = new ObjectMapper().writeValueAsString(list);
			redis.opsForHash().put("ContentList", cid.toString(), str);
			return str;
		}
		System.out.println("缓存命中，直接使用");
		return text;
	}
}
