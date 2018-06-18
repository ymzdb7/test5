package com.winhands.modules.market.service;

import java.util.List;
import java.util.Map;

import com.winhands.common.utils.R;
import com.winhands.modules.market.entity.GoodsEntity;
import com.winhands.modules.market.entity.OrderEntity;
import com.winhands.modules.sys.entity.SysUserEntity;

/**
 *订单管理
 * 
 */
public interface OrderService {
	
	List<OrderEntity> queryList(Map<String, Object> map);
	
	List<OrderEntity> querySelList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	int querySelTotal(Map<String, Object> map);
	OrderEntity queryObjectW(Object id);
	OrderEntity queryObjectMa(Map<String,Object> map);
	
	Map<String, Object> getObject(int id);

	
	/**
	 * 删除订单 
	 */
	void delete(Long id);
	
	
	/**
	 * 保存订单
	 */
	void save(OrderEntity orders);
	
	//用事务
	int saveOo(Long[] ids,Long[] counts,String[] goodsPrice,SysUserEntity user,String uuid,int id);
	/**
	 * 修改订单	 */
	void update(OrderEntity orders);
	
	/**
	 * 删除订单 
	 */
	void deleteBatch(Long[] ids);
	

	
	/**
	 * 查询订单
	 */
	OrderEntity queryObject(Long id);
	
}
