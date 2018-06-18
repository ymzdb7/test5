package com.winhands.modules.market.service;
import java.util.List;
import java.util.Map;

import com.winhands.modules.market.entity.GoodsEntity;
import com.winhands.modules.market.entity.OrderGoodsEntity;
import com.winhands.modules.market.entity.OrderGoodsHeEntity;
/**
 * 订单商品关联信息
 * 
 */
public interface OrderGoodsService {
	
	List<OrderGoodsEntity> queryList(Map<String, Object> map);
	
	List<OrderGoodsEntity> querySelList(Map<String, Object> map);
	List<OrderGoodsHeEntity> queryListO(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	int querySelTotal(Map<String, Object> map);
	//关联查询到GoodsEntity
	List<GoodsEntity> queryListG(Map<String, Object> map);
	
	/**
	 * 保存关联信息
	 */
	void save(OrderGoodsEntity orderGoods);
	
	/**
	 * 修改关联信息	
	 *  */
	void update(OrderGoodsEntity orderGoods);
	
	/**
	 * 删除关联信息
	 */
	void deleteBatch(Long[] ids);
	
	/**
	 * 查询关联信息
	 */
	OrderGoodsEntity queryObject(Long id);
	
	
	/**
	 * 删除订单 
	 */
	void delete(Long id);
}
