package com.zcwng.shop.search.service;

import com.itshidu.web.tools.Result;

public interface SearchService {

	Result query(String kw, int page);

}
