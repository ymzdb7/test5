package com.winhands.modules.market.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.winhands.modules.market.entity.GoodsEntity;
import com.winhands.modules.market.entity.TypeEntity;
import com.winhands.modules.sys.dao.BaseDao;

/**
 * 系统用户
 */
@Mapper
public interface TypeDao extends BaseDao<TypeEntity> {
	List<TypeEntity> querySelList(Map<String, Object> map);
	List<TypeEntity> queryList(Map<String, Object> map);
	List<TypeEntity> queryListYes(Map<String, Object> map);
/*	List<GoodsEntity> queryDishList(Long id);*/
	int querySelTotal(Map<String, Object> map);
	//根据商品类型名查TypeEntity
	TypeEntity queryObject1(Map<String,Object> map);
	
}
