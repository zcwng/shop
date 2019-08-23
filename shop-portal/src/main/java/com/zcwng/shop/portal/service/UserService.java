package com.zcwng.shop.portal.service;

import com.zcwng.shop.portal.vo.TbUser;

public interface UserService {

	String getLoginURL();

	TbUser getUserByToken(String token);



}
