package com.zcwng.shop.shopmanagerweb.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.manager.mapper.TbContentMapper;
import com.zcwng.shop.manager.pojo.TbContent;
import com.zcwng.shop.manager.pojo.TbContentExample;
import com.zcwng.shop.shopmanagerweb.service.ContentService;

@Service
public class ContentServiceimpl implements ContentService {

	@Autowired TbContentMapper contentMapper;
	@Autowired StringRedisTemplate redis;
	
	@Override
	public Object queryList(Long categoryId, int page, int rows) {
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(page,rows);
		List<TbContent> list = contentMapper.selectByExample(example);
		PageInfo<TbContent> info = new PageInfo<>(list);
		return Result.of().put("total", info.getTotal()).put("rows",list);
	}

	@Override
	public Object save(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		
		redis.delete("ContentList");	//数据删除，删除是为了更好的重建
		
		return Result.of(200,"内容添加成功");
	}

}
