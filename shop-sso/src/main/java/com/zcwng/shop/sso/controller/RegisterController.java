package com.zcwng.shop.sso.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itshidu.web.tools.MD5Util;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.sso.pojo.TbUser;
import com.zcwng.shop.sso.service.RegisterService;

@Controller
@RequestMapping("/user")
public class RegisterController {

	@Autowired StringRedisTemplate redis;
	@Autowired RegisterService registerService;
	
	@ResponseBody
	@RequestMapping("/check/{param}/{type}")	//type:1用户名；2手机号
	public Object check(@PathVariable String param,@PathVariable int type,String callback) throws Exception{
		Result result = null;
		result = registerService.check(param,type);
		if(!StringUtils.isEmpty(callback)) {
			MappingJacksonValue mjv = new MappingJacksonValue(result);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/register")
	public Object register(TbUser user) throws Exception{		
		return registerService.register(user);
	}
}
