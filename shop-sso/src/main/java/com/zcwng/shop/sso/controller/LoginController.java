package com.zcwng.shop.sso.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itshidu.web.tools.Result;
import com.zcwng.shop.sso.service.LoginService;

@Controller
@RequestMapping("/user")
public class LoginController {
	
	@Autowired LoginService loginService;

	@ResponseBody
	@RequestMapping("/login")
	public Object login(String username,String password) {
		return loginService.login(username,password);
	}
	
	@ResponseBody
	@RequestMapping("/info/{token}")
	public Object info(@PathVariable String token,String callback) {
		Result result = null;
		try {
			result = loginService.info(token);
		} catch (IOException e) {			
			//e.printStackTrace();
			return Result.of(5,"发生IO异常");
		}
		
		if(!StringUtils.isEmpty(callback)) {
			MappingJacksonValue mjv = new MappingJacksonValue(result);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
		return result;
	}
	
	
}
