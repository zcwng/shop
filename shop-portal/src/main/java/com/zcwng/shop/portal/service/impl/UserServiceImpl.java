package com.zcwng.shop.portal.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.common.HttpClientUtil;
import com.zcwng.shop.common.JsonUtils;
import com.zcwng.shop.portal.service.UserService;
import com.zcwng.shop.portal.vo.TbUser;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public String getLoginURL() {		
		return "http://localhost:8082/login.html";
	}

	@Override
	public TbUser getUserByToken(String token) {
		String json = HttpClientUtil.doGet("http://localhost:8085/user/info/"+token);
		try {
			ObjectMapper m = new ObjectMapper();
			JsonNode root = m.readTree(json);
			String userText = root.get("data").toString();	
			TbUser user = m.readValue(userText,TbUser.class);
			return user;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



}
