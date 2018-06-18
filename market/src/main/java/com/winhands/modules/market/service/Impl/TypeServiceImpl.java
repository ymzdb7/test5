package com.winhands.modules.market.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winhands.modules.market.dao.GoodsDao;
import com.winhands.modules.market.dao.TypeDao;
import com.winhands.modules.market.entity.GoodsEntity;
import com.winhands.modules.market.entity.TypeEntity;
import com.winhands.modules.market.service.GoodsService;
import com.winhands.modules.market.service.TypeService;

/**
 * 商品类型管理
 */
@Service("TypeService")
public class TypeServiceImpl implements TypeService {
	@Autowired
	private TypeDao typeDao;

	@Override
	public List<TypeEntity> queryList(Map<String, Object> map) {
		return typeDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return typeDao.queryTotal();
	}

	@Override
	public void save(TypeEntity type) {
		typeDao.save(type);
	}

	@Override
	public void update(TypeEntity type) {
		typeDao.update(type);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		typeDao.deleteBatch(ids);
	}

	@Override
	public TypeEntity queryObject(Long id) {
		return typeDao.queryObject(id);
	}

	@Override
	public List<TypeEntity> querySelList(Map<String, Object> map) {
		return typeDao.querySelList(map);
	}

	@Override
	public int querySelTotal(Map<String, Object> map) {
		return typeDao.querySelTotal(map);
	}

	@Override
	public TypeEntity queryObject1(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return typeDao.queryObject1(map);
	}

	@Override
	public List<TypeEntity> queryListYes(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return typeDao.queryListYes(map);
	}

	
}
