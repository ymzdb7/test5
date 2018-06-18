package com.winhands.modules.market.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.winhands.modules.market.entity.GoodsEntity;
import com.winhands.modules.market.entity.OrderGoodsEntity;
import com.winhands.modules.market.entity.OrderGoodsHeEntity;
import com.winhands.modules.sys.dao.BaseDao;

/**
 * 系统用户
 */
@Mapper
public interface OrderGoodsDao extends BaseDao<OrderGoodsEntity> {
	List<OrderGoodsEntity> querySelList(Map<String, Object> map);
	List<OrderGoodsEntity> queryList(Map<String, Object> map);
	List<GoodsEntity> queryListG(Map<String, Object> map);
	List<OrderGoodsHeEntity> queryListO(Map<String, Object> map);
/*	List<GoodsEntity> queryDishList(Long id);*/
	int querySelTotal(Map<String, Object> map);
}
