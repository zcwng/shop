package com.zcwng.shop.shopmanagerweb.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.manager.mapper.TbContentCategoryMapper;
import com.zcwng.shop.manager.pojo.TbContentCategory;
import com.zcwng.shop.manager.pojo.TbContentCategoryExample;
import com.zcwng.shop.shopmanagerweb.service.ContentCategoryService;

@Service
public class CententCategoryServiceimpl implements ContentCategoryService{

	@Autowired TbContentCategoryMapper contentCategoryMapper;
	
	ObjectMapper mapper = new ObjectMapper();
	@Override
	public Result getContentCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		ArrayNode array = mapper.createArrayNode();
		list.forEach(cate->{
			ObjectNode node = mapper.createObjectNode();
			node.put("id", cate.getId());
			node.put("text", cate.getName());
			if(cate.getIsParent()) {
				node.put("state", "closed");
			}else {
				node.put("state", "open");
			}
			array.add(node);
		});
	
		try {
			return Result.of().put("date",mapper.writeValueAsString(array));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Result.of();
	}
	@Override
	
	public Object create(Long parentId, String name) {
		Date date = new Date();
		TbContentCategory cate = new TbContentCategory();
		cate.setCreated(date);
		cate.setUpdated(date);
		cate.setName(name);
		cate.setStatus(1);	//1正常2删除
		cate.setIsParent(false);
		cate.setParentId(parentId);
		cate.setSortOrder(1);
		contentCategoryMapper.insert(cate);
		
		//获取父节点，如果父节点是parent则跳过，如果不是，则该状态
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		return Result.of(200, "创建成功", cate);
	}

}
