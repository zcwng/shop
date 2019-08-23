package com.zcwng.shop.sso.service;

import java.io.IOException;

import com.itshidu.web.tools.Result;

public interface LoginService {

	Result login(String username, String password);

	Result info(String token)  throws IOException;

}
