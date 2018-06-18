package com.winhands.modules.market.service;

import java.util.List;
import java.util.Map;

import com.winhands.modules.market.entity.GoodsEntity;
import com.winhands.modules.market.entity.TypeEntity;

/**
 * 商品类型管理
 * 
 */
public interface TypeService {
	
	List<TypeEntity> queryList(Map<String, Object> map);
	//用于查询有商品的商品类型
	List<TypeEntity> queryListYes(Map<String, Object> map);
	
	List<TypeEntity> querySelList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	int querySelTotal(Map<String, Object> map);
	TypeEntity queryObject1(Map<String,Object> map);
	
	/**
	 * 保存商品类型 
	 */
	void save(TypeEntity type);
	
	/**
	 * 修改商品类型	 */
	void update(TypeEntity type);
	
	/**
	 * 删除商品类型
	 */
	void deleteBatch(Long[] ids);
	
	/**
	 * 查询商品类型
	 */
	TypeEntity queryObject(Long id);
}
