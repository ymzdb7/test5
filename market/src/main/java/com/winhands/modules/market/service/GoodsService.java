package com.winhands.modules.market.service;

import java.util.List;
import java.util.Map;

import com.winhands.modules.market.entity.GoodsEntity;

/**
 * 订单管理
 * 
 */
public interface GoodsService {
	
	List<GoodsEntity> queryList(Map<String, Object> map);
	
	List<GoodsEntity> querySelList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	int querySelTotal(Map<String, Object> map);
	void updateBatch(Object[] ids);
	/**
	 * 保存商品
	 */
	void save(GoodsEntity goods);
	
	/**
	 * 修改商品	 */
	void update(GoodsEntity goods);
	void updateO(GoodsEntity goods);
	
	/**
	 * 删除商品
	 */
	void deleteBatch(Long[] ids);
	
	/**
	 * 查询商品
	 */
	GoodsEntity queryObject(Long id);
}
