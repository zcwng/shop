package com.zcwng.shop.shopmanagerweb.service;

import com.itshidu.web.tools.Result;

public interface ContentCategoryService {

	/**
	 * 
	 */
	Result getContentCategoryList(long parentId);

	Object create(Long parentId, String name);
}
