package com.zcwng.shop.shopmanagerweb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.manager.mapper.TbItemCatMapper;
import com.zcwng.shop.manager.mapper.TbItemDescMapper;
import com.zcwng.shop.manager.mapper.TbItemMapper;
import com.zcwng.shop.manager.mapper.TbItemParamItemMapper;
import com.zcwng.shop.manager.mapper.TbItemParamMapper;
import com.zcwng.shop.manager.pojo.TbItem;
import com.zcwng.shop.manager.pojo.TbItemCat;
import com.zcwng.shop.manager.pojo.TbItemCatExample;
import com.zcwng.shop.manager.pojo.TbItemCatExample.Criteria;
import com.zcwng.shop.manager.pojo.TbItemDesc;
import com.zcwng.shop.manager.pojo.TbItemExample;
import com.zcwng.shop.manager.pojo.TbItemParam;
import com.zcwng.shop.manager.pojo.TbItemParamExample;
import com.zcwng.shop.manager.pojo.TbItemParamItem;
import com.zcwng.shop.shopmanagerweb.service.ItemService;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{

	@Autowired TbItemMapper itemMpper;
	@Autowired TbItemCatMapper itemCatMapper;
	@Autowired TbItemDescMapper itemDescMapper;
	@Autowired TbItemParamMapper itemParamMapper;
	@Autowired TbItemParamItemMapper paramItemMapper;
	
	@Override
	public Result list(int page, int rows) {
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMpper.selectByExample(new TbItemExample());
		PageInfo<TbItem> info = new PageInfo(list);
		return Result.of()
				.put("code", 200)
				.put("total", info.getTotal())
				.put("rows", list);
	}

	@Override
	public Result delete(String ids) {
		String arr[] = ids.split(",");
		for(String str:arr) {
			Long id = Long.parseLong(str);
			itemMpper.deleteByPrimaryKey(id);
		}
		return Result.of(200,"删除成功");
	}

	@Override
	public Result instock(String ids) {
		String arr[] = ids.split(",");
		for(String str:arr) {
			Long id = Long.parseLong(str);
			TbItem item= itemMpper.selectByPrimaryKey(id);
			item.setStatus((byte)2);
			itemMpper.updateByPrimaryKey(item);//通过对象的id修改属性
		}
		return Result.of(200,"下架成功");
	}

	@Override
	public Result reshelf(String ids) {
		String arr[] = ids.split(",");
		for(String str:arr) {
			Long id = Long.parseLong(str);
			TbItem item= itemMpper.selectByPrimaryKey(id);
			item.setStatus((byte)1);
			itemMpper.updateByPrimaryKey(item);//通过对象的id修改属性
		}
		return Result.of(200,"上架成功");
	}

	@Override
	public Object categoryList(long parent) {

		TbItemCatExample example = new TbItemCatExample();//条件对象
		example.createCriteria().andParentIdEqualTo(parent);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List<Result> data = new ArrayList<>();
		list.forEach(c->{
			data.add(
					Result.of()
					.put("id",c.getId())
					.put("text",c.getName())
					.put("state",c.getIsParent()?"closed":"open"));
		});
		return data;
	}

	@Override
	public Object save(TbItem item,String desc,String itemParams) {
		
		long id = System.currentTimeMillis();//分布式情况下一定要保证ID不重复，这种写法是错的
		
		Date date = new Date();
		
		item.setId(id);
		item.setCreated(date);
		item.setUpdated(date);
		item.setStatus((byte)1);
		itemMpper.insert(item);
		
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(id);
		itemDescMapper.insertSelective(itemDesc);
		
		TbItemParamItem paramValue = new TbItemParamItem();
		paramValue.setCreated(date);
		paramValue.setUpdated(date);
		paramValue.setItemId(id);
		paramValue.setParamData(itemParams);
		paramItemMapper.insert(paramValue);
		
		return Result.of(200, "添加商品成功");
	}

	@Override
	public Object itemParemQuery(Long itemCatId) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(itemCatId);
		// 根据类别ID,查询规格属性
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if(list.isEmpty()||list.size()==0) {	//两种方法
			return Result.of(100);
		}
		return Result.of(200).put("data", list.get(0));
	}

	@Override
	public Object paramSave(Long itemCatId,String paramData) {
		//TbItemParamExample example = new TbItemParamExample();
		//example.createCriteria().andItemCatIdEqualTo(itemCatId);
		//List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		
		Date date = new Date();
		TbItemParam param = new TbItemParam();
		param.setCreated(date);
		param.setUpdated(date);
		param.setItemCatId(itemCatId);
		param.setParamData(paramData);
		itemParamMapper.insert(param);
		return Result.of(200);
	}

	@Override
	public Object paramlist(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		
		PageInfo<TbItemParam> info = new PageInfo<>(list);
		return Result.of()
				.put("total", info.getTotal())
				.put("rows", list);
	}

	
}
