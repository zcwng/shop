package com.zcwng.shop.portal.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.common.Paging;
import com.zcwng.shop.portal.vo.ItemInfo;

public interface SearchService {

	Paging<ItemInfo> search(String q,int page)throws JsonProcessingException, IOException;

}
