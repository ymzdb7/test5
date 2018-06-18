package com.winhands.modules.market.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.winhands.modules.market.entity.CalculateEntity;
import com.winhands.modules.sys.dao.BaseDao;
/**
 * 系统用户
 */
@Mapper
public interface CalculateDao extends BaseDao<CalculateEntity> {
	List<CalculateEntity> querySelList(Map<String, Object> map);
	List<CalculateEntity> queryList(Map<String, Object> map);
/*	List<GoodsEntity> queryDishList(Long id);*/
	int querySelTotal(Map<String, Object> map);
}
