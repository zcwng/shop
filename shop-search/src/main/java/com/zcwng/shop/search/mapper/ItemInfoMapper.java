package com.zcwng.shop.search.mapper;

import java.util.List;

import com.zcwng.shop.search.entity.ItemInfo;

public interface ItemInfoMapper {

	List<ItemInfo> selectforsolr();
}
