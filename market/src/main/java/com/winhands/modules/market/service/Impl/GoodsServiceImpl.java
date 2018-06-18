package com.winhands.modules.market.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winhands.modules.market.dao.GoodsDao;
import com.winhands.modules.market.entity.GoodsEntity;
import com.winhands.modules.market.service.GoodsService;

/**
 * 商品管理
 */
@Service("GoodsService")
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsDao goodsDao;

	@Override
	public List<GoodsEntity> queryList(Map<String, Object> map) {
		return goodsDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return goodsDao.queryTotal(map);
	}

	@Override
	public void save(GoodsEntity goods) {
		goodsDao.save(goods);
	}

	@Override
	public void update(GoodsEntity goods) {
		goodsDao.update(goods);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		goodsDao.deleteBatch(ids);
	}

	@Override
	public GoodsEntity queryObject(Long id) {
		return goodsDao.queryObject(id);
	}

	@Override
	public List<GoodsEntity> querySelList(Map<String, Object> map) {
		return goodsDao.querySelList(map);
	}

	@Override
	public int querySelTotal(Map<String, Object> map) {
		return goodsDao.querySelTotal(map);
	}

	@Override
	public void updateO(GoodsEntity goods) {
		goodsDao.updateO(goods);
	}

	@Override
	public void updateBatch(Object[] ids) {
		// TODO Auto-generated method stub
		goodsDao.updateBatch(ids);
	}
}
