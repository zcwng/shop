package com.zcwng.shop.sso.service;

import com.itshidu.web.tools.Result;
import com.zcwng.shop.sso.pojo.TbUser;

public interface RegisterService {

	Result check(String param, int type);

	Result register(TbUser user);


}
