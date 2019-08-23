package com.zcwng.shop.sso.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itshidu.web.tools.MD5Util;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.common.JsonUtils;
import com.zcwng.shop.sso.mapper.TbUserMapper;
import com.zcwng.shop.sso.pojo.TbUser;
import com.zcwng.shop.sso.pojo.TbUserExample;
import com.zcwng.shop.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired TbUserMapper userMapper;
	@Autowired StringRedisTemplate redis;
	
	@Override
	public Result login(String username, String password) {
		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if(list==null||list.isEmpty()) {
			return Result.of(1,"用户名不存在");
		}
		TbUser user = list.get(0);
		password = MD5Util.md5(password);
		if(!user.getPassword().equals(password)) {
			return Result.of(2,"密码不正确");
		}
		//再往下就是登陆成功了
		String token = UUID.randomUUID().toString();
		
		try {
			String key = "TOKEN:"+token;
			redis.opsForValue().set(key, JsonUtils.toJson(user));
			redis.expire(key, 30, TimeUnit.MINUTES);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			return Result.of(4,"存储登录信息是异常");
		}
		return Result.of(200,"登陆成功").put("token",token);
	}

	@Override
	public Result info(String token) throws IOException {
		String key="TOKEN:"+token;
		String json = redis.opsForValue().get(key);
		if(StringUtils.isEmpty(json)) {
			return Result.of(0,"token信息不存在，用户已下线");
		}
		TbUser user = JsonUtils.jsonToObject(json, TbUser.class);
		return Result.of(1,"查询成功").put("data", user);
	}

}
