package com.zcwng.shop.shopmanagerweb.service;

import com.itshidu.web.tools.Result;
import com.zcwng.shop.manager.pojo.TbItem;

public interface ItemService {

	Result list(int page,int rows);

	Result delete(String ids);

	/** 商品下架，多个ids用逗号分隔*/
	Result instock(String ids);

	/** 商品上架，多个ids用逗号分隔*/
	Result reshelf(String ids);

	/** 通过类别ID子类别 */
	Object categoryList(long parent);

	Object save(TbItem item,String desc,String itemParams);
	
	/** 根据类别ID查询规格属性 */
	Object itemParemQuery(Long itemCatId);

	Object paramSave(Long itemCatId,String paramData);

	Object paramlist(int page, int rows);
}
