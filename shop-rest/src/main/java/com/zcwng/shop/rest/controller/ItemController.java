package com.zcwng.shop.rest.controller;
//

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.solr.common.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcwng.shop.common.JsonUtils;
import com.zcwng.shop.manager.mapper.TbContentMapper;
import com.zcwng.shop.manager.mapper.TbItemDescMapper;
import com.zcwng.shop.manager.mapper.TbItemMapper;
import com.zcwng.shop.manager.mapper.TbItemParamItemMapper;
import com.zcwng.shop.manager.mapper.TbItemParamMapper;
import com.zcwng.shop.manager.pojo.TbContent;
import com.zcwng.shop.manager.pojo.TbContentExample;
import com.zcwng.shop.manager.pojo.TbItem;
import com.zcwng.shop.manager.pojo.TbItemDesc;
import com.zcwng.shop.manager.pojo.TbItemParam;
import com.zcwng.shop.manager.pojo.TbItemParamItem;
import com.zcwng.shop.manager.pojo.TbItemParamItemExample;
import com.itshidu.web.tools.Result;

@RestController
@RequestMapping("/rest/item")
public class ItemController {
	
	@Autowired TbContentMapper contentMapper;
	@Autowired TbItemMapper itemMapper;
	@Autowired TbItemDescMapper itemDescMapper;
	@Autowired TbItemParamItemMapper itemParamItemMapper;
	@Autowired StringRedisTemplate redis;
	@Autowired RedissonClient redisson;
	/**
	 * 根据类别ID获取内容列表
	 * @param cid
	 * @return
	 */
	@RequestMapping("/{itemId}")
	public Object a(@PathVariable Long itemId) throws Exception {
		String key = "shop:item:"+itemId;
		String itemText = redis.opsForValue().get(key);
		
		if(StringUtils.isEmpty(itemText)) {
			RLock rlock = redisson.getLock("item:hello");
			try {
				rlock.lock();
				itemText = redis.opsForValue().get(key);
				if(StringUtils.isEmpty(itemText)) {
					TbItem item = itemMapper.selectByPrimaryKey(itemId);
					if(item==null) {
						return Result.of(0,"此商品不存在");
					}
					String json = JsonUtils.toJson(item);
					redis.opsForValue().set(key, json,30,TimeUnit.MINUTES);
					return json;
				}
			} catch (Exception e) {
				rlock.unlock();
			}
			
		}
		
		System.out.println("缓存命中："+key);
		return itemText;	
	}
	
	@RequestMapping("/desc/{itemId}")
	public Object desc(@PathVariable Long itemId) throws Exception {
		String key = "shop:item:desc:"+itemId;
		String itemText = redis.opsForValue().get(key);
		
		if(StringUtils.isEmpty(itemText)) {
			RLock rlock = redisson.getLock("zcwng.shop:lock:item:desc");
			try {
				rlock.lock();
				itemText = redis.opsForValue().get(key);
				if(StringUtils.isEmpty(itemText)) {
					TbItemDesc desc = itemDescMapper.selectByPrimaryKey(itemId);
					if(desc==null) {
						return Result.of(0,"此商品描述不存在");
					}
					String json = JsonUtils.toJson(desc);
					redis.opsForValue().set(key, json,30,TimeUnit.MINUTES);
					return json;
				}
			} catch (Exception e) {
				rlock.unlock();
			}
			
		}
		
		System.out.println("缓存命中："+key);
		return itemText;	
	}
	
	@RequestMapping("/param/{itemId}")
	public Object param(@PathVariable Long itemId) throws Exception {
		String key = "shop:item:param:"+itemId;
		String text = redis.opsForValue().get(key);
		
		if(StringUtils.isEmpty(text)) {
			RLock rlock = redisson.getLock("zcwng.shop:lock:item:param");
			try {
				rlock.lock();
				text = redis.opsForValue().get(key);
				if(StringUtils.isEmpty(text)) {
					TbItemParamItemExample example = new TbItemParamItemExample();
					example.createCriteria().andItemIdEqualTo(itemId);
					List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
					if(list==null||list.size()==0) {
						return Result.of(0,"此商品规格不存在");
					}
					TbItemParamItem param = list.get(0);
					String json = JsonUtils.toJson(param);
					redis.opsForValue().set(key, json,30,TimeUnit.MINUTES);
					System.out.println(json);
					return json; 
				}
			} catch (Exception e) {
				rlock.unlock();
			}
			
		}
		System.out.println("缓存命中："+key);
		return text;	
	}
	
	@RequestMapping("/hello")
	public Object hello() throws InterruptedException {
		System.out.println("1");
		RLock rlock = redisson.getLock("item:hello");
		
		try {
			rlock.lock(10,TimeUnit.SECONDS);
			System.out.println("2");
			Thread.sleep(3000);
			System.out.println("3");
		}finally {
			rlock.unlock();
		}
		return Result.of(1,"SUCCESS");
	}
}
