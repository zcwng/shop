package com.zcwng.shop.shopmanagerweb.service;

import com.zcwng.shop.manager.pojo.TbContent;

public interface ContentService {

	Object queryList(Long categoryId, int page, int rows);

	Object save(TbContent content);

}
