package com.winhands.modules.market.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.winhands.modules.market.entity.OrderEntity;
import com.winhands.modules.sys.dao.BaseDao;

/**
 * 系统用户
 */
@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {
	
	List<OrderEntity> querySelList(Map<String, Object> map);
	
	List<OrderEntity> queryList(Map<String, Object> map);
	
/*	List<GoodsEntity> queryDishList(Long id);*/
	
	int querySelTotal(Map<String, Object> map);
	
	Map<String,Object> getObject(int id);
	
	OrderEntity queryObjectW(Object id);
	
	OrderEntity queryObjectMa(Map<String,Object> map);
	
	//根据订单号修改订单状态
	void updateState(OrderEntity entity);
	
	void updateOpenId(@Param("id")long orderId,@Param("openId")String openId);

}
