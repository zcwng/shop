package com.zcwng.shop.sso.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itshidu.web.tools.MD5Util;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.sso.mapper.TbUserMapper;
import com.zcwng.shop.sso.pojo.TbUser;
import com.zcwng.shop.sso.pojo.TbUserExample;
import com.zcwng.shop.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired TbUserMapper userMapper;
	
	@Override
	public Result check(String param, int type) {
		TbUserExample example = new TbUserExample();
		switch (type) {
		case 1:	//检测用户名是否可用
			example.createCriteria().andUsernameEqualTo(param);
			userMapper.selectByExample(example);
			if(userMapper.countByExample(example)==0) {
				return Result.of(1,"用户名可用");
			}
			return Result.of(2,"用户名不可用，已经存在");
			
		case 2:	//检测手机号是否可用
			example.createCriteria().andPhoneEqualTo(param);
			userMapper.selectByExample(example);
			if(userMapper.countByExample(example)==0) {
				return Result.of(1,"手机号可用");
			}
			return Result.of(2,"Err.手机号不可用，已被注册");
		}
		return null;
	}

	@Override
	public Result register(TbUser user) {
		Date date = new Date();
		user.setPassword(MD5Util.md5(user.getPassword()));
		user.setCreated(date);
		user.setUpdated(date);
		userMapper.insertSelective(user);
		return Result.of(200,"注册成功");
	}

}
