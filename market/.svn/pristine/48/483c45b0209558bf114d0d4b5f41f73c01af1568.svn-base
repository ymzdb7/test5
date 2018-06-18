package com.winhands.modules.market.service;

import java.util.List;
import java.util.Map;

import com.winhands.modules.market.entity.CalculateEntity;
/**
 * 计量单位管理
 * 
 */
public interface CalculateService {
	
	List<CalculateEntity> queryList(Map<String, Object> map);
	
	List<CalculateEntity> querySelList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	int querySelTotal(Map<String, Object> map);
	
	/**
	 * 保存商品类型 
	 */
	void save(CalculateEntity calculate);
	
	/**
	 * 修改商品类型	 */
	void update(CalculateEntity calculate);
	
	/**
	 * 删除商品类型
	 */
	void deleteBatch(Long[] ids);
	
	/**
	 * 查询商品类型
	 */
	CalculateEntity queryObject(Long id);
}
